package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;

import java.sql.*;
import java.util.List;

public class RegistraVendaDAO {
    public void registrarVenda(int clienteID, int vendedorID, List<Integer> itemIDs, List<Integer> quantidades, double valorTotalCarrinho, List<Integer> perguntaIDs, List<Integer> alternativaIDs, String obsCompra) throws SQLException {
        // Registrar a venda
        String sql = "INSERT INTO registrovenda (Cliente_ID, Colaborador_ID, Valor_Venda, Data_Venda) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, clienteID);
            stmt.setInt(2, vendedorID);
            stmt.setDouble(3, Math.round(valorTotalCarrinho * 100.0) / 100.0); // Arredondar o valor para duas casas decimais
            stmt.executeUpdate();

            // Obter o ID da venda registrada
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int vendaID = rs.getInt(1);

                    // Registrar os itens vendidos
                    for (int i = 0; i < itemIDs.size(); i++) {
                        int itemID = itemIDs.get(i);
                        int quantidade = quantidades.get(i);

                        sql = "INSERT INTO registrovenda_item (Venda_ID, catalogo_Item_ID, Quantidade) VALUES (?, ?, ?)";
                        try (PreparedStatement stmt2 = ConnectionSingleton.getConnection().prepareStatement(sql)) {
                            stmt2.setInt(1, vendaID);
                            stmt2.setInt(2, itemID);
                            stmt2.setInt(3, quantidade);
                            stmt2.executeUpdate();
                        }
                    }

                    // Registrar as respostas do cliente para as perguntas da experiência de venda
                    for (int i = 0; i < perguntaIDs.size(); i++) {
                        int perguntaID = perguntaIDs.get(i);
                        int alternativaID = alternativaIDs.get(i);

                        sql = "INSERT INTO experiencia_venda (registrovenda_ID, cliente_Cliente_ID, Perguntas_id, Alternativas_id) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement stmt2 = ConnectionSingleton.getConnection().prepareStatement(sql)) {
                            stmt2.setInt(1, vendaID);
                            stmt2.setInt(2, clienteID);
                            stmt2.setInt(3, perguntaID);
                            stmt2.setInt(4, alternativaID);
                            stmt2.executeUpdate();
                        }
                    }

                    // Adicionar uma observação pelo vendedor
                    if (obsCompra != null && !obsCompra.isEmpty()) {
                        sql = "UPDATE registrovenda SET anotacoes = ? WHERE Venda_ID = ?";
                        try (PreparedStatement stmt2 = ConnectionSingleton.getConnection().prepareStatement(sql)) {
                            stmt2.setString(1, obsCompra);
                            stmt2.setInt(2, vendaID);
                            stmt2.executeUpdate();
                        }
                    }
                }
            }
        }
    }
}