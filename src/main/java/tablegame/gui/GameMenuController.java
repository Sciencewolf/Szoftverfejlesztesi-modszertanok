package tablegame.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import org.tinylog.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Handles the interactions at the main menu.
 */
public class GameMenuController {

    @FXML
    private TextField nameTextFieldPlayerOne;

    @FXML
    private TextField nameTextFieldPlayerTwo;

    @FXML
    private Label errorLabelText;

    @FXML
    private Button informationButton;

    @FXML
    private Button closeMenuButton;

    @FXML
    private Button closeGameInfo;

    @FXML
    private Button statsButton;

    protected static PathClass PATH = new PathClass();

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
            scene.getStylesheets().add(PATH.getSTYLEGAMESCENEPATH()); // except board style @fxml/gameUI.css
            stage.getIcons().add(new Image(PATH.getICONGAMESCENEPATH()));
            stage.setScene(scene);
            stage.show();

            // added action for info button on main page
            informationButton.setOnAction(e -> {
                try {
                    openGameInfo();
                } catch (IOException ex) {
                    Logger.warn("Couldn't open the information window.");
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

    // game info content

    /**
     * Creates the content of the information window.
     * @throws FileNotFoundException if the necessary files not found.
     */
    @FXML
    public static Group getContentInsideInfoWindow() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder()
                .append("\tEgy játékot két játékos játszhat egy 5 × 5 méretű mezőből álló játéktáblán, ahol felváltva kell lépniük. ")
                .append("\nEgy lépésben a játékosnak egy üres mezőre kell helyeznie saját jelét.")
                .append("\nA játék akkor ér véget, ha valamelyik játékos egy olyan mezőre helyezi a jelét, ")
                .append("\namely négy szomszédosan kapcsolódik egy másik, saját jelet tartalmazó mezőhöz." )
                .append("\nEbben az esetben a játékos veszít.");

        Label label2 = new Label("Játék Szabályzat");
        label2.getStyleClass().add("label2");

        Label label1 = new Label(sb.toString());
        label1.getStyleClass().add("label1");

        Image image1 = new Image(new FileInputStream(PATH.getIMAGEPATH1()));

        ImageView imageView1 = new ImageView(image1);
        imageView1.setX(100);
        imageView1.setY(70);
        imageView1.setFitHeight(300);
        imageView1.setFitWidth(300);
        imageView1.getStyleClass().add("imageView1");


        Button buttonClose = new Button("Close");
        buttonClose.getStyleClass().add("buttonClose");
        buttonClose.setOnAction(event -> {
            Stage stage = (Stage) buttonClose.getScene().getWindow();
            stage.close();
            Logger.info("Closing the game information window.");
        });

        return new Group(imageView1,label2, label1, buttonClose);
    }

    // Game Info window
    /**
     * Handles the game information pop-up window.
     */
    @FXML
    private void openGameInfo() throws IOException {
        Logger.info("Opened information window.");
        Group group = getContentInsideInfoWindow();

        Stage stage = new Stage();
        Scene scene = new Scene(group);
        scene.getStylesheets().add(PATH.getSTYLEINFOPATH());
        scene.setFill(Color.web("#3498db")); // Háttérszín beállítása

        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setTitle("Game Info");
        stage.getIcons().add(new Image(PATH.getICONINFOPAGEPATH()));
        stage.hide();
        stage.show();
    }

    // close Game Info window on close button click
    @FXML
    private void closeGameInfo() {
        Stage stage = (Stage) closeGameInfo.getScene().getWindow();
        Logger.info("Closing the game information window.");
        stage.close();
    }

    // Stats Window
    @FXML
    public void openStatsWindow() throws IOException {
        try {
            Logger.info("Opened Statistics window.");
            GameStats gm = new GameStats();
            gm.openStatsWindow();
        } catch (IOException ex) {
            Logger.warn("Couldn't open the Statistics window.");
            throw new RuntimeException(ex);
        }
    }

    // close main window
    @FXML
    private void closeMainWindow() {
        Stage stage = (Stage) closeMenuButton.getScene().getWindow();
        stage.close();
        Logger.info("Exiting...");
    }
}