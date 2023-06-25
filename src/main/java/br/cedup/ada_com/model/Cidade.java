package br.cedup.ada_com.model;

public class Cidade {
    private int cidadeID;
    private String nomeCidade;
    private int estadoID;

    public Cidade(String nomeCidade, int estadoID) {
        this.nomeCidade = nomeCidade;
        this.estadoID = estadoID;
    }

    public int getCidadeID() {
        return cidadeID;
    }

    public void setCidadeID(int cidadeID) {
        this.cidadeID = cidadeID;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public int getEstadoID() {
        return estadoID;
    }

    public void setEstadoID(int estadoID) {
        this.estadoID = estadoID;
    }

    @Override
    public String toString() {
        return nomeCidade;
    }
}
