package tablegame.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameMenuController {

    @FXML
    private FXMLLoader fxmlLoader;

    @FXML
    private TextField nameTextFieldPlayerOne;

    @FXML
    private TextField nameTextFieldPlayerTwo; // hasznald ezt hogyha latni akarjuk hogy ki lep

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

    private static final String IMAGEPATH1 = "src/main/java/tablegame/images/image.png";

    private static final String IMAGEPATH2 = "src/main/java/tablegame/images/image2.png";

    private static final String STYLEGAMESCENEPATH = "file:src/main/java/tablegame/style/styleGameScene.css";

    private static final String ICONGAMESCENEPATH = "file:src/main/java/tablegame/icon/iconGameScene.png";

    private static final String STYLEINFOPATH = "file:src/main/java/tablegame/style/styleInfo.css";

    private static final String ICONINFOPAGEPATH = "file:src/main/java/tablegame/icon/iconInfoPage.png";

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
            scene.getStylesheets().add(STYLEGAMESCENEPATH); // except board style @fxml/gameUI.css
            stage.getIcons().add(new Image(ICONGAMESCENEPATH));
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

    // game info content
    @FXML
    public static Group getContentInsideInfoWindow() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder()
                .append("\tEgy játékot két játékos játszhat egy 5 × 5 méretű mezőből álló játéktáblán, ahol felváltva kell lépniük. ")
                .append("\nEgy lépésben a játékosnak egy üres mezőre kell helyeznie saját jelét.")
                .append("\nA játék akkor ér véget, ha valamelyik játékos egy olyan mezőre helyezi a jelét, ")
                .append("\namely négy szomszédosan kapcsolódik egy másik, saját jelet tartalmazó mezőhöz." )
                .append("\nEbben az esetben a játékos veszít.");

        Label label1 = new Label(sb.toString());
        label1.getStyleClass().add("label1");

        Image image1 = new Image(new FileInputStream(IMAGEPATH1));
        Image image2 = new Image(new FileInputStream(IMAGEPATH2));

        ImageView imageView1 = new ImageView(image1);
        imageView1.setX(100);
        imageView1.setY(70);
        imageView1.setFitHeight(300);
        imageView1.setFitWidth(300);
        imageView1.setPreserveRatio(true);
        imageView1.getStyleClass().add("imageView1");

        // test
        ImageView imageView2 = new ImageView(image2);
        imageView2.setX(100);
        imageView2.setY(70);
        imageView2.setFitHeight(300);
        imageView2.setFitWidth(300);
        imageView2.setPreserveRatio(true);
        imageView2.getStyleClass().add("imageView2");

        Button buttonClose = new Button("Close");
        buttonClose.getStyleClass().add("buttonClose");
        buttonClose.setOnAction(event -> {
            Stage stage = (Stage) buttonClose.getScene().getWindow();
            stage.close();
        });

        return new Group(imageView1, label1, buttonClose);
    }

    // Game Info window
    @FXML
    private void openGameInfo() throws IOException {
        Group group = getContentInsideInfoWindow();

        Stage stage = new Stage();
        Scene scene = new Scene(group);
        scene.getStylesheets().add(STYLEINFOPATH);

        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setTitle("Game Info");
        stage.getIcons().add(new Image(ICONINFOPAGEPATH));
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