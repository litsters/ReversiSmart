package main;

import java.util.List;

public class GameTreeNode implements IGameTreeNode{

    private int utility = 0;

    private List<GameTreeNode> children;

    public List<IGameTreeNode> getChildren(){
        return children;
    }

    public int getUtility(){
        return utility;
    }

    public void setUtility(int utility){
        this.utility = utility;
    }
}
