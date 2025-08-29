package com.blossomcafe.model;
public class Cafe extends Produto {
    private String tipoCafe;

    public Cafe(int idProduto, String nome, double preco, boolean disponivel, String tipoCafe) {
        super(idProduto, nome, preco, disponivel);
        this.tipoCafe = tipoCafe;
    }

    public String getTipoCafe(){
        return tipoCafe;
    }

    public void setTipocafe(String tipoCafe){
        this.tipoCafe = tipoCafe;
    }

    public String getDescricao() {
        return getNome() + " - " + tipoCafe;
    }
}
