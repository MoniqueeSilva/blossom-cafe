package com.blossomcafe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.blossomcafe.model.Entregador;
import com.blossomcafe.util.ConexaoBD;

public class EntregadorDAO {

    // CREATE
    public void inserir(Entregador entregador) {
        String sql = "INSERT INTO entregador (nome, veiculo, placa, cnh) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getVeiculo());
            stmt.setString(3, entregador.getPlaca());
            stmt.setString(4, entregador.getCnh());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ 
    public Entregador buscarPorCnh(String cnh) {
        String sql = "SELECT * FROM entregador WHERE cnh = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnh);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montarEntregador(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Entregador buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM entregador WHERE placa = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montarEntregador(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Entregador> listarTodos() {
        List<Entregador> entregadores = new ArrayList<>();
        String sql = "SELECT * FROM entregador";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                entregadores.add(montarEntregador(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entregadores;
    }

    // UPDATE 
    public void atualizarPorCnh(String cnh, Entregador entregador) {
        String sql = "UPDATE entregador SET nome = ?, veiculo = ?, placa = ? WHERE cnh = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getVeiculo());
            stmt.setString(3, entregador.getPlaca());
            stmt.setString(4, cnh);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarPorPlaca(String placa, Entregador entregador) {
        String sql = "UPDATE entregador SET nome = ?, veiculo = ?, cnh = ? WHERE placa = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getVeiculo());
            stmt.setString(3, entregador.getCnh());
            stmt.setString(4, placa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletarPorCnh(String cnh) {
        String sql = "DELETE FROM entregador WHERE cnh = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnh);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarPorPlaca(String placa) {
        String sql = "DELETE FROM entregador WHERE placa = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //HELPER
    private Entregador montarEntregador(ResultSet rs) throws SQLException {
        return new Entregador(
            rs.getString("nome"),
            rs.getString("veiculo"),
            rs.getString("placa"),
            rs.getString("cnh")
        );
    }
}
