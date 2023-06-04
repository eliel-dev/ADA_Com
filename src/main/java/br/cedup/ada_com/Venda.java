package br.cedup.ada_com;

public class Venda {
    public String nomeColaborador;
    public double totalVendas;

    public Venda(String nomeColaborador, double totalVendas) {
        this.nomeColaborador = nomeColaborador;
        this.totalVendas = totalVendas;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public double getTotalVendas() {
        return totalVendas;
    }
}
