package main;

import java.util.List;

public class GameTree implements IGameTree{

    private GameTreeNode root;


    public GameTree(){
        root = new GameTreeNode();
        root.getChildren() = new ArrayList<GameTreeNode>();
    }

    public IGameTreeNode getRoot(){
        return root;
    }
}
