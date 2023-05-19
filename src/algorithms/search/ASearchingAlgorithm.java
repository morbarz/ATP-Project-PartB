package algorithms.search;

import java.util.PriorityQueue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{
    protected PriorityQueue<AState> openList;
    protected int visitedNodes;
    protected String name;

    public ASearchingAlgorithm()
    {
        openList = new PriorityQueue<>();
        visitedNodes = 0;
    }

    protected AState popOpenList()
    {
        visitedNodes++;
        return openList.poll();
    }

    public Solution solve(ISearchable s)
    {
        Solution sol = new Solution();
        AState state = this.search(s);

        sol.states.add(state);
        if (state == null)
            return sol;
        if (state.getCameFrom() == null)
        {
            return sol;
        }
        AState curr = state.getCameFrom();
        while (curr != null)
        {
            sol.states.add(curr);
            curr = curr.getCameFrom();
        }
        for (int i = sol.states.size()-1; i >= 0; i--)
        {
            sol.reversed.add(sol.states.get(i));
        }
        return sol;
    }
}
