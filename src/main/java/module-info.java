module com.example.telas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires passay;
    requires com.calendarfx.view;
    requires caelum.stella.core;


    opens br.cedup.ada_com to javafx.fxml;
    exports br.cedup.ada_com;
    exports br.cedup.ada_com.Controller;
    opens br.cedup.ada_com.Controller to javafx.fxml;
}