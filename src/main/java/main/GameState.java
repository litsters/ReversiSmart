package main;

/**
 * Represents the state for a Reversi game
 */
public class GameState {
    private int[][] state;
    private int playerNumber;

    public GameState(int playerNumber){
        this.playerNumber = playerNumber;
        state = new int[8][8];  // state[0][0] is the bottom-left corner of the board
    }

    public int[][] getStatesArray(){
        return state;
    }
    public void setStatesArray(int[][] state){
        this.state = state;
    }

    public void setPlayerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }
    public int getPlayerNumber(){
        return playerNumber;
    }

    /**
     * Sets a value at a specific space on the board
     * @param value The value to use
     * @param row The row index
     * @param col The column index
     */
    public void setValue(int value, int row, int col){
        state[row][col] = value;
    }

    /**
     * Gets a value at a specific space on the board
     * @param row The row index
     * @param col The column index
     * @return Obvious.
     */
    public int getValue(int row, int col){
        return state[row][col];
    }

    /**
     * Finds the valid moves based on the board state.
     * @param round The round of the game.
     * @return The valid moves for the turn.
     */
    public ValidMoves getValidMoves(int round){
        ValidMoves validMoves = new ValidMoves();

        if(round < 4){
            // We're still in the first 4 moves of the game
            if(getValue(3,3) == 0) validMoves.addMove(3*8 + 3);
            if(getValue(3,4) == 0) validMoves.addMove(3*8 + 4);
            if(getValue(4,3) == 0) validMoves.addMove(4*8 + 3);
            if(getValue(4,4) == 0) validMoves.addMove(4*8 + 4);
            //System.out.println("Valid moves:");
            for(int i = 0; i < validMoves.getNumValidMoves(); ++i){
//                System.out.println(validMoves.getValue(i) / 8 + ", " + validMoves.getValue(i) % 8);
            }
        } else {
            // We're in the remainder of the game
            //System.out.println("Valid Moves:");
            for(int i = 0; i < 8; ++i){
                for(int j = 0; j < 8; j++){
                    if(getValue(i,j) == 0) {
                        if(couldBe(i,j)){
                            validMoves.addMove(i*8 + j);
//                            System.out.println(i + ", " + j);
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    /**
     * Checks if a specific space would be a valid move.
     * @param row The row index
     * @param col The column index
     * @return True if the move is valid, false if it is not
     */
    private boolean couldBe(int row, int col){
        for (int incx = -1; incx < 2; incx++) {
            for (int incy = -1; incy < 2; incy++) {
                if ((incx == 0) && (incy == 0))
                    continue;

                if (checkDirection(row, col, incx, incy))
                    return true;
            }
        }

        return false;
    }

    /**
     * Checks if a direction works to make a move valid
     * @param row The row index of the original space
     * @param col The column index of the original space
     * @param incx
     * @param incy
     * @return
     */
    private boolean checkDirection(int row, int col, int incx, int incy){
        int sequence[] = new int[7];

        int seqLen = 0;
        for (int i = 1; i < 8; i++) {
            int r = row+incy*i;
            int c = col+incx*i;

            if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
                break;

            sequence[seqLen] = state[r][c];
            seqLen++;
        }

        int count = 0;
        for (int i = 0; i < seqLen; i++) {
            if (this.playerNumber == 1) {
                if (sequence[i] == 2)
                    count ++;
                else {
                    if ((sequence[i] == 1) && (count > 0))
                        return true;
                    break;
                }
            }
            else {
                if (sequence[i] == 1)
                    count ++;
                else {
                    if ((sequence[i] == 2) && (count > 0))
                        return true;
                    break;
                }
            }
        }

        return false;
    }
}
