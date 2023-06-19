package br.cedup.ada_com.DAO;

import br.cedup.ada_com.ConnectionSingleton;
import br.cedup.ada_com.RegistroVenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<RegistroVenda> gerarResumoVendas(int colaboradorID) throws SQLException {
        List<RegistroVenda> resumoVendas = new ArrayList<>();

        String sql = "SELECT rv.Venda_ID, rv.Data_Venda, c.NomeCliente, c.SobreNomeCliente, co.NomeColaborador, co.SobreNomeColab FROM registrovenda rv JOIN cliente c ON rv.Cliente_ID = c.Cliente_ID JOIN colaborador co ON rv.Colaborador_ID = co.Colaborador_ID WHERE rv.Colaborador_ID = ? GROUP BY rv.Venda_ID";
        PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql);
        stmt.setInt(1, colaboradorID);
        ResultSet rs = stmt.executeQuery();

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

        rs.close();
        stmt.close();

        return resumoVendas;
    }

    public List<RegistroVenda> gerarResumoVendasGestor() throws SQLException {
        List<RegistroVenda> resumoVendas = new ArrayList<>();

        String sql = "SELECT rv.Venda_ID, rv.Data_Venda, c.NomeCliente, c.SobreNomeCliente, co.NomeColaborador, co.SobreNomeColab FROM registrovenda rv JOIN cliente c ON rv.Cliente_ID = c.Cliente_ID JOIN colaborador co ON rv.Colaborador_ID = co.Colaborador_ID GROUP BY rv.Venda_ID";
        PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

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

        rs.close();
        stmt.close();

        return resumoVendas;
    }

}

