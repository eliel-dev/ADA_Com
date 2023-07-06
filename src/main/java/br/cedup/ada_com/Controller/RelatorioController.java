package br.cedup.ada_com.controller;

import br.cedup.ada_com.model.dao.RelatorioDAO;
import br.cedup.ada_com.HelloApplication;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;


public class RelatorioController implements Initializable {
    @FXML
    ComboBox <String> comboRelatorios;
    @FXML
    Button btnGerarRelatorios;

    @FXML
    BarChart<String, Number> rteste;

    CategoryAxis xAxis = new CategoryAxis();

    RelatorioDAO relatorioDAO = new RelatorioDAO();
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (LoginController.nivelDeAcesso == 2) {
            comboRelatorios.getItems().addAll("Vendas no mes", "Vendas por colaborador", "Vendas por itens");
        } else {
            comboRelatorios.getItems().add("Vendas no mes");
        }

        btnGerarRelatorios.setOnAction(event -> {
            String opcaoSelecionada = comboRelatorios.getValue();
            if ("Vendas no mes".equals(opcaoSelecionada)) {
                try {
                    atualizarGraficoVendasPorDia();
                } catch (SQLException e) {
                    // Tratar exceção
                }
            } else if ("Vendas por colaborador".equals(opcaoSelecionada)) {
                try {
                    atualizarGraficoNumVendasPorColaboradorVendedor();
                } catch (SQLException e) {
                    // Tratar exceção
                }
            } else if ("Vendas por itens".equals(opcaoSelecionada)) {
                try {
                    atualizarGraficoNumVendasPorItemCatalogo();
                } catch (SQLException e) {
                    // Tratar exceção
                }
            }
        });
    }

    @FXML
    private void atualizarGraficoVendasPorDia() throws SQLException {
        // Recuperar informações sobre vendas por dia do banco de dados
        Map<LocalDate, Integer> vendasPorDia;
        if (LoginController.nivelDeAcesso == 2) {
            // Se o nível de acesso do colaborador logado for 2 (gestor), recuperar informações sobre vendas por dia para todos os vendedores
            vendasPorDia = relatorioDAO.getNumVendasDiaGeral();
        } else {
            // Caso contrário, recuperar informações sobre vendas por dia para o vendedor logado
            vendasPorDia = relatorioDAO.getNumVendasDiaVendedor(LoginController.colaboradorID);
        }

        // Criar um formatador de data para formatar as datas das vendas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar uma série de dados para o gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Vendas por Dia");

        // Adicionar pontos de dados à série para cada entrada no mapa de vendas por dia
        for (Map.Entry<LocalDate, Integer> entry : vendasPorDia.entrySet()) {
            // Formatar a data da venda
            String data = entry.getKey().format(formatter);
            // Recuperar o número de vendas para a data
            int numeroVendas = entry.getValue();
            // Adicionar um novo ponto de dados à série
            series.getData().add(new XYChart.Data<>(data, numeroVendas));
        }

        // Limpar os dados existentes do gráfico e adicionar a nova série de dados
        rteste.getData().clear();
        rteste.getData().add(series);
        rteste.setAnimated(false);
    }

    @FXML
    private void atualizarGraficoNumVendasPorColaboradorVendedor() throws SQLException {
        // Recuperar informações sobre o número de vendas por colaborador vendedor do banco de dados
        Map<String, Integer> numVendasPorColaborador = relatorioDAO.getNumVendasPorColaboradorVendedor();

        // Criar uma série de dados para o gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Número de Vendas por Colaborador Vendedor");

        // Adicionar pontos de dados à série para cada entrada no mapa de número de vendas por colaborador vendedor
        for (Map.Entry<String, Integer> entry : numVendasPorColaborador.entrySet()) {
            String nomeColaborador = entry.getKey();
            int numeroVendas = entry.getValue();
            series.getData().add(new XYChart.Data<>(nomeColaborador, numeroVendas));
        }

        // Limpar os dados existentes do gráfico e adicionar a nova série de dados
        rteste.getData().clear();
        rteste.getData().add(series);
        rteste.setAnimated(false);
    }


    @FXML
    private void atualizarGraficoNumVendasPorItemCatalogo() throws SQLException {
        // Recuperar informações sobre o número de vendas por item do catálogo do banco de dados
        Map<String, Integer> numVendasPorItem = relatorioDAO.getNumVendasPorItemCatalogo();

        // Criar uma série de dados para o gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Número de Vendas por Item do Catálogo");

        // Adicionar pontos de dados à série para cada entrada no mapa de número de vendas por item do catálogo
        for (Map.Entry<String, Integer> entry : numVendasPorItem.entrySet()) {
            String nomeItem = entry.getKey();
            int numeroVendas = entry.getValue();
            series.getData().add(new XYChart.Data<>(nomeItem, numeroVendas));
        }

        // Limpar os dados existentes do gráfico e adicionar a nova série de dados
        rteste.getData().clear();
        rteste.getData().add(series);
        rteste.setAnimated(false);
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }

}
