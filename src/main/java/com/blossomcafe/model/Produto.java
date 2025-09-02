package com.blossomcafe.model;

import java.util.Objects;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private boolean disponivel; // atributo que estava faltando

    // Construtor completo
    public Produto(int id, String nome, double preco, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public double getPreco(){
        return preco;
    }
    public void setPreco(double preco){
        this.preco = preco;
    }

    public boolean isDisponivel(){
        return disponivel;
    }
    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }

    public boolean equal(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Produto)){
            return false;
        }
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    public int hashCode(){
        return Objects.hash(id);
    }

    public String toString(){
        return String.format("Id: %d, Nome: %s, Preço: %.2f, Disponível: %s", id, nome, preco, disponivel ? "Sim" : "Não" ); 
    }

    
    

}
