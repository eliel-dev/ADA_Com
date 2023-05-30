package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainVendedorController implements Initializable {

    @FXML
    Label labelVendedor;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelVendedor.setText("Nome do vendedor que logar");

    }

    @FXML
    public void registrarVenda() throws IOException {
        HelloApplication.setRoot("RegistraVenda-view");


    }

    @FXML
    public void relatorioVenda() throws IOException {
        HelloApplication.setRoot("relatorioVendedor-view");
    }

    @FXML
    public void sair () throws IOException {
        HelloApplication.setRoot("Login-view");
    }


}