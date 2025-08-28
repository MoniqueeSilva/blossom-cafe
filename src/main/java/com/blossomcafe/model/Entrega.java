package com.blossomcafe.model;
public class Entrega {
    private String codRastreio;
    private Entregador entregador;

    public Entrega(String codRastreio, Entregador entregador) {
        this.codRastreio = codRastreio;
        this.entregador = entregador;
    }

    public String getCodRastreio(){
        return codRastreio;
    }
    public Entregador getEntregador(){
        return entregador;
    }

    public void detalhesEntrega() {
        System.out.println("Entrega - Código de Rastreio: " + codRastreio);
        System.out.println("Entregador: " + entregador.getNome() + " (" + entregador.getVeiculo() + ")");
        System.out.println("\n");
    }
}
