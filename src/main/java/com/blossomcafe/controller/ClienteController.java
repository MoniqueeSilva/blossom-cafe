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

        return clienteDAO.buscarPorEmailSenha(email, senha);
    }

    // Cadastrar cliente com senha
    public boolean cadastrarCliente(String nome, String telefone, String email, String cpf, String senha) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        // Verificar se email já existe
        if (clienteDAO.emailExiste(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        try {
            Cliente cliente = new Cliente(0, nome, telefone, email, cpf, senha);
            return clienteDAO.inserir(cliente);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            return false;
        }
    }

    // Atualizar senha
    public boolean alterarSenha(int idCliente, String novaSenha) {
        if (novaSenha == null || novaSenha.isEmpty()) {
            throw new IllegalArgumentException("Nova senha não pode ser vazia");
        }

        return clienteDAO.atualizarSenha(idCliente, novaSenha);
    }

    // Buscar cliente por CPF
    public Cliente buscarClientePorCpf(String cpf) {
        List<Cliente> clientes = clienteDAO.listarTodos();
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }

    public boolean atualizarCliente(String cpf, String nome, String telefone, String email, String senha) {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            return false;
        }

        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setSenha(senha);

        return clienteDAO.atualizar(cliente);
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