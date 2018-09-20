package main;

import java.util.List;

public interface IGameTreeNode {
    public List<IGameTreeNode> getChildren();
    public int getUtility();    // This will be set for leaf nodes
    public void setUtility(int value);
}
