module ru.yourname.timeserverfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.yourname.timeserverfx to javafx.fxml;
    exports ru.yourname.timeserverfx;
}