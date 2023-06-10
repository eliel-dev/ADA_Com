package br.cedup.ada_com.DAO;

import br.cedup.ada_com.Catalogo;
import br.cedup.ada_com.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogoDAO {

    private static final String coluna_id = "Item_ID";
    private static final String coluna_tipo = "Tipo";
    private static final String coluna_nome = "nome";
    private static final String coluna_categoria = "categoria";
    private static final String coluna_valor = "valor";


    public List<Catalogo> getItens() throws SQLException {

        List<Catalogo> itens = new ArrayList<>();

        Connection connection = ConnectionSingleton.getConnection();
        String sql = "SELECT * FROM catalogo";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt(coluna_id);
            int tipo = resultado.getInt(coluna_tipo);
            String nome = resultado.getString(coluna_nome);
            String categoria = resultado.getString(coluna_categoria);
            double valor = resultado.getDouble(coluna_valor);
            itens.add(new Catalogo(id, nome, valor, categoria, tipo));
        }

    return itens;
    }

    public void inserirItem(Catalogo catalogo) throws SQLException {
        try (Connection connection = ConnectionSingleton.getConnection()) {
            String sql = "INSERT INTO catalogo (Tipo, nome, categoria, valor) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1,catalogo.getTipo());
                stmt.setString(2,catalogo.getNome());
                stmt.setString(3,catalogo.getCategoria());
                stmt.setDouble(4,catalogo.getValor());


                stmt.executeUpdate();
            }
        }
    }

    public List<String> getCategorias() throws SQLException {
        List<String> categorias = new ArrayList<>();

        Connection connection = ConnectionSingleton.getConnection();
        String sql = "SELECT DISTINCT categoria FROM Catalogo";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        while (resultado.next()) {
            String categoria = resultado.getString("categoria");
            categorias.add(categoria);
        }

        return categorias;
    }

    public void atualizarItem(Catalogo catalogo) throws SQLException {
        try (Connection connection = ConnectionSingleton.getConnection()) {
            String sql = "UPDATE catalogo SET Tipo = ?, nome = ?, categoria = ?, valor = ? WHERE Item_ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, catalogo.getTipo());
                stmt.setString(2, catalogo.getNome());
                stmt.setString(3, catalogo.getCategoria());
                stmt.setDouble(4, catalogo.getValor());
                stmt.setInt(5, catalogo.getItemID());

                stmt.executeUpdate();
            }
        }
    }

    public boolean itemExiste(String nome, String categoria) throws SQLException {
        // Consultar o banco de dados para verificar se existe um item com o mesmo nome e categoria
        String sql = "SELECT COUNT(*) FROM catalogo WHERE nome = ? AND categoria = ?";
        try (Connection connection = ConnectionSingleton.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, categoria);
            try (ResultSet rs = stmt.executeQuery()) {
                // Verificar o resultado da consulta
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                } else {
                    return false;
                }
            }
        }
    }

    public void removerItem(Catalogo catalogo) throws SQLException {
        try (Connection connection = ConnectionSingleton.getConnection()) {
            String sql = "DELETE FROM catalogo WHERE Item_ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, catalogo.getItemID());

                stmt.executeUpdate();
            }
        }
    }
}
