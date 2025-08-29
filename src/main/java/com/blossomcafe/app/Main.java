// package com.blossomcafe.app;

// import com.blossomcafe.controller.ClienteController;
// import com.blossomcafe.controller.EntregaController;
// import com.blossomcafe.controller.EntregadorController;
// import com.blossomcafe.controller.PedidoController;
// import com.blossomcafe.controller.ProdutoController;
// import com.blossomcafe.model.Cliente;
// import com.blossomcafe.model.Entrega;
// import com.blossomcafe.model.Entregador;
// import com.blossomcafe.model.Pedido;
// import com.blossomcafe.model.Produto;

// import java.util.List;

// public class Main {
    
//     private static ClienteController clienteController = new ClienteController();
//     private static ProdutoController produtoController = new ProdutoController();
//     private static PedidoController pedidoController = new PedidoController();
//     private static EntregadorController entregadorController = new EntregadorController();
//     private static EntregaController entregaController = new EntregaController();

//     public static void main(String[] args) {
//         System.out.println("=== SISTEMA BLOSSOM CAFÉ ===");
//         System.out.println("Iniciando testes dos controllers...\n");

//         // Teste do ClienteController
//         testarClienteController();
        
//         // Teste do ProdutoController
//         testarProdutoController();
        
//         // Teste do PedidoController
//         testarPedidoController();
        
//         // Teste do EntregadorController
//         testarEntregadorController();
        
//         // Teste do EntregaController
//         testarEntregaController();

//         System.out.println("\n=== TODOS OS TESTES CONCLUÍDOS ===");
//     }

//     private static void testarClienteController() {
//         System.out.println("=== TESTANDO CLIENTECONTROLLER ===");
        
//         // Cadastrar clientes
//         boolean cliente1 = clienteController.cadastrarCliente("Maria Silva", "83988887777", "maria@gmail.com", "12345678910");
//         boolean cliente2 = clienteController.cadastrarCliente("João Santos", "83977776666", "joao@gmail.com", "09876543211");
        
//         if (cliente1 && cliente2) {
//             System.out.println("✓ Clientes cadastrados com sucesso");
//         }
        
//         // Listar todos os clientes
//         List<Cliente> clientes = clienteController.listarTodosClientes();
//         System.out.println("Total de clientes: " + clientes.size());
        
//         // Testar login
//         boolean login = clienteController.fazerLogin("maria@gmail.com", "senha123");
//         System.out.println("Login realizado: " + (login ? "✓" : "✗"));
        
//         System.out.println("-----------------------------------\n");
//     }

//     private static void testarProdutoController() {
//         System.out.println("=== TESTANDO PRODUTOCONTROLLER ===");
        
//         // Cadastrar produtos
//         boolean produto1 = produtoController.cadastrarProduto("Café Expresso", 7.50);
//         boolean produto2 = produtoController.cadastrarProduto("Cappuccino", 9.00);
//         boolean produto3 = produtoController.cadastrarProduto("Rosas Vermelhas", 25.00);
        
//         if (produto1 && produto2 && produto3) {
//             System.out.println("✓ Produtos cadastrados com sucesso");
//         }
        
//         // Listar todos os produtos
//         List<Produto> produtos = produtoController.listarTodosProdutos();
//         System.out.println("Total de produtos: " + produtos.size());
        
//         // Listar produtos disponíveis
//         List<Produto> disponiveis = produtoController.listarProdutosDisponiveis();
//         System.out.println("Produtos disponíveis: " + disponiveis.size());
        
//         // Buscar produto por nome
//         List<Produto> resultados = produtoController.buscarProdutosPorNome("café");
//         System.out.println("Produtos encontrados com 'café': " + resultados.size());
        
//         System.out.println("-----------------------------------\n");
//     }

//     private static void testarPedidoController() {
//         System.out.println("=== TESTANDO PEDIDOCONTROLLER ===");
        
//         // Criar pedidos
//         boolean pedido1 = pedidoController.criarPedido(1001);
//         boolean pedido2 = pedidoController.criarPedido(1002);
        
//         if (pedido1 && pedido2) {
//             System.out.println("✓ Pedidos criados com sucesso");
//         }
        
//         // Adicionar produtos aos pedidos
//         boolean addProduto1 = pedidoController.adicionarProdutoAoPedido(1001, 1);
//         boolean addProduto2 = pedidoController.adicionarProdutoAoPedido(1001, 2);
//         boolean addProduto3 = pedidoController.adicionarProdutoAoPedido(1002, 3);
        
//         if (addProduto1 && addProduto2 && addProduto3) {
//             System.out.println("✓ Produtos adicionados aos pedidos");
//         }
        
//         // Calcular total do pedido
//         double totalPedido1 = pedidoController.calcularTotalPedido(1001);
//         System.out.println("Total do Pedido 1001: R$ " + String.format("%.2f", totalPedido1));
        
//         // Exibir detalhes do pedido
//         System.out.println("Detalhes do Pedido 1001:");
//         pedidoController.exibirDetalhesPedido(1001);
        
//         System.out.println("-----------------------------------\n");
//     }

//     private static void testarEntregadorController() {
//         System.out.println("=== TESTANDO ENTREGADORCONTROLLER ===");
        
//         // Cadastrar entregadores
//         boolean entregador1 = entregadorController.cadastrarEntregador("João Motoboy", "Moto", "ABC1234", "CNH123456");
//         boolean entregador2 = entregadorController.cadastrarEntregador("Maria Entregadora", "Carro", "DEF5678", "CNH654321");
        
//         if (entregador1 && entregador2) {
//             System.out.println("✓ Entregadores cadastrados com sucesso");
//         }
        
//         // Listar todos os entregadores
//         List<Entregador> entregadores = entregadorController.listarTodosEntregadores();
//         System.out.println("Total de entregadores: " + entregadores.size());
        
//         // Buscar entregador por CNH
//         Entregador entregador = entregadorController.buscarEntregadorPorCnh("CNH123456");
//         if (entregador != null) {
//             System.out.println("Entregador encontrado: " + entregador.getNome());
//         }
        
//         System.out.println("-----------------------------------\n");
//     }

//     private static void testarEntregaController() {
//         System.out.println("=== TESTANDO ENTREGACONTROLLER ===");
        
//         // Criar entregas
//         boolean entrega1 = entregaController.criarEntrega("RAST001", "CNH123456");
//         boolean entrega2 = entregaController.criarEntrega("RAST002", "CNH654321");
        
//         if (entrega1 && entrega2) {
//             System.out.println("✓ Entregas criadas com sucesso");
//         }
        
//         // Listar todas as entregas
//         List<Entrega> entregas = entregaController.listarTodasEntregas();
//         System.out.println("Total de entregas: " + entregas.size());
        
//         // Exibir detalhes de uma entrega
//         System.out.println("Detalhes da Entrega RAST001:");
//         entregaController.exibirDetalhesEntrega("RAST001");
        
//         // Listar entregas por entregador
//         List<Entrega> entregasEntregador = entregaController.listarEntregasPorEntregador("CNH123456");
//         System.out.println("Entregas do entregador CNH123456: " + entregasEntregador.size());
        
//         System.out.println("-----------------------------------\n");
//     }

//     private static void testarIntegracaoCompleta() {
//         System.out.println("=== TESTE DE INTEGRAÇÃO COMPLETA ===");
        
//         // 1. Cadastrar cliente
//         clienteController.cadastrarCliente("Carlos Oliveira", "83999998888", "carlos@email.com", "11122233344");
        
//         // 2. Cadastrar produtos
//         produtoController.cadastrarProduto("Café Especial", 12.00);
//         produtoController.cadastrarProduto("Buquê de Flores", 35.00);
        
//         // 3. Criar pedido
//         pedidoController.criarPedido(2001);
        
//         // 4. Adicionar produtos ao pedido
//         pedidoController.adicionarProdutoAoPedido(2001, 4); // Café Especial
//         pedidoController.adicionarProdutoAoPedido(2001, 5); // Buquê de Flores
        
//         // 5. Cadastrar entregador
//         entregadorController.cadastrarEntregador("Pedro Entregas", "Moto", "GHI9012", "CNH999888");
        
//         // 6. Criar entrega
//         entregaController.criarEntrega("RAST2001", "CNH999888");
        
//         // 7. Exibir resumo
//         System.out.println("=== RESUMO DO TESTE ===");
//         System.out.println("Pedido 2001 criado com sucesso!");
//         System.out.println("Valor total: R$ " + String.format("%.2f", pedidoController.calcularTotalPedido(2001)));
//         System.out.println("Entrega associada: RAST2001");
        
//         System.out.println("Detalhes da entrega:");
//         entregaController.exibirDetalhesEntrega("RAST2001");
        
//         System.out.println("-----------------------------------\n");
//     }
// }