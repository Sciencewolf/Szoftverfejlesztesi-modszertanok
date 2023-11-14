package tablegame;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import tablegame.model.BoardGameModel;
import tablegame.model.Position;

import java.util.concurrent.Phaser;

public class BoardGameMoveSelector {

    public enum Game_Phase {
        SELECT,
        INVALID_SELECT,
        CONFIRM_SELECT,
        ERROR_AT_CONFIRM,
        READY_TO_MOVE
    }

    private BoardGameModel model;
    private boolean InvalidSelection = false;
    private ReadOnlyObjectWrapper<Game_Phase> phase = new ReadOnlyObjectWrapper<>(Game_Phase.SELECT);
    private Position position;


    public BoardGameMoveSelector(BoardGameModel model) {
        this.model = model;
    }

    public Game_Phase getPhase() {
        return phase.get();
    }

    public ReadOnlyObjectProperty<Game_Phase> phaseProrety() {
        return phase.getReadOnlyProperty();
    }

    public boolean isReadyToMove() {
        return phase.get() == Game_Phase.READY_TO_MOVE;
    }
    public void select(Position p) {
        switch (phase.get()) {
            case SELECT -> selectPosition(p);
            case CONFIRM_SELECT -> confirmSelect(p);
            case READY_TO_MOVE -> throw new IllegalStateException();
            default -> reset();
        }
    }

    public void selectPosition(Position pos) {
        if(model.isEmpty(pos)) {
            position = pos;
            phase.set(Game_Phase.CONFIRM_SELECT);
            InvalidSelection = false;
        } else {
            InvalidSelection = true;
            phase.set(Game_Phase.INVALID_SELECT);
        }
    }

    public Position getPosition() {
        if (getPhase() == Game_Phase.SELECT) {
            throw new IllegalStateException();
        }
        return position;
    }

    public boolean isInvalidSelection() {
        return InvalidSelection;
    }

    public void makeMove() {
        if(getPhase() != Game_Phase.READY_TO_MOVE) {
            throw new IllegalStateException();
        }
        model.move(position);
        reset();
    }
    public void confirmSelect(Position pos) {
        if (model.canMove(position, pos)) {
            phase.set(Game_Phase.READY_TO_MOVE);
            InvalidSelection = false;
        } else {
            phase.set(Game_Phase.ERROR_AT_CONFIRM);
            InvalidSelection = true;
        }
    }

    public void reset() {
        position = null;
        phase.set(Game_Phase.SELECT);
        InvalidSelection = false;
    }

    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();
        BoardGameMoveSelector selector = new BoardGameMoveSelector(model);
        System.out.println(model);


        System.out.println(selector.getPhase());
        var pos = new Position(1,1);
        selector.select(pos);
        System.out.println(model);
        System.out.println(selector.getPhase());
        selector.select(pos);
        System.out.println(model);
        System.out.println(selector.getPhase());
        selector.makeMove();
        System.out.println(model);

        var pos2 = new Position(2,2);
        selector.select(pos2);
        System.out.println(model);
        selector.select(pos2);
        System.out.println(model);
        selector.makeMove();
        System.out.println(model);
        var pos3 = new Position(0,1);
        selector.select(pos3);
        selector.select(pos3);
        selector.makeMove();
        System.out.println(model);
        System.out.println(model.goalCheck(pos3));
    }
}
