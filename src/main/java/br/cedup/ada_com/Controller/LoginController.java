package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.DAO.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public static int nivelDeAcesso;

    @FXML
    TextField usuarioField;

    @FXML
    PasswordField senhaField;

    @FXML
    Label labelEntrar;

    @FXML
    public void entrar() throws IOException, SQLException {
        Colaborador loginUser = new Colaborador(0, usuarioField.getText(), senhaField.getText());
        Colaborador usuarioExiste = new ColaboradorDAO().existe(loginUser);

        if (usuarioExiste == null) {
            labelEntrar.setText("Usuário ou senha incorretos!");
        } else {
            nivelDeAcesso = usuarioExiste.getNivel();
            HelloApplication.setRoot("main-view");
        }
    }
}
