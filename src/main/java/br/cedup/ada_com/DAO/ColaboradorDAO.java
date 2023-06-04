package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.Colaborador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {
    private static final String coluna_nivel = "nivel";
    private static final String coluna_usuario = "usuario";
    private static final String coluna_senha = "senha";
    private static final String coluna_nome = "nomeColaborador";
    private static final String coluna_sobrenome = "sobreNomeColab";

    public Colaborador existe(Colaborador colaborador) throws SQLException {
        Connection connection = ConnectionSingleton.getConnection();

        String sql = "SELECT nivel, usuario, senha, nomeColaborador, sobreNomeColab FROM colaborador WHERE usuario = ? AND senha = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, colaborador.user);
        stmt.setString(2, colaborador.password);
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            int nivel = resultado.getInt(coluna_nivel);
            String user = resultado.getString(coluna_usuario);
            String password = resultado.getString(coluna_senha);
            String nome = resultado.getString(coluna_nome);
            String sobrenome = resultado.getString(coluna_sobrenome);
            return new Colaborador(nivel, user, password, nome, sobrenome);
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
            int nivel = resultado.getInt(coluna_nivel);
            String user = resultado.getString(coluna_usuario);
            String password = resultado.getString(coluna_senha);
            String nome = resultado.getString(coluna_nome);
            String sobrenome = resultado.getString(coluna_sobrenome);
            colaboradores.add(new Colaborador(nivel, user, password, nome, sobrenome));
        }
        return colaboradores;
    }
}
