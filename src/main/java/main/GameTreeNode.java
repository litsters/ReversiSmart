package main;

import java.util.List;

public class GameTreeNode implements IGameTreeNode{

    private int[][] values = {
                                {20, -3, 11,  8,  8, 11, -3, 20},
                                {-3, -7, -4,  1,  1, -4, -7, -3},
                                {11, -4,  2,  2,  2,  2, -4, 11},
                                { 8,  1,  2, -3, -3,  2,  1,  8},
                                { 8,  1,  2, -3, -3,  2,  1,  8},
                                {11, -4,  2,  2,  2,  2, -4, 11},
                                {-3, -7, -4,  1,  1, -4, -7, -3},
                                {20, -3, 11,  8,  8, 11, -3, 20},
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

    public void setRound(int round) {
        this.round = round;
    }

    public GameTreeNode(GameState state){
        this.state = state;
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
        utility = 0;
        int[][] statesArray = this.state.getStatesArray();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(statesArray[i][j] == this.state.getPlayerNumber()){
                    int temp = values[i][j];
                    utility += values[i][j];
                }
            }
        }
    }
}
