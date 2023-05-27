package Client;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

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
            System.out.println("connected to server - IP = " + ip + ", Port = " + port);
            clientStrategy.clientStrategy(socket.getInputStream(), socket.getOutputStream()); // Call the client strategy to perform communication
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}