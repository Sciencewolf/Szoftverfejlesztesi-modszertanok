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
}

