package br.cedup.ada_com.model;

public class Exp_cliente {
    private int perguntaID;
    private int alternativaID;
    private String texto;

    public Exp_cliente(int perguntaID, int alternativaID, String texto) {
        this.perguntaID = perguntaID;
        this.alternativaID = alternativaID;
        this.texto = texto;
    }

    public int getPerguntaID() {
        return perguntaID;
    }

    public int getAlternativaID() {
        return alternativaID;
    }

    @Override
    public String toString() {
        return texto;
    }
}
