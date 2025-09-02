package com.blossomcafe.dao;

import com.blossomcafe.model.Flores;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FloresDAO {

    public void inserir(Flores flores) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            // 1. Insere produto
            String sqlProduto = "INSERT INTO produto (nome, preco, disponivel, categoria) VALUES (?, ?, ?, 'FLORES')";
            PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS);
            stmtProduto.setString(1, flores.getNome());
            stmtProduto.setDouble(2, flores.getPreco());
            stmtProduto.setBoolean(3, flores.isDisponivel());
            stmtProduto.executeUpdate();

            // 2. Pega ID gerado
            ResultSet rs = stmtProduto.getGeneratedKeys();
            int idProduto = 0;
            if (rs.next()) {
                idProduto = rs.getInt(1);
                flores.setId(idProduto);
            }

            // 3. Insere flores
            String sqlFlores = "INSERT INTO flores (id_produto, cor, tipo_flor, ocasiao, aroma) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtFlores = conn.prepareStatement(sqlFlores);
            stmtFlores.setInt(1, idProduto);
            stmtFlores.setString(2, flores.getCor());
            stmtFlores.setString(3, flores.getTipo());
            stmtFlores.setString(4, "DECORACAO"); // Valor padrão
            stmtFlores.setBoolean(5, false); // Valor padrão
            stmtFlores.executeUpdate();

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

    public Flores buscarPorId(int id) {
        String sql = "SELECT p.*, f.cor, f.tipo_flor " +
                    "FROM produto p " +
                    "JOIN flores f ON p.id_produto = f.id_produto " +
                    "WHERE p.id_produto = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Flores(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("cor"),
                    rs.getString("tipo_flor")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Flores> listarTodos() {
        List<Flores> floresList = new ArrayList<>();
        String sql = "SELECT p.*, f.cor, f.tipo_flor FROM produto p " +
                    "JOIN flores f ON p.id_produto = f.id_produto " +
                    "WHERE p.categoria = 'FLORES'";
        
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Flores flor = new Flores(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("cor"),
                    rs.getString("tipo_flor")
                );
                floresList.add(flor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return floresList;
    }

    public List<Flores> listarDisponiveis() {
        List<Flores> floresList = new ArrayList<>();
        String sql = "SELECT p.*, f.cor, f.tipo_flor FROM produto p " +
                    "JOIN flores f ON p.id_produto = f.id_produto " +
                    "WHERE p.categoria = 'FLORES' AND p.disponivel = true";
        
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Flores flor = new Flores(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("cor"),
                    rs.getString("tipo_flor")
                );
                floresList.add(flor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return floresList;
    }

    public void atualizar(Flores flores) {
        Connection conn = null;
        try {
            conn = ConexaoBD.getConnection();
            conn.setAutoCommit(false);

            // Atualiza produto
            String sqlProduto = "UPDATE produto SET nome = ?, preco = ?, disponivel = ? WHERE id_produto = ?";
            PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto);
            stmtProduto.setString(1, flores.getNome());
            stmtProduto.setDouble(2, flores.getPreco());
            stmtProduto.setBoolean(3, flores.isDisponivel());
            stmtProduto.setInt(4, flores.getId());
            stmtProduto.executeUpdate();

            // Atualiza flores
            String sqlFlores = "UPDATE flores SET cor = ?, tipo_flor = ? WHERE id_produto = ?";
            PreparedStatement stmtFlores = conn.prepareStatement(sqlFlores);
            stmtFlores.setString(1, flores.getCor());
            stmtFlores.setString(2, flores.getTipo());
            stmtFlores.setInt(3, flores.getId());
            stmtFlores.executeUpdate();

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

            // Deletar de flores primeiro
            String sqlFlores = "DELETE FROM flores WHERE id_produto = ?";
            PreparedStatement stmtFlores = conn.prepareStatement(sqlFlores);
            stmtFlores.setInt(1, id);
            stmtFlores.executeUpdate();

            // Deletar de produto
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