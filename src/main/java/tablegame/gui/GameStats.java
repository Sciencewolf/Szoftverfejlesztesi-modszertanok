package tablegame.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;

public class GameStats {
    protected static ArrayList<String> arrayStats = new ArrayList<>();

    @FXML
    private Button closeStatsWindow;

    private static final String FILEPATH = "src/main/java/tablegame/stats.txt";
    private static final String STYLEPATH = "file:src/main/java/tablegame/style/styleGameScene.css";
    ;

    public static void addResultIntoArrayStats(String result, String player) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        sb.append("\n\uD83C\uDFC6 ")
                .append(result)
                .append(": ")
                .append(player)
                .append("\n\uD83D\uDCC6 ")
                .append(LocalDate.now())
                .append("\n\uD83D\uDD52 ")
                .append(LocalTime.now().format(dtf));
        arrayStats.add(sb.toString());
    }

    public static void writeArrayStatsIntoFile() {
        StringBuilder sb = new StringBuilder();

        try (FileWriter file = new FileWriter(FILEPATH, true)){
            for (String item : arrayStats) {
                sb.append(item).append("\n");
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
        scrollPane.getStyleClass().add("scrollPane");
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.setTranslateX(10);
        scrollPane.setTranslateY(20);
        scrollPane.setPrefSize(400, 390);

        Button buttonClose = new Button("Close");
        buttonClose.getStyleClass().add("buttonClose");
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
        scene.getStylesheets().add(STYLEPATH);

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
            Text labelStats = new Text();
            labelStats.getStyleClass().add("labelStats");
            labelStats.setFill(Color.BLACK);
            labelStats.setFont(Font.font(20));
            labelStats.setTranslateY(10);
            labelStats.setTranslateX(10);

            File file = new File(FILEPATH);
            Scanner sc = new Scanner(file);

            if(file.length() == 0) {
                labelStats.setText("No Statistics! Go Play!");
            }
            else {
                while(sc.hasNextLine()) {
                    String line = sc.nextLine();
                    labelStats.setText(labelStats.getText() + line + "\n");
                }
            }

            labelStats.setText(labelStats.getText());
            scrollPane.setContent(labelStats);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
