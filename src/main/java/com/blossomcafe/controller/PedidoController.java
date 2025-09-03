package com.blossomcafe.controller;

import java.util.List;

import com.blossomcafe.dao.PedidoDAO;
import com.blossomcafe.dao.ProdutoDAO;
import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;

public class PedidoController {
    private PedidoDAO pedidoDAO;
    private ProdutoDAO produtoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    //CRIAR PEDIDOS
    public boolean criarPedido(int idPedido) {
        try {
            Pedido pedido = new Pedido(idPedido);
            pedidoDAO.inserir(pedido, 1, idPedido); //Insere no banco, ID cliente temporário
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao criar pedido: " + e.getMessage());
            return false;
        }
    }

    //ADICIONAR PRODUTOS AO PEDIDO
    public boolean adicionarProdutoAoPedido(int idPedido, int idProduto) {
        try {
            Pedido pedido = pedidoDAO.buscarPorId(idPedido); //Busca pedido
            Produto produto = produtoDAO.buscarPorId(idProduto); //Busca produto

            if (pedido == null || produto == null) { //Verifica se existem
                System.out.println("Pedido ou produto não encontrado");
                return false;
            }

            if (!produto.isDisponivel()) { //Verifica se está disponivel
                System.out.println("Produto indisponível: " + produto.getNome());
                return false;
            }

            pedido.adicionarProduto(produto); //Adiciona produto ao pedido
            pedidoDAO.atualizar(pedido); //Atualiza pedido no banco
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao adicionar produto ao pedido: " + e.getMessage());
            return false;
        }
    }

    //BUSCAR PEDIDO POR ID
    public Pedido buscarPedidoPorId(int idPedido) {
        return pedidoDAO.buscarPorId(idPedido);
    }

    //LISTAR TODOS OS PEDIDOS
    public List<Pedido> listarTodosPedidos() {
        return pedidoDAO.listarTodos();
    }

    //CALCULAR O TOTAL DO PEDIDO
    public double calcularTotalPedido(int idPedido) {
        Pedido pedido = pedidoDAO.buscarPorId(idPedido); //Busca pedido
        if (pedido == null) { //Verifica se existe
            return 0.0;
        }

        double total = 0.0; //Inicializa total
        for (Produto produto : pedido.getProdutos()) { //Percorre os produtos
            total += produto.getPreco(); //Soma preços
        }
        return total; //Retorna o total
    }

    //REMOVER PRODUTO DO PEDIDO
    public boolean removerProdutoDoPedido(int idPedido, int idProduto) {
        try {
            Pedido pedido = pedidoDAO.buscarPorId(idPedido); //Buscar pedido
            if (pedido == null) { //Verifica se existe
                return false;
            }

            // Encontrar o produto para remover
            Produto produtoRemover = null;
            for (Produto produto : pedido.getProdutos()) { //Percorre produtos
                if (produto.getId() == idProduto) { //Encontra pelo id
                    produtoRemover = produto; //Armazena
                    break;
                }
            }

            if (produtoRemover == null) { //Verifica se encontrou
                return false;
            }

            //REMOVER PRODUTO DA LISTA
            pedido.getProdutos().remove(produtoRemover); //Remove produto
            pedidoDAO.atualizar(pedido); //Atualiza pedido no banco
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao remover produto do pedido: " + e.getMessage());
            return false;
        }
    }

    //LIMPAR PEDIDO
    public boolean limparPedido(int idPedido) { //Busca pedido
        try {
            Pedido pedido = pedidoDAO.buscarPorId(idPedido);
            if (pedido == null) { //Verifica se existe
                return false;
            }

            pedido.getProdutos().clear(); //Limpa a lista de produtos 
            pedidoDAO.atualizar(pedido); //Atualiza pedido no banco
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao limpar pedido: " + e.getMessage());
            return false;
        }
    }

    //DELETAR PEDIDO
    public boolean deletarPedido(int idPedido) {
        try {
            pedidoDAO.deletar(idPedido);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar pedido: " + e.getMessage());
            return false;
        }
    }

    //EXIBIR DETALHES DO PEDIDO
    public void exibirDetalhesPedido(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        if (pedido != null) {
            System.out.println("=== DETALHES DO PEDIDO ===");
            System.out.println("Pedido #" + idPedido);
            System.out.println("Produtos:");
            for (Produto produto : pedido.getProdutos()) {
                System.out.println("- " + produto.getNome() + ": R$ " + String.format("%.2f", produto.getPreco()));
            }
            System.out.println("Total: R$ " + String.format("%.2f", calcularTotalPedido(idPedido)));
        } else {
            System.out.println("Pedido não encontrado!");
        }
    }

    //QUANTIDADE DE PRODUTOS NO PEDIDO
    public int obterQuantidadeProdutosNoPedido(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido); //Busca pedido
        if (pedido == null) {
            return 0;
        }
        return pedido.getProdutos().size(); //Retorna qntd de produtos
    }
}