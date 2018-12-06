package main;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by julio on 9/25/18.
 */
public class GameTreeNodeTest {
    @Test
    public void getChildren() throws Exception {

    }

    @Test
    public void getUtility() throws Exception {
    }

    @Test
    public void setUtility() throws Exception {
    }

    @Test
    public void utilityTest2() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 2;
        int[][] grid = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,2,0,0,0,0,0},
                {0,2,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };

        GameState state = new GameState(2);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 10);
        assertTrue(test.numFrontier(state) == 4);
        assertTrue(test.numStable(state) == 0);
        assertTrue(test.numMoves(state) == 6);
        assertTrue(test.numStupid(state) == 1);
        assertTrue(test.getUtility() == -2534);
    }

    @Test
    public void utilityTest1() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 2;
        int[][] grid = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0},
                {0,0,0,1,1,1,0,0},
                {0,0,2,2,2,1,0,0},
                {0,0,2,0,0,2,0,0},
                {0,0,2,0,0,0,2,0},
                {0,0,0,0,0,0,0,0}
        };

        GameState state = new GameState(2);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);
        assertTrue(test.numFrontier(state) == 7);
        assertTrue(test.numStable(state) == 0);
        assertTrue(test.numMoves(state) == 6);
        assertTrue(test.numStupid(state) == 1);
        assertTrue(test.getUtility() == -2564);
    }

    @Test
    public void frontierTest() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        // Test 1: No frontier spaces
        assertTrue(test.numFrontier(state) == 0);

        // Test 2: 1 frontier space
        state.setValue(1,3,3);
        assertTrue(test.numFrontier(state) == 1);

        // Test 3: Add a space above
        state.setValue(1,2,3);
        assertTrue(test.numFrontier(state) == 2);

        // Test 4: Add a space below
        state.setValue(1,4,3);
        assertTrue(test.numFrontier(state) == 3);

        // Test 5: Add a space to the right
        state.setValue(1,3,4);
        assertTrue(test.numFrontier(state) == 4);

        // Test 6: Add a space to the left
        state.setValue(1,3,2);
        assertTrue(test.numFrontier(state) == 4);

        // Test 7: Add a corner
        state.setValue(1,0,0);
        assertTrue(test.numFrontier(state) == 5);

        // Test 8: Add an edge next to the corner
        state.setValue(1,0,1);
        assertTrue(test.numFrontier(state) == 6);

        // Test 9: Add another edge next to the corner
        state.setValue(1,1,0);
        assertTrue(test.numFrontier(state) == 6);


    }

    @Test
    public void stableTest() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        // Test 1: No stable pieces
        assertTrue(test.numStable(state) == 0);

        // Test 2: 1 stable piece, a corner
        state.setValue(1,0,0);
        assertTrue(test.numStable(state) == 1);

        // Test 3: Add a diagonally adjacent space
        state.setValue(1,1,1);
        assertTrue(test.numStable(state) == 1);

        // Test 4: Add a horizontally adjacent space
        state.setValue(1,0,1);
        assertTrue(test.numStable(state) == 2);

        // Test 5: Add a vertically adjacent space
        state.setValue(1,1,0);
        assertTrue(test.numStable(state) == 3);

        // Test 6: Add a separated space
//        System.out.println("**********************************");
        state.setValue(1,3,0);
//        System.out.println(test.numStable(state));
//        System.out.println("**********************************");
        assertTrue(test.numStable(state) == 3);

        // Test 7: Add a whole row
        state.setValue(1,0,2);
        state.setValue(1,0,3);
        state.setValue(1,0,4);
        state.setValue(1,0,5);
        state.setValue(1,0,6);
        state.setValue(1,0,7);
        assertTrue(test.numStable(state) == 10);

        // Test 8: Add another corner
        state.setValue(1,7,7);
        assertTrue(test.numStable(state) == 11);
    }

    @Test
    public void bugTest() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        state.setValue(1,0,0);
        state.setValue(1,0,1);
        state.setValue(1,1,0);
        state.setValue(1,1,1);

        assertTrue(test.spaceIsStable(1,0,0,GameTreeNode.UPLEFT));
        assertTrue(test.spaceIsStable(1,0,1,GameTreeNode.UPLEFT));
        assertTrue(test.spaceIsStable(1,1,0,GameTreeNode.UPLEFT));
        assertTrue(!test.spaceIsStable(1,1,1,GameTreeNode.UPLEFT));

        assertTrue(test.numStable(state) == 3);
    }

    @Test
    public void rowTest() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        state.setValue(1,0,0);
        state.setValue(2,0,1);
        assertTrue(test.numStable(state) == 1);

        state.setValue(1,0,1);
        state.setValue(1,0,2);
        assertTrue(test.numStable(state) == 3);
    }

    @Test
    public void edgeCountTest() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        assertTrue(test.numEdge(state,1) == 0);

        /*
        Check left edge counting
         */
        state.setValue(1,0,0);
        assertTrue(test.numEdge(state,1) == 1);

        state.setValue(1,1,0);
        assertTrue(test.numEdge(state,1) == 2);

        state.setValue(2,0,0);
        assertTrue(test.numEdge(state,1) == 0);

        state.setValue(0,0,0);
        assertTrue(test.numEdge(state,1) == 1);

        state.setValue(2,2,0);
        assertTrue(test.numEdge(state,1) == 0);

        state.setValue(2,7,0);
        assertTrue(test.numEdge(state,1) == 0);

        state.setValue(2,6,0);
        state.setValue(1,7,0);
        assertTrue(test.numEdge(state,1) == 1);

        state.setValue(2,5,0);
        state.setValue(1,6,0);
        state.setValue(0,7,0);
        assertTrue(test.numEdge(state,1) == 0);
    }

    @Test
    public void pinnedEdgeTest(){
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        assertTrue(test.numEdge(state,1) == 0);

        state.setValue(2,0,0);
        state.setValue(1,1,0);
        state.setValue(2,2,0);
        assertTrue(test.numEdge(state,1) == 1);
    }

    @Test
    public void topRowCount(){
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        assertTrue(test.numEdge(state,1) == 0);

        /*
        Check top row counting
         */
        state.setValue(1,0,0);
        assertTrue(test.numEdge(state,1) == 1);

        state.setValue(2,0,1);
        assertTrue(test.numEdge(state,1) == 1);

        state.setValue(2,0,0);
        state.setValue(1,0,1);
        assertTrue(test.numEdge(state,1) == 0);
    }

//    @Test
//    public void bizarreChoicesTest() throws Exception{
//        // This test is testing what happened in a specific game situation
//        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
//        int[][] grid = new int[][]{
//                {1,2,0,1,0,0,0,0},
//                {0,1,2,1,2,0,0,0},
//                {0,0,2,1,1,0,0,0},
//                {0,2,2,2,2,2,2,2},
//                {0,0,0,2,1,2,2,0},
//                {0,0,0,0,2,2,2,0},
//                {0,0,0,0,0,2,0,0},
//                {0,0,0,0,0,0,0,0}
//        };
//
//        GameState state = new GameState(1);
//        state.setStatesArray(grid);
//        GameTreeNode test = new GameTreeNode(state, 5);
//
//
//        GameTree testTree = new GameTree(state,5);
//        IGameTreeNode root = testTree.getRoot();
//        List<IGameTreeNode> children = root.getChildren();
//        System.out.println(children.size());
//        for(IGameTreeNode n : children){
//            n.getState().display();
//        }
//    }

    @Test
    public void stabilityTest() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        state.setValue(1,0,0);
        state.setValue(1,0,1);
        state.setValue(1,1,0);
        state.setValue(1,1,1);

        assertTrue(test.spaceIsStable(1,0,0,GameTreeNode.UPLEFT));
        assertTrue(test.spaceIsStable(1,0,1,GameTreeNode.UPLEFT));
        assertTrue(test.spaceIsStable(1,1,0,GameTreeNode.UPLEFT));
        assertTrue(!test.spaceIsStable(1,1,1,GameTreeNode.UPLEFT));
    }

    @Test
    public void stableRowTest(){
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);
        GameTreeNode test = new GameTreeNode(state, 5);

        state.setValue(1,0,7);
        state.setValue(1,0,6);
        state.setValue(2,0,5);
        state.setValue(2,0,4);
        assertTrue(test.numStable(state) == 2);

        state.setValue(1,0,5);
        state.setValue(1,0,4);
        state.setValue(1,0,3);
        assertTrue(test.numStable(state) == 5);
    }

    @Test
    public void stupidTest() throws Exception{
        GameTreeNode.UTILITY_PLAYER_NUMBER = 1;
        int[][] grid = new int[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j)grid[i][j] = 0;

        GameState state = new GameState(1);
        state.setStatesArray(grid);

        // Test 1: No stupid spaces.
        GameTreeNode test = new GameTreeNode(state, 5);
        assertTrue(test.xspaces(state, 1) == 0);

        // Test 2: 1 X space
        state.setValue(1, 1,1);
        assertTrue(test.xspaces(state, 1) == 1);

        // Test 3: 0 X spaces, because the corner is taken
        state.setValue(1,0,0);
        assertTrue(test.xspaces(state,1) == 0);

        // Test 4: 4 X spaces
        state.setValue(0,0,0);
        state.setValue(1,1,6);
        state.setValue(1,6,1);
        state.setValue(1,6,6);
        assertTrue(test.xspaces(state,1) == 4);

        // Test 5: 1 C space
        state.setValue(1,0,1);
        assertTrue(test.cspaces(state, 1) == 1);

        // Test 6: 8 C spaces
        state.setValue(1,1,0);
        state.setValue(1,0,6);
        state.setValue(1,1,7);
        state.setValue(1,6,7);
        state.setValue(1,7,6);
        state.setValue(1,7,1);
        state.setValue(1,6,0);
        assertTrue(test.cspaces(state,1) == 8);

        // Test 7: remove 1 X space and 2 C spaces by taking a corner
        state.setValue(1,0,0);
        assertTrue(test.xspaces(state,1) == 3);
        assertTrue(test.cspaces(state,1) == 6);

        // Test 8: count stupid
        assertTrue(test.numStupid(state) == 9);
    }
}