package com.blossomcafe.dao;

import com.blossomcafe.model.Cafe;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CafeDAO {

    public void inserir(Cafe cafe) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false); // Iniciar transação

            // 1. Primeiro insere o produto
            String sqlProduto = "INSERT INTO produto (nome, preco, disponivel, categoria) VALUES (?, ?, ?, 'CAFE')";
            PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS);
            stmtProduto.setString(1, cafe.getNome());
            stmtProduto.setDouble(2, cafe.getPreco());
            stmtProduto.setBoolean(3, cafe.isDisponivel());
            stmtProduto.executeUpdate();

            // 2. Pega o ID gerado do produto
            ResultSet rs = stmtProduto.getGeneratedKeys();
            int idProduto = 0;
            if (rs.next()) {
                idProduto = rs.getInt(1);
                cafe.setId(idProduto);
            }

            // 3. Agora insere na tabela cafe
            String sqlCafe = "INSERT INTO cafe (id_produto, tipo_cafe, intensidade, descricao) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtCafe = conn.prepareStatement(sqlCafe);
            stmtCafe.setInt(1, idProduto);
            stmtCafe.setString(2, cafe.getTipoCafe());
            stmtCafe.setString(3, "MEDIO"); // Valor padrão
            stmtCafe.setString(4, cafe.getDescricao());
            stmtCafe.executeUpdate();

            conn.commit(); // Confirmar transação

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback em caso de erro
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cafe buscarPorId(int id) {
        String sql = "SELECT p.*, c.tipo_cafe, c.intensidade, c.descricao " +
                    "FROM produto p " +
                    "JOIN cafe c ON p.id_produto = c.id_produto " +
                    "WHERE p.id_produto = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cafe cafe = new Cafe(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("tipo_cafe")
                );
                return cafe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cafe> listarTodos() {
        List<Cafe> cafes = new ArrayList<>();
        String sql = "SELECT p.*, c.tipo_cafe FROM produto p " +
                    "JOIN cafe c ON p.id_produto = c.id_produto " +
                    "WHERE p.categoria = 'CAFE'";
        
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cafe cafe = new Cafe(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("tipo_cafe")
                );
                cafes.add(cafe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cafes;
    }

    public List<Cafe> listarDisponiveis() {
        List<Cafe> cafes = new ArrayList<>();
        String sql = "SELECT p.*, c.tipo_cafe FROM produto p " +
                    "JOIN cafe c ON p.id_produto = c.id_produto " +
                    "WHERE p.categoria = 'CAFE' AND p.disponivel = true";
        
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cafe cafe = new Cafe(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("tipo_cafe")
                );
                cafes.add(cafe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cafes;
    }

    public void atualizar(Cafe cafe) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            // 1. Atualiza produto
            String sqlProduto = "UPDATE produto SET nome = ?, preco = ?, disponivel = ? WHERE id_produto = ?";
            PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto);
            stmtProduto.setString(1, cafe.getNome());
            stmtProduto.setDouble(2, cafe.getPreco());
            stmtProduto.setBoolean(3, cafe.isDisponivel());
            stmtProduto.setInt(4, cafe.getId());
            stmtProduto.executeUpdate();

            // 2. Atualiza cafe
            String sqlCafe = "UPDATE cafe SET tipo_cafe = ? WHERE id_produto = ?";
            PreparedStatement stmtCafe = conn.prepareStatement(sqlCafe);
            stmtCafe.setString(1, cafe.getTipoCafe());
            stmtCafe.setInt(2, cafe.getId());
            stmtCafe.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deletar(int id) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            // 1. Deletar de cafe primeiro
            String sqlCafe = "DELETE FROM cafe WHERE id_produto = ?";
            PreparedStatement stmtCafe = conn.prepareStatement(sqlCafe);
            stmtCafe.setInt(1, id);
            stmtCafe.executeUpdate();

            // 2. Deletar de produto
            String sqlProduto = "DELETE FROM produto WHERE id_produto = ?";
            PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto);
            stmtProduto.setInt(1, id);
            int affectedRows = stmtProduto.executeUpdate();

            conn.commit();
            return affectedRows > 0;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}