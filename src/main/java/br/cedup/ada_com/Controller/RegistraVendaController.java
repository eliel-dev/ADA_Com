package br.cedup.ada_com.Controller;

import br.cedup.ada_com.Catalogo;
import br.cedup.ada_com.Cliente;
import br.cedup.ada_com.DAO.CatalogoDAO;
import br.cedup.ada_com.DAO.ClienteDAO;
import br.cedup.ada_com.HelloApplication;
import br.cedup.ada_com.ItemVendido;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
    TableView tabelaItensCarrinho;
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
    ComboBox comboP1;
    @FXML
    Label pergunta1;
    @FXML
    ComboBox comboP2;
    @FXML
    Label pergunta2;
    @FXML
    ComboBox comboP3;
    @FXML
    Label pergunta3;
    @FXML
    ComboBox comboP4;
    @FXML
    Label pergunta4;

    @FXML
    TextArea ObsCompra;

    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            } else {
                // Ativar o TextField quantidadeProduto
                quantidadeProduto.setDisable(false);
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
                    int clienteID = cliente.getClienteID();
                    System.out.println("Cliente ID: " + clienteID);
                    // use o clienteID para registrar na tabela registravenda do banco de dados
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
            ItemVendido itemVendido = new ItemVendido(itemSelecionado.getNome(), quantidade, itemSelecionado.getValor());
            tabelaItensCarrinho.getItems().add(itemVendido);
            atualizaValorTotal();

            // Limpar o campo quantidadeProduto
            quantidadeProduto.clear();
        });
        nomeItem.setCellValueFactory(new PropertyValueFactory<>("nome"));
        qtdItem.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        precoItem.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    @FXML
    private void atualizaValorTotal() {
        double total = 0;
        for (Object item : tabelaItensCarrinho.getItems()) {
            ItemVendido itemVendido = (ItemVendido) item;
            total += itemVendido.getPreco() * itemVendido.getQuantidade();
        }
        valorTotal.setText(String.format("R$ %.2f", total));
    }


    @FXML
    void onPesquisarCliente(ActionEvent event) {
        System.out.println("onPesquisarCliente chamado");

        completaNome.setText("");
        completaDocumento.setText("");
        completaCidade.setText("");
        comfirmaSelecao.setSelected(false); // limpa a seleção do CheckBox

        ClienteDAO dao = new ClienteDAO();
        String cpfCnpj = compoPesquisaCliente.getText();
        Cliente cliente = dao.getClienteByCpfCnpj(cpfCnpj);
        if (cliente != null) {
            System.out.println("Cliente localizado: " + cliente.getNomeCliente()); // adicionado para depuração
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
    public void registrar () throws IOException {

    }

    //Volta para tela principal de vendedor
    @FXML
    public void fechar() throws IOException {
        HelloApplication.setRoot("Main-view");
    }




}
