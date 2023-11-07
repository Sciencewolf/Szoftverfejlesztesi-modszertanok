package tablegame.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
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
    private Button closeGameInfo;

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

            informationButton.setOnAction(e -> {
                try {
                    openGameInfo();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    @FXML
    private void openGameInfo() throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/info.fxml"));
        Parent root = fxml.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(200);
        stage.setTitle("Game Info");
        stage.hide();
        stage.show();
    }

    @FXML
    private void closeGameInfo() {
        Stage stage = (Stage) closeGameInfo.getScene().getWindow();
        stage.close();
    }
}
