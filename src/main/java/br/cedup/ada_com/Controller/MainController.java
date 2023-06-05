package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    Button gerenciaUsuarioG;

    @FXML
    Button gerenciaCatG;

    @FXML
    Label nomeSobrenomeLogou;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomeSobrenomeLogou.setText("Bem vindo(a) " + LoginController.nomeLogou1);

        if (LoginController.nivelDeAcesso == 1) {
            gerenciaUsuarioG.setVisible(false);
            gerenciaCatG.setVisible(false);
        }
    }

    @FXML
    public void gerenciaColaborador() throws IOException {
        HelloApplication.setRoot("vendedor-view");

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
