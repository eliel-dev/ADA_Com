package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.Colaborador;
import br.cedup.ada_com.model.dao.ColaboradorDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static int nivelDeAcesso;
    public static String nomeLogou1;
    public static int colaboradorID;

    @FXML
    Button entrarButton;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entrarButton.setOnMouseEntered(e -> entrarButton.setStyle("-fx-font-size: 18; -fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
        entrarButton.setOnMouseExited(e -> entrarButton.setStyle("-fx-font-size: 18; -fx-background-color: #0c0c0c; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
    }
}
