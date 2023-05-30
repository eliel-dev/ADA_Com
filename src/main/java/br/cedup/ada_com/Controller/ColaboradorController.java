package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ColaboradorController implements Initializable {

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("mainChefe-view");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
