package br.cedup.ada_com.controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.Cidade;
import br.cedup.ada_com.model.Cliente;
import br.cedup.ada_com.model.Estado;
import br.cedup.ada_com.model.dao.ClienteDAO;
import br.cedup.ada_com.model.dao.EnderecoDAO;
import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class NovoClienteModalController implements Initializable {

    @FXML
    ComboBox <Estado> comboEstado;
    @FXML
    ComboBox <Cidade> comboCidade;

    @FXML
    TextField fieldNome;
    @FXML
    TextField fieldSobrenome;
    @FXML
    TextField fieldDocumento;

    private boolean atualizandoCampo = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Criar um novo objeto EnderecoDAO
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        // Consultar o banco de dados para recuperar os estados
        List<Estado> estados = null;
        try {
            estados = enderecoDAO.getEstados();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Adicionar os estados à lista de opções do ComboBox comboEstado
        comboEstado.getItems().addAll(estados);

        // Adicionar um ouvinte ao valor selecionado do ComboBox comboEstado
        comboEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Limpar a lista de opções do ComboBox comboCidade
            comboCidade.getItems().clear();

            // Verificar se um estado foi selecionado
            if (newValue != null) {
                // Obter o ID do estado selecionado
                int estadoID = newValue.getEstadoID();

                // Consultar o banco de dados para recuperar as cidades do estado selecionado
                List<Cidade> cidades = null;
                try {
                    cidades = enderecoDAO.getCidadesByEstadoID(estadoID);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Adicionar as cidades à lista de opções do ComboBox comboCidade
                comboCidade.getItems().addAll(cidades);
            }
        });

        // Adicionar um ouvinte à propriedade text do campo fieldDocumento
        fieldDocumento.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!atualizandoCampo) {
                atualizandoCampo = true;

                // Formatar o CPF/CNPJ
                String cpfCnpj = formatarCpfCnpj(newValue);

                // Atualizar o texto do campo fieldDocumento
                fieldDocumento.setText(cpfCnpj);

                atualizandoCampo = false;
            }
        });
    }

    public void salvar(){
        // Verificar se todos os campos estão preenchidos
        if (comboEstado.getValue() == null ||
                comboCidade.getValue() == null ||
                fieldNome.getText().isEmpty() ||
                fieldSobrenome.getText().isEmpty() ||
                fieldDocumento.getText().isEmpty()) {
            // Exibir mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencha todos os campos!");
            alert.showAndWait();
        } else {
            // Obter os valores dos campos
            String nomeCliente = fieldNome.getText();
            String sobreNomeCliente = fieldSobrenome.getText();
            String cnpj_cpf = formatarCpfCnpj(fieldDocumento.getText());
            String nomeCidade = comboCidade.getValue().getNomeCidade();
            String nomeEstado = comboEstado.getValue().getNomeEstado();

            // Remover caracteres de formatação do CPF/CNPJ
            cnpj_cpf = cnpj_cpf.replaceAll("\\D", "");

            // Criar um novo objeto ClienteDAO
            ClienteDAO clienteDAO = new ClienteDAO();

            // Verificar se o CPF/CNPJ informado já está cadastrado
            boolean cpfCnpjJaCadastrado = false;
            try {
                cpfCnpjJaCadastrado = clienteDAO.verificarCpfCnpjJaCadastrado(cnpj_cpf);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (cpfCnpjJaCadastrado) {
                // Exibir mensagem de erro
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("O CPF/CNPJ informado já está cadastrado!");
                alert.showAndWait();
                return;
            }

            // Verificar se a combinação de nome e sobrenome informada já está cadastrada
            boolean nomeSobrenomeJaCadastrado = false;
            try {
                nomeSobrenomeJaCadastrado = clienteDAO.verificarNomeSobrenomeJaCadastrado(nomeCliente, sobreNomeCliente);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (nomeSobrenomeJaCadastrado) {
                // Exibir mensagem de erro
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("A combinação de nome e sobrenome informada já está cadastrada!");
                alert.showAndWait();
                return;
            }

            // Obter o ID da cidade selecionada
            int cidadeID = comboCidade.getValue().getCidadeID();

            // Obter o ID do estado selecionado
            int estadoID = comboEstado.getValue().getEstadoID();


            // Criar um novo objeto EnderecoDAO
            EnderecoDAO enderecoDAO = new EnderecoDAO();

            // Consultar o banco de dados para obter o ID do endereço resultante da combinação dos IDs de cidade e estado
            int enderecoID = 0;
            try {
                enderecoID = enderecoDAO.getEnderecoIDByCidadeAndEstado(cidadeID, estadoID);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Criar um novo objeto Cliente com os valores dos campos
            Cliente cliente = new Cliente(0, enderecoID, nomeCliente, sobreNomeCliente, cnpj_cpf, nomeCidade, nomeEstado);


            // Cadastrar o novo cliente no banco de dados
            ClienteDAO clienteDAO2 = new ClienteDAO();
            try {
                clienteDAO2.cadastrarCliente(cliente);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            HelloApplication.closeCurrentWindow();
        }
    }

    private String formatarCpfCnpj(String cpfCnpj) {
        // Remover caracteres não numéricos
        cpfCnpj = cpfCnpj.replaceAll("\\D", "");

        // Limitar o tamanho do CPF/CNPJ a 14 dígitos
        if (cpfCnpj.length() > 14) {
            cpfCnpj = cpfCnpj.substring(0, 14);
        }
        // Formatar como CPF ou CNPJ
        if (cpfCnpj.length() == 11) {
            // Formatar como CPF
            CPFFormatter formatter = new CPFFormatter();
            cpfCnpj = formatter.format(cpfCnpj);
        } else if (cpfCnpj.length() == 14) {
            // Formatar como CNPJ
            CNPJFormatter formatter = new CNPJFormatter();
            cpfCnpj = formatter.format(cpfCnpj);
        }
        return cpfCnpj;
    }


    public void cancelar(){
        // Fechar a janela modal
        HelloApplication.closeCurrentWindow();
    }
}
