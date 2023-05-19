package algorithms.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {
    private PriorityQueue<AState> pq;
    private HashMap<String, Boolean> marked;

    public BestFirstSearch() {
        this.pq = new PriorityQueue<>(new theComparator());
        this.name = "BestFirstSearch";
        this.marked = new HashMap<>();
    }

    // Override the search method from BreadthFirstSearch
    // Uses a priority queue to prioritize states with lower distance and cost
    // Marks visited states and keeps track of the visited nodes count
    public AState search(ISearchable s) {
        AState start = s.getStartState();
        AState goal = s.getGoalState();
        pq.add(start);
        start.setIsvisited();
        start.updateVisited();
        marked.put(start.getState(), true);
        this.visitedNodes++;
        while (!pq.isEmpty()) {
            AState u = pq.remove();
            ArrayList<AState> neighbors = s.getAllPossibleStates(u);
            updateCameFrom(neighbors, u);
            updateCost(neighbors);
            updateDistance(neighbors);
            int i = 0;
            while (!neighbors.isEmpty())
            {
                AState curr = neighbors.get(i);
                if (!marked.containsKey(curr.getState())) {
                    marked.put(curr.getState(), true);
                    pq.add(curr);
                    curr.setIsvisited();
                    visitedNodes++;
                    curr.updateVisited();
                }
                if (curr.getState().compareTo(goal.getState()) == 0)
                    return curr;

                neighbors.remove(curr);
            }
        }
        return null;
    }
    // Updates the 'cameFrom' field of each node in the given array to the given state
    public void updateCameFrom(ArrayList<AState> array, AState s)
    {
        int i = 0;
        while (i < array.size())
        {
            AState node = array.get(i);
            node.setCameFrom(s);
            i++;
        }
    }

    // Updates the 'cost' field of each node in the given array based on whether it has crossed a time zone or not
    public void updateCost(ArrayList<AState> array)
    {
        int i = 0;
        while (i < array.size())
        {
            AState node = array.get(i);
            if (node.getCameFrom() != null)
            {
                if (node.crossTome)
                    node.setCost(1.5 + node.getCameFrom().getCost());
                else
                    node.setCost(1 + node.getCameFrom().getCost());
            }
            else
            {
                if (node.crossTome)
                    node.setCost(1.5);
                else
                    node.setCost(1);
            }
            i++;
        }
    }

    // Updates the 'distance' field of each node in the given array based on its parent's distance
    public void updateDistance(ArrayList<AState> array)
    {
        int i = 0;
        while (i < array.size())
        {
            AState curr = array.get(i);
            if (curr.getCameFrom() == null)
                curr.setDistance(0);
            else
                curr.setDistance(curr.getCameFrom().getDistance() + 1);
            i++;
        }
    }

    public int getNumberOfNodesEvaluated()
    {
        return visitedNodes;
    }

    public String getName()
    {
        return name;
    }

    //comparison  based on the distance and cost of each state, with states that have smaller distances and costs being considered "greater" than states with larger distances and costs.
    static class theComparator implements Comparator<AState>
    {
        public int compare(AState s1, AState s2)
        {
            if (s1.getDistance() > s2.getDistance())
                return 1;
            if (s1.getDistance() == s2.getDistance())
            {
                if (s1.getCost() > s2.getCost())
                    return 1;
                if (s1.getCost() == s2.getCost())
                    return 0;
            }
            return -1;
        }
    }

}
