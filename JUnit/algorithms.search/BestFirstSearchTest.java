package algorithms.search;

import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    BestFirstSearch BFS = new BestFirstSearch();

    @Test
    void testName() {
        assertEquals("BestFirstSearch", BFS.getName());
    }


    @Test
    void getNumberOfNodesEvaluated() {
        BestFirstSearch best = new BestFirstSearch();
        assertEquals(0, best.getNumberOfNodesEvaluated());
    }

    @Test
    void inputIsNull() {
        boolean isNull = false;
        try {
            BFS.solve(null);
        } catch (Exception e) {
            isNull = true;
        }
        assertTrue(isNull);


    }

    @Test
    public void TestSearch3x3() throws Exception {
        EmptyMazeGenerator emg = new EmptyMazeGenerator();

        BestFirstSearch best = new BestFirstSearch();

        Maze m1 = emg.generate(3, 3);

        SearchableMaze normalMaze = new SearchableMaze(m1);

        Solution s1 = best.solve(normalMaze);

        // Checking Best search grabs only the states that are in cross.
        ArrayList<AState> sol1 = s1.getSolutionPath();

        AState curr1 = sol1.get(0);
        AState curr2 = sol1.get(1);
        AState curr3 = sol1.get(2);

        assertEquals(curr1.getState(), "{0,0}");
        assertEquals(curr2.getState(), "{1,1}");
        assertEquals(curr3.getState(), "{2,2}");

    }

    @Test
    void search() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(5, 5);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        AState goal = best.search(searchableMaze);
        assertNotEquals(null, goal.getCameFrom());
        assertEquals(4, ((MazeState) goal).getPosition().getColumnIndex());
        assertEquals(4, ((MazeState) goal).getPosition().getRowIndex());

    }
}