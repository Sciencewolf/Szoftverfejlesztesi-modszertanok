package tablegame.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameResultWindow {
    private static final String STYLEPATH = "file:src/main/java/tablegame/style/styleResultPage.css";

    private static final String ICONPATH = "file:src/main/java/tablegame/icon/iconResultPage.png";

    private static final String MENUFXMLPATH= "/fxml/menu.fxml";

    /**
     * Shows a pop-up window at the end of the game regarding the results of the game.
     */
    @FXML
    public void createResultWindow(GridPane board, String winnerName, String against) {
        Text label = new Text("Winner: " + winnerName);
        label.getStyleClass().add("label");
        GameStats.addResultIntoArrayStats(winnerName, against);

        Button buttonClose = new Button("OK");
        buttonClose.getStyleClass().add("buttonClose");
        buttonClose.setOnAction(e -> {
            Stage stage = (Stage) buttonClose.getScene().getWindow();
            stage.close();

            Stage win = (Stage) board.getParent().getScene().getWindow();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource(MENUFXMLPATH));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Scene scene = new Scene(root);
            win.setScene(scene);
            win.show();
        });

        Button showStatsButton = new Button("Show Statistic");
        showStatsButton.getStyleClass().add("showStatsButton");
        showStatsButton.setOnAction(event -> {
            try{
                GameMenuController menuController = new GameMenuController();
                menuController.openStatsWindow();
            }catch (IOException exception){
                throw new RuntimeException();
            }
        });

        Group group = new Group();
        group.getChildren().add(label);
        group.getChildren().add(buttonClose);
        group.getChildren().add(showStatsButton);

        showStatsButton.setLayoutX(120);
        showStatsButton.setLayoutY(77);

        Stage stage = new Stage();
        Scene scene = new Scene(group);
        scene.getStylesheets().add(STYLEPATH);

        stage.setScene(scene);
        stage.setWidth(300);
        stage.setHeight(150);
        stage.setTitle("Result");
        stage.getIcons().add(new Image(ICONPATH));
        stage.hide();
        stage.show();
    }
}