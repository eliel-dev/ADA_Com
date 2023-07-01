package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.Relatorio;
import br.cedup.ada_com.model.dao.RelatorioDAO;
import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.RegistroVenda;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class RelatorioController implements Initializable {
    @FXML
    LineChart rteste;

    RelatorioDAO relatorioDAO = new RelatorioDAO();
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    private void atualizarGraficoVendasPorDia() throws SQLException {
        // Recuperar informações sobre vendas por dia do banco de dados
        Map<LocalDate, Double> vendasPorDia = relatorioDAO.getVendasPorDia();

        // Criar um formatador de data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar uma série de dados para o gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Vendas por Dia");
        for (Map.Entry<LocalDate, Double> entry : vendasPorDia.entrySet()) {
            String data = entry.getKey().format(formatter);
            double totalVendas = entry.getValue();
            series.getData().add(new XYChart.Data<>(data, totalVendas));
        }

        // Adicionar a série de dados ao gráfico
        rteste.getData().clear();
        rteste.getData().add(series);
    }


    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
