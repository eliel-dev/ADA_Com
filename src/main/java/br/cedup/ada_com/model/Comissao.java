package br.cedup.ada_com.model;

import java.util.Date;

public class Comissao {
    private int comissaoId;
    private double valorComissao;
    private double taxaComissao;
    private Date data;
    private int colaboradorId;

    public int getComissaoId() {
        return comissaoId;
    }

    public void setComissaoId(int comissaoId) {
        this.comissaoId = comissaoId;
    }

    public double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(double valorComissao) {
        this.valorComissao = valorComissao;
    }

    public double getTaxaComissao() {
        return taxaComissao;
    }

    public void setTaxaComissao(double taxaComissao) {
        this.taxaComissao = taxaComissao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(int colaboradorId) {
        this.colaboradorId = colaboradorId;
    }
}
