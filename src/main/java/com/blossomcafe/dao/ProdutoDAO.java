package com.blossomcafe.dao;

import com.blossomcafe.model.Cafe;
import com.blossomcafe.model.Flores;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // CREATE
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, disponivel) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ (um produto) - CORRIGIDO
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ (todos os produtos) - CORRIGIDO
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel")
                );
                produtos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public List<Cafe> listarCafes() {
        List<Cafe> cafes = new ArrayList<>();
        String sql = "SELECT p.*, c.tipo_cafe FROM produto p " +
                    "JOIN cafe c ON p.id_produto = c.id_produto " +
                    "WHERE p.disponivel = true";
        // ... implementar
        return cafes;
    }
    public List<Flores> listarFlores() {
        List<Flores> flores = new ArrayList<>();
        String sql = "SELECT p.*, f.cor, f.tipo_flor FROM produto p " +
                    "JOIN flores f ON p.id_produto = f.id_produto " +
                    "WHERE p.disponivel = true";
        // ... implementar
        return flores;
    }

    // UPDATE - CORRIGIDO
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, disponivel = ? WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.setInt(4, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE - CORRIGIDO
    public void deletar(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAO();
        dao.inserir(new Produto(0, "Urtiga", 12.0, true));
        System.out.println(dao.listarTodos());
    }
}