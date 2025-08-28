package com.blossomcafe.app;


public class Principal {
    public static void main(String[] args) {
        //Criando alguns produtos
        Cafe cafe1 = new Cafe(1, "Café Expresso", 7.50, true, "Expresso");

        Cafe cafe2 = new Cafe(2, "Café c/ leite", 9.00, true, "Café c/ leite");

        Flores flor1 = new Flores(3, "Rosas Vermelhas", 25.00, true, "Vermelha", "Rosa");

        Flores flor2 = new Flores(4, "Urtiga", 15.00, true, "Verde", "Urtiga");

        //Criando cliente
        Cliente cliente1 = new Cliente("Maria", "Rua trelele, 123","83988887777", "maria@gmail.com", "12345678910");

        Cliente cliente2 = new Cliente("Ana Catarina", "Rua da esquina", "40028922", "anacatarina@gmail.com", "09876543211");

        // Criando pedido
        Pedido pedido1 = new Pedido(101, "Blossom Café ", cliente1, "PREPARANDO");

        Pedido pedido2 = new Pedido(2, "Blossom Café", cliente2, "A CAMINHO");

        // Adicionando produtos ao pedido
        pedido1.adicionarProduto(cafe1);
        pedido1.adicionarProduto(flor1);

        pedido2.adicionarProduto(cafe2);
        pedido2.adicionarProduto(flor2);

        // Vinculando pedido ao cliente
        cliente1.adicionarPedido(pedido1);
        cliente2.adicionarPedido(pedido2);

        // Criando entregador
        Entregador entregador1 = new Entregador("Juquinha da pop100", "Moto", "ABC1234", "1234567890");
        Entregador entregador2 = new Entregador("Lucca Motoboy", "Moto", "DEF4321", "1234567811");

        // Criando entrega
        Entrega entrega1 = new Entrega("Rastreio-001", entregador1);
        pedido1.setEntrega(entrega1);
        Entrega entrega2 = new Entrega("Rastreio- 002", entregador2);
        pedido2.setEntrega(entrega2);

        // Exibindo informações do pedido
        System.out.println("\nINFORMAÇÕES DO PEDIDO:");
        System.out.println("ID: " + pedido1.getIdPedido());
        System.out.println("Cliente: " + cliente1.getNome());
        System.out.println("Data: " + pedido1.getDataFormatada());
        System.out.println("Local: " + pedido1.getLocal());
        System.out.println("Status: " + pedido1.getStatus());

        System.out.println("\nINFORMAÇÕES DO PEDIDO:");
        System.out.println("ID: " + pedido2.getIdPedido());
        System.out.println("Cliente: " + cliente2.getNome());
        System.out.println("Data: " + pedido2.getDataFormatada());
        System.out.println("Local: " + pedido2.getLocal());
        System.out.println("Status: " + pedido2.getStatus());

        System.out.println("\nProdutos:");
        for (Produto p : pedido1.getProdutos()) {
            System.out.println("- " + p.getDescricao() + " | R$ " + p.getPreco());
        }

        System.out.println("\nProdutos:");
        for (Produto p : pedido2.getProdutos()) {
            System.out.println("- " + p.getDescricao() + " | R$ " + p.getPreco());
        }

        System.out.println("\nQuantidade: " + pedido1.getQuantProduto());
        System.out.println("Valor Total: R$ " + pedido1.calcularValorTotal());

        System.out.println("\nQuantidade: " + pedido2.getQuantProduto());
        System.out.println("Valor Total: R$ " + pedido2.calcularValorTotal());

        // Atualizando status do pedido
        System.out.println("\nATUALIZANDO STATUS DO PEDIDO");
        pedido1.atualizarStatus();
        System.out.println("Novo status: " + pedido1.getStatus());

        pedido1.atualizarStatus();
        System.out.println("Novo status: " + pedido1.getStatus());

        pedido2.atualizarStatus();
        System.out.println("Novo status: " + pedido2.getStatus());

        // Exibindo detalhes da entrega
        System.out.println("\nINFORMAÇÕES DA ENTREGA:");
        pedido1.getEntrega().detalhesEntrega();

        System.out.println("\nINFORMAÇÕES DA ENTREGA:");
        pedido2.getEntrega().detalhesEntrega();

        // Histórico de pedidos do cliente
        System.out.println("\nHISTÓRICO DOS PEDIDOS DO CLIENTE");
        for (Pedido p : cliente1.getHistoricoPedidos()) {
            System.out.println("Pedido " + p.getIdPedido() + " - " + p.getStatus() + " - Total: R$ " + p.calcularValorTotal());
        }

        System.out.println("\nHISTÓRICO DOS PEDIDOS DO CLIENTE");
        for (Pedido p : cliente2.getHistoricoPedidos()) {
            System.out.println("Pedido " + p.getIdPedido() + " - " + p.getStatus() + " - Total: R$ " + p.calcularValorTotal());
        }
    }
}

