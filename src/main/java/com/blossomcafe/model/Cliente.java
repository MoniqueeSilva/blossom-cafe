package com.blossomcafe.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int idCliente;
    private String nome, telefone, email, cpf, senha;
    private List<Endereco> enderecos;
    private List<Pedido> pedidos;

    // Construtor com senha
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

    // Getters e Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public List<Endereco> getEnderecos() { return enderecos; }
    public void setEnderecos(List<Endereco> enderecos) { this.enderecos = enderecos; }
    
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

    // Métodos
    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public boolean removerPedido(Pedido pedido) {
        return pedidos.remove(pedido);
    }

    public List<Pedido> getHistoricoPedidos() {
        return new ArrayList<>(pedidos);
    }

    public String toString() {
        return String.format("Id: %d, Nome: %s, Telefone: %s, Email: %s, CPF: %s", 
            idCliente, nome, telefone, email, cpf);
    }

    // Método para verificar senha (opcional)
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }
}