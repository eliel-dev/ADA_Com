package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Catalogo;
import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.CatalogoDAO;
import br.cedup.ada_com.DAO.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
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
    TableView<Catalogo> tabelaCatalogo;
    @FXML
    TableColumn<Catalogo, Integer> colunaId;
    @FXML
    TableColumn<Catalogo, String> colunaNome;
    @FXML
    TableColumn<Catalogo, Double> colunaValor;
    @FXML
    TableColumn<Catalogo, String> colunaCategoria;
    @FXML
    TableColumn<Catalogo, String> colunaTipo;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.colunaId.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        this.colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        this.colunaTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo() == 1 ? "Produto" : "Serviço"));

        // Define o cellFactory personalizado para a coluna colunaValor
        this.colunaValor.setCellFactory(column -> new TableCell<Catalogo, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(String.format("R$ %.2f", item));
                }
            }
        });

        CatalogoDAO dao = new CatalogoDAO();
        try {
            List<Catalogo> itens = dao.getItens();
            tabelaCatalogo.getItems().addAll(itens);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void novoItem () throws IOException {
        CatalogoModalController.catalogoItem = null;

        HelloApplication.showModal("catalogoModal");

        Catalogo novoItem = CatalogoModalController.catalogoItem;

        if (novoItem != null){
            CatalogoDAO dao = new CatalogoDAO();
            try {
                dao.inserirItem(novoItem);
                tabelaCatalogo.getItems().add(novoItem);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void editarItem () throws IOException {
        Catalogo itemSelecionado = tabelaCatalogo.getSelectionModel().getSelectedItem();

    }

    @FXML
    public void removerItem () throws IOException {

        Catalogo itemSelecionado = tabelaCatalogo.getSelectionModel().getSelectedItem();


    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
