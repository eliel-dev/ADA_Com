package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField usuarioField;

    @FXML
    PasswordField senhaField;

    @FXML
    Label labelEntrar;

    @FXML
    public void entrar() throws IOException {

        Colaborador vendedor = new Colaborador();
        vendedor.user = "vendedor";
        vendedor.password = "123";
        vendedor.codigo = 1;

        Colaborador gestor = new Colaborador();
        gestor.user = "chefe";
        gestor.password = "123";
        gestor.codigo = 2;


        if (usuarioField.getText().equals(vendedor.user) && senhaField.getText().equals(vendedor.password)) {
            //System.out.println("Entrando como vendedor");
            HelloApplication.setRoot("mainVendedor-view");
        } else if (usuarioField.getText().equals(gestor.user) && senhaField.getText().equals(gestor.password)) {
            //System.out.println("Entrando como gestor");
            HelloApplication.setRoot("mainChefe-view");
        } else {
            labelEntrar.setText("Usuário e/ou senha incorretos! Tente novamente!");
        }
    }

}
