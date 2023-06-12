package br.cedup.ada_com;

import java.util.Date;

public class RegistroVenda {
    private int vendaId;
    private int clienteId;
    private int colaboradorId;
    private int catalogoItemId;
    private int experienciaId;
    private double valorVenda;
    private Date dataVenda;
    private int quantidadeVendida;
    private String anotacoes;

    public RegistroVenda(int vendaId, int clienteId, int colaboradorId, int catalogoItemId, int experienciaId, double valorVenda, Date dataVenda, int quantidadeVendida, String anotacoes) {
        this.vendaId = vendaId;
        this.clienteId = clienteId;
        this.colaboradorId = colaboradorId;
        this.catalogoItemId = catalogoItemId;
        this.experienciaId = experienciaId;
        this.valorVenda = valorVenda;
        this.dataVenda = dataVenda;
        this.quantidadeVendida = quantidadeVendida;
        this.anotacoes = anotacoes;
    }

    public int getVendaId() {
        return vendaId;
    }

    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(int colaboradorId) {
        this.colaboradorId = colaboradorId;
    }

    public int getCatalogoItemId() {
        return catalogoItemId;
    }

    public void setCatalogoItemId(int catalogoItemId) {
        this.catalogoItemId = catalogoItemId;
    }

    public int getExperienciaId() {
        return experienciaId;
    }

    public void setExperienciaId(int experienciaId) {
        this.experienciaId = experienciaId;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }
}
