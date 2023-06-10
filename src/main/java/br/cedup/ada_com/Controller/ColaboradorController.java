package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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

    @FXML
    Button botaoRemover;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Define a fábrica de células da coluna colunaNome para exibir o nome completo do colaborador (nome e sobrenome)
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeColaborador() + " " + cellData.getValue().getSobrenome()));
        colunaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().toLowerCase()));

        // Define a fábrica de células da coluna colunaFuncao para exibir o cargo do colaborador com base no valor da propriedade nivel
        colunaFuncao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNivel() == 1 ? "Vendedor" : "Gestor"));


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

        // Adicionar um ouvinte de alteração à propriedade selectedItem da TableView
        tabelaColaborador.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Verificar se o novo colaborador selecionado é um gestor (nível 2)
            if (newSelection != null && newSelection.getNivel() == 2) {
                // Desativar o botão remover
                botaoRemover.setDisable(true);
            } else {
                // Ativar o botão remover
                botaoRemover.setDisable(false);
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
    public void editar() throws IOException, SQLException {
        Colaborador colaboradorSelecionado = tabelaColaborador.getSelectionModel().getSelectedItem();

        if (colaboradorSelecionado != null) {
            // Envia o produto para o model da edição
            NovoColabModalController.setColaborador(colaboradorSelecionado);

            // Abre o modal de edição e espera o usuário clicar OK
            HelloApplication.showModal("novoColabModal");

            // Obtém o colaborador alterado do modal de edição
            Colaborador colaboradorAlterado = NovoColabModalController.getColaborador();

            // Altera o colaborador original com o alterado
            colaboradorSelecionado.setNomeColaborador(colaboradorAlterado.getNomeColaborador());
            colaboradorSelecionado.setSobrenome(colaboradorAlterado.getSobrenome());
            colaboradorSelecionado.setUser(colaboradorAlterado.getUser());
            colaboradorSelecionado.setPassword(colaboradorAlterado.getPassword());


            // Atualiza a lista gráfica para aplicar as alterações do produto
            this.tabelaColaborador.refresh();

            // Salva o  produto no banco de dados
            ColaboradorDAO dao = new ColaboradorDAO();
            dao.update(colaboradorSelecionado);
        }

    }

    @FXML
    public void remover(){
        // Obtenha o colaborador selecionado na tabela
        Colaborador colaboradorSelecionado = tabelaColaborador.getSelectionModel().getSelectedItem();

        if (colaboradorSelecionado != null) {
            // Cria um Alert de confirmação
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Deseja remover " + colaboradorSelecionado.getNomeColaborador() + " " + colaboradorSelecionado.getSobrenome() + "?");

            // Exibe o Alert e aguarda a resposta do usuário
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
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
    }


    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }

}
