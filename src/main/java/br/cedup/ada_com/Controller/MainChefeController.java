package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainChefeController {

    @FXML
    public void gerenciaColaborador() throws IOException {
        HelloApplication.setRoot("colaborador-view");

    }
    @FXML
    public void gerenciaCatalogo() throws IOException {
        HelloApplication.setRoot("catalogo-view");

    }
    @FXML
    public void relatorio() throws IOException {
        HelloApplication.setRoot("relatorioGeral-view");

    }

    @FXML
    public void sair () throws IOException {
        HelloApplication.setRoot("Login-view");
    }
}
