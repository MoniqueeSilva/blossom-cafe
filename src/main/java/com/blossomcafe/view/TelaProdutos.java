package com.blossomcafe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blossomcafe.controller.ProdutoController;
import com.blossomcafe.model.Produto;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
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
    private Map<String, String> mapeamentoImagens;

    public TelaProdutos(Stage stage) {
        this.stage = stage;
        this.produtoController = new ProdutoController();
        this.mapeamentoImagens = new HashMap<>();
        
        // Inicializar o mapeamento de imagens apenas para alguns produtos principais
        inicializarMapeamentoImagensLimitado();
    }
    
    private void inicializarMapeamentoImagensLimitado() {
        // Apenas alguns produtos principais para não sobrecarregar
        mapeamentoImagens.put("Café Expresso", "/images/produtos/cafes/cafe-expresso.jpg");
        mapeamentoImagens.put("Cappuccino Italiano", "/images/produtos/cafes/cappuccino-italiano.jpg");
        mapeamentoImagens.put("Buquê de Rosas", "/images/produtos/flores/buque-rosas.jpg");
        mapeamentoImagens.put("Girassois", "/images/produtos/flores/girassois.jpg");
        mapeamentoImagens.put("Combo Romance", "/images/produtos/combos/cesta-romance.jpg");
    }

    public void mostrar() {
        // ==================== NAVBAR ====================
        HBox navbar = criarNavbar();
        
        // ==================== TÍTULO PRINCIPAL ====================
        Text tituloPrincipal = new Text("Cardápio Blossom Cafe");
        tituloPrincipal.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        tituloPrincipal.setStyle("-fx-fill: #4C2B0B;");
        
        // ==================== CONTEÚDO PRINCIPAL ====================
        VBox conteudoPrincipal = new VBox(20);
        conteudoPrincipal.setAlignment(Pos.TOP_CENTER);
        conteudoPrincipal.setPadding(new Insets(20));
        conteudoPrincipal.setStyle("-fx-background-color: #EADED0;");
        
        // Café
        VBox secaoCafes = criarSecaoCategoria("☕ Nossos Cafés", "cafe", 4);
        // Flores
        VBox secaoFlores = criarSecaoCategoria("🌺 Nossas Flores", "flores", 4);
        // Combos
        VBox secaoCombos = criarSecaoCategoria("🎁 Combos Especiais", "combo", 3);
        
        conteudoPrincipal.getChildren().addAll(secaoCafes, secaoFlores, secaoCombos);
        
        // ScrollPane para o conteúdo
        ScrollPane scrollPane = new ScrollPane(conteudoPrincipal);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-border: none;");
        scrollPane.setPadding(new Insets(0));
        
        // ==================== LAYOUT PRINCIPAL ====================
        VBox layoutPrincipal = new VBox();
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setStyle("-fx-background-color: #EADED0;");
        
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        layoutPrincipal.getChildren().addAll(navbar, tituloPrincipal, scrollPane);
        VBox.setMargin(tituloPrincipal, new Insets(20, 0, 10, 0));
        
        // ==================== SCENE ====================
        Scene scene = new Scene(layoutPrincipal, 1000, 700);
        stage.setTitle("Blossom Café - Cardápio");
        stage.setScene(scene);
        stage.show();
    }

    private VBox criarSecaoCategoria(String tituloCategoria, String tipo, int maxProdutos) {
        VBox secao = new VBox(15);
        secao.setAlignment(Pos.TOP_CENTER);
        secao.setPadding(new Insets(20, 20, 30, 20));
        secao.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 15; -fx-border-color: #D2B48C; -fx-border-radius: 15; -fx-border-width: 1;");
        secao.setMaxWidth(900);

        // Título da categoria
        Text titulo = new Text(tituloCategoria);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-fill: #4C2B0B;");
        
        // Container para os produtos - usando FlowPane para layout responsivo
        FlowPane containerProdutos = new FlowPane();
        containerProdutos.setAlignment(Pos.TOP_CENTER);
        containerProdutos.setHgap(20);
        containerProdutos.setVgap(20);
        containerProdutos.setPadding(new Insets(10));
        
        // Obter produtos filtrados por categoria
        List<Produto> produtosFiltrados = filtrarProdutosPorCategoria(tipo);
        
        // Limitar o número de produtos exibidos
        int limite = Math.min(maxProdutos, produtosFiltrados.size());
        for (int i = 0; i < limite; i++) {
            VBox card = criarCardProduto(produtosFiltrados.get(i));
            containerProdutos.getChildren().add(card);
        }
        
        // Se não houver produtos na categoria
        if (produtosFiltrados.isEmpty()) {
            Label labelVazio = new Label("Em breve novidades nesta categoria...");
            labelVazio.setStyle("-fx-text-fill: #8B4513; -fx-font-style: italic; -fx-font-size: 14;");
            containerProdutos.getChildren().add(labelVazio);
        }
        
        secao.getChildren().addAll(titulo, containerProdutos);
        return secao;
    }

    private List<Produto> filtrarProdutosPorCategoria(String tipo) {
        List<Produto> todosProdutos = produtoController.listarProdutosDisponiveis();
        List<Produto> filtrados = new ArrayList<>();
        
        for (Produto produto : todosProdutos) {
            String nome = produto.getNome().toLowerCase();
            
            switch (tipo) {
                case "cafe":
                    if (nome.contains("café") || nome.contains("cafe") || 
                        nome.contains("expresso") || nome.contains("cappuccino") || 
                        nome.contains("latte") || nome.contains("coffee") ||
                        nome.contains("chá") || nome.contains("cha") ||
                        nome.contains("chocolate") || nome.contains("machiatto") ||
                        nome.contains("nesquik")) {
                        filtrados.add(produto);
                    }
                    break;
                    
                case "flores":
                    if (nome.contains("astromélia") || nome.contains("astromelia") || 
                        nome.contains("flores") || nome.contains("girassois") || 
                        nome.contains("lírio") || nome.contains("lirio") || 
                        nome.contains("margaridas") || nome.contains("orquidea") ||
                        nome.contains("rosas") || nome.contains("tulipas")) {
                        filtrados.add(produto);
                    }
                    break;
                    
                case "combo":
                    if (nome.contains("caixa") || nome.contains("cesta") || 
                        nome.contains("combo") || nome.contains("kit")) {
                        filtrados.add(produto);
                    }
                    break;
            }
        }
        
        return filtrados;
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
        Button btnSobre = criarBotaoNav("Sobre");
        Button btnContato = criarBotaoNav("Contato");
        
        // Ícone de perfil
        Button btnPerfil = new Button("👤");
        btnPerfil.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16; " +
                          "-fx-border: none; -fx-cursor: hand; -fx-padding: 8;");
        btnPerfil.setOnMouseEntered(e -> btnPerfil.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 8; -fx-background-radius: 5;"));
        btnPerfil.setOnMouseExited(e -> btnPerfil.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 8;"));
        
        linksContainer.getChildren().addAll(btnHome, btnSobre, btnContato, btnPerfil);
        
        navbar.getChildren().addAll(spacer, linksContainer);
        
        // Eventos dos botoes
        btnHome.setOnAction(e -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage);
            telaProdutos.mostrar();
        });
        
        btnPerfil.setOnAction(e -> {
            TelaPerfil telaPerfil = new TelaPerfil(stage, null); 
            telaPerfil.mostrar();
            //NECESSARIO AJUSTE 
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

    private VBox criarCardProduto(Produto produto) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                     "-fx-border-color: #D2B48C; -fx-border-radius: 10; -fx-border-width: 1; " +
                     "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setMinWidth(200);
        card.setMaxWidth(200);
        card.setMinHeight(250);

        // Usar placeholder para economizar memória pois tivemos problemas de gerenciamento de memoria
        ImageView imgProduto = criarPlaceholder(produto);
        imgProduto.setFitWidth(80);
        imgProduto.setFitHeight(80);
        imgProduto.setPreserveRatio(true);

        // Nome do produto
        Label nome = new Label(produto.getNome());
        nome.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #4C2B0B;");
        nome.setWrapText(true);
        nome.setMaxWidth(170);
        nome.setAlignment(Pos.CENTER);
        nome.setMinHeight(40);

        // Preço
        Label preco = new Label(String.format("R$ %.2f", produto.getPreco()));
        preco.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: #4C2B0B;");

        // Botão de adicionar ao carrinho, carrinho ainda em processo
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; " +
                            "-fx-padding: 8 16; -fx-background-radius: 5; -fx-font-size: 12;");
        btnAdicionar.setOnMouseEntered(e -> 
            btnAdicionar.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; -fx-font-weight: bold; " +
                                "-fx-padding: 8 16; -fx-background-radius: 5; -fx-font-size: 12;"));
        btnAdicionar.setOnMouseExited(e -> 
            btnAdicionar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; " +
                                "-fx-padding: 8 16; -fx-background-radius: 5; -fx-font-size: 12;"));
        
        btnAdicionar.setOnAction(e -> {
            System.out.println("✅ " + produto.getNome() + " adicionado ao carrinho!");
            mostrarAlerta("Sucesso", produto.getNome() + " adicionado ao carrinho!");
        });

        card.getChildren().addAll(imgProduto, nome, preco, btnAdicionar);
        return card;
    }

    private ImageView criarPlaceholder(Produto produto) {
        // Cria um placeholder colorido baseado no tipo de produto
        StackPane placeholder = new StackPane();
        String corFundo;
        String emoji;
        
        String nome = produto.getNome().toLowerCase();
        
        if (nome.contains("café") || nome.contains("cafe") || nome.contains("expresso") || 
            nome.contains("cappuccino") || nome.contains("latte") || nome.contains("coffee") ||
            nome.contains("chá") || nome.contains("cha") || nome.contains("chocolate") ||
            nome.contains("machiatto") || nome.contains("nesquik")) {
            corFundo = "#8B4513"; // Marrom para café
            emoji = "☕";
        } else if (nome.contains("astromélia") || nome.contains("astromelia") || nome.contains("flores") || 
                  nome.contains("girassois") || nome.contains("lírio") || nome.contains("lirio") || 
                  nome.contains("margaridas") || nome.contains("orquidea") || nome.contains("rosas") || 
                  nome.contains("tulipas")) {
            corFundo = "#FF69B4"; // Rosa para flores
            emoji = "🌺";
        } else {
            corFundo = "#4C2B0B"; // Marrom escuro para combos
            emoji = "🎁";
        }
        
        placeholder.setStyle("-fx-background-color: " + corFundo + "; -fx-min-width: 80; -fx-min-height: 80; -fx-background-radius: 10;");
        
        Text textoEmoji = new Text(emoji);
        textoEmoji.setStyle("-fx-font-size: 24; -fx-fill: white;");
        
        placeholder.getChildren().add(textoEmoji);
        
        // Converte StackPane para ImageView
        ImageView imageView = new ImageView();
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        
        return imageView;
    }
    
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}