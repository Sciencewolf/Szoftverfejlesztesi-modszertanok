package tablegame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.ArrayList;

public class BoardGameModel {

    public static final int BOARD_SIZE=5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    private ArrayList<ArrayList<Integer>> sub_board = new ArrayList<>();
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


    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }

    private void setSquare(int i, int j, Square square) {
        board[i][j].set(square);
    }

    public boolean isEmpty(int i, int j) {
        return getSquare(i,j) == Square.NONE;
    }

    /*TODO
        Kerzdetledes move metódus
     */
    public void move(int i, int j) {
        setSquare(i, j, Square.WHITE);
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
}
