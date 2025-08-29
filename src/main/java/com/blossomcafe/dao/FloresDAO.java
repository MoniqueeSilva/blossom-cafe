package com.blossomcafe.dao;

import com.blossomcafe.model.Flores;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FloresDAO {

    // CREATE
    public void inserir(Flores flores) {
        String sql = "INSERT INTO flores (nome, preco, disponivel, cor, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flores.getNome());
            stmt.setDouble(2, flores.getPreco());
            stmt.setBoolean(3, flores.isDisponivel());
            stmt.setString(4, flores.getCor());
            stmt.setString(5, flores.getTipo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ (uma flor)
    public Flores buscarPorId(int id) {
        String sql = "SELECT * FROM flores WHERE id_produto = ?";
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
                    rs.getString("tipo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ (todas as flores)
    public List<Flores> listarTodos() {
        List<Flores> floresList = new ArrayList<>();
        String sql = "SELECT * FROM flores";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Flores f = new Flores(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("cor"),
                    rs.getString("tipo")
                );
                floresList.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return floresList;
    }

    // UPDATE
    public void atualizar(Flores flores) {
        String sql = "UPDATE flores SET nome = ?, preco = ?, disponivel = ?, cor = ?, tipo = ? WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flores.getNome());
            stmt.setDouble(2, flores.getPreco());
            stmt.setBoolean(3, flores.isDisponivel());
            stmt.setString(4, flores.getCor());
            stmt.setString(5, flores.getTipo());
            stmt.setInt(6, flores.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM flores WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}