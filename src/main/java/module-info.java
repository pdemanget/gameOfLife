module gol {
    requires javafx.controls;
    requires javafx.fxml;
    opens pdemanget.gameoflife to javafx.fxml;
    exports pdemanget.gameoflife;
}
