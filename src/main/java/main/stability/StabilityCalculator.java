package main.stability;

import main.GameState;

import java.util.Stack;

public class StabilityCalculator {
    public int countStable(GameState state, int playerNumber){
        // Generate grid of BoardCells
        BoardCell[][] grid = new BoardCell[8][8];
        for(int row = 0; row < 8; ++row){
            for(int col = 0; col < 8; ++col){
                grid[row][col] = new BoardCell(row,col,state.getValue(row,col) == playerNumber);
            }
        }

        // Set up stack
        Stack<BoardCell> stack = new StackSet<>();

        // Add corners, if they're stable
        if(grid[0][0].isControlled()){
            grid[0][0].setStable(true);
            stack.push(grid[0][0]);
        }
        if(grid[0][7].isControlled()){
            grid[0][7].setStable(true);
            stack.push(grid[0][7]);
        }
        if(grid[7][0].isControlled()){
            grid[7][0].setStable(true);
            stack.push(grid[7][0]);
        }
        if(grid[7][7].isControlled()){
            grid[7][7].setStable(true);
            stack.push(grid[7][7]);
        }

        // Process stack
        int count = 0;
        while(!stack.empty()){
            BoardCell cell = stack.pop();
            ++count;

            // Update each adjacent cell
            for(int rowMod = -1; rowMod < 2; ++rowMod){
                for(int colMod = -1; colMod < 2; ++colMod){
                    if(rowMod == 0 && colMod == 0) continue; // Skip the cell itself
                    int row = cell.getRow() + rowMod;
                    int col = cell.getCol() + colMod;
                    if(row < 0 || row > 7 || col < 0 || col > 7) continue;  // Skip neighbors that are off the board
                    if(grid[row][col].updateStable(cell)){
                        // Neighbor is now stable!
                        grid[row][col].setStable(true);
                        stack.push(grid[row][col]);
                    }
                }
            }
        }

        return count;
    }
}
