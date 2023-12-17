module com.example.projeodevi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projeodevi to javafx.fxml;
    exports com.example.projeodevi;
}