package com.blossomcafe.dao;

import com.blossomcafe.model.Cafe;
import com.blossomcafe.model.Flores;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, disponivel, categoria) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.setString(4, "GERAL"); // Categoria padrão
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY nome";
        
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

    public List<Produto> listarDisponiveis() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE disponivel = true ORDER BY nome";
        
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

    public List<Flores> listarFlores() {
        List<Flores> flores = new ArrayList<>();
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
                flores.add(flor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flores;
    }

    public List<Produto> listarCombos() {
        List<Produto> combos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE categoria = 'COMBO' AND disponivel = true";
        
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
                combos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return combos;
    }

    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, disponivel = ?, categoria = ? WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isDisponivel());
            stmt.setString(4, "GERAL"); // Categoria padrão
            stmt.setInt(5, produto.getId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> buscarPorNome(String nome) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE LOWER(nome) LIKE LOWER(?) AND disponivel = true";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

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
}