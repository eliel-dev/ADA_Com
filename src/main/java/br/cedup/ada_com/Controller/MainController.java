package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.RegistroVenda;
import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    Button gerenciaUsuarioG;
    @FXML
    Button gerenciaCatG;
    @FXML
    MenuButton gerenciarGeral;
    @FXML
    Button gerenciarCliente;
    @FXML
    Button gerenciarEndereco;
    @FXML
    Button gerenciarTaxaComissao;
    @FXML
    Button gerenciarExp;
    @FXML
    Label nomeSobrenomeLogou;

    @FXML
    AnchorPane paneCalendario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeSobrenomeLogou.setText("Bem vindo(a) " + LoginController.nomeLogou1);

        if (LoginController.nivelDeAcesso == 1) {
            gerenciaUsuarioG.setVisible(false);
            gerenciaCatG.setVisible(false);
            gerenciarGeral.setVisible(false);
            gerenciarCliente.setVisible(false);
            gerenciarEndereco.setVisible(false);
            gerenciarTaxaComissao.setVisible(false);
            gerenciarExp.setVisible(false);
        }
        //Talvez no futuro
        gerenciarCliente.setDisable(true);
        gerenciarEndereco.setDisable(true);
        gerenciarTaxaComissao.setDisable(true);
        gerenciarExp.setDisable(true);

    }

    private List<RegistroVenda> getCompras(LocalDate date) {
        // Implemente este método para retornar as compras para uma determinada data
        return new ArrayList<>();
    }


    @FXML
    public void gerenciaColaborador() throws IOException {
        HelloApplication.setRoot("colaborador-view");

    }
    @FXML
    public void gerenciaCatalogo() throws IOException {
        HelloApplication.setRoot("catalogo-view");

    }
    @FXML
    public void relatorio() throws IOException {
        HelloApplication.setRoot("relatorio-view");

    }

    @FXML
    public void registrarVenda() throws IOException {
        HelloApplication.setRoot("registraVenda-view");

    }

    @FXML
    public void sair() throws IOException {
        HelloApplication.setRoot("login-view");
    }
}
