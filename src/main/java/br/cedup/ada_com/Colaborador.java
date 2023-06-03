package br.cedup.ada_com;

public class Colaborador {
    public int nivel;
    public String nome;
    public String user;
    public String password;


    public Colaborador(int nivel,String user, String password) {
        this.nivel = nivel;
        this.nome = nome;
        this.user = user;
        this.password = password;
    }


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
