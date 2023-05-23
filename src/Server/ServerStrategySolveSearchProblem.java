package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.Solution;
import test.RunSearchOnMaze;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            Maze maze = (Maze) fromClient.readObject();
            Solution news = ASearchingAlgorithm;
            news.getSolutionPath();



        }
