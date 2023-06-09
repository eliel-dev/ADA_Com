package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Catalogo;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CatalogoModalController implements Initializable {

    @FXML
    ComboBox comboCategoria;
    @FXML
    RadioButton radioButtonProduto;
    @FXML
    RadioButton radioButtonServiço;
    @FXML
    TextField nomeItem;
    @FXML
    TextField preco;

    public static Catalogo catalogoItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Habilitar a edição no ComboBox
        comboCategoria.setEditable(true);

        // Adicionar um ouvinte ao valor selecionado do ComboBox
        comboCategoria.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Se o novo valor não estiver na lista de opções do ComboBox, adicione-o
            if (!comboCategoria.getItems().contains(newValue)) {
                comboCategoria.getItems().add(newValue);
            }
        });

        // Adicionar algumas categorias de exemplo à lista de opções do ComboBox
        comboCategoria.getItems().addAll("Eletrônicos", "Roupas", "Alimentos");
    }


    @FXML
    public void salvar() {
        // Verificar se todos os campos estão preenchidos
        if (comboCategoria.getValue() == null ||
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
