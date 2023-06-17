package br.cedup.ada_com;

public class Alternativa {
    private int perguntaID;
    private int alternativaID;
    private String texto;

    public Alternativa(int perguntaID, int alternativaID, String texto) {
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
