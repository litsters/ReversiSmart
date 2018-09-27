package main;

import java.util.List;

public class GameTreeNode implements IGameTreeNode{
    private static final int WIN_UTILITY = 1000;
    private static final int LOSS_UTILITY = -1000;

    private int[][] values = {
                                {200, -3, 11,  8,  8, 11, -3, 200},
                                {-3, -200, -4,  1,  1, -4, -200, -3},
                                {11, -4,  2,  2,  2,  2, -4, 11},
                                { 8,  1,  2, -3, -3,  2,  1,  8},
                                { 8,  1,  2, -3, -3,  2,  1,  8},
                                {11, -4,  2,  2,  2,  2, -4, 11},
                                {-3, -200, -4,  1,  1, -4, -200, -3},
                                {200, -3, 11,  8,  8, 11, -3, 200},
                             };

    GameState state;
    private int index;
    private int utility = 0;
    private int round = 0;

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
                    if(this.state.getValue(i,j) == this.state.getPlayerNumber()) ++myPieces;
                    else ++theirPieces;
                }
            }
            if(myPieces > theirPieces) utility = WIN_UTILITY;
            else utility = LOSS_UTILITY;
            return;
        }

        utility = 0;
        int[][] statesArray = this.state.getStatesArray();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(statesArray[i][j] == this.state.getPlayerNumber()){
                    int temp = values[i][j];
                    utility += values[i][j];
                } else if(statesArray[i][j] != 0){
                    utility -= values[i][j];
                }
            }
        }
    }

    private boolean endState(){
        return this.state.getValidMoves(round).getNumValidMoves() == 0;
    }
}
