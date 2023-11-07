package tablegame.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameSceneController {
    @FXML
    private GridPane board;

    @FXML
    private void initialize() {
        for (int i = 0; i < board.getRowCount(); i++){
            for (int j = 0; j < board.getColumnCount(); j++){
                Node state = createState(i, j);
                board.add(state, i,j);
            }
        }

    }

    //Change it to {Color.TRANSPARENT}
    private StackPane createState(int i, int j){
        StackPane pane = new StackPane();
        pane.getChildren().add(new Circle(35, Color.BLACK));

        return pane;
    }

}
