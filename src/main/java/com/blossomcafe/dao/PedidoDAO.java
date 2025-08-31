package com.blossomcafe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.ConexaoBD;

public class PedidoDAO {
    private Connection conexao;

    public PedidoDAO() {
        try {
            this.conexao = ConexaoBD.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE - Inserir pedido
    public boolean inserir(Pedido pedido, int idCliente) {
        String sql = "INSERT INTO pedido (id_cliente, id_endereco, status) VALUES (?, ?, 'PENDENTE')";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, 1); // ID endereço temporário
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idPedido = rs.getInt(1);
                    pedido.setId(idPedido);
                    return adicionarItensPedido(pedido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Inserir itens do pedido
    private boolean adicionarItensPedido(Pedido pedido) {
        String sql = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            for (Produto produto : pedido.getProdutos()) {
                stmt.setInt(1, pedido.getId());
                stmt.setInt(2, produto.getId());
                stmt.setInt(3, 1); // quantidade padrão
                stmt.setDouble(4, produto.getPreco());
                stmt.addBatch();
            }
            
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ - Buscar pedido por ID
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id_pedido"));
                carregarItensPedido(pedido);
                return pedido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Carregar itens do pedido
    private void carregarItensPedido(Pedido pedido) {
        String sql = "SELECT p.*, ip.quantidade FROM produto p " +
                    "JOIN item_pedido ip ON p.id_produto = ip.id_produto " +
                    "WHERE ip.id_pedido = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
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

    // READ - Listar todos os pedidos
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido FROM pedido";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id_pedido"));
                carregarItensPedido(pedido);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    // UPDATE - Atualizar pedido
    public void atualizar(Pedido pedido) {
        // Primeiro remover itens antigos
        removerItensPedido(pedido.getId());
        
        // Depois adicionar novos itens
        adicionarItensPedido(pedido);
    }

    // Remover itens do pedido
    private void removerItensPedido(int idPedido) {
        String sql = "DELETE FROM item_pedido WHERE id_pedido = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE - Deletar pedido
    public boolean deletar(int id) {
        Connection conn = null;
        
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            // 1. Deletar itens do pedido
            String sqlItens = "DELETE FROM item_pedido WHERE id_pedido = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlItens)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // 2. Deletar pedido
            String sqlPedido = "DELETE FROM pedido WHERE id_pedido = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows > 0) {
                    conn.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Método para testar conexão
    public static void testarConexao() {
        try (Connection conn = ConexaoBD.getConnection()) {
            System.out.println("✅ Conexão com banco OK!");
        } catch (SQLException e) {
            System.out.println("❌ Erro de conexão: " + e.getMessage());
        }
    }
}