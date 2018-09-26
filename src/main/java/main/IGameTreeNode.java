package main;

import java.util.List;

public interface IGameTreeNode {
    public List<IGameTreeNode> getChildren();
    public int getUtility();    // This will be set for leaf nodes
    public void setUtility(int value);
    public GameState getState();
    public void setState(GameState state);
    public void setChildren(List<IGameTreeNode> children);
    public int getRound();
    public void setRound(int round);
    public int getIndex();
    public void setIndex(int index);
}
