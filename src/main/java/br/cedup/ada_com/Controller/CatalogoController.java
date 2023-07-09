package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.Catalogo;
import br.cedup.ada_com.model.dao.CatalogoDAO;
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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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

    @FXML
    Button bNovo;
    @FXML
    Button bEditar;
    @FXML
    Button bExcluir;
    @FXML
    Button bVoltar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.colunaId.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        this.colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        this.colunaTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo() == 1 ? "Produto" : "Serviço"));

        // Criar uma instância de NumberFormat para formatar os valores como moeda
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        // Definir o cellFactory da coluna colunaValor
        colunaValor.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // Formatar o valor como moeda e exibi-lo na célula
                        setText(format.format(item));
                    }
                }
            };
        });

        CatalogoDAO dao = new CatalogoDAO();
        try {
            List<Catalogo> itens = dao.getItens();
            tabelaCatalogo.getItems().addAll(itens);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tabelaCatalogo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                bEditar.setDisable(false);
                bExcluir.setDisable(false);
            }
        });

        //Botão verde:
        bNovo.setOnMouseEntered(e -> bNovo.setStyle("-fx-font-size: 18; -fx-background-color: green; -fx-border-color: green; -fx-border-radius: 3; -fx-border-width: 2;"));
        bNovo.setOnMouseExited(e -> bNovo.setStyle("-fx-font-size: 18; -fx-background-color: #000000;  -fx-border-color: green; -fx-border-radius: 3; -fx-border-width: 2;"));

        //Botão branco:
        Button[] branco = {bEditar, bVoltar};

        for (Button button : branco) {
            button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18; -fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18; -fx-background-color: #000000; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
        }

        //Botão vermelho:
        bExcluir.setOnMouseEntered(e -> bExcluir.setStyle("-fx-font-size: 18; -fx-background-color: red; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));
        bExcluir.setOnMouseExited(e -> bExcluir.setStyle("-fx-font-size: 18; -fx-background-color: #000000; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));
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
        Catalogo itemSelecionado = tabelaCatalogo.getSelectionModel().getSelectedItem();

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

    @FXML
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
