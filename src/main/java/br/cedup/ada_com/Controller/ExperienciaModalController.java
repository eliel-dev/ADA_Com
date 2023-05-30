package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;

import java.io.IOException;

public class ExperienciaModalController {

    @FXML
    public void registrar() throws IOException {
        HelloApplication.setRoot("registraVenda-view");

        HelloApplication.closeCurrentWindow();
    }
}
