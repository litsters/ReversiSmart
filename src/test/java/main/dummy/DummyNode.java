package main.dummy;

import main.IGameTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyNode implements IGameTreeNode {
    private int id;
    private int value;
    private List<IGameTreeNode> children;

    public DummyNode(int id, int value, IGameTreeNode... children){
        this.id = id;
        this.value = value;
        this.children = new ArrayList<>();
        this.children.addAll(Arrays.asList(children));
    }

    public int getId() {
        return id;
    }

    @Override
    public List<IGameTreeNode> getChildren() {
        return this.children;
    }

    @Override
    public int getUtility() {
        return this.value;
    }

    @Override
    public void setUtility(int value) {
        this.value = value;
    }
}
