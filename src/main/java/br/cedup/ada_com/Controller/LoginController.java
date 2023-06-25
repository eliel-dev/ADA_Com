package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.Colaborador;
import br.cedup.ada_com.model.dao.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public static int nivelDeAcesso;
    public static String nomeLogou1;
    public static int colaboradorID;

    @FXML
    TextField usuarioField;
    @FXML
    PasswordField senhaField;
    @FXML
    Label labelEntrar;

    @FXML
    public void entrar() throws IOException, SQLException {
        Colaborador loginUser = new Colaborador(0, 0, nomeLogou1, nomeLogou1, usuarioField.getText(), senhaField.getText());
        Colaborador usuarioExiste = new ColaboradorDAO().loginUser(loginUser);

        if (usuarioExiste == null) {
            labelEntrar.setText("Usuário ou senha incorretos!");
        } else {
            nivelDeAcesso = usuarioExiste.getNivel();
            nomeLogou1 = usuarioExiste.getNomeColaborador() + " " + usuarioExiste.getSobrenome() + ".";
            colaboradorID = usuarioExiste.getColaboradorId();
            HelloApplication.setRoot("main-view");
        }
    }
}
