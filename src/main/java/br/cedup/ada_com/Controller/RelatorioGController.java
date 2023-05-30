package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class RelatorioGController {

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("mainChefe-view");
    }
}
