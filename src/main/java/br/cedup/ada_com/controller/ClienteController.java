package br.cedup.ada_com.controller;

import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.model.*;
import br.cedup.ada_com.model.dao.ClienteDAO;
import br.cedup.ada_com.model.dao.EnderecoDAO;
import br.cedup.ada_com.model.dao.ExperienciaVendaDAO;
import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class ClienteController implements Initializable {
    @FXML
    ListView<Estado> listEstados;
    @FXML
    ListView<Cidade> listCidades;

    @FXML
    ListView<String> perguntasListView;
    @FXML
    ListView<Exp_cliente> alternativasListView;

    @FXML
    TableView<Cliente> listaCliente;
    @FXML
    TableColumn<Cliente, Integer> codCliente;
    @FXML
    TableColumn<Cliente, String> nomeCliente;
    @FXML
    TableColumn<Cliente, String> documentoCliente;
    @FXML
    TableColumn<Cliente, String> enderecoCliente;

    @FXML
    Button bNovo;
    @FXML
    Button bEditar;
    @FXML
    Button bExcluir;
    @FXML
    Button bVoltar;
    @FXML
    Tab tabCliente;
    @FXML
    TabPane tabPane;

    EnderecoDAO enderecoDAO = new EnderecoDAO();
    ExperienciaVendaDAO dao = new ExperienciaVendaDAO();
    ClienteDAO clienteDAO = new ClienteDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == tabCliente) {
                // Habilitar os botões
                bNovo.setDisable(false);
                bEditar.setDisable(false);
                bExcluir.setDisable(false);
            } else {
                // Desabilitar os botões
                bNovo.setDisable(true);
                bEditar.setDisable(true);
                bExcluir.setDisable(true);
            }
        });

        //new PropertyValueFactory() o valor é exibido da mesma forma como está no BD
        codCliente.setCellValueFactory(new PropertyValueFactory<>("clienteID"));
        //cellData posso personalizar a lógica de exibição dos dados
        nomeCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeCliente() + " " + cellData.getValue().getSobreNomeCliente()));
        enderecoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCidade() + ", " + cellData.getValue().getEstado()));

        documentoCliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cliente, String> param) {
                return new SimpleStringProperty(formatarCpfCnpj(param.getValue().getCnpj_cpf()));
            }
        });

        List<Cliente> clientes = null;
        try {
            clientes = clienteDAO.getClientes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listaCliente.setItems(FXCollections.observableArrayList(clientes));

        //Lista as cidades do estado selecionado
        try {
            List<Estado> estados = enderecoDAO.getEstados();
            ObservableList<Estado> estadoData = FXCollections.observableArrayList(estados);
            listEstados.setItems(estadoData);

            listEstados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Estado estadoSelecionado = (newValue);
                    List<Cidade> cidades;
                    try {
                        cidades = enderecoDAO.getCidadesByEstadoID(estadoSelecionado.getEstadoID());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ObservableList<Cidade> cidadeData = FXCollections.observableArrayList(cidades);
                    listCidades.setItems(cidadeData);
                }
            });
        } catch (SQLException e) {
            // tratar exceção
        }

        List<String> perguntas = dao.getPerguntas();
        perguntasListView.getItems().addAll(perguntas);

        perguntasListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int perguntaID = perguntasListView.getSelectionModel().getSelectedIndex() + 1;
            List<Exp_cliente> alternativas = null;
            try {
                alternativas = dao.getAlternativas(perguntaID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            alternativasListView.getItems().clear();
            alternativasListView.getItems().addAll(alternativas);
        });

        listaCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                bEditar.setDisable(false);
                bExcluir.setDisable(false);
            }
        });

        //Botão branco:
        Button[] branco = {bNovo, bEditar, bVoltar};

        for (Button button : branco) {
            button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18; -fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18; -fx-background-color: #0c0c0c; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3; -fx-border-width: 2;"));
        }

        //Botão vermelho:
        bExcluir.setOnMouseEntered(e -> bExcluir.setStyle("-fx-font-size: 18; -fx-background-color: red; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));
        bExcluir.setOnMouseExited(e -> bExcluir.setStyle("-fx-font-size: 18; -fx-background-color: #0c0c0c; -fx-border-color: red; -fx-border-radius: 3; -fx-border-width: 2;"));

    }

    @FXML
    public void novo() throws IOException {
        // Definir o atributo estático cliente da classe NovoClienteModalController como nulo
        NovoClienteModalController.cliente = null;

        // Abrir o modal de cadastro de novo cliente e esperar o usuário clicar em OK
        HelloApplication.showModal("novoClienteModal");

        // Obter o novo cliente do modal de cadastro
        Cliente novoCliente = NovoClienteModalController.cliente;

        // Verificar se o novo cliente não é nulo
        if (novoCliente != null) {
            // Criar um novo objeto ClienteDAO
            ClienteDAO dao = new ClienteDAO();
            try {
                // Cadastrar o novo cliente no banco de dados
                dao.cadastrarCliente(novoCliente);
                // Adicionar o novo cliente à lista gráfica
                listaCliente.getItems().add(novoCliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void editar() throws IOException, SQLException {
        // Obter o cliente selecionado na tabela
        Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            // Enviar o cliente para o controlador do modal de edição
            NovoClienteModalController.setCliente(clienteSelecionado);

            // Abrir o modal de edição e esperar o usuário clicar em OK
            HelloApplication.showModal("novoClienteModal");

            // Obter o cliente alterado do modal de edição
            Cliente clienteAlterado = NovoClienteModalController.getCliente();

            /// Alterar o cliente original com o alterado
            clienteSelecionado.setNomeCliente(clienteAlterado.getNomeCliente());
            clienteSelecionado.setSobreNomeCliente(clienteAlterado.getSobreNomeCliente());
            clienteSelecionado.setCnpj_cpf(clienteAlterado.getCnpj_cpf());
            clienteSelecionado.setCidade(clienteAlterado.getCidade());
            clienteSelecionado.setEstado(clienteAlterado.getEstado());

            // Atualizar a lista gráfica para aplicar as alterações do cliente
            this.listaCliente.refresh();

            // Salvar o cliente no banco de dados
            ClienteDAO dao = new ClienteDAO();
            dao.editarCliente(clienteSelecionado);
        }
    }

    @FXML
    public void excluir() throws SQLException {
        // Obter o cliente selecionado na tabela
        Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            // Exibir uma caixa de diálogo de confirmação
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Cliente");
            alert.setHeaderText("Tem certeza que deseja excluir este cliente?");
            alert.setContentText(clienteSelecionado.getNomeCliente() + " " + clienteSelecionado.getSobreNomeCliente());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remover o cliente do banco de dados
                ClienteDAO dao = new ClienteDAO();

                try {
                    dao.excluirCliente(clienteSelecionado.getClienteID());
                    listaCliente.getItems().remove(clienteSelecionado);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
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
}
