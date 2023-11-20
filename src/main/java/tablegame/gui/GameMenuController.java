package tablegame.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
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
    private Button closeMenuButton;

    @FXML
    private Button closeGameInfo;

    @FXML
    private Button statsButton;

    @FXML
    private Button showScoreBoardButton;

    @FXML
    private void openGameSceneButton(ActionEvent event) throws IOException {

        if (nameTextFieldPlayerOne == null || nameTextFieldPlayerOne.getText().isEmpty()
                || nameTextFieldPlayerTwo == null || nameTextFieldPlayerTwo.getText().isEmpty()) {

            errorLabelText.setText("Names Required");

        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameSceneController>getController().setPlayerNameOneText(nameTextFieldPlayerOne.getText());
            fxmlLoader.<GameSceneController>getController().setPlayerNameTwoText(nameTextFieldPlayerTwo.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // added action for info button on main page
            informationButton.setOnAction(e -> {
                try {
                    openGameInfo();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // onClick open Stats Window
            statsButton.setOnAction(e -> {
                try {
                    openStatsWindow();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }


    // Game Info window
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

    // close Game Info window on close button click
    @FXML
    private void closeGameInfo() {
        Stage stage = (Stage) closeGameInfo.getScene().getWindow();
        stage.close();
    }

    // Stats Window
    @FXML
    private void openStatsWindow() throws IOException {
        try {
            // test
            GameStats.addResultIntoArrayStats("Winner", "user");

            GameStats gm = new GameStats();
            gm.openStatsWindow();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // close main window
    @FXML
    private void closeMainWindow() {
        Stage stage = (Stage) closeMenuButton.getScene().getWindow();
        stage.close();
    }
}