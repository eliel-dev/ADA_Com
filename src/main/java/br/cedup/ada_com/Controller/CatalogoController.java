package br.cedup.ada_com.Controller;

import br.cedup.ada_com.CatalogoItem;
import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.CatalogoDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CatalogoController implements Initializable {
    @FXML
    TableView<CatalogoItem> tabelaCatalogo;
    @FXML
    TableColumn<CatalogoItem, Integer> colunaCodigo;
    @FXML
    TableColumn<CatalogoItem, String> colunaNome;
    @FXML
    TableColumn<CatalogoItem, Double> colunaPreco;
    @FXML
    TableColumn<CatalogoItem, String> colunaCategoria;
    @FXML
    TableColumn<CatalogoItem, String> colunaTipo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar as colunas da tabela
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Obter dados de produtos e serviços do banco de dados
        CatalogoDAO dao = new CatalogoDAO();
        try {
            List<CatalogoItem> produtos = dao.getProdutos();
            List<CatalogoItem> servicos = dao.getServicos();

            // Adicionar dados à tabela
            tabelaCatalogo.getItems().addAll(produtos);
            tabelaCatalogo.getItems().addAll(servicos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
