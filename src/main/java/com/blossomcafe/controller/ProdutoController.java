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

    //CADASTRAR PRODUTO
    public boolean cadastrarProduto(String nome, double preco) {
        //Validações de campos obrigatórios
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (preco <= 0) { //Preço positivo
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        try {
            Produto produto = new Produto(0, nome, preco, true); //Cria produto (ID 0 = auto-increment)
            produtoDAO.inserir(produto); //Insere no banco
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            return false;
        }
    }

    //BUSCAR PRODUTO POR ID
    public Produto buscarProdutoPorId(int id) {
        return produtoDAO.buscarPorId(id);
    }

    //LISTAR TODOS OS PRODUTOS
    public List<Produto> listarTodosProdutos() {
        return produtoDAO.listarTodos();
    }

    //ATUALIZAR PRODUTOS
    public boolean atualizarProduto(int id, String nome, double preco, boolean disponivel) {
        try {
            Produto produto = produtoDAO.buscarPorId(id);//Busca produto
            if (produto == null) { //Verifica se existe
                return false;
            }

            //Atualiza
            produto.setNome(nome);
            produto.setPreco(preco);
            produto.setDisponivel(disponivel);
            
            produtoDAO.atualizar(produto); //Atualiza no banco
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    //DISPONIBILIDADE DO PRODUTO
    public boolean alternarDisponibilidadeProduto(int id) {
        try {
            Produto produto = produtoDAO.buscarPorId(id); //Busca produto
            if (produto == null) { //Verifica se existe
                return false;
            }

            produto.setDisponivel(!produto.isDisponivel()); //Inverte disponibilidade
            produtoDAO.atualizar(produto); //Atualiza no banco
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao alternar disponibilidade: " + e.getMessage());
            return false;
        }
    }

    //DELETAR PRODUTO
    public boolean deletarProduto(int id) {
        try {
            produtoDAO.deletar(id);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
            return false;
        }
    }

    //LISTAR PRODUTOS DISPONIVEIS
    public List<Produto> listarProdutosDisponiveis() {
        List<Produto> produtosDoBanco = produtoDAO.listarTodos(); //Busca produtos do banco
        
        if (produtosDoBanco.isEmpty()) { //Se banco for vazio
            System.out.println("Usando dados mock temporários...");
            List<Produto> mockProdutos = new ArrayList<>(); //Cria lista mock
            
            //Adicionando produtos mock
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


    //BUSCAR PRODUTOS POR NOME
    public List<Produto> buscarProdutosPorNome(String nome) {
        if (nome == null || nome.isEmpty()) { //Valida nome
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        List<Produto> todosProdutos = produtoDAO.listarTodos(); //Busca todos os produtos
        List<Produto> resultados = new ArrayList<>(); //Lista para os resultados
        
        for (Produto produto : todosProdutos) { //Percorre os produtos
            if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) { //Busca por substring 
                resultados.add(produto); //Adiciona a lista de resultados
            }
        }
        return resultados;
    }
}