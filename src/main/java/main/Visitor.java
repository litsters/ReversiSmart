package main;

import java.util.List;

/**
 * Visits a tree of GameTreeNodes and finds the best move, using maximin search with alpha-beta pruning.
 */
public class Visitor implements IVisitor {

    public Visitor(){}

    /**
     * Finds the best child of a node, using maximin search with alpha-beta pruning.
     * @param node The parent node.
     * @param alpha The current alpha value. Starts at negative infinity.
     * @param beta The current beta value. Starts at positive infinity.
     * @param myTurn Whether or not it is this player's turn.
     * @return The child node with the best move.
     */
    @Override
    public IGameTreeNode visit(IGameTreeNode node, int alpha, int beta, boolean myTurn) {
        // Get children of current node
        List<IGameTreeNode> children = node.getChildren();
        if(children == null || children.size() == 0){
            // This is a leaf node
            return node;
        } else {
            // This is not a leaf node; visit children and get the best one using maximin
            int best = myTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            IGameTreeNode bestChild = null;
            for(IGameTreeNode child : children){
                // Visit the child
                IGameTreeNode result = this.visit(child, alpha, beta, !myTurn);
                int utility = result.getUtility();
                if(better(best, utility, myTurn)){
                    // This child is the best so far
                    best = utility;
                    bestChild = child;

                    // Update alpha/beta
                    if(myTurn){
                        if(best > alpha) alpha = best;
                    } else {
                        if(best < beta) beta = best;
                    }

                    // Determine whether to prune
                    if(shouldPrune(best, alpha, beta, myTurn)){
                        // Return current best child; pruning rest of the children.
                        node.setUtility(best);
                        return bestChild;
                    }
                }
            }
            // Return the best child.
            node.setUtility(best);
            return bestChild;
        }
    }

    /**
     * Determines if a value is better than the current best, based on whether it is this player's turn or not.
     * @param currentBest The current best utility value for all children.
     * @param possibleBest The utility value being considered.
     * @param myTurn True if it is this player's turn, false otherwise.
     * @return True if this is a better value, false if not.
     */
    private boolean better(int currentBest, int possibleBest, boolean myTurn){
        if(myTurn) return possibleBest > currentBest;
        else return possibleBest < currentBest;
    }

    /**
     * Determines whether or not the rest of the children should be pruned.
     * @param best The current value for a parent node.
     * @param alpha The current alpha value.
     * @param beta The current beta value.
     * @param myTurn True if it is this player's turn, false otherwise.
     * @return True if further children should be pruned, false if not.
     */
    private boolean shouldPrune(int best, int alpha, int beta, boolean myTurn){
        if(myTurn && best > beta) return true;
        else return !myTurn && best < alpha;
    }
}
