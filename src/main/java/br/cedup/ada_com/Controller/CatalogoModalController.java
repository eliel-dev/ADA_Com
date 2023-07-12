package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.Catalogo;
import br.cedup.ada_com.model.dao.CatalogoDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CatalogoModalController implements Initializable {

    @FXML
    ComboBox comboCategoria;
    @FXML
    RadioButton radioButtonProduto;
    @FXML
    RadioButton radioButtonServico;
    @FXML
    TextField nomeItem;
    @FXML
    TextField preco;
    @FXML
    Label valorInvalido;

    @FXML
    Button bSalvarModal;
    @FXML
    Button bCancelarModal;

    @FXML
    Label aviso1;
    @FXML
    Label aviso2;

    public static Catalogo catalogoItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label[] alerta ={aviso1, aviso2};

        // Adicionar um ouvinte ao texto do campo nomeItem
        nomeItem.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao texto do campo preco
        preco.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao valor selecionado do comboCategoria
        comboCategoria.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao valor selecionado do ComboBox
        comboCategoria.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Se o novo valor não estiver na lista de opções do ComboBox, adicione-o
            if (!comboCategoria.getItems().contains(newValue)) {
                comboCategoria.getItems().add(newValue);
            }
        });

        // Verificar se um item foi enviado para edição
        if (catalogoItem != null) {
            // Definir os valores dos campos com os valores do item
            comboCategoria.setValue(catalogoItem.getCategoria());
            nomeItem.setText(catalogoItem.getNome());
            preco.setText(String.valueOf(catalogoItem.getValor()));
            if (catalogoItem.getTipo() == 1) {
                radioButtonProduto.setSelected(true);
            } else {
                radioButtonServico.setSelected(true);
            }
        }

        CatalogoDAO catalogoDAO = new CatalogoDAO();

        // Consultar o banco de dados para recuperar as categorias
        List<String> categoria = null;
        try {
            categoria = catalogoDAO.getCategorias();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Adicionar as categorias à lista de opções do ComboBox
        comboCategoria.getItems().addAll(categoria);

        // Habilitar a edição no ComboBox
        comboCategoria.setEditable(true);

        // Criar um novo ToggleGroup para os RadioButtons
        ToggleGroup group = new ToggleGroup();

        // Adicionar os RadioButtons ao ToggleGroup
        radioButtonProduto.setToggleGroup(group);
        radioButtonServico.setToggleGroup(group);

        // Selecionar o radioButtonProduto por padrão
        radioButtonProduto.setSelected(true);

        // Adicionar um ouvinte ao valor do campo TextField
        preco.textProperty().addListener((observable, oldValue, newValue) -> {
            // Verificar se o novo valor é um número válido
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                // Se não for um número válido, alterar a cor do texto para vermelho
                preco.setStyle("-fx-text-fill: red;");
                // Exibir a mensagem de valor inválido no Label valorInvalido
                valorInvalido.setVisible(true);
                valorInvalido.setText("Valor inválido!");
            } else {
                // Se for um número válido, alterar a cor do texto para preto
                preco.setStyle("-fx-text-fill: black;");
                // Limpar o texto do Label valorInvalido
                valorInvalido.setText("");
            }
        });

        //Botão verde:
        bSalvarModal.setOnMouseEntered(e -> bSalvarModal.setStyle("-fx-background-color: green; -fx-border-color: green;"));
        bSalvarModal.setOnMouseExited(e -> bSalvarModal.setStyle("-fx-background-color: #0c0c0c;  -fx-border-color: green;"));

        //Botão vermelho:
        bCancelarModal.setOnMouseEntered(e -> bCancelarModal.setStyle("-fx-background-color: red; -fx-border-color: red;"));
        bCancelarModal.setOnMouseExited(e -> bCancelarModal.setStyle("-fx-background-color: #0c0c0c; -fx-border-color: red;"));
    }

    public static void setCatalogoItem(Catalogo catalogo) {
        CatalogoModalController.catalogoItem = catalogo;
    }

    public static Catalogo getCatalogoItem() {
        return CatalogoModalController.catalogoItem;
    }

    private void verificarCamposVazios(Label[] alerta) {
        if (nomeItem.getText().isEmpty() || preco.getText().isEmpty() || comboCategoria.getEditor().getText().isEmpty()) {
            for (Label label : alerta) {
                label.setVisible(true);
            }
            bSalvarModal.setDisable(true);
        } else {
            for (Label label : alerta) {
                label.setVisible(false);
            }
            bSalvarModal.setDisable(false);
        }
    }

    @FXML
    public void salvaritem() {
        if (catalogoItem == null ||
                !nomeItem.getText().equals(catalogoItem.getNome()) ||
                !comboCategoria.getValue().equals(catalogoItem.getCategoria())) {
            // Verificar se já existe um item com o mesmo nome e categoria
            CatalogoDAO catalogoDAO = new CatalogoDAO();
            try {
                if (catalogoDAO.itemExiste(nomeItem.getText(), (String) comboCategoria.getValue())) {
                    // Exibir mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Item duplicado");
                    alert.setContentText("Já existe um item com o mesmo nome e categoria!");
                    alert.showAndWait();
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Obter os valores dos campos
        String categoria = comboCategoria.getValue().toString();
        String nome = nomeItem.getText();
        double valor = Double.parseDouble(preco.getText());
        int tipo = radioButtonProduto.isSelected() ? 1 : 2;

        // Criar um novo objeto Catalogo
        Catalogo novoItem = new Catalogo(0, nome, valor, categoria, tipo);

        // Atribuir o novo item à variável estática catalogoItem
        catalogoItem = novoItem;

        // Fechar a janela modal
        HelloApplication.closeCurrentWindow();
    }

    @FXML
    public void cancelar(){
        HelloApplication.closeCurrentWindow();
    }
}
