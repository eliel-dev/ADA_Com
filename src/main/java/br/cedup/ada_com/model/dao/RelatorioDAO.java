package br.cedup.ada_com.model.dao;
import br.cedup.ada_com.ConnectionSingleton;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class RelatorioDAO {
    public Map<LocalDate, Integer> getNumVendasDiaGeral() throws SQLException {
        Map<LocalDate, Integer> vendasPorDia = new LinkedHashMap<>();

        String sql = "SELECT DATE(data_venda) AS data, COUNT(*) AS numero_vendas FROM registrovenda GROUP BY DATE(data_venda)";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LocalDate data = rs.getDate("data").toLocalDate();
                int numeroVendas = rs.getInt("numero_vendas");
                vendasPorDia.put(data, numeroVendas);
            }
        }
        return vendasPorDia;
    }

    public Map<LocalDate, Integer> getNumVendasDiaVendedor(int idVendedor) throws SQLException {
        Map<LocalDate, Integer> vendasPorDia = new LinkedHashMap<>();

        String sql = "SELECT DATE(data_venda) AS data, COUNT(*) AS numero_vendas FROM registrovenda WHERE Colaborador_ID = ? GROUP BY DATE(data_venda)";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idVendedor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate data = rs.getDate("data").toLocalDate();
                    int numeroVendas = rs.getInt("numero_vendas");
                    vendasPorDia.put(data, numeroVendas);
                }
            }
        }
        return vendasPorDia;
    }

    public Map<String, Integer> getNumVendasPorColaboradorVendedor() throws SQLException {
        Map<String, Integer> numVendasPorColaborador = new LinkedHashMap<>();

        String sql = "SELECT colaborador.NomeColaborador, COUNT(*) AS numero_vendas " +
                "FROM colaborador " +
                "INNER JOIN registrovenda ON colaborador.Colaborador_ID = registrovenda.Colaborador_ID " +
                "WHERE colaborador.Nivel = 1 " +
                "GROUP BY colaborador.Colaborador_ID";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nomeColaborador = rs.getString("NomeColaborador");
                int numeroVendas = rs.getInt("numero_vendas");
                numVendasPorColaborador.put(nomeColaborador, numeroVendas);
            }
        }
        return numVendasPorColaborador;
    }

    public Map<String, Integer> getNumVendasPorItemCatalogo() throws SQLException {
        Map<String, Integer> numVendasPorItem = new LinkedHashMap<>();

        String sql = "SELECT catalogo.nome, SUM(registrovenda_item.Quantidade) AS numero_vendas " +
                "FROM catalogo " +
                "INNER JOIN registrovenda_item ON catalogo.Item_ID = registrovenda_item.catalogo_Item_ID " +
                "GROUP BY catalogo.Item_ID";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nomeItem = rs.getString("nome");
                int numeroVendas = rs.getInt("numero_vendas");
                numVendasPorItem.put(nomeItem, numeroVendas);
            }
        }
        return numVendasPorItem;
    }

}

