package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.Colaborador;
import br.cedup.ada_com.model.dao.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    Button bSalvar;
    @FXML
    Button bCancelar;

    @FXML
    Label aviso;
    @FXML
    Label aviso1;
    @FXML
    Label aviso2;

    public static Colaborador colaborador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label[] alerta ={aviso, aviso1, aviso2};

        // Adicionar um ouvinte ao texto do campo nomefield
        nomefield.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao texto do campo sobrenomefield
        sobrenomefield.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao texto do campo usuariofield
        usuariofield.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao texto do campo senhafield
        senhafield.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

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

        //Botão verde:
        bSalvar.setOnMouseEntered(e -> bSalvar.setStyle("-fx-font-size: 18; -fx-background-color: green; -fx-border-color: green; -fx-border-radius: 3; -fx-border-width: 2;"));
        bSalvar.setOnMouseExited(e -> bSalvar.setStyle("-fx-font-size: 18; -fx-background-color: #000000;  -fx-border-color: green; -fx-border-radius: 3; -fx-border-width: 2;"));

        //Botão vermelho:
        bCancelar.setOnMouseEntered(e -> bCancelar.setStyle("-fx-font-size: 18; -fx-background-color: red; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));
        bCancelar.setOnMouseExited(e -> bCancelar.setStyle("-fx-font-size: 18; -fx-background-color: #000000; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));
    }

    public static void setColaborador (Colaborador colaborador){
        NovoColabModalController.colaborador = colaborador;
    }

    public static Colaborador getColaborador (){
        return NovoColabModalController.colaborador;
    }

    @FXML
    public void salvar() {
        // Verificar se já existe um colaborador com a mesma combinação de nome e sobrenome ou com o mesmo nome de usuário
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        try {
            if (colaboradorDAO.colaboradorExiste(nomefield.getText(), sobrenomefield.getText(), usuariofield.getText())) {
                // Verificar se é um novo cadastro
                if (NovoColabModalController.colaborador.getColaboradorId() == 0) {
                    // Exibir mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Cliente duplicado");
                    alert.setContentText("Já existe um cliente com a mesma combinação de nome e sobrenome ou com o mesmo CPF/CNPJ!");
                    alert.showAndWait();
                    return;
                }
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


    private void verificarCamposVazios(Label[] alerta) {
        if (nomefield.getText().isEmpty() || sobrenomefield.getText().isEmpty() || usuariofield.getText().isEmpty() || senhafield.getText().isEmpty()) {
            for (Label label : alerta) {
                label.setVisible(true);
            }
            bSalvar.setDisable(true);
        } else {
            for (Label label : alerta) {
                label.setVisible(false);
            }
            bSalvar.setDisable(false);
        }
    }

    @FXML
    public void cancelar() throws IOException {
        HelloApplication.closeCurrentWindow();
    }
}
