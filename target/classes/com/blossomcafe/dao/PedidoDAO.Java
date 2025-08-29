package com.blossomcafe.dao;

import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE
    public void inserir(Pedido pedido) {
        String sql = "INSERT INTO pedido (id_pedido) VALUES (?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            stmt.executeUpdate();
            
            // Inserir produtos do pedido
            inserirProdutosPedido(pedido);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inserirProdutosPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido_produto (id_pedido, id_produto) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (Produto produto : pedido.getProdutos()) {
                stmt.setInt(1, pedido.getId());
                stmt.setInt(2, produto.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ (um pedido)
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id_pedido"));
                // Carregar produtos do pedido
                carregarProdutosPedido(pedido);
                return pedido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void carregarProdutosPedido(Pedido pedido) {
        String sql = "SELECT p.* FROM produto p " +
                    "JOIN pedido_produto pp ON p.id_produto = pp.id_produto " +
                    "WHERE pp.id_pedido = ?";
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
            e.printStackTrace();
        }
    }

    // READ (todos os pedidos)
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id_pedido"));
                carregarProdutosPedido(pedido);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    // UPDATE
    public void atualizar(Pedido pedido) {
        // Primeiro remover todos os produtos do pedido
        removerProdutosPedido(pedido.getId());
        
        // Depois inserir os novos produtos
        inserirProdutosPedido(pedido);
    }

    private void removerProdutosPedido(int idPedido) {
        String sql = "DELETE FROM pedido_produto WHERE id_pedido = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletar(int id) {
        // Primeiro remover os produtos do pedido
        removerProdutosPedido(id);
        
        // Depois remover o pedido
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}