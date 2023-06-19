package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComissaoDAO {
    public void calcularComissao(int vendedorID, double valorTotalCarrinho) throws SQLException {
        // Calcular o valor total vendido pelo vendedor no ciclo atual
        String sql = "SELECT SUM(Valor_Venda * Quantidade) FROM registrovenda JOIN registrovenda_item ON registrovenda.Venda_ID = registrovenda_item.Venda_ID WHERE Colaborador_ID = ? AND Data_Venda >= DATE(CONCAT_WS('-', YEAR(CURRENT_DATE() - INTERVAL 20 DAY), MONTH(CURRENT_DATE() - INTERVAL 20 DAY), 20)) AND Data_Venda < DATE(CONCAT_WS('-', YEAR(CURRENT_DATE() + INTERVAL 10 DAY), MONTH(CURRENT_DATE() + INTERVAL 10 DAY), 20))";
        double valorVendido;
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, vendedorID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    valorVendido = rs.getDouble(1);
                } else {
                    valorVendido = 0;
                }
            }
        }

        // Determinar a taxa de comissão apropriada
        double taxaComissao;
        if (valorVendido > 5000) {
            taxaComissao = 0.03;
        } else {
            taxaComissao = 0.015;
        }

        // Calcular o valor da comissão
        double valorComissao = valorTotalCarrinho * taxaComissao;

        // Atualizar a tabela comissao
        sql = "INSERT INTO comissao (Valor_comissao, TaxaComissao, Data, colaborador_Colaborador_ID) VALUES (?, ?, CURRENT_DATE(), ?) ON DUPLICATE KEY UPDATE Valor_comissao = Valor_comissao + ?, TaxaComissao = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setDouble(1, valorComissao);
            stmt.setDouble(2, taxaComissao);
            stmt.setInt(3, vendedorID);
            stmt.setDouble(4, valorComissao);
            stmt.setDouble(5, taxaComissao);
            stmt.executeUpdate();
        }
    }

    public double getValorComissaoAtual(int colaboradorID) throws SQLException {
        double valorComissao = 0;
        String sql = "SELECT Valor_comissao FROM comissao WHERE Data = CURRENT_DATE() AND colaborador_Colaborador_ID = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, colaboradorID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    valorComissao = rs.getDouble(1);
                }
            }
        }
        return valorComissao;
    }
}
