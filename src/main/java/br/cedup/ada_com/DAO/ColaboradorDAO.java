package br.cedup.ada_com.DAO;

import br.cedup.ada_com.Colaborador;

import java.sql.*;

public class ColaboradorDAO {


    public Colaborador existe(Colaborador colaborador) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ada/com",
                "root",
                "");

        String sql = "SELECT nivel, usuario, senha FROM colaborador WHERE usuario = ? AND senha = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, colaborador.user);
        stmt.setString(2, colaborador.password);
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            int nivel = resultado.getInt("nivel");
            String user = resultado.getString("usuario");
            String password = resultado.getString("senha");
            return new Colaborador(nivel, user, password);
    } else {
            return null;
        }
    }
}
