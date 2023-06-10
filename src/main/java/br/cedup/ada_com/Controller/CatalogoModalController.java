package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Catalogo;
import br.cedup.ada_com.DAO.CatalogoDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

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
    Button salvar;
    @FXML
    Label valorInvalido;

    public static Catalogo catalogoItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Adicionar um ouvinte ao valor selecionado do ComboBox
        comboCategoria.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Ouvinte chamado com novo valor: " + newValue);
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
                System.out.println("radioButtonProduto selecionado");
            } else {
                radioButtonServico.setSelected(true);
                System.out.println("radioButtonServico selecionado");
            }
        }

        CatalogoDAO catalogoDAO = new CatalogoDAO();

        // Consultar o banco de dados para recuperar as categorias
        List<String> categorias = null;
        try {
            categorias = catalogoDAO.getCategorias();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Adicionar as categorias à lista de opções do ComboBox
        comboCategoria.getItems().addAll(categorias);

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

                // Desativar o botão salvar
                salvar.setDisable(true);

                // Exibir a mensagem de valor inválido no Label valorInvalido
                valorInvalido.setText("Valor inválido!");
            } else {
                // Se for um número válido, alterar a cor do texto para preto
                preco.setStyle("-fx-text-fill: black;");

                // Ativar o botão salvar
                salvar.setDisable(false);

                // Limpar o texto do Label valorInvalido
                valorInvalido.setText("");
            }
        });
    }

    public static void setCatalogoItem(Catalogo catalogo) {
        CatalogoModalController.catalogoItem = catalogo;
    }

    public static Catalogo getCatalogoItem() {
        return CatalogoModalController.catalogoItem;
    }

    @FXML
    public void salvar() {
        System.out.println("Método salvar chamado");

        // Verificar se todos os campos estão preenchidos
        if ((catalogoItem == null && comboCategoria.getValue() == null) ||
                nomeItem.getText().isEmpty() ||
                preco.getText().isEmpty()) {
            // Exibir mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos vazios");
            alert.setContentText("Por favor, preencha todos os campos antes de salvar.");
            alert.showAndWait();
            return;
        }

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

        System.out.println("Valores dos campos:");
        System.out.println("Categoria: " + categoria);
        System.out.println("Nome: " + nome);
        System.out.println("Valor: " + valor);
        System.out.println("Tipo: " + tipo);

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
