module com.example.jatek {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.jatek to javafx.fxml;
    exports com.example.jatek;
}