package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

public interface IServerStrategy {
    void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException;

}
