package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.xml.soap.Node;

public class GameTree implements IGameTree{
    private static final int SECS_PER_MOVE = 2812;  // Number of milliseconds per move, given 3 minutes of game time
    private GameTreeNode root;
    private PriorityQueue<IGameTreeNode> priorityQueue;
    private int maxInQueue = 0;

    public GameTree(GameState state, int round) {
        priorityQueue = new PriorityQueue<>(11, new NodeComparator());

        GameState gameState = new GameState(state.getPlayerNumber());
        int [][] tempStatesArray = copyArray(state.getStatesArray());
        gameState.setStatesArray(tempStatesArray);

        root = new GameTreeNode(gameState,round);

//        long startTime = System.currentTimeMillis();
//        long endTime = startTime + SECS_PER_MOVE;

        buildChildren(root, root.getRound()+1, root.getState().getPlayerNumber());

        //This code works for three levels
        for (IGameTreeNode node : root.getChildren()) {
            buildChildren(node, node.getRound()+1, node.getState().getPlayerNumber());
            if(node.getChildren() != null) {
                for (IGameTreeNode node2 : node.getChildren()) {
                    buildChildren(node2, node2.getRound() + 1, node2.getState().getPlayerNumber());
                }
            }
        }

//        IGameTreeNode node = priorityQueue.poll();
//        while(System.currentTimeMillis() < endTime && node != null){
//            if(maxInQueue < priorityQueue.size()){
//                maxInQueue = priorityQueue.size();
//            }
//            buildChildren(node, node.getRound() + 1, node.getState().getPlayerNumber());
//            node = priorityQueue.poll();
//        }

    }

    private void buildChildren(IGameTreeNode node, int round, int playerNumber){
        List<IGameTreeNode> children = new ArrayList<>();
        ValidMoves validMoves = node.getState().getValidMoves(round);
        int numValidMoves = node.getState().getValidMoves(round).getNumValidMoves();

        for( int i = 0; i < numValidMoves; i++){
            int index = validMoves.getValue(i);
            int col  = index % 8;
            int row = index / 8;

            int childNodePlayerNumber = ((playerNumber + 1) % 3 == 0) ? 1 : 2 ;

            GameState gameState = new GameState(childNodePlayerNumber);
            int [][] tempStatesArray = copyArray(node.getState().getStatesArray());

            gameState.setStatesArray(tempStatesArray);
            gameState.setValue(childNodePlayerNumber, row, col);

            GameTreeNode tempNode = new GameTreeNode(gameState,round);
            tempNode.setIndex(i);
            children.add(tempNode);
            priorityQueue.add(tempNode);
        }
        if(numValidMoves > 0) {
            node.setChildren(children);
        }
    }

    private int[][]copyArray(int[][] array){
        int [][] myInt = new int[array.length][];
        for(int i = 0; i < array.length; i++){
            myInt[i] = array[i].clone();
        }
        return myInt;
    }

    public IGameTreeNode getRoot(){
        return root;
    }

    class NodeComparator implements Comparator<IGameTreeNode> {
        public int compare(IGameTreeNode n1, IGameTreeNode n2) {
            if (n1.getUtility() < n2.getUtility())
                return 1;
            else if (n1.getUtility() > n2.getUtility())
                return -1;
            return 0;
        }
    }
}
