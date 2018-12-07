package main.stability;

public class DnRtDependency extends BasicDependency {
    // critA = right; critB = down-right; critC = down; opD = down-left; opE = up-right
    public DnRtDependency(int row, int col) {
        super(row, col);
        if(row == 7){
            setCritB(true);
            setCritC(true);
            setOpD(true);
        }
        if(col == 7){
            setCritA(true);
            setCritB(true);
            setOpE(true);
        }
    }

    @Override
    protected int critAColMod() {
        return 1;
    }

    @Override
    protected int critARowMod() {
        return 0;
    }

    @Override
    protected int critBColMod() {
        return 1;
    }

    @Override
    protected int critBRowMod() {
        return 1;
    }

    @Override
    protected int critCColMod() {
        return 0;
    }

    @Override
    protected int critCRowMod() {
        return 1;
    }

    @Override
    protected int opDColMod() {
        return -1;
    }

    @Override
    protected int opDRowMod() {
        return 1;
    }

    @Override
    protected int opEColMod() {
        return 1;
    }

    @Override
    protected int opERowMod() {
        return -1;
    }
}
