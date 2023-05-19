package algorithms.mazeGenerators;

public interface IMazeGenerator {
        //extends MazeGenerator  {
    public Maze generate(int row ,int col );
    //generate method , return maze appereance
    public long measureAlgorithmTimeMillis(int row ,int col );
}
