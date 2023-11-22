package tablegame.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;
import tablegame.BoardGameMoveSelector;
import tablegame.model.BoardGameModel;
import tablegame.model.Position;

public class GameSceneController {
    @FXML
    private GridPane board;

    @FXML
    private Label playerNameOneText;

    @FXML
    private Label playerNameTwoText;

    private BoardGameModel model = new BoardGameModel();

    private BoardGameMoveSelector selector = new BoardGameMoveSelector(model);
    private String playerNameOne;
    private String playerNameTwo;

    @FXML
    private void initialize() {
        for (int i = 0; i < board.getRowCount(); i++){
            for (int j = 0; j < board.getColumnCount(); j++){
                Node state = createState(i, j);
                board.add(state, i,j);
                state.setOnMouseClicked(this::handleMouseClick);
            }
        }
        Platform.runLater(() -> playerNameOneText.setText(playerNameOne));
        Platform.runLater(() -> playerNameTwoText.setText(playerNameTwo));
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
        Position position = new Position(row, col);

        selector.select(position);
        switch (selector.getPhase()) {
            case READY_TO_MOVE -> {
                selector.makeMove();
                square.getChildren().add(new Circle(30, Color.valueOf(model.getPlayerColour())));
            }
            case CONFIRM_SELECT -> square.getChildren().add(new Circle(30, Color.LAWNGREEN));
            case INVALID_SELECT, ERROR_AT_CONFIRM -> {
                square.getChildren().add(new Circle(30, Color.LIGHTGRAY));
                selector.reset();

            }
        }
    }

    public void setPlayerNameOneText(String playerNameOne){
        this.playerNameOne = playerNameOne;
    }

    public void setPlayerNameTwoText(String playerNameTwo){
        this.playerNameTwo = playerNameTwo;
    }


}
