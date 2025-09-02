package com.blossomcafe.controller;

import java.util.ArrayList;
import java.util.List;

import com.blossomcafe.dao.ProdutoDAO;
import com.blossomcafe.model.Produto;

public class ProdutoController {
    private ProdutoDAO produtoDAO;

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }

    public boolean cadastrarProduto(String nome, double preco) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        try {
            Produto produto = new Produto(0, nome, preco, true);
            produtoDAO.inserir(produto);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            return false;
        }
    }

    public Produto buscarProdutoPorId(int id) {
        return produtoDAO.buscarPorId(id);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoDAO.listarTodos();
    }

    // public List<Produto> listarProdutosDisponiveis() {
    //     return produtoDAO.listarTodos().stream()
    //         .filter(Produto::isDisponivel)
    //         .collect(Collectors.toList());
    // }

    public boolean atualizarProduto(int id, String nome, double preco, boolean disponivel) {
        try {
            Produto produto = produtoDAO.buscarPorId(id);
            if (produto == null) {
                return false;
            }

            produto.setNome(nome);
            produto.setPreco(preco);
            produto.setDisponivel(disponivel);
            
            produtoDAO.atualizar(produto);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean alternarDisponibilidadeProduto(int id) {
        try {
            Produto produto = produtoDAO.buscarPorId(id);
            if (produto == null) {
                return false;
            }

            produto.setDisponivel(!produto.isDisponivel());
            produtoDAO.atualizar(produto);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao alternar disponibilidade: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarProduto(int id) {
        try {
            produtoDAO.deletar(id);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listarProdutosDisponiveis() {
    // ⚠️ TEMPORÁRIO: Retorna dados mock se o banco estiver vazio
        List<Produto> produtosDoBanco = produtoDAO.listarTodos();
        
        if (produtosDoBanco.isEmpty()) {
            System.out.println("📦 Usando dados mock temporários...");
            List<Produto> mockProdutos = new ArrayList<>();
            
            // Cafés
            mockProdutos.add(new Produto(1, "Café Expresso", 8.90, true));
            mockProdutos.add(new Produto(2, "Cappuccino Special", 12.50, true));
            
            // Flores
            mockProdutos.add(new Produto(3, "Buquê de Rosas", 89.90, true));
            mockProdutos.add(new Produto(4, "Girassóis", 45.90, true));
            
            // Combos
            mockProdutos.add(new Produto(5, "Combo Romance", 120.00, true));
            mockProdutos.add(new Produto(6, "Kit Surpresa", 75.00, true));
            
            return mockProdutos;
        }
        
        return produtosDoBanco;
    }

    public List<Produto> buscarProdutosPorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        List<Produto> todosProdutos = produtoDAO.listarTodos();
        List<Produto> resultados = new ArrayList<>();
        
        for (Produto produto : todosProdutos) {
            if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultados.add(produto);
            }
        }
        return resultados;
    }
}