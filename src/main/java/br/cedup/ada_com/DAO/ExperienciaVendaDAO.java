package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperienciaVendaDAO {

    /**
     * Este método retorna uma lista de todas as perguntas armazenadas na tabela expPerguntas.
     *
     * @return uma lista de Strings contendo todas as perguntas armazenadas na tabela expPerguntas.
     */
    public List<String> getPerguntas() {
        List<String> perguntas = new ArrayList<>();
        String sql = "SELECT Pergunta FROM expPerguntas";
        try (Connection connection = ConnectionSingleton.getConnection();
             Statement stmt  = connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                perguntas.add(rs.getString("Pergunta"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return perguntas;
    }

    /**
     * Este método retorna uma lista de todas as alternativas relacionadas a uma pergunta específica armazenadas na tabela expAlternativas.
     *
     * @param expPerguntas_id o ID da pergunta cujas alternativas serão retornadas.
     * @return uma lista de Strings contendo todas as alternativas relacionadas à pergunta especificada armazenadas na tabela expAlternativas.
     */
    public List<String> getAlternativas(int expPerguntas_id) {
        List<String> alternativas = new ArrayList<>();
        String sql = "SELECT Alternativas FROM expAlternativas WHERE expPerguntas_expPerguntas_id = ?";
        try (Connection connection = ConnectionSingleton.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, expPerguntas_id);
            ResultSet rs  = stmt.executeQuery();
            while (rs.next()) {
                alternativas.add(rs.getString("Alternativas"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return alternativas;
    }

    /**
     * Este método insere um novo registro na tabela experiencia_venda com as informações da experiência de venda de um cliente.
     *
     * @param cliente_id o ID do cliente que teve a experiência de venda registrada.
     * @param alternativas_id o ID da alternativa escolhida pelo cliente durante a experiência de venda.
     * @param perguntas_id o ID da pergunta relacionada à alternativa escolhida pelo cliente durante a experiência de venda.
     */
    public void saveExperienciaVenda(int cliente_id, int alternativas_id, int perguntas_id) {
        String sql = "INSERT INTO experiencia_venda(cliente_Cliente_ID, Alternativas_id, Perguntas_id, Data) VALUES(?,?,?,?)";
        try (Connection connection = ConnectionSingleton.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cliente_id);
            stmt.setInt(2, alternativas_id);
            stmt.setInt(3, perguntas_id);
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
