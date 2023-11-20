module table.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.tinylog.api;
    opens tablegame.gui to javafx.fxml, javafx.graphics;
}
