package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Server.IServerStrategy;
import algorithms.search.Solution;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private boolean stop;
    private ExecutorService ThreadPool;

    //Server builder
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        ThreadPool = Executors.newFixedThreadPool(Integer.parseInt(Configurations.createInstance().readConfig().getProperty("threadPoolSize")));
    }

    public void startME(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket.toString());

                    // This thread will handle the new Client
                    ThreadPool.execute(()->handleClient(clientSocket));

                } catch (SocketTimeoutException e){
                }
            }
            serverSocket.close();
            ThreadPool.shutdownNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        new Thread(() -> startME()).start();

    }


    private void handleClient(Socket clientSocket) {
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            System.out.println("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stop(){
        System.out.println("Stopping server...");
        stop = true;
    }
}
