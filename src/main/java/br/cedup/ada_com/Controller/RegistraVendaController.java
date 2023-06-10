package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Catalogo;
import br.cedup.ada_com.DAO.CatalogoDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistraVendaController implements Initializable {

    @FXML
    ComboBox <Catalogo> produtosServicos;
    @FXML
    TextField quantidadeProduto;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Criar uma instância do DAO para acessar o banco de dados
        CatalogoDAO catalogoDAO = new CatalogoDAO();

        // Consultar o banco de dados para recuperar a lista de itens
        List<Catalogo> itens = null;
        try {
            itens = catalogoDAO.getItens();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Adicionar a lista de itens à lista de opções do ComboBox
        produtosServicos.getItems().addAll(itens);

        // Definir um CellFactory personalizado para o ComboBox
        produtosServicos.setCellFactory(new Callback<ListView<Catalogo>, ListCell<Catalogo>>() {
            @Override
            public ListCell<Catalogo> call(ListView<Catalogo> param) {
                return new ListCell<Catalogo>() {
                    @Override
                    protected void updateItem(Catalogo item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNome() + " - R$: " + item.getValor());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        // Definir um CellFactory personalizado para o botão do ComboBox
        produtosServicos.setButtonCell(new ListCell<Catalogo>() {
            @Override
            protected void updateItem(Catalogo item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNome() + " - R$: " + item.getValor());
                } else {
                    setText(null);
                }
            }
        });

        // Adicionar um ouvinte ao valor selecionado do ComboBox
        produtosServicos.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Verificar se o item selecionado é um serviço
            if (newValue != null && newValue.getTipo() == 2) {
                // Desativar o TextField quantidadeProduto
                quantidadeProduto.setDisable(true);
            } else {
                // Ativar o TextField quantidadeProduto
                quantidadeProduto.setDisable(false);
            }
        });
    }



    @FXML
    public void registrar () throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja registrar a experiência de venda?");
        alert.setContentText(null);

        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(sim, nao);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == sim){
            HelloApplication.showModal("experienciaModalCliente-view");

        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    //Volta para tela principal de vendedor
    @FXML
    public void fechar() throws IOException {
        HelloApplication.setRoot("Main-view");
    }




}
