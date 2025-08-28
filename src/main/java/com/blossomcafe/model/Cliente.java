package com.blossomcafe.model;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String cpf;
    private List<Pedido> pedidos;

    public Cliente(String nome, String endereco, String telefone, String email, String cpf) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.pedidos = new ArrayList<>();
    }

    public String getNome(){ 
        return nome; 
    }
    public String getEndereco(){ 
        return endereco; 
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
    public List<Pedido> getPedidos(){ 
        return pedidos;
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    //Histórico de pedidos
    public List<Pedido> getHistoricoPedidos() {
        return new ArrayList<>(pedidos); //Retorna uma cópia da lista
    }
}
