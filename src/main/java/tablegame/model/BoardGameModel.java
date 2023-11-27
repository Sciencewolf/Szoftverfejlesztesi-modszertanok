package tablegame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

import java.util.ArrayList;

/**
 * The implemented model of the Game.
 */
public class BoardGameModel {

    /**
     * {@code BOARD_SIZE} constant variable.
     */
    public static final int BOARD_SIZE=5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    private ReadOnlyObjectWrapper<Player> current_player = new ReadOnlyObjectWrapper<Player>(Player.PLAYER_1);

    private ArrayList<ArrayList<Integer>> sub_board = new ArrayList<>();

    /**
     * Enumerator that represents the two players.
     */
    public enum Player {
            PLAYER_1("RED"),
            PLAYER_2("BLUE");

            String colour;
            Player(String c){
                colour = c;
            }

            String showColour(){
                return colour;
            }
    }
    private void BoardInitializer() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {
                row.add(0);
            }
            sub_board.add(row);
        }
        Logger.info("Gameboard succesfully created.");
    }


    /**
     * Creates the game board.
     */
    public BoardGameModel() {
        Logger.info("Generating board.");
        BoardInitializer();
        var index_i = 0;
        for (var row: sub_board) {
            var index_j = 0;
            for (var cell:row) {
                board[index_i][index_j] = new ReadOnlyObjectWrapper<Square>(
                        switch (cell) {
                            default -> Square.NONE;
                        }
                );
                index_j++;
            }
            index_i++;
        }
    }

    /**
     * @param i the first index of the board to locate the square.
     * @param j the secound index of the board to locate the square.
     * @return the square object's property as a readonly format.
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * @param p a Position type variable to locate the square
     * @return a square enum object
     */
    public Square getSquare(Position p) {
        return board[p.row()][p.col()].get();
    }

    /**
     * @param p a Position type variable to locate the square
     * @param square square enum object
     */
    private void setSquare(Position p, Square square) {
        board[p.row()][p.col()].set(square);
    }

    /**
     * Evaluates a position on the board.
     * @param p Position of the evaluation
     * @return true if the board is empty at p Position
     */
    public boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }

    /**
     * @return the current player.
     */
    public Player getPlayer() {
        return  current_player.get();
    }

    /**
     * @return the color of the player
     * (no longer used)
     */
    public String getPlayerColour() {
        return getPlayer().showColour();
    }

    /**
     * Changes the currently active player.
     */
    public void changePlayers() {
        if (getPlayer() == Player.PLAYER_1) {
            current_player.set(Player.PLAYER_2);
        } else {
            current_player.set(Player.PLAYER_1);
        }
        Logger.info("Players changed.");
    }

    /**
     * Evaluates if the selected move is legal or not.
     * @param p1 which position selected for confirmation.
     * @param p2 which position it selected at confirmation.
     * @return true if the same position selected, and the move is possible.
     */
    public Boolean canMove(Position p1, Position p2) {
        return (isEmpty(p1) && p1.equals(p2) && isOnBoard(p1) && isOnBoard(p2));
    }

    /**
     * Checks if the given position is in the boundaries of the board.
     * @param p Position of the square
     * @return true if it is on the board
     */
    public Boolean isOnBoard(Position p) {
        return p.col()>=0 && p.col()<=BOARD_SIZE && p.row()>=0 && p.row()<=BOARD_SIZE;
    }

    /**
     * Handles the placing of the pieces, checks if the winner is decided, and prepares the next player's turn.
     * @param pos which position the piece placed.
     */
    public void move(Position pos) {
        switch (getPlayer()) {
            case PLAYER_1 -> setSquare(pos, Square.RED);
            case PLAYER_2 -> setSquare(pos, Square.BLUE);
        }
        Logger.info("Game piece successfully placed.");

        Logger.info("Checking if end of game.");
        goalCheck(pos);
        changePlayers();
    }


    public String toString() {
        var Sb = new StringBuilder();
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                Sb.append(board[i][j].get().ordinal()).append(' ');
            }
            Sb.append('\n');
        }
        return Sb.toString();
    }

    /**
     * Checks if the two squares at the given positions have the same square types.
     * @param p1 Position of the first square
     * @param p2 Position of the secound square
     * @return true if they are the same type.
     */
    private boolean checkEquality(Position p1 , Position p2) {
        if (!isOnBoard(p1)|| !isOnBoard(p2)) {
            Logger.warn("At least one of the Positions is invalid.");
            return false;
        }
        return getSquare(p1) == getSquare(p2);
    }

    /**
     * Checks if the four neighbouring squares have the same color piece.
     * @param p the choosen sqare's position.
     * @return true if any neighbour has the same coloured piece.
     */
    private boolean checkTiles(Position p) {
        if(checkEquality(p, new Position(p.row(), p.col()+1)) ||
                checkEquality(p, new Position(p.row(), p.col()-1)) ||
                checkEquality(p, new Position(p.row()+1, p.col())) ||
                checkEquality(p, new Position(p.row()-1, p.col())))
            return true;
        return false;
    }

    /**
     * Evaluates the winner of the game.
     * @return the square of the winner.
     */
    public Square goalCheck(Position p){
        if(checkTiles(p))
            if(getSquare(p) == Square.BLUE) {
                Logger.info(String.format("%s, has won!", getPlayer()));
                return Square.RED;
            }
            else {
                Logger.info(String.format("%s, has won!", getPlayer()));
                return Square.BLUE;
            }
        return Square.NONE;
    }


    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();

        System.out.println(model);
        var pos = new Position(1,1);
        var pos2 = new Position(1,2);
        model.move(pos);
        System.out.println(model);
        model.move(pos2);
        System.out.println(model);
        model.move(new Position(1,0));
        model.goalCheck(new Position(1,0));
    }
}
