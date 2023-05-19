package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{
    @Override
    public long measureAlgorithmTimeMillis(int row, int col) { // override method .
        long startTime = System.currentTimeMillis();
        generate(row,col);//try this method
        long endTime =System.currentTimeMillis();
        long totalTime = endTime-startTime;
        return totalTime;
    }
}
