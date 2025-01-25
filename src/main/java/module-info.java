module dough.fivealive {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens dough.fivealive to javafx.fxml;
    exports dough.fivealive;
}