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
        
        // Inicializar o mapeamento de imagens
        inicializarMapeamentoImagens();
    }
    
    private void inicializarMapeamentoImagens() {
        // Cafés
        mapeamentoImagens.put("Café Expresso", "/images/produtos/cafes/cafe-expresso.jpg");
        mapeamentoImagens.put("Cappuccino Italiano", "/images/produtos/cafes/cappuccino-italiano.jpg");
        mapeamentoImagens.put("Café com Leite", "/images/produtos/cafes/cafe-com-leite.jpg");
        // mapeamentoImagens.put("Café Filtrado ou Coado", "/images/produtos/cafes/cafe-filtrado-ou-coado.jpg");
        // mapeamentoImagens.put("Café Gelado", "/images/produtos/cafes/cafe-gelado.jpg");
        // mapeamentoImagens.put("Café Java", "/images/produtos/cafes/cafe-java.jpg");
        // mapeamentoImagens.put("Chá", "/images/produtos/cafes/cha.jpg");
        // mapeamentoImagens.put("Chocolate Quente", "/images/produtos/cafes/chocolate-quente.jpg");
        // mapeamentoImagens.put("Machiatto", "/images/produtos/cafes/machiatto.jpg");
        // mapeamentoImagens.put("Nesquik", "/images/produtos/cafes/nesquik.jpeg");

        // Flores
        mapeamentoImagens.put("Astromélia", "/images/produtos/flores/astromelia.jpg");
        mapeamentoImagens.put("Flores do Campo", "/images/produtos/flores/flores-do-campo.jpg");
        // mapeamentoImagens.put("Girassois", "/images/produtos/flores/girassois.jpg");
        // mapeamentoImagens.put("Lírio Rosa", "/images/produtos/flores/lirio-rosa.jpg");
        // mapeamentoImagens.put("Margaridas", "/images/produtos/flores/margaridas.jpg");
        // mapeamentoImagens.put("Orquidea Azul", "/images/produtos/flores/orquidea-azul.jpg");
        // mapeamentoImagens.put("Rosas Brancas Buquê", "/images/produtos/flores/rosas-brancas-buque.jpg");
        // mapeamentoImagens.put("Tulipas", "/images/produtos/flores/tulipas.jpg");
        
        // Combos
        mapeamentoImagens.put("Caixa de Café da Manhã", "/images/produtos/combos/caixa-cafe-da-manha.png");
        // mapeamentoImagens.put("Cesta Romance", "/images/produtos/combos/cesta-romance.jpg");
        // mapeamentoImagens.put("Combo Café e Croissant", "/images/produtos/combos/combo-cafe-croissant.png");
        // mapeamentoImagens.put("Combo de Girassois", "/images/produtos/combos/girassois-combo.png");
        // mapeamentoImagens.put("Kit Presente com Flores", "/images/produtos/combos/kit-presente-flores.png");
    }

    public void mostrar() {
        // ==================== NAVBAR ====================
        HBox navbar = criarNavbar();
        
        // ==================== TÍTULO PRINCIPAL ====================
        Text tituloPrincipal = new Text("Cardápio Blossom Cafe");
        tituloPrincipal.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        tituloPrincipal.setStyle("-fx-fill: #4C2B0B;");
        
        // ==================== VERIFICAÇÃO DE PRODUTOS ====================
        List<Produto> todosProdutos = produtoController.listarProdutosDisponiveis();
        
        if (todosProdutos.isEmpty()) {
            mostrarAlerta("Aviso", "Nenhum produto encontrado no banco de dados. Verifique a conexão.");
        } else {
            System.out.println("Produtos carregados do banco: " + todosProdutos.size());
            for (Produto p : todosProdutos) {
                System.out.println("- " + p.getNome() + " (R$ " + p.getPreco() + ")");
            }
        }
        
        // ==================== CATEGORIAS ====================
        VBox categoriasContainer = new VBox(30);
        categoriasContainer.setAlignment(Pos.TOP_CENTER);
        categoriasContainer.setPadding(new Insets(20));
        categoriasContainer.setStyle("-fx-background-color: #EADED0;");
        
        // Café
        VBox secaoCafes = criarSecaoCategoria("☕ Nossos Cafés", "cafe");
        // Flores
        VBox secaoFlores = criarSecaoCategoria("🌺 Nossas Flores", "flores");
        // Combos
        VBox secaoCombos = criarSecaoCategoria("🎁 Combos Especiais", "combo");
        
        categoriasContainer.getChildren().addAll(secaoCafes, secaoFlores, secaoCombos);
        
        // ScrollPane para todas as categorias
        ScrollPane scrollPane = new ScrollPane(categoriasContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-border: none;");
        scrollPane.setPadding(new Insets(5));
        
        //LAYOUT PRINCIPAL
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
            Label labelVazio = new Label("Nenhum produto disponível nesta categoria");
            labelVazio.setStyle("-fx-text-fill: #8B4513; -fx-font-style: italic;");
            gridProdutos.getChildren().add(labelVazio);
        }
        
        secao.getChildren().addAll(titulo, gridProdutos);
        return secao;
    }

    private List<Produto> filtrarProdutosPorCategoria(String tipo) {
        List<Produto> todosProdutos = produtoController.listarProdutosDisponiveis();
        List<Produto> filtrados = new ArrayList<>();
        
        for (Produto produto : todosProdutos) {
            String nome = produto.getNome();
            
            switch (tipo) {
                case "cafe":
                    if (nome.toLowerCase().contains("café") || nome.toLowerCase().contains("cafe") || 
                        nome.toLowerCase().contains("expresso") || nome.toLowerCase().contains("cappuccino") || 
                        nome.toLowerCase().contains("latte") || nome.toLowerCase().contains("coffee") ||
                        nome.toLowerCase().contains("chá") || nome.toLowerCase().contains("cha") ||
                        nome.toLowerCase().contains("chocolate") || nome.toLowerCase().contains("machiatto") ||
                        nome.toLowerCase().contains("nesquik")) {
                        filtrados.add(produto);
                    }
                    break;
                    
                case "flores":
                    if (nome.toLowerCase().contains("astromélia") || nome.toLowerCase().contains("astromelia") || 
                        nome.toLowerCase().contains("flores") || nome.toLowerCase().contains("girassois") || 
                        nome.toLowerCase().contains("lírio") || nome.toLowerCase().contains("lirio") || 
                        nome.toLowerCase().contains("margaridas") || nome.toLowerCase().contains("orquidea") ||
                        nome.toLowerCase().contains("rosas") || nome.toLowerCase().contains("tulipas")) {
                        filtrados.add(produto);
                    }
                    break;
                    
                case "combo":
                    if (nome.toLowerCase().contains("caixa") || nome.toLowerCase().contains("cesta") || 
                        nome.toLowerCase().contains("combo") || nome.toLowerCase().contains("kit")) {
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

        // Imagem do produto - busca imagem específica
        ImageView imgProduto = carregarImagemProduto(produto);
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
            mostrarAlerta("Sucesso", produto.getNome() + " adicionado ao carrinho!");
        });

        card.getChildren().addAll(imgProduto, nome, preco, btnAdicionar);
        return card;
    }

    private ImageView carregarImagemProduto(Produto produto) {
        String caminhoImagem = mapeamentoImagens.get(produto.getNome());
        
        if (caminhoImagem == null) {
            // Usa placeholder se não encontrar imagem específica
            return criarPlaceholder(produto);
        }
        
        try {
            Image imagem = new Image(getClass().getResourceAsStream(caminhoImagem));
            return new ImageView(imagem);
        } catch (Exception e) {
            System.out.println("Imagem não encontrada: " + caminhoImagem);
            return criarPlaceholder(produto);
        }
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
        
        placeholder.setStyle("-fx-background-color: " + corFundo + "; -fx-min-width: 100; -fx-min-height: 100; -fx-background-radius: 10;");
        
        Text textoEmoji = new Text(emoji);
        textoEmoji.setStyle("-fx-font-size: 24; -fx-fill: white;");
        
        placeholder.getChildren().add(textoEmoji);
        
        // Converte StackPane para ImageView (aproximação)
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        
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