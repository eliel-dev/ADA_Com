package br.cedup.ada_com.model;

public class Estado {
    private int estadoID;
    private String nomeEstado;

    public Estado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public int getEstadoID() {
        return estadoID;
    }

    public void setEstadoID(int estadoID) {
        this.estadoID = estadoID;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    @Override
    public String toString() {
        return nomeEstado;
    }

}

