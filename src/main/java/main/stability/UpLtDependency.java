package main.stability;

public class UpLtDependency extends BasicDependency {
    // critA = left; critB = up-left; critC = up; opD = up-right; opE = down-left
    public UpLtDependency(int row, int col){
        super(row, col);
        if(row == 0){
            setCritB(true);
            setCritC(true);
            setOpD(true);
        }
        if(col == 0){
            setCritA(true);
            setCritB(true);
            setOpE(true);
        }
    }

    @Override
    protected int critAColMod() {
        return -1;
    }

    @Override
    protected int critARowMod() {
        return 0;
    }

    @Override
    protected int critBColMod() {
        return -1;
    }

    @Override
    protected int critBRowMod() {
        return -1;
    }

    @Override
    protected int critCColMod() {
        return 0;
    }

    @Override
    protected int critCRowMod() {
        return -1;
    }

    @Override
    protected int opDColMod() {
        return 1;
    }

    @Override
    protected int opDRowMod() {
        return -1;
    }

    @Override
    protected int opEColMod() {
        return -1;
    }

    @Override
    protected int opERowMod() {
        return 1;
    }

}
