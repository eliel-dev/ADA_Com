package br.cedup.ada_com;

public class ItemVendido {
    private int itemID;
    private String nome;
    private int quantidade;
    private double preco;

    public ItemVendido(int itemID, String nome, int quantidade, double preco) {
        this.itemID = itemID;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
