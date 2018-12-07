package main.stability;

import main.GameState;
import org.junit.Test;

import static org.junit.Assert.*;

public class StabilityCalculatorTest {
    @Test
    public void count(){
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i) for(int j = 0; j < 8; ++j) grid[i][j] = 0;
        GameState state = new GameState(1);
        state.setStatesArray(grid);

        StabilityCalculator test = new StabilityCalculator();

        // Test 1: Nothing controlled.
        assertTrue(test.countStable(state,1) == 0);

        // Test 2: Corners
        state.setValue(1,0,0);
        state.setValue(1,7,7);
        state.setValue(1,0,7);
        state.setValue(1,7,0);
        assertTrue(test.countStable(state,1) == 4);

        // Test 3: Adjacency
        state.setValue(1,0,1);
        state.setValue(1,1,0);
        state.setValue(1,0,6);
        state.setValue(1,1,7);
        state.setValue(1,6,7);
        state.setValue(1,7,6);
        state.setValue(1,7,1);
        state.setValue(1,6,0);
        assertTrue(test.countStable(state,1) == 12);

        // Test 4: Corners
        state.setValue(1,1,1);
        state.setValue(1,6,6);
        state.setValue(1,1,6);
        state.setValue(1,6,1);
        assertTrue(test.countStable(state,1) == 12);

        // Test 5: Improved corners
        state.setValue(1,0,2);
        state.setValue(1,0,5);
        state.setValue(1,5,7);
        state.setValue(1,5,0);
        assertTrue(test.countStable(state,1) == 20);

        // Test 6: Complete row
        state.setValue(1,0,3);
        state.setValue(1,0,4);
        assertTrue(test.countStable(state,1) == 22);
    }

}