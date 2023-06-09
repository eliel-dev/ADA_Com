package br.cedup.ada_com;

public class Catalogo {

    public int itemID;
    public String nome;
    public Double valor;
    public String categoria;
    public int tipo;

    public Catalogo(int itemID, String nome, Double valor, String categoria, int tipo) {
        this.itemID = itemID;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.tipo = tipo;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}