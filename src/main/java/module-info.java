module com.example.kalkulacka {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.kalkulacka to javafx.fxml;
    exports com.example.kalkulacka;
}