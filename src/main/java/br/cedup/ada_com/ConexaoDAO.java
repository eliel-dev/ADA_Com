package br.cedup.ada_com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Classe que centraliza a conexão com o banco de dados
public class ConexaoDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ada/com";
    private static final String USER = "root";
    private static final String PASS = "";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
