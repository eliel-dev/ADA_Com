package br.cedup.ada_com.DAO;

import br.cedup.ada_com.Alternativa;
import br.cedup.ada_com.ConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperienciaVendaDAO {

    public List<String> getPerguntas() {
        List<String> perguntas = new ArrayList<>();
        String sql = "SELECT Pergunta FROM expPerguntas";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                perguntas.add(rs.getString("Pergunta"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return perguntas;
    }


    public List<Alternativa> getAlternativas(int perguntaID) throws SQLException {
        List<Alternativa> alternativas = new ArrayList<>();

        String sql = "SELECT * FROM expAlternativas WHERE expPerguntas_expPerguntas_id = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)){
            stmt.setInt(1, perguntaID);
            try(ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    int alternativaID = rs.getInt("expAlternativas_id");
                    String texto = rs.getString("Alternativas");
                    Alternativa alternativa = new Alternativa(perguntaID, alternativaID, texto);
                    alternativas.add(alternativa);
                }
            }
        }
        return alternativas;
    }


    public void saveExperienciaVenda(int cliente_id, int alternativas_id, int perguntas_id) {
        String sql = "INSERT INTO experiencia_venda(cliente_Cliente_ID, Alternativas_id, Perguntas_id, Data) VALUES(?,?,?,?)";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, cliente_id);
            stmt.setInt(2, alternativas_id);
            stmt.setInt(3, perguntas_id);
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
