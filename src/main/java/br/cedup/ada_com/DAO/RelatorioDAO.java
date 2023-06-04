package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.Venda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<Venda> gerarRelatorioTotalVendasPorVendedor() {
        List<Venda> vendas = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // Abrir uma conexão
            Connection connection = ConnectionSingleton.getConnection();

            // Executar uma consulta
            stmt = conn.createStatement();
            String sql = "SELECT * FROM TotalVendasPorVendedor";
            ResultSet rs = stmt.executeQuery(sql);

            // Extrair dados do conjunto de resultados
            while (rs.next()) {
                // Recuperar por nome de coluna
                String nomeColaborador = rs.getString("NomeColaborador");
                double totalVendas = rs.getDouble("TotalVendas");

                // Adicionar à lista de vendas
                vendas.add(new Venda(nomeColaborador, totalVendas));
            }
            // Limpar o ambiente
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception se) {
                se.printStackTrace();
            }
        }
        return vendas;
    }
}

