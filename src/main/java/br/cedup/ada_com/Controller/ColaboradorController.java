package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Define a fábrica de células da coluna colunaNome para exibir o nome completo do colaborador (nome e sobrenome)
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeColaborador() + " " + cellData.getValue().getSobrenome()));

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

    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }

}
