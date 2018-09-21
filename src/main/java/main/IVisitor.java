package main;

public interface IVisitor {
    public IGameTreeNode visit(IGameTreeNode node, int alpha, int beta, boolean myTurn);
}
