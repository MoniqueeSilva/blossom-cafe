package com.blossomcafe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blossomcafe.controller.ProdutoController;
import com.blossomcafe.model.Cliente;
import com.blossomcafe.model.Produto;
import com.blossomcafe.util.Sessao;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaProdutos {
    private Stage stage;
    private ProdutoController produtoController;
    private Map<String, String> mapeamentoImagens;
    private Cliente clienteLogado;

    public TelaProdutos(Stage stage, Cliente cliente) {
        this.stage = stage;
        this.clienteLogado = cliente;
        this.produtoController = new ProdutoController();
        this.mapeamentoImagens = new HashMap<>();
        this.clienteLogado = Sessao.getClienteLogado(); 
        inicializarMapeamentoImagensLimitado();
    }

    private void inicializarMapeamentoImagensLimitado() {
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
        tituloPrincipal.getStyleClass().add("titulo-principal");

        // ==================== CONTEÚDO PRINCIPAL ====================
        VBox conteudoPrincipal = new VBox(20);
        conteudoPrincipal.setAlignment(Pos.TOP_CENTER);
        conteudoPrincipal.setPadding(new Insets(20));
        conteudoPrincipal.getStyleClass().add("conteudo-principal");

        VBox secaoCafes = criarSecaoCategoria("☕ Nossos Cafés", "cafe", 4);
        VBox secaoFlores = criarSecaoCategoria("🌺 Nossas Flores", "flores", 4);
        VBox secaoCombos = criarSecaoCategoria("🎁 Combos Especiais", "combo", 3);

        conteudoPrincipal.getChildren().addAll(secaoCafes, secaoFlores, secaoCombos);

        ScrollPane scrollPane = new ScrollPane(conteudoPrincipal);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll");

        VBox layoutPrincipal = new VBox();
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.getStyleClass().add("layout-principal");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        layoutPrincipal.getChildren().addAll(navbar, tituloPrincipal, scrollPane);
        VBox.setMargin(tituloPrincipal, new Insets(20, 0, 10, 0));

        // ==================== SCENE ====================
        Scene scene = new Scene(layoutPrincipal, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/css/produtos.css").toExternalForm());

        stage.setTitle("Blossom Café - Cardápio");
        stage.setScene(scene);
        stage.show();
    }

    private VBox criarSecaoCategoria(String tituloCategoria, String tipo, int maxProdutos) {
        VBox secao = new VBox(15);
        secao.setAlignment(Pos.TOP_CENTER);
        secao.setPadding(new Insets(20, 20, 30, 20));
        secao.getStyleClass().add("secao");

        Text titulo = new Text(tituloCategoria);
        titulo.getStyleClass().add("titulo-categoria");

        FlowPane containerProdutos = new FlowPane();
        containerProdutos.setAlignment(Pos.TOP_CENTER);
        containerProdutos.setHgap(20);
        containerProdutos.setVgap(20);
        containerProdutos.setPadding(new Insets(10));

        List<Produto> produtosFiltrados = filtrarProdutosPorCategoria(tipo);

        int limite = Math.min(maxProdutos, produtosFiltrados.size());
        for (int i = 0; i < limite; i++) {
            VBox card = criarCardProduto(produtosFiltrados.get(i));
            card.getStyleClass().add("card-produto");
            containerProdutos.getChildren().add(card);
        }

        if (produtosFiltrados.isEmpty()) {
            Label labelVazio = new Label("Em breve novidades nesta categoria...");
            labelVazio.getStyleClass().add("label-vazio");
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
                    if (nome.contains("café") || nome.contains("cafe") || nome.contains("expresso") || 
                        nome.contains("cappuccino") || nome.contains("latte") || nome.contains("coffee") ||
                        nome.contains("chá") || nome.contains("cha") || nome.contains("chocolate") || 
                        nome.contains("machiatto") || nome.contains("nesquik")) {
                        filtrados.add(produto);
                    }
                    break;
                case "flores":
                    if (nome.contains("astromélia") || nome.contains("astromelia") || nome.contains("flores") || 
                        nome.contains("girassois") || nome.contains("lírio") || nome.contains("lirio") || 
                        nome.contains("margaridas") || nome.contains("orquidea") || nome.contains("rosas") || 
                        nome.contains("tulipas")) {
                        filtrados.add(produto);
                    }
                    break;
                case "combo":
                    if (nome.contains("caixa") || nome.contains("cesta") || nome.contains("combo") || nome.contains("kit")) {
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
        navbar.getStyleClass().add("navbar");

        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(45);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            Text logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.getStyleClass().add("logo-navbar");
            navbar.getChildren().add(logoTexto);
        }

        if (logoView != null) {
            navbar.getChildren().add(logoView);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navbar.getChildren().add(spacer);

        if (clienteLogado != null) {
            Label nomeUsuario = new Label("Olá, " + clienteLogado.getNome() + "!");
            nomeUsuario.getStyleClass().add("nome-usuario");
            navbar.getChildren().add(nomeUsuario);
        }

        HBox linksContainer = new HBox(20);
        linksContainer.setAlignment(Pos.CENTER_RIGHT);

        Button btnHome = criarBotaoNav("Home");
        Button btnSobre = criarBotaoNav("Sobre");
        Button btnContato = criarBotaoNav("Contato");

        Button btnPerfil = new Button("Perfil");
        btnPerfil.getStyleClass().add("btn-perfil");

        linksContainer.getChildren().addAll(btnHome, btnSobre, btnContato, btnPerfil);
        navbar.getChildren().add(linksContainer);

        btnHome.setOnAction(e -> new TelaProdutos(stage, clienteLogado).mostrar());
        btnPerfil.setOnAction(e -> {
            if (clienteLogado != null) {
                new TelaPerfil(stage, clienteLogado).mostrar();
            } else {
                new TelaLogin(stage).mostrar();
            }
        });

        return navbar;
    }

    private Button criarBotaoNav(String texto) {
        Button button = new Button(texto);
        button.getStyleClass().add("btn-nav");
        return button;
    }

    private VBox criarCardProduto(Produto produto) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(15));

        ImageView imgProduto = criarPlaceholder(produto);
        imgProduto.setFitWidth(80);
        imgProduto.setFitHeight(80);
        imgProduto.setPreserveRatio(true);

        Label nome = new Label(produto.getNome());
        nome.getStyleClass().add("nome-produto");
        nome.setWrapText(true);
        nome.setMaxWidth(170);
        nome.setAlignment(Pos.CENTER);
        nome.setMinHeight(40);

        Label preco = new Label(String.format("R$ %.2f", produto.getPreco()));
        preco.getStyleClass().add("preco-produto");

        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.getStyleClass().add("btn-adicionar");
        btnAdicionar.setOnAction(e -> {
            mostrarAlerta("Sucesso", produto.getNome() + " adicionado ao carrinho!");
        });

        card.getChildren().addAll(imgProduto, nome, preco, btnAdicionar);
        return card;
    }

    private ImageView criarPlaceholder(Produto produto) {
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

        placeholder.setStyle("-fx-background-color: " + corFundo + "; -fx-min-width: 80; -fx-min-height: 80; -fx-background-radius: 10;");
        Text textoEmoji = new Text(emoji);
        textoEmoji.setStyle("-fx-font-size: 24; -fx-fill: white;");
        placeholder.getChildren().add(textoEmoji);

        return new ImageView(); // apenas placeholder visual
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
