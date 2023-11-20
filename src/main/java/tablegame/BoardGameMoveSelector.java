package tablegame;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import tablegame.model.BoardGameModel;
import tablegame.model.Position;
import org.tinylog.Logger;
import java.util.concurrent.Phaser;

/**
 * Handles the selection of the squares in the application.
 */
public class BoardGameMoveSelector {

    /**
     * Phases of the move process.
     */
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


    /**
     * Selects the game model, for the moving.
     * @param model selected game model.
     */
    public BoardGameMoveSelector(BoardGameModel model) {
        Logger.info("Game model selected.");
        this.model = model;
    }

    /**
     * @return the current Phase.
     */
    public Game_Phase getPhase() {
        return phase.get();
    }

    /**
     * Read only wrapper for Phase enum.
     * @return a Phase property.
     */
    public ReadOnlyObjectProperty<Game_Phase> phaseProrety() {
        return phase.getReadOnlyProperty();
    }

    /**
     * Checks if the current Phase is {@code READY_TO_MOVE}.
     * @return true if current Phase is {@code READY_TO_MOVE}
     */
    public boolean isReadyToMove() {
        return phase.get() == Game_Phase.READY_TO_MOVE;
    }

    /**
     * Handles the selection process at the Phases.
     * @param p of the selected square.
     */
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
            Logger.info("Valid position selected.");
        } else {
            InvalidSelection = true;
            phase.set(Game_Phase.INVALID_SELECT);
            Logger.warn("Invalid position.");
        }
    }

    /**
     * @return the position if it already passed the {@code SELECT} Phase.
     */
    public Position getPosition() {
        if (getPhase() == Game_Phase.SELECT) {
            throw new IllegalStateException();
        }
        return position;
    }

    /**
     * @return the value of invalidSelection.
     */
    public boolean isInvalidSelection() {
        return InvalidSelection;
    }

    /**
     * Handles the piece placement.
     */
    public void makeMove() {
        if(getPhase() != Game_Phase.READY_TO_MOVE) {
            Logger.warn("Illegal state, program isn't capable of moving yet.");
            throw new IllegalStateException();
        }
        model.move(position);
        Logger.info("Move successfully made.");
        reset();
    }

    public void confirmSelect(Position pos) {
        if (model.canMove(position, pos)) {
            phase.set(Game_Phase.READY_TO_MOVE);
            InvalidSelection = false;
            Logger.info("Position succesfully confirmed.");
        } else {
            phase.set(Game_Phase.ERROR_AT_CONFIRM);
            InvalidSelection = true;
            Logger.warn("Position confirmation unsuccesful.");
        }
    }

    /**
     * Resets the selection process.
     */
    public void reset() {
        position = null;
        phase.set(Game_Phase.SELECT);
        InvalidSelection = false;
        Logger.info("The gamephase reseted.");
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
