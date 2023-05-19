package algorithms.mazeGenerators;
import java.util.*;

import java.util.Random;
public class SimpleMazeGenerator extends AMazeGenerator {
    private Maze simpleM;

    @Override
    public Maze generate(int row, int col) {
        Maze newMaze = new Maze(row, col);
        Random random = new Random();
        Random randomNUmber = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 && j == 0) {
                    newMaze.maze[i][j] = 0;
                } else {

                    newMaze.maze[i][j] = randomNUmber.nextInt(2);
                }
            }
        }
        if (getRoute(newMaze, row, col) <= 0)
            setRoute(newMaze);
        return newMaze;
    }

    public static boolean canMove(Maze maze, int i, int j) {
        boolean bool = (0 <= i && i <= maze.goalPosition.getRowIndex()) && (0 <= j && j <= maze.goalPosition.getColumnIndex());
        return bool;
    }
    public static void setRoute(Maze m) {
        int i = m.startPosition.getRowIndex();
        int j = m.startPosition.getColumnIndex();
        while (i != m.goalPosition.getRowIndex() || j != m.goalPosition.getColumnIndex()) {
            Random ran = new Random();
            int index = ran.nextInt(2);
            if (index == 1 && canMove(m, i + 1, j)) {
                m.maze[i + 1][j] = 0;
                i++;
            } else {
                if (canMove(m, i, j + 1)) {
                    m.maze[i][j + 1] = 0;
                    j++;
                }
            }
        }
    }

    public static int getRoute(Maze simpleMaze, int row, int col) {
        Maze solGrid = new Maze(row, col);
        for (int i = 0; i < row; i++) {
            if (simpleMaze.maze[i][0] == 0)
                solGrid.maze[i][0] = 1;
            else break;
        }
        for (int i = 1; i < col; i++) {
            if (simpleMaze.maze[0][i] == 0)
                solGrid.maze[0][i] = 1;
            else break;
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (simpleMaze.maze[i][j] == 1)
                    continue;
                if (solGrid.maze[i - 1][j] > 0)
                    solGrid.maze[i][j] = (solGrid.maze[i][j] + solGrid.maze[i - 1][j]);
                if (solGrid.maze[i][j - 1] > 0)
                    solGrid.maze[i][j] = (solGrid.maze[i][j] + solGrid.maze[i][j - 1]);
            }
        }
        if (solGrid.maze[row - 1][col - 1] > 0)
            return solGrid.maze[row - 1][col - 1];
        else
            return -1;
    }
}









