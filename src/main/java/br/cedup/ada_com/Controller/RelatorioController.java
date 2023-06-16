package br.cedup.ada_com.Controller;

import br.cedup.ada_com.DAO.RelatorioDAO;
import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.Venda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class RelatorioController implements Initializable {

    @FXML
    ComboBox<String> comboRelatorios;

    private RelatorioDAO relatorioDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.relatorioDAO = new RelatorioDAO();
        if (LoginController.nivelDeAcesso == 2) {

        }
    }

    @FXML
    public void onComboBoxChanged() throws SQLException {
        String selectedReport = comboRelatorios.getValue();
        switch (selectedReport) {
            case "Total de Vendas por Vendedor":
                List<Venda> vendas = relatorioDAO.gerarRelatorioTotalVendasPorVendedor();
                break;
            case "Total de Comissão por Vendedor":
                // ...
                break;
            // Adicione cases para suas outras views aqui
        }
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
