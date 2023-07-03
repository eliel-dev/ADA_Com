package br.cedup.ada_com.model;

public class Colaborador {
    public int colaboradorId;
    public int nivel;
    public String nomeColaborador;
    public String sobrenome;
    public String user;
    public String password;

    public Colaborador(int colaboradorId,int nivel,String nomeColaborador, String sobrenome, String user, String password) {
        this.colaboradorId = colaboradorId;
        this.nivel = nivel;
        this.nomeColaborador = nomeColaborador;
        this.sobrenome = sobrenome;
        this.user = user;
        this.password = password;
    }

    public int getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(int colaboradorId) {
        this.colaboradorId = colaboradorId;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
