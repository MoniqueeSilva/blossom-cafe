package com.blossomcafe.view;

import java.util.ArrayList;
import java.util.List;

import com.blossomcafe.controller.ProdutoController;
import com.blossomcafe.model.Produto;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

public class TelaProdutos {
    private Stage stage;
    private ProdutoController produtoController;

    public TelaProdutos(Stage stage) {
        this.stage = stage;
        this.produtoController = new ProdutoController();
    }

    public void mostrar() {
        // ==================== NAVBAR ====================
        HBox navbar = criarNavbar();
        
        // ==================== TÍTULO PRINCIPAL ====================
        Text tituloPrincipal = new Text("Cardápio Blossom Cafe");
        tituloPrincipal.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        tituloPrincipal.setStyle("-fx-fill: #4C2B0B;");
        
        // ==================== CATEGORIAS ====================
        VBox categoriasContainer = new VBox(30);
        categoriasContainer.setAlignment(Pos.TOP_CENTER);
        categoriasContainer.setPadding(new Insets(20));
        categoriasContainer.setStyle("-fx-background-color: #EADED0;");
        
        // Café
        VBox secaoCafes = criarSecaoCategoria("☕ Nossos Cafés", "cafe");
        // Flores
        VBox secaoFlores = criarSecaoCategoria("🌺 Nossas Flores", "flores");
        // Combos (produtos com preço maior)
        VBox secaoCombos = criarSecaoCategoria("🎁 Combos Especiais", "combo");
        
        categoriasContainer.getChildren().addAll(secaoCafes, secaoFlores, secaoCombos);
        
        // ScrollPane para todas as categorias
        ScrollPane scrollPane = new ScrollPane(categoriasContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-border: none;");
        scrollPane.setPadding(new Insets(5));
        
        // No método mostrar() da TelaProdutos, adicione este teste:
    System.out.println("=== TESTE PRODUTOS DO BANCO ===");
    List<Produto> todosProdutos = produtoController.listarProdutosDisponiveis();
    System.out.println("Total de produtos encontrados: " + todosProdutos.size());
    for (Produto p : todosProdutos) {
        System.out.println("- " + p.getNome() + " (R$ " + p.getPreco() + ")");
    }
        // ==================== LAYOUT PRINCIPAL ====================
        VBox layoutPrincipal = new VBox(15);
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setPadding(new Insets(10));
        layoutPrincipal.setStyle("-fx-background-color: #EADED0;");
        
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        layoutPrincipal.getChildren().addAll(navbar, tituloPrincipal, scrollPane);
        
        // ==================== SCENE ====================
        Scene scene = new Scene(layoutPrincipal, 1000, 700);
        stage.setTitle("Blossom Café - Cardápio");
        stage.setScene(scene);
        stage.show();
    }

    private VBox criarSecaoCategoria(String tituloCategoria, String tipo) {
        VBox secao = new VBox(15);
        secao.setAlignment(Pos.TOP_CENTER);
        secao.setPadding(new Insets(20));
        secao.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 15;");
        
        // Título da categoria
        Text titulo = new Text(tituloCategoria);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-fill: #4C2B0B;");
        
        // Grid de produtos
        HBox gridProdutos = new HBox(20);
        gridProdutos.setAlignment(Pos.TOP_CENTER);
        gridProdutos.setPadding(new Insets(10));
        
        // Obter produtos filtrados por categoria
        List<Produto> produtosFiltrados = filtrarProdutosPorCategoria(tipo);
        
        for (Produto produto : produtosFiltrados) {
            VBox card = criarCardProduto(produto);
            gridProdutos.getChildren().add(card);
        }
        
        // Se não houver produtos na categoria
        if (produtosFiltrados.isEmpty()) {
            Label labelVazio = new Label("Em breve...");
            labelVazio.setStyle("-fx-text-fill: #8B4513; -fx-font-style: italic;");
            gridProdutos.getChildren().add(labelVazio);
        }
        
        secao.getChildren().addAll(titulo, gridProdutos);
        return secao;
    }

private List<Produto> filtrarProdutosPorCategoria(String tipo) {
    List<Produto> todosProdutos = produtoController.listarProdutosDisponiveis();
    List<Produto> filtrados = new ArrayList<>();
    
    System.out.println("Filtrando categoria: " + tipo); // DEBUG
    System.out.println("Total de produtos: " + todosProdutos.size()); // DEBUG
    
    for (Produto produto : todosProdutos) {
        String nome = produto.getNome().toLowerCase();
        System.out.println("Produto: " + nome + " - R$ " + produto.getPreco()); // DEBUG
        
        switch (tipo) {
            case "cafe":
                if (nome.contains("café") || nome.contains("cafe") || nome.contains("expresso") || 
                    nome.contains("cappuccino") || nome.contains("latte") || nome.contains("coffee") ||
                    nome.contains("café") || nome.contains("espresso")) {
                    filtrados.add(produto);
                    System.out.println("✅ Adicionado em Cafés: " + nome); // DEBUG
                }
                break;
                
            case "flores":
                if (nome.contains("flor") || nome.contains("rosa") || nome.contains("orquídea") || 
                    nome.contains("orquidea") || nome.contains("girassol") || 
                    nome.contains("tulipa") || nome.contains("buquê") || nome.contains("buque") ||
                    nome.contains("flores") || nome.contains("flower")) {
                    filtrados.add(produto);
                    System.out.println("✅ Adicionado em Flores: " + nome); // DEBUG
                }
                break;
                
            case "combo":
                if (nome.contains("combo") || nome.contains("kit") || nome.contains("pacote") ||
                    nome.contains("presente") || nome.contains("special") || nome.contains("kit") ||
                    produto.getPreco() > 20.0) {
                    filtrados.add(produto);
                    System.out.println("✅ Adicionado em Combos: " + nome + " (R$ " + produto.getPreco() + ")"); // DEBUG
                }
                break;
        }
    }
    
    System.out.println("Encontrados na categoria " + tipo + ": " + filtrados.size()); // DEBUG
    return filtrados;
}

    private HBox criarNavbar() {
        HBox navbar = new HBox(15);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(12));
        navbar.setStyle("-fx-background-color: #4C2B0B; -fx-background-radius: 5;");
        navbar.setPrefWidth(Region.USE_COMPUTED_SIZE);
        
        // Logo
        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(40);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            Text logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
            navbar.getChildren().add(logoTexto);
        }
        
        if (logoView != null) {
            navbar.getChildren().add(logoView);
        }
        
        // Espaço flexível
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Links de navegação
        HBox linksContainer = new HBox(15);
        linksContainer.setAlignment(Pos.CENTER_RIGHT);
        
        Button btnHome = criarBotaoNav("Home");
        Button btnSobre = criarBotaoNav("Sobre");
        Button btnContato = criarBotaoNav("Contato");
        
        // Ícone de perfil
        Button btnPerfil = new Button("👤");
        btnPerfil.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16; " +
                          "-fx-border: none; -fx-cursor: hand; -fx-padding: 5;");
        btnPerfil.setOnMouseEntered(e -> btnPerfil.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 5;"));
        btnPerfil.setOnMouseExited(e -> btnPerfil.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 5;"));
        
        linksContainer.getChildren().addAll(btnHome, btnSobre, btnContato, btnPerfil);
        
        navbar.getChildren().addAll(spacer, linksContainer);
        
        // Eventos dos botões
        btnHome.setOnAction(e -> {
            TelaInicial telaInicial = new TelaInicial(stage);
            telaInicial.mostrar();
        });
        
        return navbar;
    }

    private Button criarBotaoNav(String texto) {
        Button button = new Button(texto);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold; " +
                       "-fx-border: none; -fx-cursor: hand; -fx-padding: 8 12; -fx-font-size: 12;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; " +
                                                    "-fx-font-weight: bold; -fx-border: none; -fx-padding: 8 12; -fx-font-size: 12;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " +
                                                   "-fx-font-weight: bold; -fx-border: none; -fx-padding: 8 12; -fx-font-size: 12;"));
        return button;
    }

    

    private VBox criarCardProduto(Produto produto) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                     "-fx-border-color: #D2B48C; -fx-border-radius: 10; -fx-border-width: 1; " +
                     "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setPrefWidth(200);
        card.setMinHeight(250);

        // Imagem do produto
        ImageView imgProduto;
        try {
            Image img = new Image(getClass().getResourceAsStream("/images/produto-placeholder.jpg"));
            imgProduto = new ImageView(img);
        } catch (Exception e) {
            // Placeholder colorido baseado no tipo
            StackPane placeholder = new StackPane();
            String corFundo = produto.getNome().toLowerCase().contains("café") ? "#8B4513" : 
                             produto.getNome().toLowerCase().contains("flor") ? "#FF69B4" : "#4C2B0B";
            placeholder.setStyle("-fx-background-color: " + corFundo + "; -fx-min-width: 100; -fx-min-height: 100; -fx-background-radius: 10;");
            placeholder.getChildren().add(new Text(produto.getNome().contains("Café") ? "☕" : "🌺"));
            imgProduto = new ImageView();
        }
        
        imgProduto.setFitWidth(100);
        imgProduto.setFitHeight(100);
        imgProduto.setPreserveRatio(true);

        // Nome do produto
        Label nome = new Label(produto.getNome());
        nome.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #4C2B0B;");
        nome.setWrapText(true);
        nome.setMaxWidth(180);
        nome.setAlignment(Pos.CENTER);

        // Preço
        Label preco = new Label(String.format("R$ %.2f", produto.getPreco()));
        preco.setStyle("-fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: #4C2B0B;");

        // Botão de adicionar ao carrinho
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; " +
                            "-fx-padding: 6 12; -fx-background-radius: 5; -fx-font-size: 12;");
        btnAdicionar.setOnMouseEntered(e -> 
            btnAdicionar.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; -fx-font-weight: bold; " +
                                "-fx-padding: 6 12; -fx-background-radius: 5; -fx-font-size: 12;"));
        btnAdicionar.setOnMouseExited(e -> 
            btnAdicionar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; " +
                                "-fx-padding: 6 12; -fx-background-radius: 5; -fx-font-size: 12;"));
        
        btnAdicionar.setOnAction(e -> {
            System.out.println("✅ " + produto.getNome() + " adicionado ao carrinho!");
        });

        card.getChildren().addAll(imgProduto, nome, preco, btnAdicionar);
        return card;
    }

}