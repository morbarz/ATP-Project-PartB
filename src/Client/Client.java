package Client;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private int port;
    private InetAddress ip;
    private IClientStrategy clientStrategy;

    public Client(InetAddress ip, int port, IClientStrategy clientStrategy) {
        this.ip = ip;
        this.port = port;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer() {
        try {

            Socket socket = new Socket(ip, port); //Create a socket using ip,port parameters

            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            clientStrategy.clientStrategy(fromServer, toServer); // Call the client strategy to perform communication

            //String s = fromServer.readLine();
            //System.out.println("Server: " + s);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}