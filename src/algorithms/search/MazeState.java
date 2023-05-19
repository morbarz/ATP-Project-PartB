package algorithms.search;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState
{
    protected Position p;
    MazeState(Position position)
    {
        super();
        this.p=position;
        p.setp(this.isVisited());
        this.setState("{" + position.getRowIndex() + ","+ p.getColumnIndex() + "}");
        crossTome = false;
        setDistance(0);
    }
    public void setVisited(boolean visited)
    {
        this.isvisited = visited;
    }
    public boolean isVisited()
    {
        return this.isvisited;
    }
    public void updateVisited()
    {
        if (this.isVisited())
        {
            p.setp(true);
        }
    }


    public Position getPosition() {
        return this.p;
    }
}
