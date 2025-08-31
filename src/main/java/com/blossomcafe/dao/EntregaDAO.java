package com.blossomcafe.dao;

import com.blossomcafe.model.Entrega;
import com.blossomcafe.model.Entregador;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    // CREATE
    public void inserir(Entrega entrega) {
        String sql = "INSERT INTO entrega (cod_rastreio, cnh_entregador) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entrega.getCodRastreio());
            stmt.setString(2, entrega.getEntregador().getCnh());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ (uma entrega)
    public Entrega buscarPorCodRastreio(String codRastreio) {
        String sql = "SELECT e.*, ent.nome, ent.veiculo, ent.placa, ent.cnh " +
                    "FROM entrega e " +
                    "JOIN entregador ent ON e.cnh_entregador = ent.cnh " +
                    "WHERE e.cod_rastreio = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codRastreio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Entregador entregador = new Entregador(
                    rs.getString("nome"),
                    rs.getString("veiculo"),
                    rs.getString("placa"),
                    rs.getString("cnh")
                );
                return new Entrega(
                    rs.getString("cod_rastreio"),
                    entregador
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ (entregas por entregador)
    public List<Entrega> buscarPorEntregador(String cnhEntregador) {
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT e.*, ent.* FROM entrega e " +
                    "JOIN entregador ent ON e.cnh_entregador = ent.cnh " +
                    "WHERE e.cnh_entregador = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnhEntregador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Entregador entregador = new Entregador(
                    rs.getString("ent.nome"),
                    rs.getString("ent.veiculo"),
                    rs.getString("ent.placa"),
                    rs.getString("ent.cnh")
                );
                Entrega entrega = new Entrega(
                    rs.getString("e.cod_rastreio"),
                    entregador
                );
                entregas.add(entrega);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    // READ (todas as entregas)
    public List<Entrega> listarTodas() {
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT e.*, ent.* FROM entrega e " +
                    "JOIN entregador ent ON e.cnh_entregador = ent.cnh";
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Entregador entregador = new Entregador(
                    rs.getString("ent.nome"),
                    rs.getString("ent.veiculo"),
                    rs.getString("ent.placa"),
                    rs.getString("ent.cnh")
                );
                Entrega entrega = new Entrega(
                    rs.getString("e.cod_rastreio"),
                    entregador
                );
                entregas.add(entrega);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    // UPDATE
    public void atualizar(Entrega entrega) {
        String sql = "UPDATE entrega SET cnh_entregador = ? WHERE cod_rastreio = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entrega.getEntregador().getCnh());
            stmt.setString(2, entrega.getCodRastreio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletar(String codRastreio) {
        String sql = "DELETE FROM entrega WHERE cod_rastreio = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codRastreio);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}