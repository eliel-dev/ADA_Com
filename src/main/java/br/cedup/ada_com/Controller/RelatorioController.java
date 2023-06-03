package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.Produto;
import br.cedup.ada_com.Servico;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RelatorioController implements Initializable {

    @FXML
    ComboBox comboRelatorios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (LoginController.nivelDeAcesso == 2) {

            Produto xbox = new Produto();
            xbox.modelo = "Xbox 360";

            Produto pc = new Produto();
            pc.modelo = "PC Gamer 2000";

            Servico internet = new Servico();
            internet.nome = "Plano Gamer Rapidão";

            Servico tv = new Servico();
            tv.nome = "Plano 4K";

            comboRelatorios.getItems().removeAll(comboRelatorios.getItems());
            comboRelatorios.getItems().addAll(xbox.modelo, pc.modelo, internet.nome, tv.nome);
        }




    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }


}
