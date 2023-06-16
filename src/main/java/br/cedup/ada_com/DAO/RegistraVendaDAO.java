package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.ItemVendido;

import java.sql.*;
import java.util.List;

public class RegistraVendaDAO {
    /**
     * Este método insere um novo registro na tabela registrovenda com as informações da venda.
     *
     * @param vendedorID o ID do vendedor que realizou a venda.
     * @param clienteID o ID do cliente que realizou a compra.
     * @param itensVendidos a lista de itens vendidos.
     * @param valorTotal o valor total da venda.
     * @return o ID da venda registrada.
     */
    public int registrar(int vendedorID, int clienteID, List<ItemVendido> itensVendidos, double valorTotal) throws SQLException {
        String sql = "INSERT INTO registrovenda (Cliente_ID, Colaborador_ID, catalogo_Item_ID, Valor_Venda, Data_Venda, Quantidade_Vendida) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionSingleton.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (ItemVendido item : itensVendidos) {
                stmt.setInt(1, clienteID);
                stmt.setInt(2, vendedorID);
                stmt.setInt(3, item.getItemID());
                stmt.setDouble(4, item.getPreco());
                stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                stmt.setInt(6, item.getQuantidade());
                stmt.executeUpdate();
            }
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    /**
     * Este método atualiza a observação do vendedor na tabela registrovenda.
     *
     * @param vendaID o ID da venda cuja observação será atualizada.
     * @param observacao a observação do vendedor.
     */
    public void atualizarObservacao(int vendaID, String observacao) throws SQLException {
        String sql = "UPDATE registrovenda SET anotacoes = ? WHERE Venda_ID = ?";
        try (Connection connection = ConnectionSingleton.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, observacao);
            stmt.setInt(2, vendaID);
            stmt.executeUpdate();
        }
    }
}
