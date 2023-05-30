package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;

import java.io.IOException;

public class RelatorioVController {

    @FXML
    public void voltar () throws IOException {
        HelloApplication.setRoot("mainVendedor-view");
    }

}
