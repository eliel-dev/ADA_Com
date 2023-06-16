package br.cedup.ada_com.DAO;

import br.cedup.ada_com.Cliente;
import br.cedup.ada_com.ConnectionSingleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

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
                    cliente = new Cliente(clienteID, enderecoID, nomeCliente, sobreNomeCliente, cnpj_cpf);
                    cliente.setCidade(nomeCidade);
                    cliente.setEstado(nomeEstado);
                }
            }return cliente;
        }
    }
}
