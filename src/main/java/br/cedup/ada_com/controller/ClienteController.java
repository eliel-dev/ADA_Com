package br.cedup.ada_com.controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.Cidade;
import br.cedup.ada_com.model.Cliente;
import br.cedup.ada_com.model.Estado;
import br.cedup.ada_com.model.Exp_cliente;
import br.cedup.ada_com.model.dao.ClienteDAO;
import br.cedup.ada_com.model.dao.EnderecoDAO;
import br.cedup.ada_com.model.dao.ExperienciaVendaDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class ClienteController implements Initializable {
    @FXML
    ListView<Estado> listEstados;
    @FXML
    ListView<Cidade> listCidades;

    @FXML
    ListView<String> perguntasListView;
    @FXML
    ListView<Exp_cliente> alternativasListView;

    @FXML
    TableView<Cliente> listaCliente;
    @FXML
    TableColumn<Cliente, Integer> codCliente;
    @FXML
    TableColumn<Cliente, String> nomeCliente;
    @FXML
    TableColumn<Cliente, String> enderecoCliente;

    EnderecoDAO enderecoDAO = new EnderecoDAO();
    ExperienciaVendaDAO dao = new ExperienciaVendaDAO();
    ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //new PropertyValueFactory() o valor é exibido da mesma forma como está no BD
        codCliente.setCellValueFactory(new PropertyValueFactory<>("clienteID"));
        //cellData posso personalizar a lógica de exibição dos dados
        nomeCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeCliente() + " " + cellData.getValue().getSobreNomeCliente()));
        enderecoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCidade() + ", " + cellData.getValue().getEstado()));

        List<Cliente> clientes = null;
        try {
            clientes = clienteDAO.getClientes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listaCliente.setItems(FXCollections.observableArrayList(clientes));

        //Lista as cidades do estado selecionado
        try {
            List<Estado> estados = enderecoDAO.getEstados();
            ObservableList<Estado> estadoData = FXCollections.observableArrayList(estados);
            listEstados.setItems(estadoData);

            listEstados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Estado estadoSelecionado = (newValue);
                    List<Cidade> cidades;
                    try {
                        cidades = enderecoDAO.getCidadesByEstadoID(estadoSelecionado.getEstadoID());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ObservableList<Cidade> cidadeData = FXCollections.observableArrayList(cidades);
                    listCidades.setItems(cidadeData);
                }
            });
        } catch (SQLException e) {
            // tratar exceção
        }

        List<String> perguntas = dao.getPerguntas();
        perguntasListView.getItems().addAll(perguntas);

        perguntasListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int perguntaID = perguntasListView.getSelectionModel().getSelectedIndex() + 1;
            List<Exp_cliente> alternativas = null;
            try {
                alternativas = dao.getAlternativas(perguntaID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            alternativasListView.getItems().clear();
            alternativasListView.getItems().addAll(alternativas);
        });
    }

    @FXML
    public void novo() throws IOException {
        HelloApplication.showModal("novoClienteModal");
    }

    @FXML
    public void editar() throws IOException {

    }
    @FXML
    public void detetar() throws SQLException {

    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
