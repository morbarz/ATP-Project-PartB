
package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private Map<Maze, Solution> solutions;

    public void ServerStrategyGenerateMaze() {
        solutions = new HashMap<>();
    }

    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            Solution solution;

            Maze maze = (Maze) fromClient.readObject();
            if (!solutions.containsKey(maze)) {
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                BestFirstSearch bfs = new BestFirstSearch();
                solution = bfs.solve(searchableMaze);
                solutions.put(maze, solution);
                toClient.writeObject(solution);
            } else {
                solution = solutions.get(maze);
                toClient.writeObject(solution);
            }
            toClient.flush();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}