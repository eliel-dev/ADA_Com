package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConexaoDAO;
import br.cedup.ada_com.Colaborador;

import java.sql.*;

public class ColaboradorDAO extends ConexaoDAO {

    public Colaborador existe(Colaborador colaborador) throws SQLException {
        Connection connection = getConnection();

        String sql = "SELECT nivel, usuario, senha, nomeColaborador, sobreNomeColab FROM colaborador WHERE usuario = ? AND senha = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, colaborador.user);
        stmt.setString(2, colaborador.password);
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            int nivel = resultado.getInt("nivel");
            String user = resultado.getString("usuario");
            String password = resultado.getString("senha");
            String nome = resultado.getString("nomeColaborador");
            String sobrenome = resultado.getString("sobreNomeColab");
            return new Colaborador(nivel, user, password, nome, sobrenome);
        } else {
            return null;
        }
    }
}
