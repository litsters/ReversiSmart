package main;

import main.dummy.DummyNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class VisitorTest {

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