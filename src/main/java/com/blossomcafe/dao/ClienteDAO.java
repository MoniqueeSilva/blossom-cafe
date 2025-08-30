package com.blossomcafe.dao;

import com.blossomcafe.model.Cliente;
import com.blossomcafe.model.Endereco;
import com.blossomcafe.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // CREATE
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (telefone, nome, email, cpf, senha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getTelefone());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getSenha());
            stmt.executeUpdate();

            // pega id gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setIdCliente(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ler endereços
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("senha")
                );

                // carrega endereços do cliente
                c.setEnderecos(buscarEnderecosPorCliente(c.getIdCliente()));
                clientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    //busca endereços de um cliente
    private List<Endereco> buscarEnderecosPorCliente(int idCliente) {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM endereco WHERE idCliente = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Endereco e = new Endereco(rs.getInt("idEndereco"), rs.getString("rua"), rs.getInt("numero"), rs.getString("bairro"), rs.getString("cep"), rs.getString("cidade"), rs.getString("estado"), rs.getString("pontoRef"));
                enderecos.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enderecos;
    }

    // DELETE
    public void excluir(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TESTE
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> lista = dao.listar();
        for (Cliente c : lista) {
            System.out.println(c);
            System.out.println("Endereços:");
            for (Endereco e : c.getEnderecos()) {
                System.out.println("  -> " + e);
            }
        }
    }
}
