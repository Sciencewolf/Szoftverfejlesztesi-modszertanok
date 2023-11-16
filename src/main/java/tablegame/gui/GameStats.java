package tablegame.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

public class GameStats {
    public static ArrayList<String> arrayStats = new ArrayList<>();

    @FXML
    private Button closeStatsWindow;

    public static void addResultIntoArrayStats(String result) {
        arrayStats.add(result);
    }

    // TODO: fix issue with writing into file multiple times when menu is opened
    public static void writeArrayStatsIntoFile() {
        StringBuilder sb = new StringBuilder();
        try (FileWriter file = new FileWriter("src/main/java/tablegame/stats.txt", true)){
            if(arrayStats.isEmpty()) file.write("Array is empty!");
            for (String item : arrayStats) {
                sb.append(item).append(" ").append(LocalDate.now()).append("\n");
            }
            file.write(sb.toString());

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Stats Window
    // TODO: add close button and stats from file
    @FXML
    public void openStatsWindow() throws IOException {
        Label l = new Label("test");
        l.setTranslateX(19);
        l.setTranslateY(20);
        l.setTextFill(Color.BLACK);
        Group group = new Group();
        group.getChildren().add(l);

        Stage stage = new Stage();
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setTitle("Statistics");
        stage.hide();
        stage.show();
    }

    @FXML
    public void closeStatsWindow() {
        Stage stage = (Stage) closeStatsWindow.getScene().getWindow();
        stage.close();
    }
}
