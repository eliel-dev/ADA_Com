module com.example.telas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires passay;
    requires com.calendarfx.view;
    requires caelum.stella.core;


    opens br.cedup.ada_com to javafx.fxml;
    exports br.cedup.ada_com;
    exports br.cedup.ada_com.controller;
    opens br.cedup.ada_com.controller to javafx.fxml;
    exports br.cedup.ada_com.model;
    opens br.cedup.ada_com.model to javafx.fxml;
}