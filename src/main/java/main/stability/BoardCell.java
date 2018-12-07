package main.stability;

import java.util.ArrayList;
import java.util.List;

public class BoardCell {
    private int row;
    private int col;
    private List<IDependency> dependencies;
    private boolean controlled;
    private boolean stable;

    public BoardCell(int row, int col, boolean controlled){
        this.row = row;
        this.col = col;
        this.controlled = controlled;
        this.dependencies = new ArrayList<>();
        this.stable = false;

        // Add dependencies
        this.dependencies.add(new UpLtDependency(row,col));
        this.dependencies.add(new UpRtDependency(row,col));
        this.dependencies.add(new DnLtDependency(row,col));
        this.dependencies.add(new DnRtDependency(row,col));
    }

    /**
     * Updates the dependencies and marks whether this cell is now stable too.
     * @param neighbor A neighboring cell that has just been marked stable.
     * @return True if this cell is now stable, false if it is not (or already was).
     */
    public boolean updateStable(BoardCell neighbor){
        if(this.stable) return false;
        boolean anyStable = false;
        for(IDependency dependency : this.dependencies){
            if(dependency.updateRequirements(neighbor)){
                anyStable = true;
                break;
            }
        }
        if(anyStable && this.controlled) this.stable = true;
        return this.stable;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isControlled() {
        return controlled;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable){
        this.stable = stable;
    }

    @Override
    public int hashCode(){
        return 10*row + col;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof BoardCell)) return false;
        BoardCell other = (BoardCell)o;
        return other.row == this.row && other.col == this.col;
    }
}
