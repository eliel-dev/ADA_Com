package br.cedup.ada_com.model;

public class Cliente {
    public int clienteID;
    public int enderecoID;
    public String nomeCliente;
    public String sobreNomeCliente;
    public String cnpj_cpf;
    public String cidade;
    public String estado;

    public Cliente(int clienteID, int enderecoID, String nomeCliente, String sobreNomeCliente, String cnpj_cpf) {
        this.clienteID = clienteID;
        this.enderecoID = enderecoID;
        this.nomeCliente = nomeCliente;
        this.sobreNomeCliente = sobreNomeCliente;
        this.cnpj_cpf = cnpj_cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public int getEnderecoID() {
        return enderecoID;
    }

    public void setEnderecoID(int enderecoID) {
        this.enderecoID = enderecoID;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getSobreNomeCliente() {
        return sobreNomeCliente;
    }

    public void setSobreNomeCliente(String sobreNomeCliente) {
        this.sobreNomeCliente = sobreNomeCliente;
    }

    public String getCnpj_cpf() { // alterado para String
        return cnpj_cpf;
    }

    public void setCnpj_cpf(String cnpj_cpf) { // alterado para String
        this.cnpj_cpf = cnpj_cpf;
    }

}


