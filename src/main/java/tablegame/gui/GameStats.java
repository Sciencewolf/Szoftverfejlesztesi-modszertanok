package tablegame.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.*;

public class GameStats {
    protected static ArrayList<String> arrayStats = new ArrayList<>();

    @FXML
    private Button closeStatsWindow;

    public static void addResultIntoArrayStats(String result, String player) {
        arrayStats.add(result + " -> " + player);
    }

    public static void writeArrayStatsIntoFile() {
        StringBuilder sb = new StringBuilder();
        try (FileWriter file = new FileWriter("src/main/java/tablegame/stats.txt", true)){
            if(arrayStats.isEmpty()) System.out.println("Array is empty!");
            for (String item : arrayStats) {
                sb.append(item).append(" at ").append(LocalDate.now()).append(" ")
                        .append(LocalTime.now().getHour()).append("h ").append(LocalTime.now().getMinute())
                        .append("m ")
//                        .append(LocalTime.now().getSecond()).append("s ")   // esetleg ha kell masodperc is
                        .append("\n");
            }
            file.write(sb.toString());
            arrayStats.clear();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Stats Window
    public void openStatsWindow() throws IOException {
        writeArrayStatsIntoFile();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.setTranslateX(10);
        scrollPane.setTranslateY(20);
        scrollPane.setPrefSize(400, 390);

        Button buttonClose = new Button("Close");
        buttonClose.setTranslateX(420);
        buttonClose.setTranslateY(400);
        buttonClose.setOnAction(e -> {
            Stage stage = (Stage) buttonClose.getScene().getWindow();
            stage.close();
        });

        Group group = new Group();
        group.getChildren().add(buttonClose);

        Stage stage = new Stage();
        Scene scene = new Scene(group);

        // mouse event [method]
        setMouseEventsForButtonClose(scene, buttonClose);
        // write stats from file into label [method]
        writeStatsFromFileIntoLabel(scrollPane);

        group.getChildren().add(scrollPane);

        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setTitle("Statistics");
        stage.hide();
        stage.show();
    }

    private void closeStatsWindow() {
        Stage stage = (Stage) closeStatsWindow.getScene().getWindow();
        stage.close();
    }

    private void setMouseEventsForButtonClose(Scene scene, Button buttonClose) {
        buttonClose.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setCursor(Cursor.HAND);
            }
        });

        buttonClose.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
    }

    private void writeStatsFromFileIntoLabel(ScrollPane scrollPane) throws IOException{
        try {
            Label labelStats = new Label();
            labelStats.setFont(Font.font(20));
            labelStats.setTranslateY(10);
            labelStats.setTranslateX(10);

            File file = new File("src/main/java/tablegame/stats.txt");
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                labelStats.setText(labelStats.getText() + line + "\n");
            }

            labelStats.setText(labelStats.getText() + "\n");
            scrollPane.setContent(labelStats);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
