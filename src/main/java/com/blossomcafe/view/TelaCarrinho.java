package com.blossomcafe.view;

import com.blossomcafe.controller.PedidoController;
import com.blossomcafe.controller.ProdutoController;
import com.blossomcafe.model.Cliente;
import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.Carrinho;
import com.blossomcafe.util.Sessao;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaCarrinho {
    private Stage stage;
    private PedidoController pedidoController;
    private ProdutoController produtoController;
    private Cliente cliente;
    private Pedido pedidoAtual;
    private VBox containerItens;
    private Label labelTotal;
    private Label labelContadorCarrinho;

    public TelaCarrinho(Stage stage, Label labelContadorCarrinho) {
        this.stage = stage;
        this.pedidoController = new PedidoController();
        this.produtoController = new ProdutoController();
        this.cliente = Sessao.getClienteLogado();
        this.labelContadorCarrinho = labelContadorCarrinho;
        this.pedidoAtual = Carrinho.getPedidoAtual();

    }

    private Pedido obterPedidoAtual() {
        // ajustar
        Pedido pedido = new Pedido(gerarIdPedido());
        return pedido;
    }

    private int gerarIdPedido() {
        return (int) (Math.random() * 1000) + 1;
    }

    public int getQuantidadeProdutos() {
        return pedidoAtual.getQuantidadeProdutos();
    }

    public void mostrar() {
        // Configuração do layout principal
        VBox layoutPrincipal = new VBox();
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setStyle("-fx-background-color: #EADED0;");
        layoutPrincipal.setPadding(new Insets(0, 0, 20, 0));

        // Navbar
        HBox navbar = criarNavbar();
        
        // Título
        Text titulo = new Text("🛒 Meu Carrinho");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setStyle("-fx-fill: #4C2B0B;");
        
        VBox.setMargin(titulo, new Insets(20, 0, 20, 0));

        // Container principal
        HBox containerPrincipal = new HBox(20);
        containerPrincipal.setAlignment(Pos.TOP_CENTER);
        containerPrincipal.setPadding(new Insets(0, 20, 20, 20));

        // Seção de itens do carrinho
        VBox secaoItens = criarSecaoItens();

        // Seção de resumo do pedido
        VBox secaoResumo = criarSecaoResumo();

        containerPrincipal.getChildren().addAll(secaoItens, secaoResumo);
        
        layoutPrincipal.getChildren().addAll(navbar, titulo, containerPrincipal);

        // Scene
        Scene scene = new Scene(layoutPrincipal, 1000, 700);
        
        // Carregar CSS se disponível
        try {
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("CSS não carregado: " + e.getMessage());
        }
        
        stage.setTitle("Blossom Café - Carrinho");
        stage.setScene(scene);
        stage.show();
        
        // Atualizar a exibição dos itens
        atualizarItensCarrinho();
    }

    private HBox criarNavbar() {
        HBox navbar = new HBox(15);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(15, 20, 15, 20));
        navbar.setStyle("-fx-background-color: #4C2B0B;");

        // Logo
        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(45);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            Text logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 16;");
            navbar.getChildren().add(logoTexto);
        }

        if (logoView != null) {
            navbar.getChildren().add(logoView);
        }

        // Espaço flexível
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Links de navegação
        HBox linksContainer = new HBox(20);
        linksContainer.setAlignment(Pos.CENTER_RIGHT);

        Button btnHome = criarBotaoNav("Home");
        Button btnCarrinho = criarBotaoNav("Carrinho");
        Button btnContato = criarBotaoNav("Contato");

        // Exibir nome do usuário se estiver logado
        if (cliente != null) {
            Label nomeUsuario = new Label("Olá, " + cliente.getNome() + "!");
            nomeUsuario.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            navbar.getChildren().add(nomeUsuario);
        }

        // Ícone de perfil
        Button btnPerfil = new Button("👤");
        btnPerfil.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16; " +
                          "-fx-border: none; -fx-cursor: hand; -fx-padding: 8;");
        btnPerfil.setOnMouseEntered(e -> btnPerfil.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 8; -fx-background-radius: 5;"));
        btnPerfil.setOnMouseExited(e -> btnPerfil.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 8;"));

        linksContainer.getChildren().addAll(btnHome, btnCarrinho, btnContato, btnPerfil);

        navbar.getChildren().addAll(spacer, linksContainer);

        // Eventos dos botões
        btnHome.setOnAction(e -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage, cliente);
            telaProdutos.mostrar();
        });
        
        btnCarrinho.setOnAction(e -> {
            // Já estamos na tela de carrinho
        });

        btnPerfil.setOnAction(e -> {
            if (cliente != null) {
                TelaPerfil telaPerfil = new TelaPerfil(stage, cliente);
                telaPerfil.mostrar();
            } else {
                mostrarAlerta("Erro", "Faça login primeiro!");
            }
        });

        return navbar;
    }

    private Button criarBotaoNav(String texto) {
        Button button = new Button(texto);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold; " +
                       "-fx-border: none; -fx-cursor: hand; -fx-padding: 10 15; -fx-font-size: 14;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; " +
                                                    "-fx-font-weight: bold; -fx-border: none; -fx-padding: 10 15; -fx-font-size: 14; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " +
                                                   "-fx-font-weight: bold; -fx-border: none; -fx-padding: 10 15; -fx-font-size: 14;"));
        return button;
    }

    private VBox criarSecaoItens() {
        VBox secao = new VBox(10);
        secao.setAlignment(Pos.TOP_CENTER);
        secao.setPadding(new Insets(20));
        secao.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-color: #D2B48C; -fx-border-radius: 15; -fx-border-width: 1;");
        secao.setPrefWidth(650);

        // Título da seção
        Label titulo = new Label("Itens no Carrinho");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");

        // Container para os itens (com scroll)
        containerItens = new VBox(15);
        containerItens.setAlignment(Pos.TOP_CENTER);
        containerItens.setPadding(new Insets(10));

        ScrollPane scrollItens = new ScrollPane(containerItens);
        scrollItens.setFitToWidth(true);
        scrollItens.setPrefHeight(400);
        scrollItens.setStyle("-fx-background: transparent; -fx-border: none;");

        // Mensagem quando o carrinho estiver vazio
        Label labelVazio = new Label("Seu carrinho está vazio");
        labelVazio.setStyle("-fx-text-fill: #8B4513; -fx-font-style: italic; -fx-font-size: 16;");
        labelVazio.setVisible(pedidoAtual.getProdutos().isEmpty());
        containerItens.getChildren().add(labelVazio);

        secao.getChildren().addAll(titulo, scrollItens);

        return secao;
    }

    private VBox criarSecaoResumo() {
        VBox secao = new VBox(20);
        secao.setAlignment(Pos.TOP_CENTER);
        secao.setPadding(new Insets(20));
        secao.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-color: #D2B48C; -fx-border-radius: 15; -fx-border-width: 1;");
        secao.setPrefWidth(300);

        // Título da seção
        Label titulo = new Label("Resumo do Pedido");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");

        // Itens do resumo
        VBox containerResumo = new VBox(10);
        containerResumo.setAlignment(Pos.TOP_CENTER);

        HBox linhaSubtotal = criarLinhaResumo("Subtotal", "R$ 0,00");
        HBox linhaTaxa = criarLinhaResumo("Taxa de entrega", "R$ 8,50");
        HBox linhaDesconto = criarLinhaResumo("Desconto", "- R$ 0,00");
        
        // Linha separadora
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));

        // Total
        HBox linhaTotal = new HBox();
        linhaTotal.setAlignment(Pos.CENTER_LEFT);
        linhaTotal.setPadding(new Insets(10, 0, 0, 0));
        
        Label labelTotalTexto = new Label("Total:");
        labelTotalTexto.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");
        
        labelTotal = new Label("R$ 0,00");
        labelTotal.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        linhaTotal.getChildren().addAll(labelTotalTexto, spacer, labelTotal);

        containerResumo.getChildren().addAll(linhaSubtotal, linhaTaxa, linhaDesconto, separator, linhaTotal);

        // Botão de finalizar pedido
        Button btnFinalizar = new Button("Finalizar Compra");
        btnFinalizar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; " +
                            "-fx-padding: 12 30; -fx-background-radius: 25; -fx-font-size: 16;");
        btnFinalizar.setOnMouseEntered(e -> 
            btnFinalizar.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; -fx-font-weight: bold; " +
                                "-fx-padding: 12 30; -fx-background-radius: 25; -fx-font-size: 16;"));
        btnFinalizar.setOnMouseExited(e -> 
            btnFinalizar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; " +
                                "-fx-padding: 12 30; -fx-background-radius: 25; -fx-font-size: 16;"));
        
        btnFinalizar.setOnAction(e -> finalizarPedido());

        // Botão de continuar comprando
        Button btnContinuar = new Button("Continuar Comprando");
        btnContinuar.setStyle("-fx-background-color: transparent; -fx-text-fill: #4C2B0B; -fx-border-color: #4C2B0B; " +
                            "-fx-border-width: 1; -fx-border-radius: 25; -fx-padding: 10 20; -fx-font-size: 14;");
        btnContinuar.setOnAction(e -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage, cliente);
            telaProdutos.mostrar();
        });

        secao.getChildren().addAll(titulo, containerResumo, btnFinalizar, btnContinuar);

        return secao;
    }

    private HBox criarLinhaResumo(String texto, String valor) {
        HBox linha = new HBox();
        linha.setAlignment(Pos.CENTER_LEFT);
        
        Label labelTexto = new Label(texto);
        labelTexto.setStyle("-fx-text-fill: #6B4C35; -fx-font-size: 14;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label labelValor = new Label(valor);
        labelValor.setStyle("-fx-text-fill: #6B4C35; -fx-font-size: 14;");
        
        linha.getChildren().addAll(labelTexto, spacer, labelValor);
        return linha;
    }

    private void atualizarItensCarrinho() {
        containerItens.getChildren().clear();
        
        if (pedidoAtual.getProdutos().isEmpty()) {
            Label labelVazio = new Label("Seu carrinho está vazio");
            labelVazio.setStyle("-fx-text-fill: #8B4513; -fx-font-style: italic; -fx-font-size: 16;");
            containerItens.getChildren().add(labelVazio);
        } else {
            for (Produto produto : pedidoAtual.getProdutos()) {
                HBox item = criarItemCarrinho(produto);
                containerItens.getChildren().add(item);
            }
        }
        
        atualizarResumo();
        atualizarContadorCarrinho();
    }

    private HBox criarItemCarrinho(Produto produto) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(15));
        item.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 10; -fx-border-color: #D2B48C; -fx-border-radius: 10; -fx-border-width: 1;");

        // Imagem do produto (placeholder)
        ImageView imgProduto = criarPlaceholder(produto);
        imgProduto.setFitWidth(60);
        imgProduto.setFitHeight(60);
        imgProduto.setPreserveRatio(true);

        // Detalhes do produto
        VBox detalhes = new VBox(5);
        detalhes.setAlignment(Pos.CENTER_LEFT);
        
        Label nome = new Label(produto.getNome());
        nome.setStyle("-fx-font-weight: bold; -fx-text-fill: #4C2B0B; -fx-font-size: 14;");
        
        Label preco = new Label(String.format("R$ %.2f", produto.getPreco()));
        preco.setStyle("-fx-text-fill: #6B4C35; -fx-font-size: 14;");
        
        detalhes.getChildren().addAll(nome, preco);

        // Controles de quantidade
        HBox controles = new HBox(10);
        controles.setAlignment(Pos.CENTER);
        
        Button btnMenos = new Button("-");
        btnMenos.setStyle("-fx-background-color: #CCB9A3; -fx-text-fill: #4C2B0B; -fx-font-weight: bold; " +
                         "-fx-min-width: 30; -fx-min-height: 30; -fx-background-radius: 15;");
        btnMenos.setOnAction(e -> diminuirQuantidade(produto));
        
        Label quantidade = new Label("1"); // Em uma implementação real, você teria controle de quantidades
        quantidade.setStyle("-fx-font-weight: bold; -fx-text-fill: #4C2B0B; -fx-min-width: 30; -fx-alignment: center;");
        
        Button btnMais = new Button("+");
        btnMais.setStyle("-fx-background-color: #CCB9A3; -fx-text-fill: #4C2B0B; -fx-font-weight: bold; " +
                       "-fx-min-width: 30; -fx-min-height: 30; -fx-background-radius: 15;");
        btnMais.setOnAction(e -> aumentarQuantidade(produto));
        
        controles.getChildren().addAll(btnMenos, quantidade, btnMais);

        // Botão de remover
        Button btnRemover = new Button("✕");
        btnRemover.setStyle("-fx-background-color: transparent; -fx-text-fill: #8B0000; -fx-font-size: 16; " +
                          "-fx-border: none; -fx-cursor: hand;");
        btnRemover.setOnAction(e -> removerProduto(produto));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        item.getChildren().addAll(imgProduto, detalhes, spacer, controles, btnRemover);

        return item;
    }

    private ImageView criarPlaceholder(Produto produto) {
        // Implementação similar à da TelaProdutos
        StackPane placeholder = new StackPane();
        String corFundo;
        String emoji;
        
        String nome = produto.getNome().toLowerCase();
        
        if (nome.contains("café") || nome.contains("cafe") || nome.contains("expresso") ||
            nome.contains("cappuccino") || nome.contains("latte") || nome.contains("coffee") ||
            nome.contains("chá") || nome.contains("cha") || nome.contains("chocolate") ||
            nome.contains("machiatto") || nome.contains("nesquik")) {
            corFundo = "#8B4513";
            emoji = "☕";
        } else if (nome.contains("astromélia") || nome.contains("astromelia") || nome.contains("flores") ||
                  nome.contains("girassois") || nome.contains("lírio") || nome.contains("lirio") ||
                  nome.contains("margaridas") || nome.contains("orquidea") || nome.contains("rosas") ||
                  nome.contains("tulipas")) {
            corFundo = "#FF69B4";
            emoji = "🌺";
        } else {
            corFundo = "#4C2B0B";
            emoji = "🎁";
        }
        
        placeholder.setStyle("-fx-background-color: " + corFundo + "; -fx-min-width: 60; -fx-min-height: 60; -fx-background-radius: 10;");
        
        Text textoEmoji = new Text(emoji);
        textoEmoji.setStyle("-fx-font-size: 20; -fx-fill: white;");
        
        placeholder.getChildren().add(textoEmoji);
        
        ImageView imageView = new ImageView();
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        
        return imageView;
    }

    private void aumentarQuantidade(Produto produto) {
        // Em uma implementação real, você aumentaria a quantidade do produto
        // Por enquanto, vamos apenas adicionar outro item do mesmo produto
        pedidoAtual.adicionarProduto(produto);
        atualizarItensCarrinho();
    }

    private void diminuirQuantidade(Produto produto) {
        // Em uma implementação real, você diminuiria a quantidade do produto
        // Por enquanto, vamos apenas remover um item do produto
        pedidoAtual.removerProduto(produto);
        atualizarItensCarrinho();
    }

    private void removerProduto(Produto produto) {
        pedidoAtual.removerProduto(produto);
        atualizarItensCarrinho();
    }

    private void atualizarResumo() {
        double subtotal = pedidoAtual.calcularValorTotal();
        double taxa = 8.50;
        double desconto = 0.0;
        double total = subtotal + taxa - desconto;
        
        labelTotal.setText(String.format("R$ %.2f", total));
    }

    public void atualizarContadorCarrinho() {
        if (labelContadorCarrinho != null) {
            int quantidade = pedidoAtual.getQuantidadeProdutos();
            labelContadorCarrinho.setText(quantidade > 0 ? String.valueOf(quantidade) : "");
            labelContadorCarrinho.setVisible(quantidade > 0);
        }
    }

    public void finalizarPedido() {
        if (pedidoAtual.getProdutos().isEmpty()) {
            mostrarAlerta("Carrinho Vazio", "Adicione produtos ao carrinho antes de finalizar o pedido.");
            return;
        }
        
        if (cliente == null) {
            mostrarAlerta("Login Necessário", "Faça login para finalizar o pedido.");
            TelaLogin telaLogin = new TelaLogin(stage);
            telaLogin.mostrar();
            return;
        }
        
        // Em uma implementação real, você salvaria o pedido no banco de dados
        boolean sucesso = pedidoController.criarPedido(pedidoAtual.getId());
        
        if (sucesso) {
            mostrarAlerta("Sucesso", "Pedido realizado com sucesso! Número do pedido: #" + pedidoAtual.getId());
            
            // Limpar carrinho após finalização
            pedidoAtual.limparProdutos();
            atualizarItensCarrinho();
            
            // Redirecionar para acompanhamento de pedido
            TelaAcompanharEntrega telaAcompanhamento = new TelaAcompanharEntrega(stage, pedidoAtual);
            telaAcompanhamento.mostrar();
        } else {
            mostrarAlerta("Erro", "Não foi possível finalizar o pedido. Tente novamente.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Método para adicionar produto ao carrinho (chamado de outras telas)
    public void adicionarProdutoAoCarrinho(Produto produto) {
        pedidoAtual.adicionarProduto(produto);
        atualizarItensCarrinho();
    }
}