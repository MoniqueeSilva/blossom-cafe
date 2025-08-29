package com.blossomcafe.controller;

import com.blossomcafe.dao.EntregaDAO;
import com.blossomcafe.dao.EntregadorDAO;
import com.blossomcafe.model.Entrega;
import com.blossomcafe.model.Entregador;
import java.util.List;

public class EntregaController {
    private EntregaDAO entregaDAO;
    private EntregadorDAO entregadorDAO;

    public EntregaController() {
        this.entregaDAO = new EntregaDAO();
        this.entregadorDAO = new EntregadorDAO();
    }

    public boolean criarEntrega(String codRastreio, String cnhEntregador) {
        if (codRastreio == null || codRastreio.isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser vazio");
        }
        if (cnhEntregador == null || cnhEntregador.isEmpty()) {
            throw new IllegalArgumentException("CNH do entregador não pode ser vazia");
        }

        try {
            Entregador entregador = entregadorDAO.buscarPorCnh(cnhEntregador);
            if (entregador == null) {
                throw new IllegalArgumentException("Entregador não encontrado");
            }

            Entrega entrega = new Entrega(codRastreio, entregador);
            entregaDAO.inserir(entrega);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao criar entrega: " + e.getMessage());
            return false;
        }
    }

    public Entrega buscarEntregaPorCodigo(String codRastreio) {
        if (codRastreio == null || codRastreio.isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser vazio");
        }
        return entregaDAO.buscarPorCodRastreio(codRastreio);
    }

    public List<Entrega> listarEntregasPorEntregador(String cnhEntregador) {
        if (cnhEntregador == null || cnhEntregador.isEmpty()) {
            throw new IllegalArgumentException("CNH do entregador não pode ser vazia");
        }
        return entregaDAO.buscarPorEntregador(cnhEntregador);
    }

    public List<Entrega> listarTodasEntregas() {
        return entregaDAO.listarTodas();
    }

    public boolean atualizarEntregadorEntrega(String codRastreio, String novaCnhEntregador) {
        if (codRastreio == null || codRastreio.isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser vazio");
        }
        if (novaCnhEntregador == null || novaCnhEntregador.isEmpty()) {
            throw new IllegalArgumentException("CNH do entregador não pode ser vazia");
        }

        try {
            Entrega entrega = entregaDAO.buscarPorCodRastreio(codRastreio);
            Entregador novoEntregador = entregadorDAO.buscarPorCnh(novaCnhEntregador);
            
            if (entrega == null || novoEntregador == null) {
                return false;
            }

            Entrega entregaAtualizada = new Entrega(codRastreio, novoEntregador);
            entregaDAO.atualizar(entregaAtualizada);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar entrega: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarEntrega(String codRastreio) {
        if (codRastreio == null || codRastreio.isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser vazio");
        }

        try {
            entregaDAO.deletar(codRastreio);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar entrega: " + e.getMessage());
            return false;
        }
    }

    public void exibirDetalhesEntrega(String codRastreio) {
        Entrega entrega = buscarEntregaPorCodigo(codRastreio);
        if (entrega != null) {
            entrega.detalhesEntrega();
        } else {
            System.out.println("Entrega não encontrada!");
        }
    }
}