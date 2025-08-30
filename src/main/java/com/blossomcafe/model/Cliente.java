package com.blossomcafe.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int idCliente;
    private String nome, telefone, email, cpf, senha;
    private List<Endereco> enderecos;
    private List<Pedido> pedidos;

    public Cliente(int idCliente, String nome, String telefone, String email, String cpf, String senha) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.enderecos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    // getters
    public int getIdCliente(){
        return idCliente;
    }
    public String getNome(){
        return nome;
    }
    public String getTelefone(){
        return telefone;
    }
    public String getEmail(){
        return email;
    }
    public String getCpf(){
        return cpf;
    }
    public String getSenha(){
        return senha;
    }
    public List<Endereco> getEnderecos(){
        return enderecos;
    }
    public List<Pedido> getPedidos(){
        return pedidos;
    }

    // setters
    public void setIdCliente(int idCliente){
        this.idCliente = idCliente;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public void setEnderecos(List<Endereco> enderecos){
        this.enderecos = enderecos;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void setPedidos(List<Pedido> pedidos){
        this.pedidos = pedidos;
    }

    //metódos
    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public boolean removerPedido(Pedido pedido) {
        return pedidos.remove(pedido);
    }

    public List<Pedido> getHistoricoPedidos() {
        return new ArrayList<>(pedidos);
    }

    //toString de cliente
    public String toString() {
        return String.format("Id: %d, Nome: %s, Telefone: %s, Email: %s, CPF: %s, Endereço: %s",idCliente, nome, telefone, email, cpf, enderecos);
    }
}
