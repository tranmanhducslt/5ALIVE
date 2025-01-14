module dough.fivealive {
    requires javafx.controls;
    requires javafx.fxml;


    opens dough.fivealive to javafx.fxml;
    exports dough.fivealive;
}