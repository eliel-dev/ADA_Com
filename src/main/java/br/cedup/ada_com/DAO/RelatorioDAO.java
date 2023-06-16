package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.Venda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<Venda> gerarRelatorioTotalVendasPorVendedor() throws SQLException {
        List<Venda> vendas = new ArrayList<>();

        String sql = "SELECT * FROM TotalVendasPorVendedor";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nomeColaborador = rs.getString("NomeColaborador");
                double totalVendas = rs.getDouble("TotalVendas");

                vendas.add(new Venda(nomeColaborador, totalVendas));
            }
        }return vendas;
    }
}

