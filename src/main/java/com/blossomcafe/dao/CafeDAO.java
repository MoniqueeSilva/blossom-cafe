package com.blossomcafe.dao;

import com.blossomcafe.model.Cafe;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CafeDAO {
    public void inserir(Cafe cafe) {
        String sql = "INSERT INTO cafe (nome, preco, disponivel, tipo_cafe) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cafe.getNome());
            stmt.setDouble(2, cafe.getPreco());
            stmt.setBoolean(3, cafe.isDisponivel());
            stmt.setString(4, cafe.getTipoCafe());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cafe buscarPorId(int id) {
        String sql = "SELECT * FROM cafe WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cafe(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getBoolean("disponivel"),
                    rs.getString("tipo_cafe")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cafe> listarTodos() {
        List<Cafe> cafes = new ArrayList<>();
        String sql = "SELECT * FROM cafe";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cafe c = new Cafe(rs.getInt("id_produto"), rs.getString("nome"), rs.getDouble("preco"),rs.getBoolean("disponivel"), rs.getString("tipo_cafe"));
                cafes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cafes;
    }

    // UPDATE
    public void atualizar(Cafe cafe) {
        String sql = "UPDATE cafe SET nome = ?, preco = ?, disponivel = ?, tipo_cafe = ? WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cafe.getNome());
            stmt.setDouble(2, cafe.getPreco());
            stmt.setBoolean(3, cafe.isDisponivel());
            stmt.setString(4, cafe.getTipoCafe());
            stmt.setInt(5, cafe.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM cafe WHERE id_produto = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}