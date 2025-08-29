package com.blossomcafe.controller;

import com.blossomcafe.dao.EntregadorDAO;
import com.blossomcafe.model.Entregador;
import java.util.List;

public class EntregadorController {
    private EntregadorDAO entregadorDAO;

    public EntregadorController() {
        this.entregadorDAO = new EntregadorDAO();
    }

    public boolean cadastrarEntregador(String nome, String veiculo, String placa, String cnh) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (veiculo == null || veiculo.isEmpty()) {
            throw new IllegalArgumentException("Veículo não pode ser vazio");
        }
        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazia");
        }
        if (cnh == null || cnh.isEmpty()) {
            throw new IllegalArgumentException("CNH não pode ser vazia");
        }

        try {
            // Verificar se já existe entregador com mesma CNH ou placa
            if (entregadorDAO.buscarPorCnh(cnh) != null) {
                throw new IllegalArgumentException("Já existe entregador com esta CNH");
            }
            if (entregadorDAO.buscarPorPlaca(placa) != null) {
                throw new IllegalArgumentException("Já existe entregador com esta placa");
            }

            Entregador entregador = new Entregador(nome, veiculo, placa, cnh);
            entregadorDAO.inserir(entregador);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar entregador: " + e.getMessage());
            return false;
        }
    }

    public Entregador buscarEntregadorPorCnh(String cnh) {
        if (cnh == null || cnh.isEmpty()) {
            throw new IllegalArgumentException("CNH não pode ser vazia");
        }
        return entregadorDAO.buscarPorCnh(cnh);
    }

    public Entregador buscarEntregadorPorPlaca(String placa) {
        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazia");
        }
        return entregadorDAO.buscarPorPlaca(placa);
    }

    public List<Entregador> listarTodosEntregadores() {
        return entregadorDAO.listarTodos();
    }

    public boolean atualizarEntregador(String cnhAtual, String novoNome, String novoVeiculo, String novaPlaca, String novaCnh) {
        if (cnhAtual == null || cnhAtual.isEmpty()) {
            throw new IllegalArgumentException("CNH atual não pode ser vazia");
        }

        try {
            Entregador entregadorAtual = entregadorDAO.buscarPorCnh(cnhAtual);
            if (entregadorAtual == null) {
                return false;
            }

            // Verificar se nova CNH já existe (se for diferente da atual)
            if (!novaCnh.equals(cnhAtual) && entregadorDAO.buscarPorCnh(novaCnh) != null) {
                throw new IllegalArgumentException("Já existe entregador com a nova CNH");
            }

            // Verificar se nova placa já existe (se for diferente da atual)
            if (!novaPlaca.equals(entregadorAtual.getPlaca()) && entregadorDAO.buscarPorPlaca(novaPlaca) != null) {
                throw new IllegalArgumentException("Já existe entregador com a nova placa");
            }

            Entregador entregadorAtualizado = new Entregador(
                novoNome, novoVeiculo, novaPlaca, novaCnh
            );

            entregadorDAO.atualizarPorCnh(cnhAtual, entregadorAtualizado);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar entregador: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarEntregadorPorCnh(String cnh) {
        if (cnh == null || cnh.isEmpty()) {
            throw new IllegalArgumentException("CNH não pode ser vazia");
        }

        try {
            entregadorDAO.deletarPorCnh(cnh);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar entregador: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarEntregadorPorPlaca(String placa) {
        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazia");
        }

        try {
            entregadorDAO.deletarPorPlaca(placa);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar entregador: " + e.getMessage());
            return false;
        }
    }
}