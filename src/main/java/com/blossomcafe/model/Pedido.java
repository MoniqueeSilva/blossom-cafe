package com.blossomcafe.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<Produto> produtos;

    public Pedido(int id) {
        this.id = id;
        this.produtos = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Produto> getProdutos() { return produtos; }

    public void adicionarProduto(Produto produto) {
        if (produto.isDisponivel()) {
            produtos.add(produto);
        } else {
            System.out.println("Produto indisponível: " + produto.getNome());
        }
    }

    // Método para remover produto
    public boolean removerProduto(Produto produto) {
        return produtos.remove(produto);
    }

    // Método para limpar todos os produtos
    public void limparProdutos() {
        produtos.clear();
    }

    // Método para calcular valor total
    public double calcularValorTotal() {
        double total = 0.0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }

    // Método para obter quantidade de produtos
    public int getQuantidadeProdutos() {
        return produtos.size();
    }
}