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
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        return clienteDAO.buscarPorEmailSenha(email, senha); // deve retornar Cliente ou null
    }

    // Cadastrar cliente com senha
    public boolean cadastrarCliente(String nome, String telefone, String email, String cpf, String senha) {
        if (nome == null || nome.isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio");
        if (telefone == null || telefone.isEmpty()) throw new IllegalArgumentException("Telefone não pode ser vazio");
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email não pode ser vazio");
        if (cpf == null || cpf.isEmpty()) throw new IllegalArgumentException("CPF não pode ser vazio");
        if (senha == null || senha.isEmpty()) throw new IllegalArgumentException("Senha não pode ser vazia");

        if (clienteDAO.emailExiste(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Cliente cliente = new Cliente(0, nome, telefone, email, cpf, senha);
        return clienteDAO.inserir(cliente);
    }

    // Atualizar cliente completo
    public boolean atualizarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        try {
            return clienteDAO.atualizar(cliente); // Atualizar todos os dados do cliente
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Alterar somente a senha
    public boolean alterarSenha(int idCliente, String novaSenha) {
        if (novaSenha == null || novaSenha.isEmpty()) {
            throw new IllegalArgumentException("Nova senha não pode ser vazia");
        }
        return clienteDAO.atualizarSenha(idCliente, novaSenha);
    }

    // Buscar cliente por CPF
    // public Cliente buscarClientePorCpf(String cpf) {
    //     if (cpf == null || cpf.isEmpty()) return null;
    //     return clienteDAO.buscarPorCpf(cpf); // se ClienteDAO tiver esse método
    // }

    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }

    public boolean deletarCliente(int id) {
        try {
            return clienteDAO.deletar(id);
        } catch (Exception e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }
}
