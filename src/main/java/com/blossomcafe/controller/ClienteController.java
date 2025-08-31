package com.blossomcafe.controller;

import com.blossomcafe.dao.ClienteDAO;
import com.blossomcafe.model.Cliente;
import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public Cliente fazerLogin(String email, String senha) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }

        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        return clienteDAO.buscarPorEmailSenha(email, senha);
    }

    public boolean cadastrarCliente(String nome, String telefone, String email, String cpf) {
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

        try {
            Cliente cliente = new Cliente(0, nome, telefone, email, cpf);
            clienteDAO.inserir(cliente);
            System.out.println("Cliente cadastrado com sucesso: " + nome);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            return false;
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        // Você precisará implementar este método no ClienteDAO
        System.out.println("Método buscarClientePorCpf não implementado ainda");
        return null;
    }

    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }

    public boolean atualizarCliente(String cpf, String nome, String telefone, String email) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        // Você precisará implementar este método no ClienteDAO
        System.out.println("Método atualizarCliente não implementado ainda");
        return false;
    }

    public boolean deletarCliente(int id) {
        try {
            clienteDAO.deletar(id);
            System.out.println("Cliente deletado com sucesso");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }
}