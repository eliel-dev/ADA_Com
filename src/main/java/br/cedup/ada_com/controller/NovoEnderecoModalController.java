package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.Cidade;
import br.cedup.ada_com.model.Estado;
import br.cedup.ada_com.model.dao.EnderecoDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class NovoEnderecoModalController implements Initializable {
    @FXML
    ComboBox<Estado> comboEstado;
    @FXML
    ComboBox<Cidade> comboCidade;


    EnderecoDAO enderecoDAO = new EnderecoDAO ();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Estado> estados = null;
        try {
            estados = enderecoDAO.getEstados();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        comboEstado.setItems(FXCollections.observableArrayList(estados));

        comboEstado.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Estado estadoSelecionado = (Estado) newValue;
                List<Cidade> cidades = null;
                try {
                    cidades = enderecoDAO.getCidadesByEstadoID(estadoSelecionado.getEstadoID());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                comboCidade.setItems(FXCollections.observableArrayList(cidades));
            }
        });
    }

}
