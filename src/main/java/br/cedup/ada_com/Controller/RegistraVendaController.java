package br.cedup.ada_com.controller;

import br.cedup.ada_com.*;
import br.cedup.ada_com.model.dao.*;
import br.cedup.ada_com.model.Exp_cliente;
import br.cedup.ada_com.model.Catalogo;
import br.cedup.ada_com.model.Cliente;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import br.cedup.ada_com.model.ItemVendido;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.CNPJFormatter;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;

public class RegistraVendaController implements Initializable {

    //Passo 1 - Selecione um cliente.
    @FXML
    TextField compoPesquisaCliente;
    @FXML
    AnchorPane paneLocalizado;
    @FXML
    CheckBox comfirmaSelecao;
    @FXML
    Label completaNome;
    @FXML
    Label completaDocumento;
    @FXML
    Label completaCidade;
    @FXML
    Label naoLocalizado;
    @FXML
    Text nCliente;
    @FXML
    Text dCliente;
    @FXML
    Text eCliente;

    //Passo 2 - Registre os itens vendidos.
    @FXML
    ComboBox <Catalogo> produtosServicos;
    @FXML
    TextField quantidadeProduto;
    @FXML
    Button botaoIncluir;
    @FXML
    TableView<ItemVendido> tabelaItensCarrinho;
    @FXML
    TableColumn nomeItem;
    @FXML
    TableColumn qtdItem;
    @FXML
    TableColumn precoItem;
    @FXML
    Label valorTotal;

    //Passo 3 - Registre a experiência do cliente, onde é necessario escolher ao menos 1 alternativa para cada pergunta.
    @FXML
    Label pergunta1;
    @FXML
    Label pergunta2;
    @FXML
    Label pergunta3;
    @FXML
    Label pergunta4;
    @FXML
    ComboBox<Exp_cliente> comboP1;
    @FXML
    ComboBox<Exp_cliente> comboP2;
    @FXML
    ComboBox<Exp_cliente> comboP3;
    @FXML
    ComboBox<Exp_cliente> comboP4;

    //Passo 4 - Registra uma observação, caso necessario (Opcional)
    @FXML
    TextArea ObsCompra;

    private Cliente cliente;
    private int clienteID;
    private List<ComboBox<Exp_cliente>> comboBoxes;
    private boolean atualizandoCampo = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        // Criar uma instância do DAO para acessar o banco de dados
        CatalogoDAO catalogoDAO = new CatalogoDAO();

        // Consultar o banco de dados para recuperar a lista de itens
        List<Catalogo> itens = null;
        try {
            itens = catalogoDAO.getItens();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Adicionar a lista de itens à lista de opções do ComboBox
        produtosServicos.getItems().addAll(itens);

        // Definir um CellFactory personalizado para o ComboBox
        produtosServicos.setCellFactory(new Callback<ListView<Catalogo>, ListCell<Catalogo>>() {
            @Override
            public ListCell<Catalogo> call(ListView<Catalogo> param) {
                return new ListCell<Catalogo>() {
                    @Override
                    protected void updateItem(Catalogo item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNome() + " - R$: " + item.getValor());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        // Definir um CellFactory personalizado para o botão do ComboBox
        produtosServicos.setButtonCell(new ListCell<Catalogo>() {
            @Override
            protected void updateItem(Catalogo item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNome() + " - R$: " + item.getValor());
                } else {
                    setText(null);
                }
            }
        });

        // Adicionar um ouvinte ao valor selecionado do ComboBox
        produtosServicos.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Verificar se o item selecionado é um serviço
            if (newValue != null && newValue.getTipo() == 2) {
                // Desativar o TextField quantidadeProduto
                quantidadeProduto.setDisable(true);
                // Ativar o botão incluir
                botaoIncluir.setDisable(false);
            } else {
                // Ativar o TextField quantidadeProduto
                quantidadeProduto.setDisable(false);
                // Desativar o botão incluir se a quantidade não for válida
                try {
                    int quantidade = Integer.parseInt(quantidadeProduto.getText());
                    botaoIncluir.setDisable(quantidade <= 0);
                } catch (NumberFormatException e) {
                    botaoIncluir.setDisable(true);
                }
            }
        });

        // Desativar o botão incluir inicialmente
        botaoIncluir.setDisable(true);

        // Adicionar um ouvinte de texto ao campo quantidadeProduto
        quantidadeProduto.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Tentar converter o texto digitado em um número inteiro
                int quantidade = Integer.parseInt(newValue);
                // Se a conversão for bem-sucedida e a quantidade for maior que 0, ativar o botão incluir
                botaoIncluir.setDisable(quantidade <= 0);
            } catch (NumberFormatException e) {
                // Se a conversão falhar, desativar o botão incluir
                botaoIncluir.setDisable(true);
            }
        });

        nomeItem.setCellValueFactory(new PropertyValueFactory<>("nome"));
        qtdItem.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        precoItem.setCellValueFactory(new PropertyValueFactory<>("preco"));

        // Declaração do DAO
        ExperienciaVendaDAO experienciaVendaDAO = new ExperienciaVendaDAO();

        // Recuperação das perguntas do banco de dados
        List<String> perguntas = experienciaVendaDAO.getPerguntas();

        // Exibição das perguntas nos labels
        pergunta1.setText(perguntas.get(0));
        pergunta2.setText(perguntas.get(1));
        pergunta3.setText(perguntas.get(2));
        pergunta4.setText(perguntas.get(3));

        try {
            List<Exp_cliente> alternativasP1 = experienciaVendaDAO.getAlternativas(1);
            List<Exp_cliente> alternativasP2 = experienciaVendaDAO.getAlternativas(2);
            List<Exp_cliente> alternativasP3 = experienciaVendaDAO.getAlternativas(3);
            List<Exp_cliente> alternativasP4 = experienciaVendaDAO.getAlternativas(4);

            // Exibição das alternativas nos combo boxes
            comboP1.setItems(FXCollections.observableArrayList(alternativasP1));
            comboP2.setItems(FXCollections.observableArrayList(alternativasP2));
            comboP3.setItems(FXCollections.observableArrayList(alternativasP3));
            comboP4.setItems(FXCollections.observableArrayList(alternativasP4));
        } catch (SQLException e) {
            // Tratamento da exceção SQLException
            System.err.println("Erro ao recuperar as alternativas do banco de dados: " + e.getMessage());
        }
        comboBoxes = new ArrayList<>();
        comboBoxes.add(comboP1);
        comboBoxes.add(comboP2);
        comboBoxes.add(comboP3);
        comboBoxes.add(comboP4);

        compoPesquisaCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!atualizandoCampo) {
                atualizandoCampo = true;

                String cpfCnpj = formatarCpfCnpj(newValue);

                // Atualizar o texto do campo compoPesquisaCliente
                compoPesquisaCliente.setText(cpfCnpj);

                atualizandoCampo = false;
            }
        });

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

    @FXML
    private void botaoIncluir() {
        Catalogo itemSelecionado = produtosServicos.getValue();
        int quantidade;
        if (itemSelecionado.getTipo() == 2) {
            // Se o item selecionado é um serviço, definir a quantidade como 1
            quantidade = 1;
        } else {
            // Caso contrário, obter a quantidade digitada no campo quantidadeProduto
            quantidade = Integer.parseInt(quantidadeProduto.getText());
        }
        ItemVendido itemVendido = new ItemVendido(itemSelecionado.getItemID(), itemSelecionado.getNome(), quantidade, itemSelecionado.getValor());

        tabelaItensCarrinho.getItems().add(itemVendido);
        atualizaValorTotal();

        // Limpar o campo quantidadeProduto
        quantidadeProduto.clear();
    }

    @FXML
    private void atualizaValorTotal() {
        double total = 0;
        for (ItemVendido itemVendido : tabelaItensCarrinho.getItems()) {
            total += itemVendido.getPreco() * itemVendido.getQuantidade();
        }

        // Criar uma instância de NumberFormat para formatar o valor como moeda
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorFormatado = format.format(total);

        // Atualizar o texto do Label valorTotal com o valor formatado
        valorTotal.setText(valorFormatado);
    }

    @FXML
    void onPesquisarCliente(ActionEvent event) throws SQLException {
        // limpa a seleção do CheckBox
        completaNome.setText("");
        completaDocumento.setText("");
        completaCidade.setText("");

        ClienteDAO dao = new ClienteDAO();
        String cpfCnpj = compoPesquisaCliente.getText();
        // Remover caracteres de formatação
        cpfCnpj = cpfCnpj.replaceAll("\\D", "");

        Cliente cliente = dao.getClienteByCpfCnpj(cpfCnpj);
        if (cliente != null) {
            clienteID = cliente.getClienteID();
            System.out.println(clienteID);
            //this.cliente = cliente; // atribui o cliente localizado ao campo cliente

            String nomeCompleto = cliente.getNomeCliente() + " " + cliente.getSobreNomeCliente();
            completaNome.setText(nomeCompleto);
            String cidadeEstado = cliente.getCidade() + ", " + cliente.getEstado();
            completaCidade.setText(cidadeEstado);
            completaDocumento.setText((String.valueOf(cliente.getCnpj_cpf())));
            paneLocalizado.setVisible(true);
            naoLocalizado.setVisible(false);
        } else {
            System.out.println("Nenhum cliente localizado");
            paneLocalizado.setVisible(false);
            naoLocalizado.setText("Nenhum cliente localizado");
            naoLocalizado.setTextFill(Color.RED);
            naoLocalizado.setVisible(true);
        }
    }

    @FXML
    public void registrar() throws IOException {
        try {
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
            int vendedorID = colaboradorDAO.getVendedorLogadoID();

            List<Integer> itemIDs = new ArrayList<>();
            List<Integer> quantidades = new ArrayList<>();

            double valorTotalCarrinho = 0;
            for (ItemVendido itemVendido : tabelaItensCarrinho.getItems()) {
                int itemID = itemVendido.getItemID();
                int quantidade = itemVendido.getQuantidade();

                itemIDs.add(itemID);
                quantidades.add(quantidade);
                valorTotalCarrinho += quantidade * itemVendido.getPreco();
            }

            List<Integer> perguntaIDs = new ArrayList<>();
            List<Integer> alternativaIDs = new ArrayList<>();

            //for-each
            for (ComboBox<Exp_cliente> comboBox : comboBoxes) {
                Exp_cliente expclienteSelecionada = comboBox.getValue();
                if (expclienteSelecionada != null) {
                    int perguntaID = expclienteSelecionada.getPerguntaID();
                    int alternativaID = expclienteSelecionada.getAlternativaID();

                    perguntaIDs.add(perguntaID);
                    alternativaIDs.add(alternativaID);
                }
            }

            //instância compra e obtêm o texto do campo ObsCompra e armazena na variavel obsCompra
            String obsCompra = ObsCompra.getText();

            // Verificar se todos os campos necessários foram preenchidos
            //se um cliente foi selecionado
            if (clienteID != 0) {
                //se pelo menos 1 item foi colocado ao "carrinho de compras" = tabelaItensCarrinho
                if (!tabelaItensCarrinho.getItems().isEmpty()) {
                    //se as perguntas da experiência de venda foram preenchidas
                    if (comboP1.getValue() != null && comboP2.getValue() != null && comboP3.getValue() != null && comboP4.getValue() != null) {
                        // Todos os campos necessários foram preenchidos
                        // Registrar a venda no banco de dados
                        RegistraVendaDAO registraVendaDAO = new RegistraVendaDAO();
                        registraVendaDAO.registrarVenda(clienteID, vendedorID, itemIDs, quantidades, valorTotalCarrinho, perguntaIDs, alternativaIDs, obsCompra);

                        // Exibir alerta de sucesso
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Venda registrada");
                        alert.setHeaderText(null);
                        alert.setContentText("A venda foi registrada com sucesso. Deseja realizar um novo cadastro?");

                        ButtonType buttonTypeSim = new ButtonType("Sim");
                        ButtonType buttonTypeNao = new ButtonType("Não");
                        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == buttonTypeSim){
                            // Realizar novo cadastro
                            // Limpar campos
                            compoPesquisaCliente.clear();
                            paneLocalizado.setVisible(false);
                            comfirmaSelecao.setSelected(false);
                            completaNome.setText("");
                            completaDocumento.setText("");
                            completaCidade.setText("");
                            naoLocalizado.setVisible(false);
                            nCliente.setText("");
                            dCliente.setText("");
                            eCliente.setText("");

                            produtosServicos.setValue(null);
                            quantidadeProduto.clear();
                            tabelaItensCarrinho.getItems().clear();
                            valorTotal.setText("");

                            comboP1.setValue(null);
                            comboP2.setValue(null);
                            comboP3.setValue(null);
                            comboP4.setValue(null);

                            ObsCompra.clear();
                        } else {
                            HelloApplication.setRoot("Main-view");
                        }
                    } else {
                        // Exibir alerta de erro
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText(null);
                        alert.setContentText("Pelo menos uma alternativa deve ser selecionada para cada pergunta.");

                        alert.showAndWait();
                    }
                } else {
                    // Exibir alerta de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Pelo menos um item deve ser adicionado ao carrinho.");

                    alert.showAndWait();
                }
            } else {
                // Exibir alerta de erro
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Nenhum cliente localizado.");

                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Tratamento da exceção SQLException
            System.err.println("Erro ao registrar a venda no banco de dados: " + e.getMessage());
        }
    }

    //Volta para tela principal
    @FXML
    public void fechar() throws IOException {
        HelloApplication.setRoot("Main-view");
    }
}
