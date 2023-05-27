package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            AMazeGenerator MazeGen;
            switch (Configurations.createInstance().readConfig().getProperty("mazeGeneratingAlgorithm")) {
                case "My" -> MazeGen = new MyMazeGenerator();
                case "Simple" -> MazeGen = new SimpleMazeGenerator();
                case "Empty" -> MazeGen = new EmptyMazeGenerator();
                default -> MazeGen = null;
            }
            //int[] al;
            int[] al; // read int[number of rows , number of columns ]
            al = (int[]) fromClient.readObject();
            assert MazeGen != null;
            Maze maze = MazeGen.generate(al[0], al[1]); //Generate new maze

// save maze to a file
                ByteArrayOutputStream b_out = new ByteArrayOutputStream();
                toClient.writeObject(b_out.toByteArray());
                toClient.flush();
                toClient.close();
                fromClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
