package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import test.RunSearchOnMaze;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    HashMap<Maze,Solution> solutions;
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            Maze maze = (Maze) fromClient.readObject();
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            BestFirstSearch bfs = new BestFirstSearch();
            Solution solution = bfs.solve(searchableMaze);
            solutions.put(maze,solution);
            toClient.writeObject(solution);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}