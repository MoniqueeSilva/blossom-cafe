package com.blossomcafe.dao;

import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE - CORRIGIDO para o novo schema
    public void inserir(Pedido pedido, int idCliente) {
        String sql = "INSERT INTO pedido (id_cliente, status) VALUES (?, 'PENDENTE') RETURNING id_pedido";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int idPedido = rs.getInt("id_pedido");
                pedido.setId(idPedido);
                inserirItensPedido(pedido);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void inserirItensPedido(Pedido pedido) {
        String sql = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade_item) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (Produto produto : pedido.getProdutos()) {
                stmt.setInt(1, pedido.getId());
                stmt.setInt(2, produto.getId());
                stmt.setInt(3, 1); // quantidade fixa 1 por enquanto
                stmt.addBatch();
            }
            stmt.executeBatch();
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir itens do pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // READ - CORRIGIDO
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
            System.out.println("❌ Erro ao buscar pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void carregarItensPedido(Pedido pedido) {
        String sql = "SELECT p.* FROM produto p " +
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
            System.out.println("❌ Erro ao carregar itens do pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // READ todos - CORRIGIDO
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido ORDER BY id_pedido";
        
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
            e.printStackTrace();
        }
        return pedidos;
    }

    // UPDATE - CORRIGIDO
    public void atualizar(Pedido pedido) {
        // Primeiro remove todos os itens antigos
        removerItensPedido(pedido.getId());
        
        // Depois insere os novos itens
        inserirItensPedido(pedido);
        
        // Atualiza status se necessário
        String sql = "UPDATE pedido SET status = ? WHERE id_pedido = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "ATUALIZADO"); // Você pode ajustar o status
            stmt.setInt(2, pedido.getId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void removerItensPedido(int idPedido) {
        String sql = "DELETE FROM item_pedido WHERE id_pedido = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao remover itens do pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // DELETE - CORRIGIDO
    public void deletar(int id) {
        // Primeiro remove os itens
        removerItensPedido(id);
        
        // Depois remove o pedido
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao deletar pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // NOVO: Buscar pedidos por cliente
    public List<Pedido> buscarPorCliente(int idCliente) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE id_cliente = ? ORDER BY id_pedido";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id_pedido"));
                carregarItensPedido(pedido);
                pedidos.add(pedido);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar pedidos do cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return pedidos;
    }
}