package tablegame.model;

/**
 * Represents the position of a board piece.
 * @param row the row coordinate of the piece.
 * @param col the column coordinate of the piece.
 */
public record Position(int row, int col) {

    public String toString() {
        return String.format("(%d, %d)", row, col);
    }
}
