package com.blossomcafe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.blossomcafe.model.Cliente;
import com.blossomcafe.util.ConexaoBD;

public class ClienteDAO {
    private Connection conexao;

    public ClienteDAO() {
        try {
            this.conexao = ConexaoBD.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE - Inserir cliente com senha
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (telefone, nome, email, cpf, senha) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getTelefone());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getSenha());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cliente.setIdCliente(rs.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ - Buscar por email e senha (para login)
    public Cliente buscarPorEmailSenha(String email, String senha) {
        String sql = "SELECT * FROM cliente WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf"),
                    rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Buscar apenas por email
    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf"),
                    rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - Buscar por ID
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf"),
                    rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente buscarPorCpf(String cpf){
        String sql = "SELECT * FROM  cliente WHERE cpf = ?";
        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new Cliente(rs.getInt("id_cliente"), // consistente com o resto
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("cpf"),
                rs.getString("senha"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // READ - Listar todos
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf"),
                    rs.getString("senha")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    // UPDATE - Atualizar cliente (incluindo senha)
    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET telefone = ?, nome = ?, email = ?, cpf = ?, senha = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getTelefone());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getSenha());
            stmt.setInt(6, cliente.getIdCliente());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE - Apenas atualizar senha
    public boolean atualizarSenha(int idCliente, String novaSenha) {
        String sql = "UPDATE cliente SET senha = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, idCliente);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean deletar(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verificar se email já existe
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE email = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}