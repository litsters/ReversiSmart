package main;

import java.util.List;

public class GameTreeNode implements IGameTreeNode{
    public static int UTILITY_PLAYER_NUMBER = 0;    // Needs to be set for correct evaluation

    private static final int WIN_UTILITY = 100000;
    private static final int LOSS_UTILITY = -100000;
    private static final int STUPIDITY_UTILITY = -2500;
    private static final int STABLE_UTILITY = 500;
    private static final int FRONTIER_UTILITY = -10;
    private static final int MOVE_UTILITY = 1;

    GameState state;
    private int index;
    private int utility = 0;
    private int round;

    private List<IGameTreeNode> children;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRound() {
        return round;
    }

    public GameTreeNode(GameState state, int round){
        this.state = state;
        this.round = round;
        calculateUtility();
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
        calculateUtility();
    }

    public List<IGameTreeNode> getChildren(){
        return children;
    }

    public void setChildren(List<IGameTreeNode> children){
        this.children = children;
    }

    public int getUtility(){

        return utility;
    }

    public void setUtility(int utility){
        this.utility = utility;
    }

    private void calculateUtility(){
        if(endState()){
            int myPieces = 0;
            int theirPieces = 0;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(this.state.getValue(i,j) == UTILITY_PLAYER_NUMBER) ++myPieces;
                    else ++theirPieces;
                }
            }
            if(myPieces > theirPieces) utility = WIN_UTILITY;
            else utility = LOSS_UTILITY;
            return;
        }

        utility = numStable(state) * STABLE_UTILITY;
        utility += numFrontier(state) * FRONTIER_UTILITY;
        utility += numMoves(state) * MOVE_UTILITY;
        utility += numStupid(state) * STUPIDITY_UTILITY;
    }

    public int numStupid(GameState state){
        int count = 0;
        count += xspaces(state, UTILITY_PLAYER_NUMBER);
        count += cspaces(state, UTILITY_PLAYER_NUMBER);

        return count;
    }

    public int cspaces(GameState state, int playerNumber){
        int count = 0;
        if(state.getValue(0,0) != playerNumber && state.getValue(1,0) == playerNumber) ++count;
        if(state.getValue(0,0) != playerNumber && state.getValue(0,1) == playerNumber) ++count;
        if(state.getValue(0,7) != playerNumber && state.getValue(1,7) == playerNumber) ++count;
        if(state.getValue(0,7) != playerNumber && state.getValue(0,6) == playerNumber) ++count;
        if(state.getValue(7,0) != playerNumber && state.getValue(6,0) == playerNumber) ++count;
        if(state.getValue(7,0) != playerNumber && state.getValue(7,1) == playerNumber) ++count;
        if(state.getValue(7,7) != playerNumber && state.getValue(7,6) == playerNumber) ++count;
        if(state.getValue(7,7) != playerNumber && state.getValue(6,7) == playerNumber) ++count;
        return count;
    }

    public int xspaces(GameState state, int playerNumber){
        int count = 0;
        if(state.getValue(0,0) != playerNumber && state.getValue(1,1) == playerNumber) ++count;
        if(state.getValue(0,7) != playerNumber && state.getValue(1,6) == playerNumber) ++count;
        if(state.getValue(7,0) != playerNumber && state.getValue(6,1) == playerNumber) ++count;
        if(state.getValue(7,7) != playerNumber && state.getValue(6,6) == playerNumber) ++count;
        return count;
    }

    public int numMoves(GameState state){
        return state.getValidMoves(round).getNumValidMoves();
    }

    public int numFrontier(GameState state){
        int count = 0;
        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                if(state.getValue(i,j) == UTILITY_PLAYER_NUMBER){
                    if(j > 0 && state.getValue(i, j-1) == 0) ++count;       // Check to the left
                    else if(j < 7 && state.getValue(i, j+1) == 0) ++count;  // Check to the right
                    else if(i > 0 && state.getValue(i-1,j) == 0) ++count;   // Check up
                    else if(i < 7 && state.getValue(i+1, j) == 0) ++count; // Check down
                }
            }
        }
        return count;
    }

    public int numStable(GameState state){
        int playerNumber = UTILITY_PLAYER_NUMBER;
        // Initialize array for tracking checks
        boolean[][] checked = new boolean[8][8];
        for(int i = 0; i < 8; ++i)for(int j = 0; j < 8; ++j) checked[i][j] = false;

        int count = 0;
        // Check 0,0 corner
        if(state.getValue(0,0) == playerNumber){
            for(int i = 0; i < 8; ++i){
                if(state.getValue(i,0) != playerNumber || checked[i][0]) break;    // Need to control the first spot in each row
                for(int j = 0; j < 8; ++j){
                    if(state.getValue(i,j) != playerNumber || checked[i][j]) break;
                    else{
                        ++count;
                        checked[i][j] = true;
                    }
                }
            }
        }
        // Check 0,7 corner
        if(state.getValue(0,7) == playerNumber){
            for(int i = 0; i < 8; ++i){
                if(state.getValue(i,0) != playerNumber || checked[i][0]) break;    // Need to control the last spot in each row
                for(int j = 7; j >= 0; --j){
                    if(state.getValue(i,j) != playerNumber || checked[i][j]) break;
                    else{
                        ++count;
                        checked[i][j] = true;
                    }
                }
            }
        }
        // Check 7,0 corner
        if(state.getValue(7,0) == playerNumber){
            for(int i = 7; i >= 0; --i){
                if(state.getValue(i,0) != playerNumber || checked[i][0]) break;    // Need to control the first spot in each row
                for(int j = 0; j < 8; ++j){
                    if(state.getValue(i,j) != playerNumber || checked[i][j]) break;
                    else{
                        ++count;
                        checked[i][j] = true;
                    }
                }
            }
        }
        // Check 7,7 corner
        if(state.getValue(7,7) == playerNumber){
            for(int i = 7; i >= 0; --i){
                if(state.getValue(i, 7) != playerNumber || checked[i][7]) break;   // Need to control the last spot in each row
                for(int j = 7; j >= 0; --j){
                    if(state.getValue(i,j) != playerNumber || checked[i][j]) break;
                    else {
                        ++count;
                        checked[i][j] = true;
                    }
                }
            }
        }

        return count;
    }

    private boolean endState(){
        return this.state.getValidMoves(round).getNumValidMoves() == 0;
    }
}
