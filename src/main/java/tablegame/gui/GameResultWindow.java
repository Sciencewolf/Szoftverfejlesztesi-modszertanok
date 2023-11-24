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
    public static final String STYLEPATH = "file:src/main/java/tablegame/style/styleResultPage.css";
    public static final String ICONPATH = "file:src/main/java/tablegame/icon/iconResultPage.png";
    @FXML
    public void createResultWindow(GridPane board, String winnerName) {
        Text label = new Text("Winner: " + winnerName);
        label.getStyleClass().add("label");
        GameStats.addResultIntoArrayStats(winnerName);

        Button buttonClose = new Button("OK");
        buttonClose.getStyleClass().add("buttonClose");
        buttonClose.setOnAction(e -> {
            Stage stage = (Stage) buttonClose.getScene().getWindow();
            stage.close();
            Stage win = (Stage) board.getParent().getScene().getWindow();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene scene = new Scene(root);
            win.setScene(scene);
            win.show();
        });

        Group group = new Group();
        group.getChildren().add(label);
        group.getChildren().add(buttonClose);

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