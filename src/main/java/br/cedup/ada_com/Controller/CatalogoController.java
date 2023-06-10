package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Catalogo;
import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.CatalogoDAO;
import br.cedup.ada_com.DAO.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
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
    public void editarItem() throws IOException, SQLException {
        System.out.println("Método editarItem chamado");

        Catalogo itemSelecionado = tabelaCatalogo.getSelectionModel().getSelectedItem();
        System.out.println("Item selecionado: " + itemSelecionado);

        if (itemSelecionado != null) {
            // Envia o produto para o model da edição
            CatalogoModalController.setCatalogoItem(itemSelecionado);

            // Abre o modal de edição e espera o usuário clicar OK
            HelloApplication.showModal("catalogoModal");

            // Obtém o produto alterado do modal de edição
            Catalogo itemAlterado = CatalogoModalController.getCatalogoItem();
            System.out.println("Item alterado: " + itemAlterado);

            // Altera o produto original com o alterado
            itemSelecionado.setTipo(itemAlterado.getTipo());
            itemSelecionado.setNome(itemAlterado.getNome());
            itemSelecionado.setCategoria(itemAlterado.getCategoria());
            itemSelecionado.setValor(itemAlterado.getValor());

            // Atualiza a lista gráfica para aplicar as alterações do produto
            this.tabelaCatalogo.refresh();

            // Salva o  produto no banco de dados
            CatalogoDAO dao = new CatalogoDAO();
            dao.atualizarItem(itemSelecionado);
        }
    }


    @FXML
    public void removerItem() {
        // Obter o item selecionado na tabela de itens
        Catalogo itemSelecionado = tabelaCatalogo.getSelectionModel().getSelectedItem();

        // Verificar se um item foi selecionado
        if (itemSelecionado == null) {
            // Exibir mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum item selecionado");
            alert.setContentText("Por favor, selecione um item antes de remover.");
            alert.showAndWait();
            return;
        }

        // Exibir mensagem de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Remover item");
        alert.setContentText("Tem certeza de que deseja remover o item selecionado?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remover o item selecionado do banco de dados
            CatalogoDAO catalogoDAO = new CatalogoDAO();
            try {
                catalogoDAO.removerItem(itemSelecionado);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Atualizar a tabela de itens
            atualizarTabelaItens();
        }
    }


    private void atualizarTabelaItens() {
        // Consultar o banco de dados para obter a lista atualizada de itens
        CatalogoDAO catalogoDAO = new CatalogoDAO();
        List<Catalogo> itens;
        try {
            itens = catalogoDAO.getItens();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Atualizar a lista de itens exibida na tabela
        ObservableList<Catalogo> data = FXCollections.observableArrayList(itens);
        tabelaCatalogo.setItems(data);
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
