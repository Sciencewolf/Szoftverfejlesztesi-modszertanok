module table.game {
    requires javafx.controls;
    requires javafx.fxml;
    opens tablegame.gui to javafx.fxml, javafx.graphics;
}
