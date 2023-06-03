package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    Button gerenciaUsuarioG;

    @FXML
    Button gerenciaCatG;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (LoginController.nivelDeAcesso == 1) {
            gerenciaUsuarioG.setVisible(false);
            gerenciaCatG.setVisible(false);
        }

    }

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
        HelloApplication.setRoot("relatorio-view");

    }

    @FXML
    public void registrarVenda() throws IOException {
        HelloApplication.setRoot("registraVenda-view");

    }

    @FXML
    public void sair() throws IOException {
        HelloApplication.setRoot("login-view");
    }


}
