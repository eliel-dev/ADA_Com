package br.cedup.ada_com.model.dao;

import br.cedup.ada_com.model.Cliente;
import br.cedup.ada_com.ConnectionSingleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> getClientes() throws SQLException {
       List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT cliente.*, cidade.Nome_Cidade AS nomeCidade, estado.Nome_estado AS nomeEstado " +
                "FROM cliente " +
                "JOIN endereco ON cliente.Endereco_idEndereco = endereco.idEndereco " +
                "JOIN cidade ON endereco.Cidade_id_Cidade = cidade.id_Cidade " +
                "JOIN estado ON cidade.id_Estado = estado.id_Estado";
        try (Statement stmt = ConnectionSingleton.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int clienteID = rs.getInt("Cliente_ID");
                int enderecoID = rs.getInt("Endereco_idEndereco");
                String nomeCliente = rs.getString("NomeCliente");
                String sobreNomeCliente = rs.getString("SobreNomeCliente");
                String cnpj_cpf = rs.getString("cnpj_cpf");
                String nomeCidade = rs.getString("nomeCidade");
                String nomeEstado = rs.getString("nomeEstado");
                Cliente cliente = new Cliente(clienteID, enderecoID, nomeCliente, sobreNomeCliente, cnpj_cpf, nomeCidade, nomeEstado);
                clientes.add(cliente);
            }
        }return clientes;
    }

    public Cliente getClienteByCpfCnpj(String cpfCnpj) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT cliente.*, cidade.Nome_Cidade AS nomeCidade, estado.Nome_estado AS nomeEstado " +
                "FROM cliente " +
                "JOIN endereco ON cliente.Endereco_idEndereco = endereco.idEndereco " +
                "JOIN cidade ON endereco.Cidade_id_Cidade = cidade.id_Cidade " +
                "JOIN estado ON cidade.id_Estado = estado.id_Estado " +
                "WHERE cnpj_cpf = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cpfCnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int clienteID = rs.getInt("Cliente_ID");
                    int enderecoID = rs.getInt("Endereco_idEndereco");
                    String nomeCliente = rs.getString("NomeCliente");
                    String sobreNomeCliente = rs.getString("SobreNomeCliente");
                    String cnpj_cpf = rs.getString("cnpj_cpf");
                    String nomeCidade = rs.getString("nomeCidade");
                    String nomeEstado = rs.getString("nomeEstado");
                    cliente = new Cliente(clienteID, enderecoID, nomeCliente, sobreNomeCliente, cnpj_cpf, nomeCidade, nomeEstado);
                }
            }return cliente;
        }
    }

    public boolean verificarCpfCnpjJaCadastrado(String cpfCnpj) throws SQLException {
        boolean cpfCnpjJaCadastrado = false;
        String sql = "SELECT COUNT(*) FROM cliente WHERE cnpj_cpf = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cpfCnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    cpfCnpjJaCadastrado = count > 0;
                }
            }
        }
        return cpfCnpjJaCadastrado;
    }

    public boolean verificarNomeSobrenomeJaCadastrado(String nomeCliente, String sobreNomeCliente) throws SQLException {
        boolean nomeSobrenomeJaCadastrado = false;
        String sql = "SELECT COUNT(*) FROM cliente WHERE NomeCliente = ? AND SobreNomeCliente = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, nomeCliente);
            stmt.setString(2, sobreNomeCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    nomeSobrenomeJaCadastrado = count > 0;
                }
            }
        }
        return nomeSobrenomeJaCadastrado;
    }


    public void cadastrarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (NomeCliente, SobreNomeCliente, cnpj_cpf, Endereco_idEndereco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getSobreNomeCliente());
            stmt.setString(3, cliente.getCnpj_cpf());
            stmt.setInt(4, cliente.getEnderecoID());
            stmt.executeUpdate();
        }
    }

    public void editarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET NomeCliente = ?, SobreNomeCliente = ?, cnpj_cpf = ? WHERE Cliente_ID = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getSobreNomeCliente());
            stmt.setString(3, cliente.getCnpj_cpf());
            stmt.setInt(4, cliente.getClienteID());
            stmt.executeUpdate();
        }
    }

    public void excluirCliente(int clienteID) throws SQLException {
        String sql = "DELETE FROM cliente WHERE Cliente_ID = ?";
        try (PreparedStatement stmt = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, clienteID);
            stmt.executeUpdate();
        }
    }


}
