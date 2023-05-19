package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    private HashMap<String, Boolean> marked;
    private Queue<AState> queue;

    public BreadthFirstSearch() {
        this.queue = new LinkedList<>();
        this.marked = new HashMap<>();
        this.name = "BreadthFirstSearch";
    }

    /// Generic BFS
    public AState search(ISearchable s) {
        AState start = s.getStartState();//the start state
        AState goal = s.getGoalState();//the end state

        queue.add(start); // add start state to queue
        start.setIsvisited();
        start.updateVisited();
        marked.put(start.getState(), true); // mark start state as visited
        visitedNodes++;

        while (!queue.isEmpty() && !goal.isVisited()) {
            AState visitedState = queue.remove(); // remove state with highest priority (i.e., smallest cost)
            ArrayList<AState> neighbors = s.getAllPossibleStates(visitedState); // get all neighboring states
            updateCost(visitedState, neighbors); // update cost of neighboring states

            for (AState curr : neighbors) {
                if (!marked.containsKey(curr.getState())) { // if state has not been visited before
                    marked.put(curr.getState(), true); // mark state as visited
                    curr.setCameFrom(visitedState);
                    queue.add(curr); // add state to queue
                    curr.setIsvisited();
                    visitedNodes++;
                    curr.updateVisited();
                }

                if (curr.getState().compareTo(goal.getState()) == 0) // if goal state is found return state
                    return curr;//
            }
        }

        // goal  not found
        return null;
    }

    // helper method to update cost of neighboring states
    private void updateCost(AState curr, ArrayList<AState> neighbors) {
        for (AState neighbor : neighbors) {
            if (!neighbor.isVisited() || curr.getCost() + 1 < neighbor.getCost()) {
                neighbor.setCost(curr.getCost() + 1);
            }
        }
    }

    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }

    public String getName() {
        return name;
    }



}

