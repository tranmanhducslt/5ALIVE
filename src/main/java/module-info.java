module dough.fivealive {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens dough.fivealive to javafx.fxml;
    exports dough.fivealive;
}