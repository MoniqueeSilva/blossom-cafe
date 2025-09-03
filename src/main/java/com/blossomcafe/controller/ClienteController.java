package com.blossomcafe.controller;

import java.util.List;
import com.blossomcafe.dao.ClienteDAO; //Acesso aos DAOS
import com.blossomcafe.model.Cliente; //Modelo cliente

public class ClienteController {
    private ClienteDAO clienteDAO; //Instância do DAO

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    //LOGIN COM EMAIL E SENHA
    public Cliente fazerLogin(String email, String senha) {
        if (email == null || email.trim().isEmpty()) { //trim: espaços brancos. isEmpty: string vazios
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        
        if (!email.contains("@")) { //contains: procurando certo caracteres
            throw new IllegalArgumentException("Email deve ser válido");
        }
        
        return clienteDAO.buscarPorEmailSenha(email.trim(), senha.trim()); //Chama o DAO para autenticar
    }

    //CADASTRAR CLIENTE
    public boolean cadastrarCliente(String nome, String telefone, String email, String cpf, String senha) {
        //Validações de campos obrigatórios
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

        //VERIFICA SE EMAIL JÁ EXISTE
        if (clienteDAO.emailExiste(email.trim())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        //VERIFICA SE CPF JÁ EXISTE
        if (clienteDAO.cpfExiste(cpf.trim())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Cliente cliente = new Cliente(0, nome.trim(), telefone.trim(), email.trim(), cpf.trim(), senha.trim());
        return clienteDAO.inserir(cliente); //Insere no banco via DAO
    }

    //ATUALIZAR CLIENTE
    public boolean atualizarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        
        try {
            return clienteDAO.atualizar(cliente); //Chama o DAO para atualizar
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    //ALTERAR SENHA
    public boolean alterarSenha(int idCliente, String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new IllegalArgumentException("Nova senha não pode ser vazia");
        }
        
        if (novaSenha.length() < 4) {
            throw new IllegalArgumentException("Nova senha deve ter pelo menos 4 caracteres");
        }
        
        return clienteDAO.atualizarSenha(idCliente, novaSenha.trim()); //Chama o DAO
    }

    //BUSCAR CLIENTE POR ID
    public Cliente buscarClientePorId(int id) {
        return clienteDAO.buscarPorId(id); //Transfere execução para o DAO
    }

    //LISTAR TODOS OS CLIENTES
    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos(); //Transfere execução para o DAO
    }

    //DELETAR CLIENTE
    public boolean deletarCliente(int id) {
        try {
            return clienteDAO.deletar(id); //Chama cliente para deletar
        } catch (Exception e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }

    //VERIFICAR SE EMAIL EXISTE
    public boolean emailExiste(String email) {
        return clienteDAO.emailExiste(email); //Transfere para o DAO
    }

    //VERIFICA SE CPF EXISTE
    public boolean cpfExiste(String cpf) {
        return clienteDAO.cpfExiste(cpf); //Transfere para o DAO
    }

    //FECHAR CONEXÃO
    public void fecharConexao() {
        clienteDAO.fecharConexao(); //Fecha a conexão com o banco
    }
}