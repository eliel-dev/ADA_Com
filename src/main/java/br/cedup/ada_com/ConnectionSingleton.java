package br.cedup.ada_com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Classe que centraliza a conexão com o banco de dados
public class ConnectionSingleton {
    private static Connection connection;

    private ConnectionSingleton() {
        // Singleton class
    }
    /**
     * Obtém a conexão ativa com o banco.
     * Caso não exista nenhuma conexão ativa ainda, cria uma nova.
     */
    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection( //
                    "jdbc:mysql://:3306/ada_com_db", //
                    "root", //
                    "");
        }
        return connection;
    }
}
