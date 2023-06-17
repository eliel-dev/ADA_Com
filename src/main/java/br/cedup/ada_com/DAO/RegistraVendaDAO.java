package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.ItemVendido;

import java.sql.*;
import java.util.List;

public class RegistraVendaDAO {
    public void registrarVenda(int clienteID, int vendedorID, List<Integer> itemIDs, List<Integer> quantidades, double valorTotalCarrinho, List<Integer> perguntaIDs, List<Integer> alternativaIDs, String obsCompra) throws SQLException {
        // Registrar a venda
        String sql = "INSERT INTO registrovenda (Cliente_ID, Colaborador_ID, catalogo_Item_ID, Valor_Venda, Data_Venda, Quantidade_Vendida) VALUES (?, ?, ?, ?, NOW(), ?)";
        PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, clienteID);
        stmt.setInt(2, vendedorID);
        stmt.setInt(3, itemIDs.get(0)); // Adicionar o ID do primeiro item vendido
        stmt.setDouble(4, valorTotalCarrinho);
        stmt.setInt(5, quantidades.get(0)); // Adicionar a quantidade do primeiro item vendido
        stmt.executeUpdate();

// Obter o ID da venda registrada
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            int vendaID = rs.getInt(1);

            // Registrar os itens vendidos
            for (int i = 1; i < itemIDs.size(); i++) { // Começar do segundo item vendido
                int itemID = itemIDs.get(i);
                int quantidade = quantidades.get(i);

                sql = "UPDATE registrovenda SET catalogo_Item_ID = ?, Quantidade_Vendida = ? WHERE Venda_ID = ?";
                stmt = ConnectionSingleton.getConnection().prepareStatement(sql);
                stmt.setInt(1, itemID);
                stmt.setInt(2, quantidade);
                stmt.setInt(3, vendaID);
                stmt.executeUpdate();
            }

            // Registrar as respostas do cliente para as perguntas da experiência de venda
            for (int i = 0; i < perguntaIDs.size(); i++) {
                int perguntaID = perguntaIDs.get(i);
                int alternativaID = alternativaIDs.get(i);

                sql = "INSERT INTO experiencia_venda (registrovenda_ID, cliente_Cliente_ID, Perguntas_id, Alternativas_id) VALUES (?, ?, ?, ?)";
                stmt = ConnectionSingleton.getConnection().prepareStatement(sql);
                stmt.setInt(1, vendaID);
                stmt.setInt(2, clienteID);
                stmt.setInt(3, perguntaID);
                stmt.setInt(4, alternativaID);
                stmt.executeUpdate();
            }

            // Adicionar uma observação pelo vendedor
            if (obsCompra != null && !obsCompra.isEmpty()) {
                sql = "UPDATE registrovenda SET anotacoes = ? WHERE Venda_ID = ?";
                stmt = ConnectionSingleton.getConnection().prepareStatement(sql);
                stmt.setString(1, obsCompra);
                stmt.setInt(2, vendaID);
                stmt.executeUpdate();
            }
        }

        rs.close();
        stmt.close();
    }

}
