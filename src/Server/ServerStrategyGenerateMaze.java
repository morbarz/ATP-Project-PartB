package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            int[] al = new int[2];
            al = (int[]) fromClient.readObject(); // read int[number of rows , number of columns ]
            //toClient.writeObject(al);
            //toClient.flush();
            //romClient.close();
            //toClient.close();
            // catch (Exception e) {
            //  e.printStackTrace();
            //}
            String mazeFileName = "savedMaze.maze";
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(al[1], al[2]); //Generate new maze
            try {
// save maze to a file
                OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
                out.write(maze.toByteArray())
                ;
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}