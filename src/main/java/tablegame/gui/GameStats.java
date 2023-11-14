package tablegame.gui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameStats {
    private ArrayList<String> arrayStats = new ArrayList<>();

    private void addResultIntoArrayStats(String result) {
        arrayStats.add(result);
    }

    private void writeArrayStatsIntoFile() {
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
}
