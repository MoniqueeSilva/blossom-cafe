package com.blossomcafe.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private String enderecoEntrega;
    private List<Produto> produtos;
    private String status; // "Preparando", "A caminho", "Entregue"

    public Pedido(int id) {
        this.id = id;
        this.produtos = new ArrayList<>();
        this.status = "Preparando"; // status inicial
    }

    // ====================== GETTERS E SETTERS ======================
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getEnderecoEntrega() {
    return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }


    public List<Produto> getProdutos(){
        return produtos;
    }

    public String getStatus(){
        return status; 
    }
    
    public void setStatus(String status){
        this.status = status;
    }

    // ====================== MÉTODOS DE PRODUTOS ======================
    public void adicionarProduto(Produto produto) {
        if (produto.isDisponivel()) {
            produtos.add(produto);
        } else {
            System.out.println("Produto indisponível: " + produto.getNome());
        }
    }

    public boolean removerProduto(Produto produto) {
        return produtos.remove(produto);
    }

    public void limparProdutos() {
        produtos.clear();
    }

    public double calcularValorTotal() {
        double total = 0.0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }

    public int getQuantidadeProdutos() {
        return produtos.size();
    }

    // ====================== MÉTODOS DE STATUS ======================
    public void marcarComoPreparando() {
        status = "Preparando";
    }

    public void marcarComoACaminho() {
        status = "A caminho";
    }

    public void marcarComoEntregue() {
        status = "Entregue";
    }
}
