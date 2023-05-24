package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException{
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            int[] al;
            al = (int[]) fromClient.readObject(); // read int[number of rows , number of columns ]

            String mazeFileName = "savedMaze.maze";
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(al[1], al[2]); //Generate new maze
            //byte[] byte_array = maze.toByteArray();
            try {
// save maze to a file
                ByteArrayOutputStream b_out = new ByteArrayOutputStream();
                OutputStream compress = new MyCompressorOutputStream(b_out);
                byte[] Compressed_maze = maze.toByteArray();
                toClient.write(Compressed_maze); // check if it compressed ********
                toClient.writeObject(b_out.toByteArray());
                toClient.flush();
                toClient.close();
                fromClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
}