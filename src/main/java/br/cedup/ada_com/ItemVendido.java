package br.cedup.ada_com;

public class ItemVendido {
    private String nome;
    private int quantidade;
    private double preco;

    public ItemVendido(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }
}
