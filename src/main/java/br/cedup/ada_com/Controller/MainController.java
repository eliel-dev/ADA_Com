package br.cedup.ada_com.Controller;

import br.cedup.ada_com.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.calendarfx.view.CalendarView;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    Button gerenciaUsuarioG;
    @FXML
    Button gerenciaCatG;
    @FXML
    Label nomeSobrenomeLogou;

    CalendarView vendasMes = new CalendarView();

    @FXML
    AnchorPane paneCalendario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomeSobrenomeLogou.setText("Bem vindo(a) " + LoginController.nomeLogou1);

        if (LoginController.nivelDeAcesso == 1) {
            gerenciaUsuarioG.setVisible(false);
            gerenciaCatG.setVisible(false);
        }

        CalendarView vendasMes = new CalendarView();
        // Adicione o CalendarView ao AnchorPane
        paneCalendario.getChildren().add(vendasMes);

        // Ancore o CalendarView nas bordas do AnchorPane
        AnchorPane.setTopAnchor(vendasMes, 0.0);
        AnchorPane.setRightAnchor(vendasMes, 0.0);
        AnchorPane.setBottomAnchor(vendasMes, 0.0);
        AnchorPane.setLeftAnchor(vendasMes, 0.0);
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
