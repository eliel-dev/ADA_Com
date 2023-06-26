package br.cedup.ada_com.controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.Cidade;
import br.cedup.ada_com.model.Cliente;
import br.cedup.ada_com.model.Estado;
import br.cedup.ada_com.model.Exp_cliente;
import br.cedup.ada_com.model.dao.EnderecoDAO;
import br.cedup.ada_com.model.dao.ExperienciaVendaDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


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
    public void novo(){
        Estado estadoSelecionado = listEstados.getSelectionModel().getSelectedItem();
        Cidade cidadeSelecionada = listCidades.getSelectionModel().getSelectedItem();


        if (estadoSelecionado != null){


        }else if (cidadeSelecionada != null ){

        }

    }
    @FXML
    public void editar() throws IOException {
        Estado estadoSelecionado = listEstados.getSelectionModel().getSelectedItem();
        Cidade cidadeSelecionada = listCidades.getSelectionModel().getSelectedItem();

        if (estadoSelecionado != null){
            //NovoEnderecoModalController.setEstado(estadoSelecionado);
            HelloApplication.showModal("novoEnderecoModal");

        }else if (cidadeSelecionada != null ){
            //NovoEnderecoModalController.setCidade(cidadeSelecionada);
            HelloApplication.showModal("novoEnderecoModal");
        }

    }
    @FXML
    public void detetar() throws SQLException {
        Estado estadoSelecionado = listEstados.getSelectionModel().getSelectedItem();
        Cidade cidadeSelecionada = listCidades.getSelectionModel().getSelectedItem();

        if (cidadeSelecionada == null && estadoSelecionado == null) {
            // nenhum cidade ou estado selecionado
        } else if (cidadeSelecionada != null) {
            // uma cidade está selecionada
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Deseja remover " + cidadeSelecionada.getNomeCidade() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                new EnderecoDAO().excluirCidade(cidadeSelecionada.getCidadeID());
                listCidades.getItems().remove(cidadeSelecionada);
            }

        } else {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Aviso");
            dialog.setHeaderText("Deletar um estado, apaga todas cidades ligadas a ele.");
            dialog.setContentText("Caso tenha certeza que deseja isso, digite 'sim': ");

            Optional<String> result = dialog.showAndWait();
            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(resposta -> {
                if (resposta.equalsIgnoreCase("sim")) {
                    System.out.println("O estado de " + estadoSelecionado.getNomeEstado() + " foi excluído");
                }
            });
        }
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
