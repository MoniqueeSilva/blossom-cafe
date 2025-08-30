package com.blossomcafe.app;

import com.blossomcafe.dao.*;
import com.blossomcafe.model.*;
import com.blossomcafe.util.ConexaoBD;
import java.sql.Connection;
import java.util.List;

public class TesteDAOs {
    
    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTES DOS DAOs ===");


        testarConexao();
        
        testarClienteDAO();
        testarProdutoDAO();
        testarEntregadorDAO();
        testarEntregaDAO();
        testarPedidoDAO();
        
        System.out.println("=== TESTES FINALIZADOS ===");
    }
    
    private static void testarConexao() {
        System.out.println("\n🔗 Testando conexão com o banco...");
        try {
            Connection conn = ConexaoBD.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexão estabelecida com sucesso!");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
        }
    }
    
    private static void testarClienteDAO() {
        System.out.println("\n👤 Testando ClienteDAO...");
        ClienteDAO dao = new ClienteDAO();
        
        try {
            // Criar cliente teste
            Cliente cliente = new Cliente(0, "João Teste", "(11) 99999-9999", "joao.teste@email.com", "99988877766");
            
            // Testar inserção
            // dao.inserir(cliente);
            // System.out.println("✅ Cliente inserido com sucesso");
            
            // Testar listagem
            List<Cliente> clientes = dao.listarTodos();
            System.out.println("✅ Listagem: " + clientes.size() + " clientes encontrados");
            
            // Testar busca por ID (se houver clientes)
            if (!clientes.isEmpty()) {
                Cliente clienteBusca = dao.buscarPorId(clientes.get(0).getIdCliente());
                System.out.println("✅ Busca por ID: " + (clienteBusca != null ? "OK" : "Falha"));
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro no ClienteDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testarProdutoDAO() {
        System.out.println("\n📦 Testando ProdutoDAO...");
        ProdutoDAO dao = new ProdutoDAO();
        
        try {
            // Criar produto teste
            Produto produto = new Produto(0, "Café Teste", 15.90, true);
            
            // Testar inserção
            // dao.inserir(produto);
            // System.out.println("✅ Produto inserido com sucesso");
            
            // Testar listagem
            List<Produto> produtos = dao.listarTodos();
            System.out.println("✅ Listagem: " + produtos.size() + " produtos encontrados");
            
            // Testar busca
            if (!produtos.isEmpty()) {
                Produto produtoBusca = dao.buscarPorId(produtos.get(0).getId());
                System.out.println("✅ Busca por ID: " + (produtoBusca != null ? "OK" : "Falha"));
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro no ProdutoDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testarEntregadorDAO() {
        System.out.println("\n Testando EntregadorDAO...");
        EntregadorDAO dao = new EntregadorDAO();
        
        try {
            // Criar entregador teste
            Entregador entregador = new Entregador("Entregador Teste", "Moto", "TEST123", "12345678901");
            
            // Testar inserção
            // dao.inserir(entregador);
            // System.out.println("✅ Entregador inserido com sucesso");
            
            // Testar listagem
            List<Entregador> entregadores = dao.listarTodos();
            System.out.println("✅ Listagem: " + entregadores.size() + " entregadores encontrados");
            
            // Testar busca por CNH
            Entregador entregadorBusca = dao.buscarPorCnh("12345678901");
            System.out.println("✅ Busca por CNH: " + (entregadorBusca != null ? "OK" : "Falha"));
            
        } catch (Exception e) {
            System.out.println("❌ Erro no EntregadorDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testarEntregaDAO() {
        System.out.println("\n📦 Testando EntregaDAO...");
        EntregaDAO dao = new EntregaDAO();
        EntregadorDAO entregadorDAO = new EntregadorDAO();
        
        try {
            // Primeiro garantir que existe um entregador
            Entregador entregador = entregadorDAO.buscarPorCnh("12345678901");
            if (entregador == null) {
                System.out.println("⚠️ Criando entregador para teste...");
                entregador = new Entregador("Entregador Entrega", "Moto", "ENT123", "12345678901");
                entregadorDAO.inserir(entregador);
            }
            
            // Criar entrega teste
            Entrega entrega = new Entrega("TEST2024", entregador);
            
            // Testar inserção
            // dao.inserir(entrega);
            // System.out.println("✅ Entrega inserida com sucesso");
            
            // Testar busca
            Entrega entregaBusca = dao.buscarPorCodRastreio("TEST2024");
            System.out.println("✅ Busca por código: " + (entregaBusca != null ? "OK" : "Falha"));
            
        } catch (Exception e) {
            System.out.println("❌ Erro no EntregaDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testarPedidoDAO() {
        System.out.println("\n🛒 Testando PedidoDAO...");
        // PedidoDAO dao = new PedidoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        try {
            // Garantir que existem cliente e produto
            Cliente cliente = clienteDAO.listarTodos().isEmpty() ? 
                new Cliente(0, "Cliente Pedido", "(11) 88888-8888", "cliente.pedido@email.com", "11122233344") : 
                clienteDAO.listarTodos().get(0);
                
            if (cliente.getIdCliente() == 0) {
                clienteDAO.inserir(cliente);
                cliente = clienteDAO.listarTodos().get(0); // Buscar com ID real
            }
            
            Produto produto = produtoDAO.listarTodos().isEmpty() ?
                new Produto(0, "Produto Pedido", 25.50, true) :
                produtoDAO.listarTodos().get(0);
                
            if (produto.getId() == 0) {
                produtoDAO.inserir(produto);
                produto = produtoDAO.listarTodos().get(0);
            }
            
            // Criar pedido
            Pedido pedido = new Pedido(0);
            pedido.adicionarProduto(produto);
            
            // Testar inserção (precisa adaptar seu PedidoDAO)
            System.out.println("⚠️ Teste de PedidoDAO precisa ser adaptado para o novo schema");
            
        } catch (Exception e) {
            System.out.println("❌ Erro no PedidoDAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}