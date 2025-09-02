package com.blossomcafe.controller;

import java.util.List;

import com.blossomcafe.dao.PedidoDAO;
import com.blossomcafe.dao.ProdutoDAO;
import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;

public class PedidoController {
    public PedidoDAO pedidoDAO;
    private ProdutoDAO produtoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    public boolean criarPedido(int idPedido) {
        try {
            Pedido pedido = new Pedido(idPedido);
            pedidoDAO.inserir(pedido, 1, idPedido); // ID cliente temporário
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao criar pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean adicionarProdutoAoPedido(int idPedido, int idProduto) {
        try {
            Pedido pedido = pedidoDAO.buscarPorId(idPedido);
            Produto produto = produtoDAO.buscarPorId(idProduto);

            if (pedido == null || produto == null) {
                System.out.println("Pedido ou produto não encontrado");
                return false;
            }

            if (!produto.isDisponivel()) {
                System.out.println("Produto indisponível: " + produto.getNome());
                return false;
            }

            pedido.adicionarProduto(produto);
            pedidoDAO.atualizar(pedido);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao adicionar produto ao pedido: " + e.getMessage());
            return false;
        }
    }

    public Pedido buscarPedidoPorId(int idPedido) {
        return pedidoDAO.buscarPorId(idPedido);
    }

    public List<Pedido> listarTodosPedidos() {
        return pedidoDAO.listarTodos();
    }

    public double calcularTotalPedido(int idPedido) {
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido == null) {
            return 0.0;
        }

        double total = 0.0;
        for (Produto produto : pedido.getProdutos()) {
            total += produto.getPreco();
        }
        return total;
    }

    public boolean removerProdutoDoPedido(int idPedido, int idProduto) {
        try {
            Pedido pedido = pedidoDAO.buscarPorId(idPedido);
            if (pedido == null) {
                return false;
            }

            // Encontrar o produto a ser removido
            Produto produtoRemover = null;
            for (Produto produto : pedido.getProdutos()) {
                if (produto.getId() == idProduto) {
                    produtoRemover = produto;
                    break;
                }
            }

            if (produtoRemover == null) {
                return false;
            }

            // Remover o produto da lista
            pedido.getProdutos().remove(produtoRemover);
            pedidoDAO.atualizar(pedido);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao remover produto do pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean limparPedido(int idPedido) {
        try {
            Pedido pedido = pedidoDAO.buscarPorId(idPedido);
            if (pedido == null) {
                return false;
            }

            pedido.getProdutos().clear();
            pedidoDAO.atualizar(pedido);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao limpar pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPedido(int idPedido) {
        try {
            pedidoDAO.deletar(idPedido);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar pedido: " + e.getMessage());
            return false;
        }
    }

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
            System.out.println("========================");
        } else {
            System.out.println("Pedido não encontrado!");
        }
    }

    public int obterQuantidadeProdutosNoPedido(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        if (pedido == null) {
            return 0;
        }
        return pedido.getProdutos().size();
    }
}