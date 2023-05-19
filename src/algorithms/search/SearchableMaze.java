package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public class SearchableMaze implements ISearchable
{
    private Maze maze;
    public SearchableMaze(Maze m)
    {
        this.maze = m;
    }

    public AState getStartState()
    {
        return new MazeState(maze.startPosition);
    }
    public AState getGoalState()
    {
        return new MazeState(maze.goalPosition);
    }

    public Maze getMaze()
    {
        return maze;
    }
    public void setMaze(Maze maze)
    {
        this.maze = maze;
    }

    public ArrayList<AState> getAllPossibleStates(AState s)
    {
        ArrayList<AState> neighbors = new ArrayList<>();
        int row = ((MazeState)s).p.getRowIndex();
        int col = ((MazeState)s).p.getColumnIndex();
        if (inBounds(row + 1, col, maze))
        {
            Position p1 = maze.PositionArray[row + 1][col];
            if (maze.maze[row+1][col] == 0)
            {
                MazeState m = new MazeState(p1);
                neighbors.add(m);
            }
        }
        if (inBounds(row, col + 1, maze))
        {
            Position p2 = maze.PositionArray[row][col + 1];
            if (maze.maze[row][col+1] == 0)
            {
                MazeState m = new MazeState(p2);
                neighbors.add(m);
            }
        }
        if (inBounds(row - 1, col, maze))
        {
            Position p3 = maze.PositionArray[row - 1][col];
            if (maze.maze[row-1][col] == 0)
            {
                MazeState m = new MazeState(p3);
                neighbors.add(m);
            }
        }
        if (inBounds(row, col - 1, maze))
        {
            Position p4 = maze.PositionArray[row][col-1];
            if (maze.maze[row][col-1] == 0)
            {
                MazeState m = new MazeState(p4);
                neighbors.add(m);
            }
        }
        if (inBounds(row + 1, col - 1, maze))
        {
            Position p5 = maze.PositionArray[row+1][col-1];
            if (maze.maze[row+1][col-1] == 0)
            {
                MazeState m = new MazeState(p5);
                m.crossTome = true;
                neighbors.add(m);
            }
        }
        if (inBounds(row + 1, col + 1, maze))
        {
            Position p6 = maze.PositionArray[row+1][col+1];
            if (maze.maze[row+1][col+1] == 0)
            {
                MazeState m = new MazeState(p6);
                m.crossTome = true;
                neighbors.add(m);
            }
        }
        if (inBounds(row - 1, col + 1, maze))
        {
            Position p7 = maze.PositionArray[row-1][col+1];
            if (maze.maze[row-1][col+1] == 0)
            {
                MazeState m = new MazeState(p7);
                m.crossTome = true;
                neighbors.add(m);
            }
        }
        if (inBounds(row - 1, col - 1, maze))
        {
            Position p8 = maze.PositionArray[row-1][col-1];
            if (maze.maze[row-1][col-1] == 0)
            {
                MazeState m = new MazeState(p8);
                m.crossTome = true;
                neighbors.add(m);
            }
        }
        return neighbors;
    }

    public static boolean inBounds(int row, int col, Maze m)
    {
        return (0 <= row && row <= m.goalPosition.getRowIndex() && 0 <= col && col <= m.goalPosition.getColumnIndex());
    }


}