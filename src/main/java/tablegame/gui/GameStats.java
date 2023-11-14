package tablegame.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameStats {
    private ArrayList<String> arrayStats = new ArrayList<>();

    @FXML
    private Button closeStatsWindow;

    public void addResultIntoArrayStats(String result) {
        arrayStats.add(result);
    }

    public void writeArrayStatsIntoFile() {
        try {
            FileWriter file = new FileWriter("/../stats.txt");
            for (String item : arrayStats) {
                file.write(item);
            }
            file.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    // Stats Window
    @FXML
    public void openStatsWindow() throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/stats.fxml"));
        Parent root = fxml.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setTitle("Statistics");
        stage.hide();
        stage.show();
    }

    @FXML
    protected void closeStatsWindow() {
        Stage stage = (Stage) closeStatsWindow.getScene().getWindow();
        stage.close();
    }
}
