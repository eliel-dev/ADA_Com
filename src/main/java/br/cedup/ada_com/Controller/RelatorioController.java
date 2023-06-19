package br.cedup.ada_com.Controller;

import br.cedup.ada_com.DAO.RelatorioDAO;
import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.RegistroVenda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class RelatorioController implements Initializable {
    @FXML
    private TableView<RegistroVenda> tableView;
    @FXML
    private TableColumn<RegistroVenda, Date> dataVendaColumn;
    @FXML
    private TableColumn<RegistroVenda, Integer> vendaIdColumn;
    @FXML
    private TableColumn<RegistroVenda, String> nomeClienteColumn;
    @FXML
    private TableColumn<RegistroVenda, String> nomeColaboradorColumn;

    @FXML
    ComboBox<String> comboRelatorios;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataVendaColumn.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        vendaIdColumn.setCellValueFactory(new PropertyValueFactory<>("vendaId"));
        nomeClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        nomeColaboradorColumn.setCellValueFactory(new PropertyValueFactory<>("nomeColaborador"));

        // Gerar o relatório de resumo de vendas
        RelatorioDAO relatorioDAO = new RelatorioDAO();
        List<RegistroVenda> resumoVendas = null;
        try {
            if (LoginController.nivelDeAcesso == 1) {
                // Gerar relatório apenas para as vendas feitas pelo vendedor nível 1 que está logado
                int colaboradorID = LoginController.colaboradorID;
                resumoVendas = relatorioDAO.gerarResumoVendas(colaboradorID);
            } else {
                // Gerar relatório para todas as vendas
                resumoVendas = relatorioDAO.gerarResumoVendasGestor();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Definir o conteúdo do TableView
        tableView.setItems(FXCollections.observableArrayList(resumoVendas));
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
