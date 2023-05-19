package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    private LinkedList<AState> linked;
    private HashMap<String, Boolean> marked;

    public DepthFirstSearch() {
        this.name = "DepthFirstSearch";
        this.linked = new LinkedList<>();
        this.marked = new HashMap<>();
    }
    @Override
    public AState search(ISearchable s) {
        AState start = s.getStartState();
        AState goal = s.getGoalState();
        linked.push(start);
        start.setIsvisited();
        start.updateVisited();
        marked.put(start.getState(),true);
        this.visitedNodes++;
        while (!linked.isEmpty() && !goal.isVisited()){
            AState n = linked.pop();
            ArrayList<AState> nei = s.getAllPossibleStates(n);
            int i =0;
            while (!nei.isEmpty()){
                AState curr=nei.get(i);
                if(!marked.containsKey(curr.getState())){
                    marked.put(curr.getState(), true);
                    curr.setIsvisited();
                    curr.setCameFrom(n);
                    linked.push(curr);
                    curr.updateVisited();
                    visitedNodes++;

                }
                if ( curr.getState().compareTo(goal.getState())==0){
                    return curr;

                }
                nei.remove(curr);
            }

        }
        return null;

    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return this.visitedNodes;
    }

    @Override
    public String getName() {
        return this.name;
    }


    }


