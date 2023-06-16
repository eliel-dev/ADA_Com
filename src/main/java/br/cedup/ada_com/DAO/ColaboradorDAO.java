package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.Colaborador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {
    private static final String coluna_id = "Colaborador_ID";
    private static final String coluna_nivel = "nivel";
    private static final String coluna_usuario = "usuario";
    private static final String coluna_senha = "senha";
    private static final String coluna_nome = "nomeColaborador";
    private static final String coluna_sobrenome = "sobreNomeColab";
    private static int vendedorLogadoID;

    public Colaborador loginUser(Colaborador colaborador) throws SQLException {

        String sql = "SELECT Colaborador_ID, nivel, usuario, senha, nomeColaborador, sobreNomeColab FROM colaborador WHERE usuario = ? AND senha = ?";
        try(PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, colaborador.getUser());
            stmt.setString(2, colaborador.getPassword());
            try(ResultSet resultado = stmt.executeQuery();) {
                if (resultado.next()) {
                    int id = resultado.getInt(coluna_id);
                    int nivel = resultado.getInt(coluna_nivel);
                    String user = resultado.getString(coluna_usuario);
                    String password = resultado.getString(coluna_senha);
                    String nome = resultado.getString(coluna_nome);
                    String sobrenome = resultado.getString(coluna_sobrenome);

                    // Definir o ID do vendedor que logou
                    setVendedorLogadoID(id);
                    return new Colaborador(id, nivel, nome, sobrenome, user, password);
                } else {
                    return null;
                }
            }
        }
    }

    public List<Colaborador> getColaboradores() throws SQLException {
        List<Colaborador> colaboradores = new ArrayList<>();

        String sql = "SELECT * FROM colaborador";
        try(PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery()) {
            while (resultado.next()) {
                int id = resultado.getInt(coluna_id);
                int nivel = resultado.getInt(coluna_nivel);
                String user = resultado.getString(coluna_usuario);
                String password = resultado.getString(coluna_senha);
                String nome = resultado.getString(coluna_nome);
                String sobrenome = resultado.getString(coluna_sobrenome);
                colaboradores.add(new Colaborador(id, nivel, nome, sobrenome, user, password));
            }
            return colaboradores;
        }
    }

    public void inserirColaborador(Colaborador colaborador) throws SQLException {
        String sql = "INSERT INTO colaborador (nivel, usuario, senha, nomeColaborador, sobreNomeColab) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, colaborador.getNivel());
            stmt.setString(2, colaborador.getUser());
            stmt.setString(3, colaborador.getPassword());
            stmt.setString(4, colaborador.getNomeColaborador());
            stmt.setString(5, colaborador.getSobrenome());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.next();
                colaborador.setColaboradorId(rs.getInt(1));
            }
        }
    }

    public void update(Colaborador colaborador) throws SQLException {
        try (Connection connection = ConnectionSingleton.getConnection()) {
            String sql = "update colaborador set nomeColaborador = ?, sobrenomecolab = ?, usuario = ?, senha = ? where colaborador_id= ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, colaborador.getSobrenome());
                stmt.setString(2, colaborador.getNomeColaborador());
                stmt.setString(3, colaborador.getUser());
                stmt.setString(4, colaborador.getPassword());
                stmt.setInt(5, colaborador.getColaboradorId());

                stmt.executeUpdate();
            }
        }
    }

    public void removerColaborador(Colaborador colaborador) throws SQLException {
        try (Connection connection = ConnectionSingleton.getConnection()) {
            String sql = "DELETE FROM colaborador WHERE Colaborador_ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, colaborador.getColaboradorId());

                stmt.executeUpdate();
            }
        }
    }


    public boolean colaboradorExiste(String nome, String sobrenome, String usuario) throws SQLException {
        // Consultar o banco de dados para verificar se existe um colaborador com a mesma combinação de nome e sobrenome ou com o mesmo nome de usuário
        String sql = "SELECT COUNT(*) FROM colaborador WHERE (nomeColaborador = ? AND sobreNomeColab = ?) OR usuario = ?";
        try (Connection connection = ConnectionSingleton.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);
            stmt.setString(3, usuario);
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

    public static void setVendedorLogadoID(int id) {
        vendedorLogadoID = id;
    }

    public int getVendedorLogadoID() {
        return vendedorLogadoID;
    }

}

