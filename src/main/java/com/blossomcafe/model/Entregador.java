package com.blossomcafe.model;
public class Entregador {
    private String nome;
    private String veiculo;
    private String placa;
    private String cnh;

    public Entregador(String nome, String veiculo, String placa, String cnh) {
        this.nome = nome;
        this.veiculo = veiculo;
        this.placa = placa;
        this.cnh = cnh;
    }

    public String getNome(){ 
        return nome; 
    }
    public String getVeiculo(){ 
        return veiculo;
    }
    public String getPlaca(){ 
        return placa;
    }
    public String getCnh(){
        return cnh;
    }
}
