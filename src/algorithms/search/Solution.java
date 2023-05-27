package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Solution implements Serializable
{
    protected ArrayList<AState> states;
    protected ArrayList<AState> reversed;

    public Solution()
    {
        this.states = new ArrayList<>();
        this.reversed = new ArrayList<>();
    }

    public ArrayList<AState> getSolutionPath()
    {
        return this.reversed;
    }
}
