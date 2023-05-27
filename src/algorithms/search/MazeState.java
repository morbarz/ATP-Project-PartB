package algorithms.search;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public class MazeState extends AState  implements Serializable
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
