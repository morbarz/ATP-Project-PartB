package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int mili;
    private IServerStrategy serverStrategy;
    private ExecutorService pool;
    private boolean stop;

    public Server(int port, int maxClients, IServerStrategy serverStrategy) {
        this.port = port;
        this.mili = maxClients;
        this.serverStrategy = serverStrategy;
        pool = Executors.newFixedThreadPool(maxClients);
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(mili);
        System.out.println("Server Booted");
        System.out.println("Any client can stop the server by sending -1");

        while (!stop) {
            Socket clientSocket = serverSocket.accept();
            pool.execute(() -> handleClient(clientSocket));
        }
        serverSocket.close();
        pool.shutdown();
    }
    public void start(){
        new Thread(() -> {
            try {
                startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
    public void stop(){
        stop = true;
    }



    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    String tempDirectoryPath = System.getProperty("java.io.tmpdir");

}



