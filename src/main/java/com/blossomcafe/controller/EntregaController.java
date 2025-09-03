package com.blossomcafe.controller;

import com.blossomcafe.dao.EntregaDAO;
import com.blossomcafe.dao.EntregadorDAO;
import com.blossomcafe.model.Entrega;
import com.blossomcafe.model.Entregador;
import java.util.List;

public class EntregaController {
    private EntregaDAO entregaDAO; //OPERAÇÃO DE ENTREGA
    private EntregadorDAO entregadorDAO; //OPERAÇÃO DE ENTREGADOR

    public EntregaController() {
        this.entregaDAO = new EntregaDAO();
        this.entregadorDAO = new EntregadorDAO();
    }

    //VALIDAR CÓDIGO DE RASTREIO
    public boolean criarEntrega(String codRastreio, String cnhEntregador) {
        if (codRastreio == null || codRastreio.isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser vazio");
        }
        //VALIDAR CNH
        if (cnhEntregador == null || cnhEntregador.isEmpty()) {
            throw new IllegalArgumentException("CNH do entregador não pode ser vazia");
        }
        //BUSCAR ENTREGADOR
        try {
            Entregador entregador = entregadorDAO.buscarPorCnh(cnhEntregador);
            if (entregador == null) {
                throw new IllegalArgumentException("Entregador não encontrado");
            }

            Entrega entrega = new Entrega(codRastreio, entregador);
            entregaDAO.inserir(entrega); //Insere no banco
            return true; //Retorna sucesso
        } catch (Exception e) {
            System.out.println("Erro ao criar entrega: " + e.getMessage());
            return false;
        }
    }

    //BUSCAR ENTREGA POR CÓDIGO
    public Entrega buscarEntregaPorCodigo(String codRastreio) {
        if (codRastreio == null || codRastreio.isEmpty()) { //Valida código
            throw new IllegalArgumentException("Código de rastreio não pode ser vazio");
        }
        return entregaDAO.buscarPorCodRastreio(codRastreio); //Busca no DAO
    }

    //LISTAR ENTREGAS POR ENTREGADOR
    public List<Entrega> listarEntregasPorEntregador(String cnhEntregador) {
        if (cnhEntregador == null || cnhEntregador.isEmpty()) { //Valida cnh
            throw new IllegalArgumentException("CNH do entregador não pode ser vazia");
        }
        return entregaDAO.buscarPorEntregador(cnhEntregador); //Busca entregas do entregador
    }

    //LISTAR TODAS AS ENTREGAS
    public List<Entrega> listarTodasEntregas() {
        return entregaDAO.listarTodas();
    }

    //ATUALIZA ENTREGADOR E SUA ENTREGA
    public boolean atualizarEntregadorEntrega(String codRastreio, String novaCnhEntregador) {
        if (codRastreio == null || codRastreio.isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser vazio");
        }
        if (novaCnhEntregador == null || novaCnhEntregador.isEmpty()) {
            throw new IllegalArgumentException("CNH do entregador não pode ser vazia");
        }

        try { //Busca entrega e o novo entregador
            Entrega entrega = entregaDAO.buscarPorCodRastreio(codRastreio);
            Entregador novoEntregador = entregadorDAO.buscarPorCnh(novaCnhEntregador);
            
            if (entrega == null || novoEntregador == null) {
                return false;
            }

            //Atualiza no BD para nova versão de entrega com novo entregador
            Entrega entregaAtualizada = new Entrega(codRastreio, novoEntregador);
            entregaDAO.atualizar(entregaAtualizada);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar entrega: " + e.getMessage());
            return false;
        }
    }

    //DETETAR ENTREGA USANDO CÓDIGO DE RASTREIO
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

    //MOSTRA DETALHES DA ENTREGA
    public void exibirDetalhesEntrega(String codRastreio) {
        Entrega entrega = buscarEntregaPorCodigo(codRastreio);
        if (entrega != null) {
            entrega.detalhesEntrega();
        } else {
            System.out.println("Entrega não encontrada!");
        }
    }
}