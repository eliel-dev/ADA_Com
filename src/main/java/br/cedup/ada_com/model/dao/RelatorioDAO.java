package br.cedup.ada_com.model.dao;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.model.RegistroVenda;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RelatorioDAO {

    public List<RegistroVenda> gerarResumoVendas(int colaboradorID) throws SQLException {
        List<RegistroVenda> resumoVendas = new ArrayList<>();

        String sql = "SELECT rv.Venda_ID, rv.Data_Venda, c.NomeCliente, c.SobreNomeCliente, co.NomeColaborador, co.SobreNomeColab FROM registrovenda rv JOIN cliente c ON rv.Cliente_ID = c.Cliente_ID JOIN colaborador co ON rv.Colaborador_ID = co.Colaborador_ID WHERE rv.Colaborador_ID = ? GROUP BY rv.Venda_ID";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql);){
            stmt.setInt(1, colaboradorID);
            try(ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    int vendaID = rs.getInt("Venda_ID");
                    Date dataVenda = rs.getDate("Data_Venda");
                    String nomeCliente = rs.getString("NomeCliente");
                    String sobrenomeCliente = rs.getString("SobreNomeCliente");
                    String nomeColaborador = rs.getString("NomeColaborador");
                    String sobrenomeColaborador = rs.getString("SobreNomeColab");

                    RegistroVenda resumoVenda = new RegistroVenda(vendaID, 0, 0, 0, 0, 0.0, dataVenda, 0, "");
                    resumoVenda.setNomeCliente(nomeCliente + " " + sobrenomeCliente);
                    resumoVenda.setNomeColaborador(nomeColaborador + " " + sobrenomeColaborador);
                    resumoVendas.add(resumoVenda);
                }
            }
        }return resumoVendas;
    }

    public List<RegistroVenda> gerarResumoVendasGestor() throws SQLException {
        List<RegistroVenda> resumoVendas = new ArrayList<>();

        String sql = "SELECT rv.Venda_ID, rv.Data_Venda, c.NomeCliente, c.SobreNomeCliente, co.NomeColaborador, co.SobreNomeColab FROM registrovenda rv JOIN cliente c ON rv.Cliente_ID = c.Cliente_ID JOIN colaborador co ON rv.Colaborador_ID = co.Colaborador_ID GROUP BY rv.Venda_ID";

        try(PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                int vendaID = rs.getInt("Venda_ID");
                Date dataVenda = rs.getDate("Data_Venda");
                String nomeCliente = rs.getString("NomeCliente");
                String sobrenomeCliente = rs.getString("SobreNomeCliente");
                String nomeColaborador = rs.getString("NomeColaborador");
                String sobrenomeColaborador = rs.getString("SobreNomeColab");

                RegistroVenda resumoVenda = new RegistroVenda(vendaID, 0, 0, 0, 0, 0.0, dataVenda, 0, "");
                resumoVenda.setNomeCliente(nomeCliente + " " + sobrenomeCliente);
                resumoVenda.setNomeColaborador(nomeColaborador + " " + sobrenomeColaborador);
                resumoVendas.add(resumoVenda);
            }
        }return resumoVendas;
    }


    public Map<LocalDate, Double> getVendasPorDia() throws SQLException {
        Map<LocalDate, Double> vendasPorDia = new LinkedHashMap<>();

        String sql = "SELECT DATE(data_venda) AS data, SUM(valor_venda) AS total_vendas FROM registrovenda GROUP BY DATE(data_venda)";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LocalDate data = rs.getDate("data").toLocalDate();
                double totalVendas = rs.getDouble("total_vendas");
                vendasPorDia.put(data, totalVendas);
            }
        }
        return vendasPorDia;
    }

}

