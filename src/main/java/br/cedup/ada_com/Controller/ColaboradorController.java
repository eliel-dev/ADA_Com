package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ColaboradorController implements Initializable {


    @FXML
    TableView<Colaborador> tabelaColaborador;

    @FXML
    TableColumn<Colaborador, String> colunaNome;

    @FXML
    TableColumn<Colaborador, String> colunaFuncao;

    @FXML
    TableColumn<Colaborador, String> colunaUsuario;

    @FXML
    Label labelCopia;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Define a fábrica de células da coluna colunaNome para exibir o nome completo do colaborador (nome e sobrenome)
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeColaborador() + " " + cellData.getValue().getSobrenome()));

        // Define a fábrica de células da coluna colunaFuncao para exibir o cargo do colaborador com base no valor da propriedade nivel
        colunaFuncao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNivel() == 1 ? "Vendedor" : "Gestor"));

        colunaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().toLowerCase()));

        // Cria um objeto ColaboradorDAO para acessar o banco de dados
        ColaboradorDAO dao = new ColaboradorDAO();
        try {
            // Obtém os dados dos colaboradores do banco de dados
            List<Colaborador> colaboradores = dao.getColaboradores();
            // Adiciona os dados dos colaboradores ao TableView
            tabelaColaborador.getItems().addAll(colaboradores);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Código que permite que um dado seja copiado da tabela
        tabelaColaborador.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.C) {
                ObservableList<TablePosition> selectedCells = tabelaColaborador.getSelectionModel().getSelectedCells();
                if (!selectedCells.isEmpty()) {
                    TablePosition<?, ?> pos = selectedCells.get(0);
                    int row = pos.getRow();
                    TableColumn<?, ?> col = pos.getTableColumn();
                    String cellContent = (String) col.getCellObservableValue(row).getValue();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(cellContent);
                    Clipboard.getSystemClipboard().setContent(content);
                    // Atualiza o texto do label para informar ao usuário qual célula foi copiada
                    labelCopia.setText("Conteúdo da célula copiado: " + col.getText() + " - Linha " + (row + 1));
                }
            }
        });
    }

    @FXML
    public void novo() throws IOException {
        NovoColabModalController.colaborador = null;

        HelloApplication.showModal("novoColabModal");

        Colaborador novoColaborador = NovoColabModalController.colaborador;

        if (novoColaborador != null){
            ColaboradorDAO dao = new ColaboradorDAO();
            try {
                dao.inserirColaborador(novoColaborador);
                tabelaColaborador.getItems().add(novoColaborador);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void editar(){

    }

    @FXML
    public void remover(){
        // Obtenha o colaborador selecionado na tabela
        Colaborador colaboradorSelecionado = tabelaColaborador.getSelectionModel().getSelectedItem();

        if (colaboradorSelecionado != null) {
            ColaboradorDAO dao = new ColaboradorDAO();
            try {
                // Remova o colaborador do banco de dados
                dao.removerColaborador(colaboradorSelecionado);
                // Remova o colaborador da tabela
                tabelaColaborador.getItems().remove(colaboradorSelecionado);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }

}
