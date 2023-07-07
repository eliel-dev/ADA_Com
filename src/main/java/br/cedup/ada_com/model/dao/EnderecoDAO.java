package br.cedup.ada_com.model.dao;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.model.Cidade;
import br.cedup.ada_com.model.Estado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    public List<Estado> getEstados() throws SQLException {
        List<Estado> estados = new ArrayList<>();
        String sql = "SELECT id_Estado, Nome_estado FROM estado";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int estadoID = rs.getInt("id_Estado");
                String nomeEstado = rs.getString("Nome_estado");
                Estado estado = new Estado(nomeEstado);
                estado.setEstadoID(estadoID);
                estados.add(estado);
            }
        }return estados;
    }

    public List<Cidade> getCidadesByEstadoID(int estadoID) throws SQLException {
        List<Cidade> cidades = new ArrayList<>();
        String sql = "SELECT id_Cidade, Nome_Cidade FROM cidade WHERE id_Estado = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, estadoID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int cidadeID = rs.getInt("id_Cidade");
                    String nomeCidade = rs.getString("Nome_Cidade");
                    Cidade cidade = new Cidade(nomeCidade, estadoID);
                    cidade.setCidadeID(cidadeID);
                    cidades.add(cidade);
                }
            }
        }return cidades;
    }

    public int getEnderecoIDByCidadeAndEstado(int cidadeID, int estadoID) throws SQLException {
        int enderecoID = 0;
        String sql = "SELECT idEndereco FROM endereco WHERE Cidade_id_Cidade = ? AND Cidade_id_Estado = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, cidadeID);
            stmt.setInt(2, estadoID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    enderecoID = rs.getInt("idEndereco");
                }
            }
        }
        return enderecoID;
    }


    public void cadastrarCidade(Cidade cidade) throws SQLException {
        String sql = "INSERT INTO cidade (Nome_Cidade, id_Estado) VALUES (?, ?)";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cidade.getNomeCidade());
            stmt.setInt(2, cidade.getEstadoID());
            stmt.executeUpdate();
        }
    }

    public void cadastrarEstado(Estado estado) throws SQLException {
        String sql = "INSERT INTO estado (Nome_estado) VALUES (?)";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, estado.getNomeEstado());
            stmt.executeUpdate();
        }
    }

    public void editarCidade(Cidade cidade) throws SQLException {
        String sql = "UPDATE cidade SET Nome_Cidade = ?, id_Estado = ? WHERE id_Cidade = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cidade.getNomeCidade());
            stmt.setInt(2, cidade.getEstadoID());
            stmt.setInt(3, cidade.getCidadeID());
            stmt.executeUpdate();
        }
    }

    public void excluirCidade(int cidadeID) throws SQLException {
        String sql = "DELETE FROM cidade WHERE id_Cidade = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, cidadeID);
            stmt.executeUpdate();
        }
    }

    public void editarEstado(Estado estado) throws SQLException {
        String sql = "UPDATE estado SET Nome_estado = ? WHERE id_Estado = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, estado.getNomeEstado());
            stmt.setInt(2, estado.getEstadoID());
            stmt.executeUpdate();
        }
    }

    public void excluirEstado(int estadoID) throws SQLException {
        String sql = "DELETE FROM estado WHERE id_Estado = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, estadoID);
            stmt.executeUpdate();
        }
    }

}
