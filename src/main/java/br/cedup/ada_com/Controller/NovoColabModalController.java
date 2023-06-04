package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Colaborador;
import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NovoColabModalController implements Initializable {

    @FXML
    TextField nomefield;

    @FXML
    TextField sobrenomefield;

    @FXML
    TextField cargofield;

    @FXML
    TextField usuariofield;

    @FXML
    TextField senhafield;

    public static Colaborador colaborador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Colaborador colaboradorSecionado = NovoColabModalController.colaborador;

        if(colaboradorSecionado != null){

            cargofield.setText(Integer.toString(colaboradorSecionado.nivel));
            nomefield.setText(colaboradorSecionado.nomeColaborador);
            sobrenomefield.setText(colaboradorSecionado.sobrenome);
            usuariofield.setText(colaboradorSecionado.user);
            senhafield.setText(colaboradorSecionado.password);

        }
    }

    @FXML
    public void salvar(){
        int codigoUsuario = 0;
        String cargo = cargofield.getText();
        if (cargo.equals("Gestor")) {
            codigoUsuario = 2;
        } else if (cargo.equals("Vendedor")) {
            codigoUsuario = 1;
        } else {
            // Tratar o caso em que o valor do campo cargofield não é nem "Gestor" nem "Vendedor"
        }

        Colaborador novoColab = new Colaborador(
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
