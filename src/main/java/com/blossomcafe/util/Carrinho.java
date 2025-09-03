package com.blossomcafe.util;

import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;

import javafx.scene.control.Label;

public class Carrinho {
    private static Pedido pedidoAtual = new Pedido(gerarIdPedido());
    private static Label labelContadorCarrinho;

    // Gera um ID aleatório para o pedido
    private static int gerarIdPedido() {
        return (int) (Math.random() * 1000) + 1;
    }

    // Retorna o pedido atual
    public static Pedido getPedidoAtual() {
        return pedidoAtual;
    }

    // Adiciona um produto ao pedido atual
    public static void adicionarProduto(Produto produto) {
        pedidoAtual.adicionarProduto(produto);
        atualizarContador();
    }

    // Remove um produto do pedido atual
    public static void removerProduto(Produto produto) {
        pedidoAtual.removerProduto(produto);
        atualizarContador();
    }

    // Limpa o pedido atual
    public static void limparPedido() {
        pedidoAtual.limparProdutos();
        atualizarContador();
        // Gera um novo ID para o próximo pedido
        pedidoAtual = new Pedido(gerarIdPedido());
    }

    // Define o label que exibirá o contador do carrinho
    public static void setLabelContador(Label label) {
        labelContadorCarrinho = label;
        atualizarContador();
    }

    // Atualiza o contador de itens no carrinho
    public static void atualizarContador() {
        if (labelContadorCarrinho != null) {
            int quantidade = pedidoAtual.getQuantidadeProdutos();
            labelContadorCarrinho.setText(quantidade > 0 ? String.valueOf(quantidade) : "");
            labelContadorCarrinho.setVisible(quantidade > 0);
        }
    }
}
