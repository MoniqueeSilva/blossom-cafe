package com.blossomcafe.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<Produto> produtos;

    public Pedido() {
        this.produtos = new ArrayList<>();
    }

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
}
