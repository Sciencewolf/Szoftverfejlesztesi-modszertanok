package tablegame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import org.tinylog.Logger;

import java.util.ArrayList;

public class BoardGameModel {

    public static final int BOARD_SIZE=5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    private ReadOnlyObjectWrapper<Player> current_player = new ReadOnlyObjectWrapper<Player>(Player.PLAYER_1);

    private ArrayList<ArrayList<Integer>> sub_board = new ArrayList<>();

    public enum Player {
            PLAYER_1,
            PLAYER_2
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


    public BoardGameModel() {
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

    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public Square getSquare(Position p) {
        return board[p.row()][p.col()].get();
    }

    private void setSquare(Position p, Square square) {
        board[p.row()][p.col()].set(square);
    }

    public boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }

    public Player getPlayer() {
        return  current_player.get();
    }
    public void changePlayers() {
        if (getPlayer() == Player.PLAYER_1) {
            current_player.set(Player.PLAYER_2);
        } else {
            current_player.set(Player.PLAYER_1);
        }
        Logger.info("Players changed.");
    }

    public  Boolean canMove(Position p1, Position p2) {
        return (isEmpty(p1) && p1.equals(p2));
    }

    public void move(Position pos) {
        switch (getPlayer()) {
            case PLAYER_1 -> setSquare(pos, Square.BLACK);
            case PLAYER_2 -> setSquare(pos, Square.WHITE);
        }
        Logger.info("Game piece succesfully placed.");
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
    private boolean checkEquality(Position p1 , Position p2) {
        if (p2.row() == -1 || p2.col() == -1) {
            return false;
        }
        return getSquare(p1) == getSquare(p2);
    }
    private boolean checkTiles(Position p) {
        if(checkEquality(p, new Position(p.row(), p.col()+1)) || checkEquality(p, new Position(p.row(), p.col()-1))
                || checkEquality(p, new Position(p.row()+1, p.col())) || checkEquality(p, new Position(p.row()-1, p.col())))
            return true;
        return false;
    }
    public Square goalCheck(Position p){
        if(checkTiles(p))
            if(getSquare(p) == Square.WHITE) {
                Logger.info(String.format("%s, has won!", getPlayer()));
                return Square.BLACK;
            }
            else {
                Logger.info(String.format("%s, has won!", getPlayer()));
                return Square.WHITE;
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
    }
}
