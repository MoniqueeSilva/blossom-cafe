package com.blossomcafe.controller;

import java.util.List;

import com.blossomcafe.dao.ClienteDAO;
import com.blossomcafe.model.Cliente;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    // Login com email e senha
    public Cliente fazerLogin(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email deve ser válido");
        }
        
        return clienteDAO.buscarPorEmailSenha(email.trim(), senha.trim());
    }

    // Cadastrar cliente
    public boolean cadastrarCliente(String nome, String telefone, String email, String cpf, String senha) {
        // Validações
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email deve ser válido");
        }
        
        if (senha.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 4 caracteres");
        }

        // Verificar se email já existe
        if (clienteDAO.emailExiste(email.trim())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Verificar se CPF já existe
        if (clienteDAO.cpfExiste(cpf.trim())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Cliente cliente = new Cliente(0, nome.trim(), telefone.trim(), email.trim(), cpf.trim(), senha.trim());
        return clienteDAO.inserir(cliente);
    }

    // Atualizar cliente
    public boolean atualizarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        
        try {
            return clienteDAO.atualizar(cliente);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Alterar senha
    public boolean alterarSenha(int idCliente, String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new IllegalArgumentException("Nova senha não pode ser vazia");
        }
        
        if (novaSenha.length() < 4) {
            throw new IllegalArgumentException("Nova senha deve ter pelo menos 4 caracteres");
        }
        
        return clienteDAO.atualizarSenha(idCliente, novaSenha.trim());
    }

    // Buscar cliente por ID
    public Cliente buscarClientePorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

    // Listar todos os clientes
    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }

    // Deletar cliente
    public boolean deletarCliente(int id) {
        try {
            return clienteDAO.deletar(id);
        } catch (Exception e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }

    // Verificar se email existe
    public boolean emailExiste(String email) {
        return clienteDAO.emailExiste(email);
    }

    // Verificar se CPF existe
    public boolean cpfExiste(String cpf) {
        return clienteDAO.cpfExiste(cpf);
    }

    // Fechar conexão
    public void fecharConexao() {
        clienteDAO.fecharConexao();
    }
}