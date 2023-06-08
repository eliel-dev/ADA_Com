package br.cedup.ada_com.DAO;

import br.cedup.ada_com.CatalogoItem;
import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.Produto;
import br.cedup.ada_com.Servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogoDAO {

    public List<CatalogoItem> getProdutos() throws SQLException {
        List<CatalogoItem> produtos = new ArrayList<>();

        Connection connection = ConnectionSingleton.getConnection();
        String sql = "SELECT * FROM produto";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("Produto_ID");
            String nome = resultado.getString("Marca_prod") + " " + resultado.getString("Modelo_prod");
            double preco = resultado.getDouble("Valor_Produto");
            String categoria = resultado.getString("Categoria_prod");
            produtos.add(new Produto(id, nome, preco, categoria));
        }
        return produtos;
    }

    public List<CatalogoItem> getServicos() throws SQLException {
        List<CatalogoItem> servicos = new ArrayList<>();

        Connection connection = ConnectionSingleton.getConnection();
        String sql = "SELECT * FROM servico";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("Servico_ID");
            String nome = resultado.getString("Nome_Servico");
            double preco = resultado.getDouble("Valor_Servico");
            String categoria = resultado.getString("Categoria");
            servicos.add(new Servico(id, nome, preco, categoria));
        }

        return servicos;
    }
}
