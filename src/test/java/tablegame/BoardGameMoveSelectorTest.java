package tablegame;

import static org.junit.jupiter.api.Assertions.*;

import javafx.beans.property.ReadOnlyObjectWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tablegame.model.BoardGameModel;
import tablegame.model.Position;

import static org.junit.jupiter.api.Assertions.*;
public class BoardGameMoveSelectorTest {
    BoardGameModel model;
    ReadOnlyObjectWrapper<BoardGameMoveSelector.Game_Phase> phase;
    BoardGameMoveSelector selector;

    @BeforeEach
    public void init() {
        model = new BoardGameModel();
        selector = new BoardGameMoveSelector(model);
    }

    @Test
    public void isReadyToMoveWhenNotReady() {
        System.out.println(selector.getPhase());
        assertEquals(false, selector.isReadyToMove());
    }

    @Test
    public void isReadyToMoveWhenSelectFrom() {
        System.out.println(selector.getPhase());
        selector.select(new Position(1, 1));
        assertEquals(false, selector.isReadyToMove());
    }

    @Test
    public void isReadyToMoveWhenReadyToMove() {
        System.out.println(selector.getPhase());
        selector.select(new Position(2, 2));
        selector.confirmSelect(new Position(2, 2));
        assertEquals(true, selector.isReadyToMove());
    }
}

