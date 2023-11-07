package tablegame.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.io.IOException;

public class GameMenuController {

    @FXML
    private FXMLLoader fxmlLoader;

    @FXML
    private TextField nameTextFieldPlayerOne;

    @FXML
    private TextField nameTextFieldPlayerTwo;

    @FXML
    private Label errorLabelText;

    @FXML
    private Button startGameButton;

    @FXML
    private Button informationButton;

    @FXML
    private Button showScoreBoardButton;

    @FXML
    private void openGameSceneButton(ActionEvent event) throws IOException {

        if (nameTextFieldPlayerOne == null || nameTextFieldPlayerOne.getText().isEmpty()
        || nameTextFieldPlayerTwo == null || nameTextFieldPlayerTwo.getText().isEmpty()) {

            errorLabelText.setText("Names Required");

        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

}
