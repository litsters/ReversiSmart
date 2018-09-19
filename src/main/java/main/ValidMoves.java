package main;

/**
 * Contains information about valid moves
 */
public class ValidMoves {
    private int[] validMoves;
    private int numValidMoves;

    public ValidMoves(){
        numValidMoves = 0;
        validMoves = new int[64];
    }

    public void addMove(int value){
        validMoves[numValidMoves] = value;
        numValidMoves++;
    }

    public int getValue(int index){
        return validMoves[index];
    }

    public int getNumValidMoves(){ return numValidMoves;}
}
