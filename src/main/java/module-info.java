module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires fontawesomefx;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;

    exports org.example.demo.client;
    opens org.example.demo.client to javafx.fxml;
}