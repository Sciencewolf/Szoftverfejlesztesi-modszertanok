package tablegame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class BoardGameModel {

    public static final int BOARD_SIZE=5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

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

    /*TODO
        Kerzdetledes move metódus
     */
    public void move(Position p) {
        setSquare(p, Square.WHITE);
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


    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();

        System.out.println(model);
        var pos = new Position(1,1);
        model.move(pos);
        System.out.println(model);
    }
}
