package tablegame;

import tablegame.model.BoardGameModel;
import tablegame.model.Position;

public class BoardGameMoveSelector {

    private BoardGameModel model;
    private boolean InvalidSelection = false;


    public BoardGameMoveSelector(BoardGameModel model) {
        this.model = model;
    }


    public void select(Position p) {
        if (model.isEmpty(p)) {
            model.move(p);
        }
    }
}
