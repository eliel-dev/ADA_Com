package br.cedup.ada_com;

public class Colaborador {
    public int nivel;
    public String nomeColaborador;
    public String sobrenome;
    public String user;
    public String password;


    public Colaborador(int nivel,String user, String password, String nomeColaborador, String sobrenome) {
        this.nivel = nivel;
        this.nomeColaborador = nomeColaborador;
        this.sobrenome = sobrenome;
        this.user = user;
        this.password = password;
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
