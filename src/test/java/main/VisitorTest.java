package main;

import main.dummy.DummyNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VisitorTest {

    @Test
    public void anotherDumbTest(){
        GameTreeNode.UTILITY_PLAYER_NUMBER = 2;
        int[][] originalgrid = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state = new GameState(2);
        state.setStatesArray(originalgrid);
        GameTree tree = new GameTree(state, 8, 1000);

        // Visit it!
        Visitor visitor = new Visitor();
        GameTreeNode result = (GameTreeNode)visitor.visit(tree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        System.out.println(result.getUtility());
        result.getState().display();
    }

    @Test
    public void dumbTreeTest(){
        GameTreeNode.UTILITY_PLAYER_NUMBER = 2;
        int[][] originalgrid = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state = new GameState(2);
        state.setStatesArray(originalgrid);
        GameTreeNode test = new GameTreeNode(state, 8);
        // Verify initial condition
        assertTrue(test.numStupid(state) == 0);
        assertTrue(test.numStable(state) == 0);
        assertTrue(test.numFrontier(state) == 2);
        assertTrue(test.numMoves(state) == 8);
        assertTrue(test.getUtility() == -12);

        // Build all children nodes
        int[][] grid1 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,2,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state1 = new GameState(2);
        state1.setStatesArray(grid1);
        GameTreeNode child1 = new GameTreeNode(state1,9);
        assertTrue(child1.getUtility() == -23);

        int[][] grid2 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,2,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state2 = new GameState(2);
        state2.setStatesArray(grid2);
        GameTreeNode child2 = new GameTreeNode(state2, 9);
        assertTrue(child2.getUtility() == -23);

        int[][] grid3 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,2,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state3 = new GameState(2);
        state3.setStatesArray(grid3);
        GameTreeNode child3 = new GameTreeNode(state3, 9);
        assertTrue(child3.getUtility() == -21);

        int[][] grid4 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,2,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state4 = new GameState(2);
        state4.setStatesArray(grid4);
        GameTreeNode child4 = new GameTreeNode(state4, 9);
        assertTrue(child4.getUtility() == -23);

        int[][] grid5 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,2,0,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state5 = new GameState(2);
        state5.setStatesArray(grid5);
        GameTreeNode child5 = new GameTreeNode(state5, 9);
        assertTrue(child5.getUtility() == -23);

        int[][] grid6 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {2,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state6 = new GameState(2);
        state6.setStatesArray(grid6);
        GameTreeNode child6 = new GameTreeNode(state6, 9);
        assertTrue(child6.getUtility() == -22);

        int[][] grid7 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,2,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state7 = new GameState(2);
        state7.setStatesArray(grid7);
        GameTreeNode child7 = new GameTreeNode(state7, 9);
        assertTrue(child7.getUtility() == -23);

        int[][] grid8 = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {1,0,1,1,1,0,0,0},
                {0,1,2,2,2,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,2,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        GameState state8 = new GameState(2);
        state8.setStatesArray(grid8);
        GameTreeNode child8 = new GameTreeNode(state8, 9);
        assertTrue(child8.getUtility() == -2523);

        List<IGameTreeNode> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        children.add(child3);
        children.add(child4);
        children.add(child5);
        children.add(child6);
        children.add(child7);
        children.add(child8);
        test.setChildren(children);

        // Use visitor!
        Visitor visitor = new Visitor();
        GameTreeNode result = (GameTreeNode)visitor.visit(test, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        assertTrue(result.getUtility() == -21);
    }

    @Test
    public void seizeCornerTest(){
        GameTreeNode.UTILITY_PLAYER_NUMBER = 2;
        System.out.println("************************************");
        int[][] grid = new int[][]{
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0},
                {0,0,0,1,1,0,0,0},
                {0,0,0,1,1,2,2,0},
                {0,0,0,0,0,2,0,0},
                {0,0,0,0,2,2,1,0},
                {0,0,0,0,0,2,0,0}
        };
        GameState state = new GameState(2);
        state.setStatesArray(grid);

        GameTree tree = new GameTree(state, 5, 4000);

        Visitor visitor = new Visitor();
        GameTreeNode result = (GameTreeNode)visitor.visit(tree.getRoot(),Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        result.state.display();
        System.out.println("************************************");

    }

    @Test
    public void visit() {
        IVisitor test = new Visitor();
        DummyNode root = (DummyNode)buildTree();
        DummyNode result = (DummyNode)test.visit(root, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        assertTrue(result.getUtility() == 8);
        assertTrue(result.getId() == 24);
        assertTrue(root.getUtility() == 8);
    }

    private IGameTreeNode buildTree(){
        int counter = 0;

        IGameTreeNode a = new DummyNode(counter++, 10, new DummyNode(counter++,8), new DummyNode(counter++,2), new DummyNode(counter++,0));
        IGameTreeNode b = new DummyNode(counter++,11, new DummyNode(counter++,1), new DummyNode(counter++,9), new DummyNode(counter++,2));
        IGameTreeNode c = new DummyNode(counter++,12, new DummyNode(counter++,2), new DummyNode(counter++,3), new DummyNode(counter++,0));
        IGameTreeNode d = new DummyNode(counter++,13, new DummyNode(counter++,0), new DummyNode(counter++,3), new DummyNode(counter++,1));
        IGameTreeNode e = new DummyNode(counter++,14, new DummyNode(counter++,0), new DummyNode(counter++,7), new DummyNode(counter++,5));
        IGameTreeNode f = new DummyNode(counter++,15, new DummyNode(counter++,5), new DummyNode(counter++,4), new DummyNode(counter++,7));

        IGameTreeNode g = new DummyNode(counter++,16, a, b);
        IGameTreeNode h = new DummyNode(counter++,17, c, d);
        IGameTreeNode i = new DummyNode(counter++,18, e, f);

        IGameTreeNode root = new DummyNode(counter++,19, g, h, i);
        return root;
    }
}