package tablegame;

import tablegame.model.BoardGameModel;

public class BoardGameMoveSelector {

    private BoardGameModel model;
    private boolean InvalidSelection = false;


    public BoardGameMoveSelector(BoardGameModel model) {
        this.model = model;
    }


    public void select(int i, int j) {
        if (model.isEmpty(i,j)) {
            model.move(i,j);
        }
    }
}
