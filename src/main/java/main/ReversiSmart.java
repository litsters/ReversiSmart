package main;

import java.io.IOException;
import java.util.Random;

/**
 * This is the AI for Reversi!
 *
 * Designed by Sam and Julio
 */
public class ReversiSmart {
    private static final int BASE_PORT = 3333;
    private static final long SECS_PER_GAME = 180000;

    private int playerNumber;
    private PlayerSocket socket;
    private int turn;
    private int round;
    private GameState state;
    private ValidMoves validMoves;
    private Random generator;
    private long secsUsed;
    int divideFactor = 2;

    /**
     * Main method that establishes a connection and then moves whenever it is this player's turn
     * @param playerNumber 1 for the black player, 2 for the white player
     * @param host The IP address hosting the server
     */
    public ReversiSmart(int playerNumber, String host){
        this.generator = new Random();
        this.turn = -1;
        this.round = 0;
        this.state = new GameState(playerNumber);
        this.validMoves = null;
        this.secsUsed = 0;

        // Establish the connection
        this.playerNumber = playerNumber;
        GameTreeNode.UTILITY_PLAYER_NUMBER = this.playerNumber;
        initHost(host);

        // Move whenever it's this player's turn
        while(true){
            updateState();

            if(this.turn == this.playerNumber){
                // It is this player's turn
                long timerStart = System.currentTimeMillis();
                long secsForMove = (SECS_PER_GAME - this.secsUsed) / (this.state.countUnclaimed()  * this.state.countUnclaimed() / divideFactor );
                this.validMoves = this.state.getValidMoves(round);

                int myMove = move(secsForMove);
                String sel = validMoves.getValue(myMove) / 8 + "\n" + validMoves.getValue(myMove) % 8;
                this.socket.send(sel);
                long timerEnd = System.currentTimeMillis();
                this.secsUsed += (timerEnd - timerStart);
            }
        }
    }

    /**
     * Determines what move to take. Moves randomly for now.
     * @return The index of the right move to make.
     */
    private int move(long secsForMove){
        if(round < 4){
            return generator.nextInt(this.validMoves.getNumValidMoves());
        }
        GameTree tree = new GameTree(state,round,secsForMove);
        IVisitor test = new Visitor();
        IGameTreeNode root = tree.getRoot();
        IGameTreeNode result = test.visit(root, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        System.out.println("Utility = " + result.getUtility());
        result.getState().display();
        return result.getIndex();
    }

    /**
     * Updates the board state.
     */
    private void updateState(){
        try {
            turn = Integer.parseInt(socket.getLine());

            if (turn == -999) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }

                System.exit(1);
            }

            round = Integer.parseInt(socket.getLine());
            double t1 = Double.parseDouble(socket.getLine());
//            System.out.println(t1);
            double t2 = Double.parseDouble(socket.getLine());
//            System.out.println(t2);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    this.state.setValue(Integer.parseInt(socket.getLine()), i, j);
                }
            }
            socket.getLine();
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

//        System.out.println("Turn: " + turn);
//        System.out.println("Round: " + round);
//        for (int i = 7; i >= 0; i--) {
//            for (int j = 0; j < 8; j++) {
//                System.out.print(state.getValue(i,j));
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    /**
     * Establishes a socket connection with the host.
     * @param host The IP address hosting the server
     */
    private void initHost(String host){
        int portNumber = BASE_PORT + this.playerNumber;
        this.socket = new PlayerSocket(host, portNumber);
    }

    public static void main(String[] args){
        new ReversiSmart(Integer.parseInt(args[1]), args[0]);
    }
}
