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
    Button bRegistrarVenda;
    @FXML
    Button bRelatorio;

    @FXML
    MenuButton bGerenciar;
    @FXML
    Button gerenciaUsuarioG;
    @FXML
    Button gerenciaCatG;
    @FXML
    Button gerenciarCliente;

    @FXML
    Label nomeSobrenomeLogou;
    @FXML
    Label comissaoAtualV;

    @FXML
    Button bSair;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeSobrenomeLogou.setText("Bem vindo(a) " + LoginController.nomeLogou1);

        if (LoginController.nivelDeAcesso == 1) {
            gerenciaUsuarioG.setVisible(false);
            gerenciaCatG.setVisible(false);
            bGerenciar.setVisible(false);
            gerenciarCliente.setVisible(false);
        }

        if (LoginController.nivelDeAcesso == 1) {
            ComissaoDAO comissaoDAO = new ComissaoDAO();
            double valorComissao = 0;
            try {
                valorComissao = comissaoDAO.getValorComissaoAtual(LoginController.colaboradorID);
            } catch (SQLException e) {
                System.err.println("Erro ao recuperar as alternativas do banco de dados: " + e.getMessage());;

            }
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String valorComissaoFormatado = format.format(valorComissao);
            comissaoAtualV.setText("Comissão atual: " + valorComissaoFormatado);
        } else {
            comissaoAtualV.setText("");
        }

        //Style
        Button[] buttons = {bRegistrarVenda, bRelatorio, gerenciaUsuarioG, gerenciaCatG, gerenciarCliente};

        for (Button button : buttons) {
            button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18; -fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18; -fx-background-color: #000000; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
        }

        bGerenciar.setOnMouseEntered(e -> bGerenciar.setStyle("-fx-font-size: 18; -fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
        bGerenciar.setOnMouseExited(e -> bGerenciar.setStyle("-fx-font-size: 18; -fx-background-color: #000000; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));

        bSair.setOnMouseEntered(e -> bSair.setStyle("-fx-font-size: 18; -fx-background-color: red; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));
        bSair.setOnMouseExited(e -> bSair.setStyle("-fx-font-size: 18; -fx-background-color: #000000; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));
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
