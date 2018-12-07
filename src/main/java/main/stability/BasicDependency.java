package main.stability;

public abstract class BasicDependency implements IDependency {
    private boolean critA;
    private boolean critB;
    private boolean critC;
    private boolean opD;
    private boolean opE;

    private int row;
    private int col;

    public BasicDependency(int row, int col){
        critA = false;
        critB = false;
        critC = false;
        opD = false;
        opE = false;

        this.row = row;
        this.col = col;
    }

    private boolean checkReq(BoardCell neighbor, int colMod, int rowMod){
        return neighbor.getCol() == this.getCol() + colMod && neighbor.getRow() == this.getRow() + rowMod;
    }

    /**
     * Determines if the dependencies are now met for stability.
     * @param neighbor A neighboring cell that is newly marked as stable.
     * @return True if the requirements are now met, false if they aren't (or they already were met).
     */
    @Override
    public boolean updateRequirements(BoardCell neighbor) {
        if(requirementsMet()) return false; // Skip if requirements already met

        // Check critA
        if(checkReq(neighbor, critAColMod(), critARowMod())) setCritA(true);

        // Check critB
        if(checkReq(neighbor, critBColMod(), critBRowMod())) setCritB(true);

        // Check critC
        if(checkReq(neighbor, critCColMod(), critCRowMod())) setCritC(true);

        // Check opD
        if(checkReq(neighbor, opDColMod(), opDRowMod())) setOpD(true);

        // Check opE
        if(checkReq(neighbor, opEColMod(), opERowMod())) setOpE(true);

        return requirementsMet();
    }

    protected abstract int critAColMod();
    protected abstract int critARowMod();
    protected abstract int critBColMod();
    protected abstract int critBRowMod();
    protected abstract int critCColMod();
    protected abstract int critCRowMod();
    protected abstract int opDColMod();
    protected abstract int opDRowMod();
    protected abstract int opEColMod();
    protected abstract int opERowMod();

    public boolean requirementsMet(){
        return critA && critB && critC && (opD || opE);
    }

    public void setCritA(boolean critA) {
        this.critA = critA;
    }

    public void setCritB(boolean critB) {
        this.critB = critB;
    }

    public void setCritC(boolean critC) {
        this.critC = critC;
    }

    public void setOpD(boolean opD) {
        this.opD = opD;
    }

    public void setOpE(boolean opE) {
        this.opE = opE;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
