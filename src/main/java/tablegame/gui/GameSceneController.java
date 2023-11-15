package tablegame.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tablegame.BoardGameMoveSelector;
import tablegame.model.BoardGameModel;

public class GameSceneController {
    @FXML
    private GridPane board;

    private BoardGameMoveSelector moveSelector;

    @FXML
    private void initialize() {
        for (int i = 0; i < board.getRowCount(); i++){
            for (int j = 0; j < board.getColumnCount(); j++){
                Node state = createState(i, j);
                board.add(state, i,j);
                state.setOnMouseClicked(this::handleMouseClick);
            }
        }

    }

    private StackPane createState(int i, int j){
        StackPane pane = new StackPane();
        pane.getChildren().add(new Circle(30, Color.LIGHTGRAY));

        return pane;
    }

    @FXML
    private void handleMouseClick(MouseEvent event){
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        square.getChildren().add(new Circle(30, Color.GREEN));

    }

}
