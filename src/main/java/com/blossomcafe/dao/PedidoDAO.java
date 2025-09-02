package com.blossomcafe.dao;

import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // ====================== CREATE ======================
    public boolean inserir(Pedido pedido, int idCliente, int idEndereco) {
        String sqlPedido = "INSERT INTO pedido (id_cliente, id_endereco, status) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            // Inserir pedido
            try (PreparedStatement stmt = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, idCliente);
                stmt.setInt(2, idEndereco);
                stmt.setString(3, pedido.getStatus());
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) throw new SQLException("Falha ao inserir pedido");

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) pedido.setId(rs.getInt(1));
            }

            // Inserir itens
            if (!adicionarItensPedido(pedido, conn)) throw new SQLException("Falha ao inserir itens do pedido");

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private boolean adicionarItensPedido(Pedido pedido, Connection conn) throws SQLException {
        String sql = "INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Produto produto : pedido.getProdutos()) {
                stmt.setInt(1, pedido.getId());
                stmt.setInt(2, produto.getId());
                stmt.setInt(3, produto.getQuantidade()); // usa quantidade real
                stmt.setDouble(4, produto.getPreco());
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        }
    }

    // ====================== READ ======================
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id"));
                pedido.setStatus(rs.getString("status"));
                carregarItensPedido(pedido);
                return pedido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void carregarItensPedido(Pedido pedido) {
        String sql = "SELECT p.*, ip.quantidade FROM produto p " +
                     "JOIN item_pedido ip ON p.id = ip.id_produto " +
                     "WHERE ip.id_pedido = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel")
                );
                produto.setQuantidade(rs.getInt("quantidade")); // pega quantidade correta
                pedido.adicionarProduto(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido(rs.getInt("id"));
                pedido.setStatus(rs.getString("status"));
                carregarItensPedido(pedido);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    // ====================== UPDATE ======================
    public boolean atualizar(Pedido pedido) {
        String sqlPedido = "UPDATE pedido SET status = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            // Atualizar status do pedido
            try (PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {
                stmt.setString(1, pedido.getStatus());
                stmt.setInt(2, pedido.getId());
                stmt.executeUpdate();
            }

            // Remover e adicionar itens atualizados
            removerItensPedido(pedido.getId(), conn);
            adicionarItensPedido(pedido, conn);

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void removerItensPedido(int idPedido, Connection conn) throws SQLException {
        String sql = "DELETE FROM item_pedido WHERE id_pedido = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
    }

    // ====================== DELETE ======================
    public boolean deletar(int id) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            removerItensPedido(id, conn);

            String sqlPedido = "DELETE FROM pedido WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
