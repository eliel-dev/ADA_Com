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

    public Colaborador existe(Colaborador colaborador) throws SQLException {
        Connection connection = ConnectionSingleton.getConnection();

        String sql = "SELECT Colaborador_ID, nivel, usuario, senha, nomeColaborador, sobreNomeColab FROM colaborador WHERE usuario = ? AND senha = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, colaborador.getUser());
        stmt.setString(2, colaborador.getPassword());
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            int id = resultado.getInt(coluna_id);
            int nivel = resultado.getInt(coluna_nivel);
            String user = resultado.getString(coluna_usuario);
            String password = resultado.getString(coluna_senha);
            String nome = resultado.getString(coluna_nome);
            String sobrenome = resultado.getString(coluna_sobrenome);
            return new Colaborador(id, nivel, user, password, nome, sobrenome);
        } else {
            return null;
        }
    }

    public List<Colaborador> getColaboradores() throws SQLException {

        List<Colaborador> colaboradores = new ArrayList<>();
        Connection connection = ConnectionSingleton.getConnection();
        String sql = "SELECT * FROM colaborador";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            int id = resultado.getInt(coluna_id);
            int nivel = resultado.getInt(coluna_nivel);
            String user = resultado.getString(coluna_usuario);
            String password = resultado.getString(coluna_senha);
            String nome = resultado.getString(coluna_nome);
            String sobrenome = resultado.getString(coluna_sobrenome);
            colaboradores.add(new Colaborador(id, nivel, user, password, nome, sobrenome));
        }
        return colaboradores;
    }

    public void inserirColaborador(Colaborador colaborador) throws SQLException {
        try (Connection connection = ConnectionSingleton.getConnection()) {
            String sql = "INSERT INTO colaborador (nivel, usuario, senha, nomeColaborador, sobreNomeColab) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, colaborador.getNivel());
                stmt.setString(2, colaborador.getUser());
                stmt.setString(3, colaborador.getPassword());
                stmt.setString(4, colaborador.getNomeColaborador());
                stmt.setString(5, colaborador.getSobrenome());

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
}

