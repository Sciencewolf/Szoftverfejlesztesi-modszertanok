package tablegame.gui;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import tablegame.BoardGameMoveSelector;
import tablegame.model.BoardGameModel;
import tablegame.model.Position;
import tablegame.model.Square;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;

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
        for (int row = 0; row < board.getRowCount(); row++){
            for (int col = 0; col < board.getColumnCount(); col++){
                Node state = createState(row, col);
                board.add(state, col,row);
                state.setOnMouseClicked(this::handleMouseClick);
                state.getStyleClass().add("state");
            }
        }
        selector.phaseProperty().addListener(this::showSelectionPhaseChange);
        // itt egybe adom a neveket lehet majd nem lesz jo ha latni akarjuk hogy ki jon
        Platform.runLater(() -> playerNameOneText.setText(playerNameOne + " vs " + playerNameTwo));

        // lehet ezt hasznalni hogy lassuk kinek kell lepni, hasonlo logika alapjan
//        Platform.runLater(() -> playerNameTwoText.setText(playerNameTwo));
    }

    private StackPane createState(int i, int j){
        StackPane pane = new StackPane();
        pane.getStyleClass().add("square");
        var piece = new Circle(30);
        piece.fillProperty().bind(createSquareBinding(model.squareProperty(i, j)));
        pane.getChildren().add(piece);
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
                Square currentSquare = model.goalCheck(position);
                if (currentSquare != Square.NONE) {
                    GameResultWindow result = new GameResultWindow();
                    String winner = getColorOfSquare(currentSquare);
                    String against = playerNameOne;

                    result.createResultWindow(board, winner, against);
                }
            }
            case INVALID_SELECT, ERROR_AT_CONFIRM ->  selector.reset();
        }
    }

    private String getColorOfSquare(Square square) {
        return square == Square.BLUE ? playerNameTwo : playerNameOne;
    }

    private ObjectBinding<Paint> createSquareBinding(ReadOnlyObjectProperty<Square> squareProperty) {
        return new ObjectBinding<Paint>() {
            {
                super.bind(squareProperty);
            }
            @Override
            protected Paint computeValue() {
                return switch (squareProperty.get()) {
                    case NONE -> Color.LIGHTGRAY;
                    case RED -> Color.RED;
                    case BLUE -> Color.BLUE;
                };
            }
        };
    }

    private void showSelectionPhaseChange(ObservableValue<? extends BoardGameMoveSelector.Game_Phase> value, BoardGameMoveSelector.Game_Phase oldPhase, BoardGameMoveSelector.Game_Phase newPhase) {
        switch (newPhase) {
            case SELECT -> {}
            case INVALID_SELECT -> {}
            case CONFIRM_SELECT -> showSelection(selector.getPosition());
            case ERROR_AT_CONFIRM -> hideSelection(selector.getPosition());
            case READY_TO_MOVE -> hideSelection(selector.getPosition());
        }
    }

    private void showSelection(Position position) {
        var square = getSquare(position);
        square.getStyleClass().add("selected");

    }

    private void hideSelection(Position position) {
        var square = getSquare(position);
        square.getStyleClass().remove("selected");
    }
    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    public void setPlayerNameOneText(String playerNameOne){
        this.playerNameOne = playerNameOne;
    }

    public void setPlayerNameTwoText(String playerNameTwo){
        this.playerNameTwo = playerNameTwo;
    }


}
