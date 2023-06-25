package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.dao.ComissaoDAO;
import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.RegistroVenda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    Button gerenciaUsuarioG;
    @FXML
    Button gerenciaCatG;
    @FXML
    MenuButton gerenciarGeral;
    @FXML
    Button gerenciarCliente;
    @FXML
    Label nomeSobrenomeLogou;

    @FXML
    Label comissaoAtualV;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeSobrenomeLogou.setText("Bem vindo(a) " + LoginController.nomeLogou1);

        if (LoginController.nivelDeAcesso == 1) {
            gerenciaUsuarioG.setVisible(false);
            gerenciaCatG.setVisible(false);
            gerenciarGeral.setVisible(false);
            gerenciarCliente.setVisible(false);
        }

        if (LoginController.nivelDeAcesso == 1) {
            ComissaoDAO comissaoDAO = new ComissaoDAO();
            double valorComissao = 0;
            try {
                valorComissao = comissaoDAO.getValorComissaoAtual(LoginController.colaboradorID);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String valorComissaoFormatado = format.format(valorComissao);
            comissaoAtualV.setText("Comissão atual: " + valorComissaoFormatado);
        } else {
            comissaoAtualV.setText("");
        }


    }

    private List<RegistroVenda> getCompras(LocalDate date) {
        // Implemente este método para retornar as compras para uma determinada data
        return new ArrayList<>();
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
    public void gerenciaCliente() throws IOException {
        HelloApplication.setRoot("cliente-view");
    }

    @FXML
    public void sair() throws IOException {
        HelloApplication.setRoot("login-view");
    }
}
