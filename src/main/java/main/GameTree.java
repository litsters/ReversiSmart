package main;

import java.util.*;

import javax.xml.soap.Node;

public class GameTree implements IGameTree{
    private static final int SECS_PER_MOVE = 2812;  // Number of milliseconds per move, given 3 minutes of game time
    private GameTreeNode root;
    private Queue<IGameTreeNode> queue;
    private int maxInQueue = 0;

    public GameTree(GameState state, int round, int secsForMove) {
        queue = new LinkedList<>();

        GameState gameState = new GameState(state.getPlayerNumber());
        int [][] tempStatesArray = copyArray(state.getStatesArray());
        gameState.setStatesArray(tempStatesArray);

        root = new GameTreeNode(gameState,round);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + secsForMove;

        buildChildren(root, root.getRound()+1, root.getState().getPlayerNumber(), endTime);

        // Check if there is a child already that increases the number of stable spaces; if so,
        // just only allow the child with the greatest increase of stable spaces.
        if(seizeStable()){
            System.out.println("Seizing stable spaces");
            return;
        }

        //This code works for three levels
//        for (IGameTreeNode node : root.getChildren()) {
//            buildChildren(node, node.getRound()+1, node.getState().getPlayerNumber());
//            if(node.getChildren() != null) {
//                for (IGameTreeNode node2 : node.getChildren()) {
//                    buildChildren(node2, node2.getRound() + 1, node2.getState().getPlayerNumber());
//                }
//            }
//        }

        IGameTreeNode node = queue.poll();
        while(System.currentTimeMillis() < endTime && node != null){
            if(maxInQueue < queue.size()) maxInQueue = queue.size();
            buildChildren(node, node.getRound() + 1, node.getState().getPlayerNumber(), endTime);
            node = queue.poll();
        }

        System.out.println("Max in queue = " + maxInQueue);
    }

    private boolean seizeStable(){
        int curStable = root.numStable(root.getState());
        int maxGain = 0;
        int indx = -1;
        for(int i = 0; i < queue.size(); ++i){
            GameTreeNode node = (GameTreeNode)((LinkedList<IGameTreeNode>)queue).get(i);
            int gain = node.numStable(node.getState()) - curStable;
            if(gain > maxGain){
                maxGain = gain;
                indx = i;
            }
        }
        if(indx > -1){
            // Prune all but the one that had the greatest gain
            LinkedList<IGameTreeNode> updated = new LinkedList<>();
            IGameTreeNode n = ((LinkedList<IGameTreeNode>)queue).get(indx);
            updated.add(n);
            queue = updated;
            return true;
        } else return false;
    }

    private void buildChildren(IGameTreeNode node, int round, int playerNumber, long endTime){
        List<IGameTreeNode> children = new ArrayList<>();
        ValidMoves validMoves = node.getState().getValidMoves(round);
        int numValidMoves = node.getState().getValidMoves(round).getNumValidMoves();

        for(int i = 0; i < numValidMoves; i++){
            if(System.currentTimeMillis() >= endTime) break;
            int index = validMoves.getValue(i);
            int col  = index % 8;
            int row = index / 8;

            int childNodePlayerNumber = ((playerNumber + 1) % 3 == 0) ? 1 : 2 ;

            GameState gameState = new GameState(childNodePlayerNumber);
            int [][] tempStatesArray = copyArray(node.getState().getStatesArray());

            gameState.setStatesArray(tempStatesArray);
            gameState.setValue(playerNumber, row, col);
            // now identify any flipped spaces
            List<Space> toFlip = new ArrayList<>();
            // Check to the right
            List<Space> temp = determineToFlip(gameState, row, col, 0, 1, playerNumber);
            toFlip.addAll(temp);

            // Check to the left
            temp = determineToFlip(gameState, row, col, 0, -1, playerNumber);
            toFlip.addAll(temp);

            // Check up
            temp = determineToFlip(gameState, row, col, -1, 0, playerNumber);
            toFlip.addAll(temp);

            // Check down
            temp = determineToFlip(gameState, row, col, 1, 0, playerNumber);
            toFlip.addAll(temp);

            // Check up-right diagonal
            temp = determineToFlip(gameState, row, col, -1, 1, playerNumber);
            toFlip.addAll(temp);

            // Check up-left diagonal
            temp = determineToFlip(gameState, row, col, -1, -1, playerNumber);
            toFlip.addAll(temp);

            // Check down-right diagonal
            temp = determineToFlip(gameState, row, col, 1, 1, playerNumber);
            toFlip.addAll(temp);

            // Check down-left diagonal
            temp = determineToFlip(gameState, row, col, 1, -1, playerNumber);
            toFlip.addAll(temp);

            // Flip all spaces
            for(Space space : toFlip) gameState.setValue(playerNumber, space.row, space.col);

            GameTreeNode tempNode = new GameTreeNode(gameState,round);
            tempNode.setIndex(i);
            children.add(tempNode);
        }
        if(numValidMoves > 0) {
            // Eliminate all children with stupid moves, unless all of them do (in which case, leave the one that is
            // least stupid)
            children = pruneStupid(children);
            node.setChildren(children);
            queue.addAll(children);
        }
    }

    List<IGameTreeNode> pruneStupid(List<IGameTreeNode> nodeList){
        List<IGameTreeNode> unstupid = new ArrayList<>();
        // Only take the nodes that don't have stupid moves
        for(IGameTreeNode n : nodeList){
            GameTreeNode node = (GameTreeNode)n;
            if(node.numStupid(node.getState()) == 0) unstupid.add(n);
        }
        // If no nodes were added, take only the one with the greatest utility
        if(unstupid.size() == 0){
            int minStupid = Integer.MAX_VALUE;
            int indx = -1;
            for(int i = 0; i < nodeList.size(); ++i){
                GameTreeNode node = (GameTreeNode)nodeList.get(i);
                if(node.numStupid(node.getState()) < minStupid){
                    indx = i;
                    minStupid = node.numStupid(node.getState());
                }
            }
            if(indx > -1) unstupid.add(nodeList.get(indx));
        }

        return unstupid;
    }

    private List<Space> determineToFlip(GameState state, int row, int col, int incRow, int incCol, int playerNumber){
        List<Space> toFlip = new ArrayList<>();
        int r = row;
        int c = col;
        while(r >= 0 && r < 8 && c >= 0 && c < 8){
            // iterate until you come to a blank space, your own piece, or the edge of the board
            if(r == row && c == col){
                r += incRow;
                c += incCol;
                continue;
            }
            int value = state.getValue(r, c);
            if(value == 0){
                // blank space; nothing to capture
                toFlip = new ArrayList<>();
                break;
            } else if(value == playerNumber){
                // your own space; we're done
                break;
            } else {
                // this is an enemy space; keep going
                toFlip.add(new Space(r, c));
            }

            r += incRow;
            c += incCol;
        }
        if(r < 0 || r > 7 || c < 0 || c > 7) toFlip = new ArrayList<>();    // Went out of bounds
        return toFlip;
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

    private class Space{
        public int row;
        public int col;

        public Space(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
}
