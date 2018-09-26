package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.xml.soap.Node;

public class GameTree implements IGameTree{


    //value 1 is for player 1, value 2 is for player 2
    private int playerNumber = 1;

    private GameTreeNode root;
    private PriorityQueue<IGameTreeNode> priorityQueue;
    private int maxInQueue = 0;

    public GameTree(GameState state,int round) {
        priorityQueue = new PriorityQueue<>(11, new NodeComparator());

        GameState gameState = new GameState(state.getPlayerNumber());
        int [][] tempStatesArray = copyArray(state.getStatesArray());
        gameState.setStatesArray(tempStatesArray);

        root = new GameTreeNode(gameState);

        root.setRound(round);

        buildChildren(root, root.getRound()+1, root.getState().getPlayerNumber());
//        This code works for two levels
//        for (IGameTreeNode node : root.getChildren()) {
//            buildChildren(node, 1, node.getState().getPlayerNumber());
//        }
//        int dummy = 0;
//        for( IGameTreeNode node: root.getChildren()){
//            for (IGameTreeNode childNode: node.getChildren()){
//                buildChildren(node,2,childNode.getState().getPlayerNumber());
//            }
//        }

        for(int i = 0; i < 20; i++){
            if(maxInQueue < priorityQueue.size()){
                maxInQueue = priorityQueue.size();
                //System.out.println("Changed size");
            }
            IGameTreeNode node = priorityQueue.poll();
            if(node != null){
                buildChildren(node,node.getRound() + 1, node.getState().getPlayerNumber());
            }
        }

        System.out.println("Round: " + round + " MaxInQueue: " + maxInQueue);

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


            GameTreeNode tempNode = new GameTreeNode(gameState);
            tempNode.setRound(round);
            tempNode.setIndex(i);
            //node.getChildren().add(tempNode);
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
