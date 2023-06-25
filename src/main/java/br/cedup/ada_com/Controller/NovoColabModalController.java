package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.passay.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NovoColabModalController implements Initializable {

    @FXML
    TextField nomefield;
    @FXML
    TextField sobrenomefield;
    @FXML
    TextField usuariofield;
    @FXML
    PasswordField senhafield;
    @FXML
    RadioButton radioButtonVendedor;
    @FXML
    RadioButton radioButtonGestor;
    @FXML
    ProgressBar passwordStrengthBar;

    public static Colaborador colaborador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomefield.textProperty().addListener((observable, oldValue, newValue) -> {
            usuariofield.setText(newValue.toLowerCase() + "." + sobrenomefield.getText().toLowerCase());
        });

        sobrenomefield.textProperty().addListener((observable, oldValue, newValue) -> {
            usuariofield.setText(nomefield.getText().toLowerCase() + "." + newValue.toLowerCase());
        });

        Colaborador colaboradorSecionado = NovoColabModalController.colaborador;

        if(colaboradorSecionado != null){

            nomefield.setText(colaboradorSecionado.nomeColaborador);
            sobrenomefield.setText(colaboradorSecionado.sobrenome);
            usuariofield.setText(colaboradorSecionado.user);
            senhafield.setText(colaboradorSecionado.password);

        }

        // Primeira versão: radioButtonVendedor sempre selecionado e desabilitado
        radioButtonVendedor.setSelected(true);
        radioButtonVendedor.setDisable(true);


    }




    public static void setColaborador (Colaborador colaborador){
        NovoColabModalController.colaborador = colaborador;
    }

    public static Colaborador getColaborador (){
        return NovoColabModalController.colaborador;
    }

    @FXML
    public void salvar() throws SQLException {
        // Verificar se todos os campos estão preenchidos
        if (nomefield.getText().isEmpty() ||
                sobrenomefield.getText().isEmpty() ||
                usuariofield.getText().isEmpty() ||
                senhafield.getText().isEmpty()) {
            // Exibir mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos vazios");
            alert.setContentText("Por favor, preencha todos os campos antes de salvar.");
            alert.showAndWait();
            return;
        }

        // Verificar se já existe um colaborador com a mesma combinação de nome e sobrenome ou com o mesmo nome de usuário
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        try {
            if (colaboradorDAO.colaboradorExiste(nomefield.getText(), sobrenomefield.getText(), usuariofield.getText())) {
                // Exibir mensagem de erro
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Colaborador duplicado");
                alert.setContentText("Já existe um colaborador com a mesma combinação de nome e sobrenome ou com o mesmo nome de usuário!");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int codigoUsuario = 1;

        usuariofield.setText(nomefield.getText() + "." + sobrenomefield.getText());

        Colaborador novoColab = new Colaborador(
                0,
                codigoUsuario,
                nomefield.getText(),
                sobrenomefield.getText(),
                usuariofield.getText(),
                senhafield.getText());

        colaborador = novoColab;

        HelloApplication.closeCurrentWindow();
    }

    @FXML
    public void cancelar() throws IOException {
        HelloApplication.closeCurrentWindow();
    }
}
