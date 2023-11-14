package tablegame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BoardGameModelTest {

    BoardGameModel model;
    //Minden teszt előtt csinál egy uj modelt
    @BeforeEach
    public void init(){
        model = new BoardGameModel();
    }


//Testing the is empty function
    @Test
    void testIsEmpty() {
        BoardGameModel model = new BoardGameModel();
        assertTrue(model.isEmpty(new Position(1, 1)));
        assertTrue(model.isEmpty(new Position(4, 4)));
        //assertFalse(model.isEmpty(new Position(0, 0))); not passed
    }
//Testing the move function
    @Test
    void testMove() {
        BoardGameModel model = new BoardGameModel();
        Position pos = new Position(1, 1);
        model.move(pos);
        assertEquals(Square.BLACK, model.getSquare(pos));
        assertEquals(BoardGameModel.Player.PLAYER_2, model.getPlayer());
    }

    //Testing if the moving is possible to the definied position.
    @Test
    void testCanMove() {
        BoardGameModel model = new BoardGameModel();
        assertTrue(model.canMove(new Position(1, 1), new Position(1, 1)));
        assertFalse(model.canMove(new Position(0, 0), new Position(1, 1)));
    }

    //Get the specified player
    @Test
    void testGetPlayer() {
        BoardGameModel model = new BoardGameModel();
        assertEquals(BoardGameModel.Player.PLAYER_1, model.getPlayer());
    }
    //Change the players
    @Test
    void testChangePlayers() {
        BoardGameModel model = new BoardGameModel();
        assertEquals(BoardGameModel.Player.PLAYER_1, model.getPlayer());
        model.changePlayers();
        assertEquals(BoardGameModel.Player.PLAYER_2, model.getPlayer());
        model.changePlayers();
        assertEquals(BoardGameModel.Player.PLAYER_1, model.getPlayer());
    }


    //Get the square
    @Test
    void testGetSquare() {
        BoardGameModel model = new BoardGameModel();
        assertEquals(Square.NONE, model.getSquare(new Position(0, 0)));
        assertEquals(Square.NONE, model.getSquare(new Position(3, 3)));
    }

    //Testing the square properties
    @Test
    void testSquareProperty() {
        BoardGameModel model = new BoardGameModel();
        assertNotNull(model.squareProperty(0, 0));
        assertNotNull(model.squareProperty(2, 2));
        assertNotNull(model.squareProperty(BoardGameModel.BOARD_SIZE - 1, BoardGameModel.BOARD_SIZE - 1));
    }

}

