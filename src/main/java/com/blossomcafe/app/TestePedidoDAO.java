package com.blossomcafe.app;

import com.blossomcafe.dao.PedidoDAO;
import com.blossomcafe.dao.ClienteDAO;
import com.blossomcafe.dao.ProdutoDAO;
import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Cliente;
import com.blossomcafe.model.Produto;
import java.util.List;

public class TestePedidoDAO {
    
    public static void main(String[] args) {
        System.out.println("=== TESTE ESPECÍFICO DO PEDIDODAO ===");
        
        // Testar conexão primeiro
        PedidoDAO.testarConexao();
        
        // Testar operações básicas
        testarOperacoesBasicas();
    }
    
    private static void testarOperacoesBasicas() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        try {
            // 1. Verificar se temos cliente e produto
            List<Cliente> clientes = clienteDAO.listarTodos();
            List<Produto> produtos = produtoDAO.listarTodos();
            
            if (clientes.isEmpty() || produtos.isEmpty()) {
                System.out.println("⚠️ Crie clientes e produtos primeiro!");
                return;
            }
            
            Cliente cliente = clientes.get(0);
            Produto produto = produtos.get(0);
            
            System.out.println("👤 Cliente: " + cliente.getNome());
            System.out.println("📦 Produto: " + produto.getNome());
            
            // 2. Criar pedido
            Pedido pedido = new Pedido(0);
            pedido.adicionarProduto(produto);
            
            boolean sucesso = pedidoDAO.inserir(pedido, cliente.getIdCliente());
            System.out.println("📝 Inserção: " + (sucesso ? "✅" : "❌"));
            
            if (sucesso) {
                // 3. Buscar pedido
                Pedido pedidoBusca = pedidoDAO.buscarPorId(pedido.getId());
                System.out.println("🔍 Busca: " + (pedidoBusca != null ? "✅" : "❌"));
                
                // 4. Listar todos
                List<Pedido> todosPedidos = pedidoDAO.listarTodos();
                System.out.println("📋 Total de pedidos: " + todosPedidos.size());
                
                // 5. Deletar (opcional)
                // boolean deletado = pedidoDAO.deletar(pedido.getId());
                // System.out.println("🗑️ Deleção: " + (deletado ? "✅" : "❌"));
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro durante teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
}