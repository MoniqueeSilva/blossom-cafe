package com.blossomcafe.model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private boolean disponivel; // atributo que estava faltando

    public Produto() {}

    // Construtor sem "disponivel" (por padrão true)
    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.disponivel = true;
    }

    // Construtor completo
    public Produto(int id, String nome, double preco, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}
