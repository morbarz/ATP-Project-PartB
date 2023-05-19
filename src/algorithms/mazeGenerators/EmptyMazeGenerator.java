package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int row, int col) { //create Maze
        Maze newMaze = new Maze(row,col); // default maze is without walls
        return newMaze;
    }



}
