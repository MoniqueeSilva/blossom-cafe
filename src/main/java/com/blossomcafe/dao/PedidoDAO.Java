package com.blossomcafe.dao;

import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE - Versão simplificada
    public boolean inserir(Pedido pedido, int idCliente) {
        String sqlPedido = "INSERT INTO pedido (id_cliente, status) VALUES (?, 'PENDENTE') RETURNING id_pedido";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {
            
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int idPedido = rs.getInt("id_pedido");
                pedido.setId(idPedido);
                
                // Inserir itens do pedido
                if (inserirItensPedido(pedido)) {
                    System.out.println("✅ Pedido " + idPedido + " criado com sucesso!");
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao criar pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean inserirItensPedido(Pedido pedido) {
        String sql = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade_item) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (Produto produto : pedido.getProdutos()) {
                stmt.setInt(1, pedido.getId());
                stmt.setInt(2, produto.getId());
                stmt.setInt(3, 1); // quantidade fixa 1
                stmt.addBatch();
            }
            
            stmt.executeBatch();
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir itens do pedido: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // READ - Buscar pedido por ID
    public Pedido buscarPorId(int id) {
        String sql = "SELECT p.* FROM pedido p WHERE p.id_pedido = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id_pedido"));
                carregarItensPedido(pedido);
                return pedido;
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar pedido " + id + ": " + e.getMessage());
        }
        return null;
    }

    private void carregarItensPedido(Pedido pedido) {
        String sql = "SELECT p.id_produto, p.nome, p.preco, p.disponivel " +
                    "FROM produto p " +
                    "JOIN item_pedido ip ON p.id_produto = ip.id_produto " +
                    "WHERE ip.id_pedido = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pedido.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel")
                );
                pedido.adicionarProduto(produto);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao carregar itens do pedido " + pedido.getId() + ": " + e.getMessage());
        }
    }

    // READ - Listar todos os pedidos
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido FROM pedido ORDER BY id_pedido";
        
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id_pedido"));
                carregarItensPedido(pedido);
                pedidos.add(pedido);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    // DELETE - Deletar pedido
    public boolean deletar(int id) {
        Connection conn = null;
        
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false); // Iniciar transação

            // 1. Primeiro deletar os itens do pedido
            String sqlItens = "DELETE FROM item_pedido WHERE id_pedido = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlItens)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // 2. Depois deletar o pedido
            String sqlPedido = "DELETE FROM pedido WHERE id_pedido = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows > 0) {
                    conn.commit(); // Confirmar transação
                    System.out.println("✅ Pedido " + id + " deletado com sucesso!");
                    return true;
                }
            }
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Reverter em caso de erro
            } catch (SQLException ex) {
                System.out.println("❌ Erro no rollback: " + ex.getMessage());
            }
            System.out.println("❌ Erro ao deletar pedido " + id + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("❌ Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return false;
    }

    // Método auxiliar para teste
    public static void testarConexao() {
        try (Connection conn = ConexaoBD.getConnection()) {
            System.out.println("✅ Conexão com banco OK!");
            
            // Testar se tabelas existem
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "pedido", new String[]{"TABLE"});
            if (tables.next()) {
                System.out.println("✅ Tabela 'pedido' existe");
            } else {
                System.out.println("❌ Tabela 'pedido' NÃO existe");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro de conexão: " + e.getMessage());
        }
    }
}