package main;

import org.junit.Test;



import static org.junit.Assert.*;

/**
 * Created by julio on 9/25/18.
 */
public class GameTreeTest {



    @Test
    public  void buildRoot() throws Exception {
        GameState state = new GameState(1);
        GameTree tree = new GameTree(state, 1);

        IVisitor test = new Visitor();
        IGameTreeNode root = tree.getRoot();
        IGameTreeNode result = test.visit(root, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        /**playerNumber 1 for the black player, 2 for the white player*/
    }

}