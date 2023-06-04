package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.Produto;
import br.cedup.ada_com.Servico;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistraVendaController implements Initializable {

    @FXML
    ComboBox produtosServicos;

    @FXML
    Label nomeSobrenomeLogou;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeSobrenomeLogou.setText("Bem vindo(a) " + LoginController.nomeLogou1);

        Produto xbox = new Produto();
        xbox.modelo = "Xbox 360";

        Produto pc = new Produto();
        pc.modelo = "PC Gamer 2000";

        Servico internet = new Servico();
        internet.nome = "Plano Gamer Rapidão";

        Servico tv = new Servico();
        tv.nome = "Plano 4K";


        produtosServicos.getItems().removeAll(produtosServicos.getItems());
        produtosServicos.getItems().addAll(xbox.modelo, pc.modelo, internet.nome, tv.nome);
        //comboBox.getSelectionModel().select(x);
    }


    @FXML
    public void registrar () throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja registrar a experiência de venda?");
        alert.setContentText(null);

        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(sim, nao);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == sim){
            HelloApplication.showModal("experienciaModalCliente-view");

        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    //Volta para tela principal de vendedor
    @FXML
    public void fechar() throws IOException {
        HelloApplication.setRoot("Main-view");
    }




}
