package br.cedup.ada_com.controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.Cidade;
import br.cedup.ada_com.model.Cliente;
import br.cedup.ada_com.model.Colaborador;
import br.cedup.ada_com.model.Estado;
import br.cedup.ada_com.model.dao.ClienteDAO;
import br.cedup.ada_com.model.dao.EnderecoDAO;
import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    @FXML
    Button bSalvar;
    @FXML
    Button bCancelar;

    @FXML
    Label aviso1;
    @FXML
    Label aviso2;

    private boolean atualizandoCampo = false;
    public static Cliente cliente;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Label[] alerta ={aviso1, aviso2};

        // Adicionar um ouvinte ao valor selecionado do comboEstado
        comboEstado.valueProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao valor selecionado do comboCidade
        comboCidade.valueProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao texto do campo fieldNome
        fieldNome.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao texto do campo fieldSobrenome
        fieldSobrenome.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        // Adicionar um ouvinte ao texto do campo fieldDocumento
        fieldDocumento.textProperty().addListener((obs, oldValue, newValue) -> {
            verificarCamposVazios(alerta);
        });

        Cliente clienteSelecionado = NovoClienteModalController.cliente;
        // Verificar se um cliente foi enviado
        if (clienteSelecionado != null) {
            // Inicializar os campos do modal com os valores do cliente
            fieldNome.setText(clienteSelecionado.getNomeCliente());
            fieldSobrenome.setText(clienteSelecionado.getSobreNomeCliente());
            fieldDocumento.setText(clienteSelecionado.getCnpj_cpf());
        }

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

        // Verificar se um cliente foi enviado
        if (cliente != null) {
            // Selecionar o estado do cliente no ComboBox comboEstado
            Estado estadoCliente = estados.stream()
                    .filter(estado -> estado.getNomeEstado().equals(cliente.getEstado()))
                    .findFirst()
                    .orElse(null);
            if (estadoCliente != null) {
                comboEstado.getSelectionModel().select(estadoCliente);
            }

            // Selecionar a cidade do cliente no ComboBox comboCidade
            Cidade cidadeCliente = comboCidade.getItems().stream()
                    .filter(cidade -> cidade.getNomeCidade().equals(cliente.getCidade()))
                    .findFirst()
                    .orElse(null);
            if (cidadeCliente != null) {
                comboCidade.getSelectionModel().select(cidadeCliente);
            }
        }

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

        //Botão verde:
        bSalvar.setOnMouseEntered(e -> bSalvar.setStyle("-fx-background-color: green; -fx-border-color: green; "));
        bSalvar.setOnMouseExited(e -> bSalvar.setStyle("-fx-background-color: #0c0c0c;  -fx-border-color: green;"));

        //Botão vermelho:
        bCancelar.setOnMouseEntered(e -> bCancelar.setStyle("-fx-background-color: red; -fx-border-color: red;"));
        bCancelar.setOnMouseExited(e -> bCancelar.setStyle("-fx-background-color: #0c0c0c; -fx-border-color: red;"));
    }


    public static void setCliente(Cliente cliente) {
        NovoClienteModalController.cliente = cliente;
    }

    public static Cliente getCliente() {
        return NovoClienteModalController.cliente;
    }


    public void salvarModal() {
        // Verificar se já existe um cliente com a mesma combinação de nome e sobrenome ou com o mesmo CPF/CNPJ
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            if (clienteDAO.verificarCpfCnpjJaCadastrado(fieldDocumento.getText()) || clienteDAO.verificarNomeSobrenomeJaCadastrado(fieldNome.getText(), fieldSobrenome.getText())) {
                // Verificar se é um novo cadastro
                if (NovoClienteModalController.cliente.getClienteID() == 0) {
                    // Exibir mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Cliente duplicado");
                    alert.setContentText("Já existe um cliente com a mesma combinação de nome e sobrenome ou com o mesmo CPF/CNPJ!");
                    alert.showAndWait();
                    return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Obter os valores dos campos
        String nomeCliente = fieldNome.getText();
        String sobreNomeCliente = fieldSobrenome.getText();
        String cnpj_cpf = formatarCpfCnpj(fieldDocumento.getText());
        String nomeCidade = comboCidade.getValue().getNomeCidade();
        String nomeEstado = comboEstado.getValue().getNomeEstado();

        // Remover caracteres de formatação do CPF/CNPJ
        cnpj_cpf = cnpj_cpf.replaceAll("\\D", "");

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

        // Criar um novo objeto Cliente com as informações obtidas dos campos e o ID do endereço resultante da combinação dos IDs de cidade e estado
        Cliente cliente = new Cliente(0, enderecoID, nomeCliente, sobreNomeCliente, cnpj_cpf, nomeCidade, nomeEstado);

        // Atribuir o novo cliente ao atributo estático cliente
        NovoClienteModalController.cliente = cliente;

        // Fechar a janela modal
        HelloApplication.closeCurrentWindow();
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

    private void verificarCamposVazios(Label[] alerta) {
        // Remover caracteres não numéricos do documento
        String documento = fieldDocumento.getText().replaceAll("\\D", "");
        if (comboEstado.getValue() == null || comboCidade.getValue() == null || fieldNome.getText().isEmpty() || fieldSobrenome.getText().isEmpty() || documento.length() != 11 && documento.length() != 14) {
            for (Label label : alerta) {
                label.setVisible(true);
            }
            bSalvar.setDisable(true);
        } else {
            for (Label label : alerta) {
                label.setVisible(false);
            }
            bSalvar.setDisable(false);
        }
    }



    public void cancelar(){
        // Fechar a janela modal
        HelloApplication.closeCurrentWindow();
    }
}
