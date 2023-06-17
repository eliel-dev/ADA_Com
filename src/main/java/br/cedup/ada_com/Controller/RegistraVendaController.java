package br.cedup.ada_com.Controller;

import br.cedup.ada_com.*;
import br.cedup.ada_com.DAO.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import br.cedup.ada_com.ItemVendido;

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

    //Passo 3 - Registre a experiencia do cliente.
    @FXML
    Label pergunta1;
    @FXML
    Label pergunta2;
    @FXML
    Label pergunta3;
    @FXML
    Label pergunta4;
    @FXML
    ComboBox<Alternativa> comboP1;
    @FXML
    ComboBox<Alternativa> comboP2;
    @FXML
    ComboBox<Alternativa> comboP3;
    @FXML
    ComboBox<Alternativa> comboP4;

    @FXML
    TextArea ObsCompra;

    private Cliente cliente;
    private int clienteID;
    private List<ComboBox<Alternativa>> comboBoxes;

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

        comfirmaSelecao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (comfirmaSelecao.isSelected()) {
                    clienteID = cliente.getClienteID();
                }
            }
        });

        botaoIncluir.setOnAction(event -> {
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
            List<Alternativa> alternativasP1 = experienciaVendaDAO.getAlternativas(1);
            List<Alternativa> alternativasP2 = experienciaVendaDAO.getAlternativas(2);
            List<Alternativa> alternativasP3 = experienciaVendaDAO.getAlternativas(3);
            List<Alternativa> alternativasP4 = experienciaVendaDAO.getAlternativas(4);

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
        System.out.println("onPesquisarCliente chamado");

        // limpa a seleção do CheckBox
        completaNome.setText("");
        completaDocumento.setText("");
        completaCidade.setText("");
        comfirmaSelecao.setSelected(false);

        ClienteDAO dao = new ClienteDAO();
        String cpfCnpj = compoPesquisaCliente.getText();
        Cliente cliente = dao.getClienteByCpfCnpj(cpfCnpj);
        if (cliente != null) {
            this.cliente = cliente; // atribui o cliente localizado ao campo cliente

            String nomeCompleto = cliente.getNomeCliente() + " " + cliente.getSobreNomeCliente();
            completaNome.setText(nomeCompleto);
            String cidadeEstado = cliente.getCidade() + ", " + cliente.getEstado();
            completaCidade.setText(cidadeEstado);
            completaDocumento.setText(String.valueOf(cliente.getCnpj_cpf()));
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
    public void registrar() throws IOException, SQLException {
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

        System.out.println("##############################################");
        System.out.println("Vendedor ID: " + vendedorID);
        System.out.println("Cliente ID: " + clienteID);
        System.out.println("Itens comprados ID: " + itemIDs);
        System.out.println("Quantidades: " + quantidades);
        System.out.println("Valor total do carrinho: " + valorTotalCarrinho);

        // TODO: Registrar a venda no banco de dados usando as listas itemIDs e quantidades
        List<Integer> perguntaIDs = new ArrayList<>();
        List<Integer> alternativaIDs = new ArrayList<>();

        for (ComboBox<Alternativa> comboBox : comboBoxes) {
            Alternativa alternativaSelecionada = comboBox.getValue();
            if (alternativaSelecionada != null) {
                int perguntaID = alternativaSelecionada.getPerguntaID();
                int alternativaID = alternativaSelecionada.getAlternativaID();

                perguntaIDs.add(perguntaID);
                alternativaIDs.add(alternativaID);
            }
        }

        System.out.println("Perguntas IDs: " + perguntaIDs);
        System.out.println("Alternativas IDs: " + alternativaIDs);

        String obsCompra = ObsCompra.getText();
        System.out.println("Observações da compra: " + obsCompra);
    }

    //Volta para tela principal
    @FXML
    public void fechar() throws IOException {
        HelloApplication.setRoot("Main-view");
    }
}
