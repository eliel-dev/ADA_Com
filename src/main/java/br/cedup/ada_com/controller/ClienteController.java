package br.cedup.ada_com.controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.Cidade;
import br.cedup.ada_com.model.Estado;
import br.cedup.ada_com.model.dao.EnderecoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class ClienteController implements Initializable {
    @FXML
    ListView<Estado> listEstados;
    @FXML
    ListView<Cidade> listCidades;
    private EnderecoDAO enderecoDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        //Lista as cidades do estado selecionado
        try {
            List<Estado> estados = enderecoDAO.getEstados();
            ObservableList<Estado> estadoData = FXCollections.observableArrayList(estados);
            listEstados.setItems(estadoData);

            listEstados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Estado estadoSelecionado = (Estado) newValue;
                    List<Cidade> cidades = null;
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
    }

    @FXML
    public void novo()   {

    }
    @FXML
    public void editar(){

    }
    @FXML
    public void detetar() throws SQLException {
        Cidade cidadeSelecionada = listCidades.getSelectionModel().getSelectedItem();
        Estado estadoSelecionado = listEstados.getSelectionModel().getSelectedItem();

        if (cidadeSelecionada == null && estadoSelecionado == null) {
            // nenhum cidade ou estado selecionado
        } else if (cidadeSelecionada != null) {
            // uma cidade está selecionada
            enderecoDAO.excluirCidade(cidadeSelecionada.getCidadeID());
        } else if (estadoSelecionado != null) {
            // um estado está selecionado
            enderecoDAO.excluirEstado(estadoSelecionado.getEstadoID());
        }
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
