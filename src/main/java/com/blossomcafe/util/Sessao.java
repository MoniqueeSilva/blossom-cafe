package com.blossomcafe.util;

import com.blossomcafe.model.Cliente;

public class Sessao {
    private static Cliente clienteLogado;
    
    // Guarda o cliente que fez login
    public static void setClienteLogado(Cliente cliente) {
        clienteLogado = cliente;
    }
    
    // Pega o cliente guardado
    public static Cliente getClienteLogado() {
        return clienteLogado;
    }
    
    // Verifica se tem alguém logado
    public static boolean estaLogado() {
        return clienteLogado != null;
    }
    
    // Limpa o cliente (logout)
    public static void logout() {
        clienteLogado = null;
    }
}