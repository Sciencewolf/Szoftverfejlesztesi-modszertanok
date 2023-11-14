package tablegame;

import static org.junit.jupiter.api.Assertions.*;

import javafx.beans.property.ReadOnlyObjectWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablegame.model.BoardGameModel;
import tablegame.model.Position;

import static org.junit.jupiter.api.Assertions.*;
class BoardGameMoveSelectorTest {
    BoardGameModel model;
    ReadOnlyObjectWrapper<BoardGameMoveSelector.Game_Phase> phase;
    BoardGameMoveSelector selector;

    @BeforeEach
    public void init() {
        model = new BoardGameModel();
        phase = new ReadOnlyObjectWrapper<>(BoardGameMoveSelector.Game_Phase.SELECT);
        selector = new BoardGameMoveSelector(model);
    }

    //át kell nézni a move selectort mert:
    //org.opentest4j.AssertionFailedError:
    //Expected :INVALID_SELECT
    //Actual   :CONFIRM_SELECT
    @Test
    void testSelect() {
        // Teszteljük a helyes választást
        Position pos = new Position(1, 1);
        selector.select(pos);

        // Most hívjuk meg a confirmSelect-et is, hogy átlépjen a READY_TO_MOVE állapotba
        selector.confirmSelect(pos);

        assertEquals(BoardGameMoveSelector.Game_Phase.READY_TO_MOVE, selector.getPhase());
        assertFalse(selector.isInvalidSelection());

        // Teszteljük az érvénytelen választást
        selector.reset(); // Alaphelyzetbe állítjuk az állapotot
        selector.select(pos);
        assertEquals(BoardGameMoveSelector.Game_Phase.INVALID_SELECT, selector.getPhase());
        assertTrue(selector.isInvalidSelection());
    }

}