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
    private static final int EDGE_UTILITY = 25;

    public static final int UPLEFT = 0;
    public static final int UPRIGHT = 1;
    public static final int DNLEFT = 2;
    public static final int DNRIGHT = 3;

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
        utility += numEdge(state, UTILITY_PLAYER_NUMBER) * EDGE_UTILITY;
    }

    private int enemy(int playerNumber){
        if(playerNumber == 1) return 2;
        else return 1;
    }

    /**
     * Counts all edge pieces controlled by the player that aren't in immediate danger of being captured.
     * @param state The state to use.
     * @return Number of safe edge pieces controlled.
     */
    public int numEdge(GameState state, int playerNumber){
        int count = 0;

        // Check left edge
        for(int row = 0; row < 8; ++row){
            // Look for consecutive groups controlled by player not adjacent to an enemy piece
            if(state.getValue(row,0) == 0) continue;    // Continue past empty spaces
            else if(state.getValue(row,0) != playerNumber){
                // Enemy space encountered. Skip the next consecutive group of controlled spaces.
                ++row;
                int startRow = row;
                while(row < 8 && state.getValue(row,0) == playerNumber)++row;
                if(row == 8 || state.getValue(row,0) == enemy(playerNumber)){
                    // They are saved by the edge of the board; count the spaces!
                    count += row - startRow;
                }
            } else {
                // Space is controlled. Count all consecutive pieces, provided they don't end with an enemy space.
                int consecCount = 0;
                int startRow = row;
                while(row < 8 && state.getValue(row,0) == playerNumber){
                    ++consecCount;
                    ++row;
                }
                if(row < 8 && state.getValue(row,0) != 0 && startRow != 0) consecCount = 0;
                count += consecCount;
            }
        }

        // Check top edge
        for(int col = 0; col < 8; ++col){
            // Look for consecutive groups controlled by player not adjacent to an enemy piece
            if(state.getValue(0,col) == 0)continue;
            else if(state.getValue(0,col) != playerNumber){
                ++col;
                int startCol = col;
                while(col < 8 && state.getValue(0,col) == playerNumber) ++col;
                if(col == 8 || state.getValue(0,col) == enemy(playerNumber)) count += col - startCol;
            } else {
                int consecCount = 0;
                int startCol = col;
                while(col < 8 && state.getValue(0,col) == playerNumber){
                    ++consecCount;
                    ++col;
                }
                if(col < 8 && state.getValue(0,col) != 0 && startCol != 0) consecCount = 0;
                count += consecCount;
            }
        }

        // Check right edge
        for(int row = 0; row < 8; ++row){
            // Look for consecutive groups controlled by player not adjacent to an enemy piece
            if(state.getValue(row,7) == 0) continue;    // Continue past empty spaces
            else if(state.getValue(row,7) != playerNumber){
                // Enemy space encountered. Skip the next consecutive group of controlled spaces.
                ++row;
                int startRow = row;
                while(row < 8 && state.getValue(row,7) == playerNumber)++row;
                if(row == 8 || state.getValue(row,7) == enemy(playerNumber)){
                    // They are saved by the edge of the board; count the spaces!
                    count += row - startRow;
                }
            } else {
                // Space is controlled. Count all consecutive pieces, provided they don't end with an enemy space.
                int consecCount = 0;
                int startRow = row;
                while(row < 8 && state.getValue(row,7) == playerNumber){
                    ++consecCount;
                    ++row;
                }
                if(row < 8 && state.getValue(row,7) != 0 && startRow != 0) consecCount = 0;
                count += consecCount;
            }
        }

        // Check bottom edge
        for(int col = 0; col < 8; ++col){
            // Look for consecutive groups controlled by player not adjacent to an enemy piece
            if(state.getValue(7,col) == 0)continue;
            else if(state.getValue(7,col) != playerNumber){
                ++col;
                int startCol = col;
                while(col < 8 && state.getValue(7,col) == playerNumber) ++col;
                if(col == 8 || state.getValue(7,col) == enemy(playerNumber)) count += col - startCol;
            } else {
                int consecCount = 0;
                int startCol = col;
                while(col < 8 && state.getValue(7,col) == playerNumber){
                    ++consecCount;
                    ++col;
                }
                if(col < 8 && state.getValue(7,col) != 0 && startCol != 0) consecCount = 0;
                count += consecCount;
            }
        }

        // Account for double-counted spaces
        if(state.getValue(0,0) == playerNumber) --count;
        if(state.getValue(0,7) == playerNumber) --count;
        if(state.getValue(7,0) == playerNumber) --count;
        if(state.getValue(7,7) == playerNumber) --count;

        return count;
    }

    public int numStupid(GameState state){
        int count = 0;
        count += xspaces(state, UTILITY_PLAYER_NUMBER);
        count += cspaces(state, UTILITY_PLAYER_NUMBER);

        return count;
    }

    public int cspaces(GameState state, int playerNumber){
        int count = 0;
        if(state.getValue(0,0) != playerNumber){
            if(state.getValue(0,1) == playerNumber){
                boolean stupid = false;
                for(int col = 2; col < 8; ++col){
                    if(state.getValue(0,col) != playerNumber) stupid = true;
                }
                if(stupid) ++count;
            }
            if(state.getValue(1,0) == playerNumber){
                boolean stupid = false;
                for(int row = 2; row < 8; ++row){
                    if(state.getValue(row,0) != playerNumber) stupid = true;
                }
                if(stupid) ++count;
            }
        }
        if(state.getValue(0,7) != playerNumber){
            if(state.getValue(1,7) == playerNumber){
                boolean stupid = false;
                for(int row = 2; row < 8; ++row){
                    if(state.getValue(row,7) != playerNumber) stupid = true;
                }
                if(stupid) ++count;
            }
            if(state.getValue(0,6) == playerNumber){
                boolean stupid = false;
                for(int col = 0; col < 6; ++col){
                    if(state.getValue(0,col) != playerNumber) stupid = true;
                }
                if(stupid) ++count;
            }
        }
        if(state.getValue(7,0) != playerNumber){
            if(state.getValue(6,0) == playerNumber){
                boolean stupid = false;
                for(int row = 0; row < 6; ++row) if(state.getValue(row,0) != playerNumber) stupid = true;
                if(stupid) ++count;
            }
            if(state.getValue(7,1) == playerNumber){
                boolean stupid = false;
                for(int col = 2; col < 8; ++col) if(state.getValue(7,col) != playerNumber) stupid = true;
                if(stupid) ++count;
            }
        }
        if(state.getValue(7,7) != playerNumber){
            if(state.getValue(7,6) == playerNumber){
                boolean stupid = false;
                for(int col = 0; col < 6; ++col) if(state.getValue(7,col) != playerNumber) stupid = true;
                if(stupid) ++count;
            }
            if(state.getValue(6,7) == playerNumber){
                boolean stupid = false;
                for(int row = 0; row < 6; ++row) if(state.getValue(row,7) != playerNumber) stupid = true;
                if(stupid) ++count;
            }
        }

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
                    else if(i > 0 && state.getValue(i-1,j) == 0) ++count;  // Check up
                    else if(i < 7 && state.getValue(i+1, j) == 0) ++count; // Check down
                }
            }
        }
        return count;
    }

    private boolean isControlled(int playerValue, int row, int col){
        if(row < 0 || row > 7 || col < 0 || col > 7) return true;
        return state.getValue(row, col) == playerValue;
    }

    public boolean spaceIsStable(int playerValue, int row, int col, int direction){
        // If the space is off the board, it's stable
        if(row < 0 || row > 7 || col < 0 || col > 7) return true;

        // If the space isn't controlled by the player, it's not stable
        if(!isControlled(playerValue, row, col)) return false;

        // Check if the space is stable
        boolean stable = true;
        switch(direction){
            case UPLEFT:
                // Check if it's an edge
                if(col == 0){
                    for(int r = 0; r < row; ++r) if(!isControlled(playerValue,r,col)) stable = false;
                }
                if(row == 0){
                    for(int c = 0; c < col; ++c) if(!isControlled(playerValue,row,c)) stable = false;
                }
                // Check that the block is controlled (rectangle from 0,0 to target space)
                for(int r = 0; r < row; ++r){
                    for(int c = 0; c < col; ++c){
                        if(!isControlled(playerValue,r,c)) stable = false;
                    }
                }
                // Check if upper wedge is controlled
                boolean upperWedgeControlled = true;
                for(int rowMod = 1; row - rowMod >= 0; ++rowMod){
                    for(int colMod = 0; colMod <= rowMod; ++colMod){
                        if(!isControlled(playerValue,row-rowMod,col+colMod)) upperWedgeControlled = false;
                    }
                }
                // Check if lower wedge is controlled
                boolean lowerWedgeControlled = true;
                for(int colMod = 1; col - colMod >= 0; ++colMod){
                    for(int rowMod = 0; rowMod <= colMod; ++rowMod){
                        if(!isControlled(playerValue,row+rowMod,col-colMod)) lowerWedgeControlled = false;
                    }
                }
                if(!upperWedgeControlled && !lowerWedgeControlled) stable = false;
                break;
            case UPRIGHT:
                // Check if it's an edge
                if(col == 7){
                    for(int r = 0; r < row; ++r) if(!isControlled(playerValue,r,col)) stable = false;
                }
                if(row == 0){
                    for(int c = 7; c > col; --c) if(!isControlled(playerValue,row,c)) stable = false;
                }
                // Check that the block is controlled (rectangle from 0,7 to target space)
                for(int r = row-1; r >= 0; --r){
                    for(int c = col; c < 8; ++c){
                        if(!isControlled(playerValue,r,c)) stable = false;
                    }
                }
                // Check if upper wedge is controlled
                upperWedgeControlled = true;
                for(int rowMod = 1; row - rowMod >= 0; ++rowMod){
                    for(int colMod = 0; colMod <= rowMod; ++colMod){
                        if(!isControlled(playerValue,row-rowMod,col-colMod)) upperWedgeControlled = false;
                    }
                }
                // Check if lower wedge is controlled
                lowerWedgeControlled = true;
                for(int colMod = 1; col + colMod < 8; ++colMod){
                    for(int rowMod = 0; rowMod <= colMod; ++rowMod){
                        if(!isControlled(playerValue,row+rowMod,col+colMod)) lowerWedgeControlled = false;
                    }
                }
                if(!upperWedgeControlled && !lowerWedgeControlled) stable = false;
                break;
            case DNLEFT:
                // Check if it's an edge
                if(row == 7){
                    for(int c = 0; c < col; ++c) if(!isControlled(playerValue,row,c)) stable = false;
                }
                if(col == 0){
                    for(int r = 7; r > row; --r) if(!isControlled(playerValue,r,col)) stable = false;
                }
                // Check that the block is controlled (rectangle from 7,0 to target space)
                for(int r = 7; r > row; --r){
                    for(int c = 0; c < col; ++c){
                        if(!isControlled(playerValue,r,c)) stable = false;
                    }
                }
                // Check if upper wedge is controlled
                upperWedgeControlled = true;
                for(int colMod = 1; col - colMod >= 0; ++colMod){
                    for(int rowMod = 0; rowMod <= colMod; ++rowMod){
                        if(!isControlled(playerValue,row-rowMod,col-colMod)) upperWedgeControlled = false;
                    }
                }
                // Check if lower wedge is controlled
                lowerWedgeControlled = true;
                for(int rowMod = 1; row + rowMod < 8; ++rowMod){
                    for(int colMod = 0; colMod <= rowMod; ++colMod){
                        if(!isControlled(playerValue,row+rowMod,col+colMod)) lowerWedgeControlled = false;
                    }
                }
                if(!upperWedgeControlled && !lowerWedgeControlled) stable = false;
                break;
            case DNRIGHT:
                // Check if it's an edge
                if(row == 7){
                    for(int c = 7; c > col; ++c) if(!isControlled(playerValue,row,c)) stable = false;
                }
                if(col == 7){
                    for(int r = 7; r > row; --r) if(!isControlled(playerValue,r,col)) stable = false;
                }
                // Check that the block is controlled (rectangle from 7,7 to target space)
                for(int r = 7; r > row; --r){
                    for(int c = 7; c > col; --c){
                        if(!isControlled(playerValue,r,c)) stable = false;
                    }
                }
                // Check if upper wedge is controlled
                upperWedgeControlled = true;
                for(int colMod = 1; col + colMod < 8; ++colMod){
                    for(int rowMod = 0; rowMod <= colMod; ++rowMod){
                        if(!isControlled(playerValue,row-rowMod,col+colMod)) upperWedgeControlled = false;
                    }
                }
                // Check if lower wedge is controlled
                lowerWedgeControlled = true;
                for(int rowMod = 1; row + rowMod < 8; ++rowMod){
                    for(int colMod = 0; colMod <= rowMod; ++colMod){
                        if(!isControlled(playerValue,row+rowMod,col-colMod)) lowerWedgeControlled = false;
                    }
                }
                if(!upperWedgeControlled && !lowerWedgeControlled) stable = false;
                break;
        }

        return stable;
    }

    public int numStable(GameState state){
        int playerNumber = UTILITY_PLAYER_NUMBER;

        int count = 0;
        for(int row = 0; row < 8; ++row){
            for(int col = 0; col < 8; ++col){
                boolean stable = false;
                if(spaceIsStable(playerNumber,row,col,UPLEFT)){
                    stable = true;
                } else if(spaceIsStable(playerNumber,row,col,UPRIGHT)){
                    stable = true;
                } else if(spaceIsStable(playerNumber,row,col,DNLEFT)){
                    stable = true;
                } else if(spaceIsStable(playerNumber,row,col,DNRIGHT)){
                    stable = true;
                }

                if(stable) ++count;
            }
        }

        return count;
    }

    public int numCorners(GameState state, int playerNumber){
        int count = 0;
        if(state.getValue(0,0) == playerNumber) ++count;
        if(state.getValue(0,7) == playerNumber) ++count;
        if(state.getValue(7,0) == playerNumber) ++count;
        if(state.getValue(7,7) == playerNumber) ++count;
        return count;
    }

    private boolean endState(){
        return this.state.getValidMoves(round).getNumValidMoves() == 0;
    }
}
