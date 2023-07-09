package br.cedup.ada_com.model;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class Cliente {
    public int clienteID;
    public int enderecoID;
    public String nomeCliente;
    public String sobreNomeCliente;
    public String cnpj_cpf;
    public String cidade;
    public String estado;

    public Cliente(int clienteID, int enderecoID, String nomeCliente, String sobreNomeCliente, String cnpj_cpf, String cidade, String estado) {
        this.clienteID = clienteID;
        this.enderecoID = enderecoID;
        this.nomeCliente = nomeCliente;
        this.sobreNomeCliente = sobreNomeCliente;
        this.cnpj_cpf = cnpj_cpf;
        this.cidade = cidade;
        this.estado = estado;
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


