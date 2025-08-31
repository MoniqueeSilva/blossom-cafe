package com.blossomcafe.view;

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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

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
        
        // ==================== IMAGEM HERO ====================
        ImageView imagemHero = criarImagemHero();
        
        // ==================== TÍTULO ====================
        Text titulo = new Text("Nossos Produtos");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setStyle("-fx-fill: #4C2B0B;");
        
        // ==================== PRODUTOS ====================
        ScrollPane scrollProdutos = criarScrollProdutos();
        
        // ==================== LAYOUT PRINCIPAL ====================
        VBox layoutPrincipal = new VBox(10);
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setPadding(new Insets(10));
        layoutPrincipal.setStyle("-fx-background-color: #EADED0;");
        
        VBox.setVgrow(scrollProdutos, Priority.ALWAYS);
        
        layoutPrincipal.getChildren().addAll(navbar, imagemHero, titulo, scrollProdutos);
        
        // ==================== SCENE ====================
        Scene scene = new Scene(layoutPrincipal, 1000, 700);
        stage.setTitle("Blossom Café - Produtos");
        stage.setScene(scene);
        stage.show();
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
            logoView.setFitWidth(120);
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
        
        btnSobre.setOnAction(e -> {
            System.out.println("Abrir tela Sobre");
        });
        
        btnContato.setOnAction(e -> {
            System.out.println("Abrir tela Contato");
        });
        
        btnPerfil.setOnAction(e -> {
            System.out.println("Abrir perfil do usuário");
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

    private ImageView criarImagemHero() {
        ImageView imagemHero;
        try {
            Image heroImage = new Image(getClass().getResourceAsStream("/images/cafe-hero.jpg"));
            imagemHero = new ImageView(heroImage);
            imagemHero.setFitWidth(800);  // Reduzido de 900 para 800
            imagemHero.setFitHeight(200); // Reduzido de 250 para 200
            imagemHero.setPreserveRatio(false);
        } catch (Exception e) {
            // Fallback - retângulo com cor
            StackPane placeholder = new StackPane();
            placeholder.setStyle("-fx-background-color: #D2B48C; -fx-min-width: 800; -fx-min-height: 150;");
            placeholder.getChildren().add(new Text("🌺 Café e Flores 🌸"));
            imagemHero = new ImageView();
        }
        return imagemHero;
    }

    private ScrollPane criarScrollProdutos() {
        GridPane gridProdutos = new GridPane();
        gridProdutos.setHgap(20);
        gridProdutos.setVgap(20);
        gridProdutos.setPadding(new Insets(15));
        gridProdutos.setAlignment(Pos.CENTER);
        
        // Obter produtos
        List<Produto> produtos = produtoController.listarProdutosDisponiveis();
        
        int col = 0;
        int row = 0;
        int maxCols = 3; // Máximo de 3 colunas (era 4)
        
        for (Produto produto : produtos) {
            VBox card = criarCardProduto(produto);
            gridProdutos.add(card, col, row);
            
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
        
        // ScrollPane para muitos produtos
        ScrollPane scrollPane = new ScrollPane(gridProdutos);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-border: none;");
        scrollPane.setPadding(new Insets(5));
        
        return scrollPane;
    }

    private VBox criarCardProduto(Produto produto) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                     "-fx-border-color: #D2B48C; -fx-border-radius: 10; -fx-border-width: 1;");
        card.setPrefWidth(220);  // Largura reduzida
        card.setMinHeight(280);  // Altura reduzida

        // Imagem do produto
        ImageView imgProduto;
        try {
            Image img = new Image(getClass().getResourceAsStream("/images/produto-placeholder.jpg"));
            imgProduto = new ImageView(img);
        } catch (Exception e) {
            // Placeholder colorido
            StackPane placeholder = new StackPane();
            placeholder.setStyle("-fx-background-color: #8B4513; -fx-min-width: 120; -fx-min-height: 120;");
            placeholder.getChildren().add(new Text(produto.getNome().contains("Café") ? "☕" : "🌺"));
            imgProduto = new ImageView();
        }
        
        imgProduto.setFitWidth(120);  // Reduzido de 150 para 120
        imgProduto.setFitHeight(120); // Reduzido de 150 para 120
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
            System.out.println("✅ Produto adicionado: " + produto.getNome() + " - R$ " + produto.getPreco());
        });

        card.getChildren().addAll(imgProduto, nome, preco, btnAdicionar);
        return card;
    }
}