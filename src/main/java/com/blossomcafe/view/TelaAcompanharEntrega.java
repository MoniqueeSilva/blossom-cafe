package com.blossomcafe.view;

import com.blossomcafe.model.Pedido;
import com.blossomcafe.model.Entregador;
import com.blossomcafe.dao.EntregadorDAO;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class TelaAcompanharEntrega {
    private Stage stage;
    private Pedido pedido;
    private Timeline timelineProgressao;
    private VBox timelineContainer;
    private Label labelProgresso;
    private ProgressBar barraProgresso;
    private Label tempoEstimado;
    private VBox progressoContainer;
    private Label porcentagemLabel;
    private VBox entregadorContainer;
    private Label entregadorNome;
    private Label entregadorVeiculo;
    private Label entregadorPlaca;

    public TelaAcompanharEntrega(Stage stage, Pedido pedido) {
        this.stage = stage;
        this.pedido = pedido;
    }

    public void mostrar() {
        stage.setTitle("Acompanhamento de Entrega - Blossom Café");

        // Container Principal
        VBox containerPrincipal = new VBox();
        containerPrincipal.getStyleClass().add("container-principal");
        containerPrincipal.setSpacing(20);
        containerPrincipal.setPadding(new Insets(20));

        // Cabeçalho
        HBox cabecalho = new HBox();
        cabecalho.getStyleClass().add("cabecalho");
        cabecalho.setAlignment(Pos.CENTER);

        // Logo
        try {
            Image logoImage = new Image(getClass().getResource("/images/logo-blossom-small.png").toString());
            ImageView logoView = new ImageView(logoImage);
            logoView.setFitHeight(40);
            logoView.setPreserveRatio(true);
            cabecalho.getChildren().add(logoView);
        } catch (Exception e) {
            Label logoFallback = new Label("🌺 Blossom Café");
            logoFallback.getStyleClass().add("logo-fallback");
            cabecalho.getChildren().add(logoFallback);
        }

        // Card Principal
        VBox card = new VBox();
        card.getStyleClass().add("card");
        card.setSpacing(20);
        card.setPadding(new Insets(25));

        // Título
        Label titulo = new Label("Acompanhe sua Entrega");
        titulo.getStyleClass().add("titulo");

        // Número do Pedido
        Label numeroPedido = new Label("Pedido #" + pedido.getId());
        numeroPedido.getStyleClass().add("numero-pedido");

        // Container do Entregador
        entregadorContainer = new VBox();
        entregadorContainer.getStyleClass().add("entregador-container");
        entregadorContainer.setSpacing(10);
        entregadorContainer.setPadding(new Insets(15));
        entregadorContainer.setVisible(false);
        entregadorContainer.setManaged(false);
        
        Label entregadorTitulo = new Label("Seu Entregador");
        entregadorTitulo.getStyleClass().add("entregador-titulo");
        
        HBox entregadorInfo = new HBox();
        entregadorInfo.getStyleClass().add("entregador-info");
        entregadorInfo.setSpacing(15);
        entregadorInfo.setAlignment(Pos.CENTER_LEFT);
        
        // Avatar do entregador (círculo com inicial)
        StackPane avatarContainer = new StackPane();
        avatarContainer.getStyleClass().add("entregador-avatar");
        
        Circle circle = new Circle(25);
        circle.setFill(Color.LIGHTBLUE);
        
        Label inicial = new Label("E");
        inicial.getStyleClass().add("entregador-inicial");
        
        avatarContainer.getChildren().addAll(circle, inicial);
        
        VBox entregadorDetalhes = new VBox();
        entregadorDetalhes.getStyleClass().add("entregador-detalhes");
        entregadorDetalhes.setSpacing(5);
        
        entregadorNome = new Label();
        entregadorNome.getStyleClass().add("entregador-nome");
        
        entregadorVeiculo = new Label();
        entregadorVeiculo.getStyleClass().add("entregador-veiculo");
        
        entregadorPlaca = new Label();
        entregadorPlaca.getStyleClass().add("entregador-placa");
        
        entregadorDetalhes.getChildren().addAll(entregadorNome, entregadorVeiculo, entregadorPlaca);
        entregadorInfo.getChildren().addAll(avatarContainer, entregadorDetalhes);
        entregadorContainer.getChildren().addAll(entregadorTitulo, entregadorInfo);

        // Timeline Visual
        timelineContainer = new VBox();
        timelineContainer.getStyleClass().add("timeline-container");
        timelineContainer.setSpacing(15);

        // Barra de Progresso
        progressoContainer = new VBox();
        progressoContainer.getStyleClass().add("progresso-container");
        progressoContainer.setSpacing(10);
        progressoContainer.setAlignment(Pos.CENTER);

        labelProgresso = new Label();
        labelProgresso.getStyleClass().add("label-progresso");

        barraProgresso = new ProgressBar(0);
        barraProgresso.getStyleClass().add("barra-progresso");
        barraProgresso.setPrefWidth(400);
        barraProgresso.setPrefHeight(12);

        porcentagemLabel = new Label();
        porcentagemLabel.getStyleClass().add("porcentagem-label");

        progressoContainer.getChildren().addAll(labelProgresso, barraProgresso, porcentagemLabel);

        // Tempo Estimado
        tempoEstimado = new Label();
        tempoEstimado.getStyleClass().add("tempo-estimado");
        tempoEstimado.setAlignment(Pos.CENTER);

        // Botões - Container com espaçamento adequado
        HBox botoesContainer = new HBox();
        botoesContainer.getStyleClass().add("botoes-container");
        botoesContainer.setSpacing(15);
        botoesContainer.setAlignment(Pos.CENTER);
        botoesContainer.setPadding(new Insets(20, 0, 0, 0));

        Button btnVoltar = new Button("← Voltar ao Menu");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setPrefWidth(150);
        btnVoltar.setOnAction(e -> {
            pararProgressaoAutomatica();
            // TelaProdutos telaProdutos = new TelaProdutos(stage, null);
            // telaProdutos.mostrar();
            System.out.println("Voltando ao menu...");
        });
        
        Button btnContatar = new Button("📞 Contatar Entregador");
        btnContatar.getStyleClass().add("btn-contatar");
        btnContatar.setPrefWidth(180);
        btnContatar.setOnAction(e -> {
            System.out.println("Contatando entregador...");
        });

        botoesContainer.getChildren().addAll(btnVoltar, btnContatar);

        // Montagem Final - Garantindo que tudo caiba na tela
        VBox contentContainer = new VBox();
        contentContainer.setSpacing(20);
        contentContainer.getChildren().addAll(
            titulo, 
            numeroPedido, 
            entregadorContainer, 
            timelineContainer, 
            progressoContainer, 
            tempoEstimado
        );
        
        // Usar VBox.setVgrow para garantir que o conteúdo principal ocupe o espaço disponível
        VBox.setVgrow(contentContainer, Priority.ALWAYS);
        
        card.getChildren().addAll(contentContainer, botoesContainer);
        containerPrincipal.getChildren().addAll(cabecalho, card);

        // Atualizar interface
        atualizarInterface();

        // Iniciar progressão automática
        iniciarProgressaoAutomatica();

        // Scene com scroll para garantir que tudo seja acessível
        ScrollPane scrollPane = new ScrollPane(containerPrincipal);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        Scene scene = new Scene(scrollPane, 550, 650);

        try {
            scene.getStylesheets().add(getClass().getResource("/css/acompanharEntrega.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS não carregado: " + e.getMessage());
            aplicarEstilosInline(containerPrincipal);
        }

        stage.setScene(scene);
        stage.show();
    }

    // ====================== PROGRESSÃO AUTOMÁTICA ======================

    private void iniciarProgressaoAutomatica() {
        pararProgressaoAutomatica();
        timelineProgressao = new Timeline(new KeyFrame(Duration.seconds(5), e -> avancarStatus()));
        timelineProgressao.setCycleCount(Timeline.INDEFINITE);
        timelineProgressao.play();
    }

    private void pararProgressaoAutomatica() {
        if (timelineProgressao != null) {
            timelineProgressao.stop();
            timelineProgressao = null;
        }
    }

    private void avancarStatus() {
        String statusAtual = pedido.getStatus();
        String novoStatus;

        switch (statusAtual.toLowerCase()) {
            case "confirmado": 
                novoStatus = "Preparando"; 
                break;
            case "preparando": 
                novoStatus = "A caminho"; 
                // Atribuir entregador quando o pedido sai para entrega
                if (pedido.getEntregador() == null) {
                    atribuirEntregador();
                }
                break;
            case "a caminho": 
                novoStatus = "Entregue"; 
                break;
            case "entregue": 
                pararProgressaoAutomatica(); 
                return;
            default: 
                novoStatus = "Confirmado";
        }

        pedido.setStatus(novoStatus);
        atualizarInterface();

        if (novoStatus.equalsIgnoreCase("Entregue")) {
            pararProgressaoAutomatica();
        }
    }
    
    private void atribuirEntregador() {
        // Simular busca de entregador no banco de dados
        EntregadorDAO entregadorDAO = new EntregadorDAO();
        java.util.List<Entregador> entregadores = entregadorDAO.listarTodos();
        
        if (entregadores != null && !entregadores.isEmpty()) {
            // Selecionar um entregador aleatório
            Random random = new Random();
            Entregador entregador = entregadores.get(random.nextInt(entregadores.size()));
            pedido.setEntregador(entregador);
        } else {
            // Entregador padrão caso não haja no banco
            Entregador entregadorPadrao = new Entregador("João Silva", "Moto", "ABC-1234", "123456789");
            pedido.setEntregador(entregadorPadrao);
        }
    }

    // ====================== ATUALIZAÇÃO DA INTERFACE ======================

    private void atualizarInterface() {
        timelineContainer.getChildren().clear();

        String[] etapas = {"Confirmado", "Preparando", "A caminho", "Entregue"};
        String[] icones = {"✅", "👨‍🍳", "🚗", "🎉"};
        String[] descricoes = {
            "Pedido confirmado e em processamento",
            "Seu pedido está sendo preparado",
            "Seu entregador está a caminho!",
            "Entrega realizada com sucesso!"
        };

        for (int i = 0; i < etapas.length; i++) {
            HBox etapaBox = new HBox();
            etapaBox.getStyleClass().add("etapa-box");
            etapaBox.setSpacing(12);
            etapaBox.setAlignment(Pos.CENTER_LEFT);

            Label icone = new Label(icones[i]);
            icone.getStyleClass().add("etapa-icone");

            Pane conector = new Pane();
            conector.getStyleClass().add("etapa-conector");
            if (i == etapas.length - 1) {
                conector.setVisible(false);
            }

            VBox conteudoEtapa = new VBox();
            conteudoEtapa.getStyleClass().add("etapa-conteudo");
            conteudoEtapa.setSpacing(3);

            Label labelEtapa = new Label(etapas[i]);
            Label labelDesc = new Label(descricoes[i]);
            
            boolean etapaAtiva = isEtapaAtiva(i, pedido.getStatus());
            boolean etapaConcluida = isEtapaConcluida(i, pedido.getStatus());
            
            if (etapaAtiva) {
                labelEtapa.getStyleClass().add("etapa-ativa");
                labelDesc.getStyleClass().add("etapa-ativa");
            } else if (etapaConcluida) {
                labelEtapa.getStyleClass().add("etapa-concluida");
                labelDesc.getStyleClass().add("etapa-concluida");
            } else {
                labelEtapa.getStyleClass().add("etapa-pendente");
                labelDesc.getStyleClass().add("etapa-pendente");
            }

            conteudoEtapa.getChildren().addAll(labelEtapa, labelDesc);
            etapaBox.getChildren().addAll(icone, conector, conteudoEtapa);
            timelineContainer.getChildren().add(etapaBox);
        }

        double progresso = calcularProgresso(pedido.getStatus());
        Timeline animacao = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(barraProgresso.progressProperty(), barraProgresso.getProgress())),
            new KeyFrame(Duration.seconds(0.8), new KeyValue(barraProgresso.progressProperty(), progresso))
        );
        animacao.play();

        labelProgresso.setText(getTextoProgresso(pedido.getStatus()));
        tempoEstimado.setText("⏰ " + getTempoEstimado(pedido.getStatus()));
        
        int porcentagem = (int) (progresso * 100);
        porcentagemLabel.setText(porcentagem + "% completo");
        
        // Atualizar informações do entregador
        atualizarInformacoesEntregador();
    }
    
    private void atualizarInformacoesEntregador() {
        Entregador entregador = pedido.getEntregador();
        
        // Mostrar informações do entregador apenas quando o pedido estiver a caminho ou entregue
        boolean mostrarEntregador = "a caminho".equalsIgnoreCase(pedido.getStatus()) || 
                                  "entregue".equalsIgnoreCase(pedido.getStatus());
        
        entregadorContainer.setVisible(mostrarEntregador);
        entregadorContainer.setManaged(mostrarEntregador);
        
        if (mostrarEntregador && entregador != null) {
            entregadorNome.setText("Entregador: " + entregador.getNome());
            entregadorVeiculo.setText("Veículo: " + entregador.getVeiculo());
            entregadorPlaca.setText("Placa: " + entregador.getPlaca());
        }
    }

    // ====================== MÉTODOS AUXILIARES ======================

    private boolean isEtapaAtiva(int index, String status) {
        String[] statusMap = {"Confirmado", "Preparando", "A caminho", "Entregue"};
        return status.equalsIgnoreCase(statusMap[index]);
    }

    private boolean isEtapaConcluida(int index, String status) {
        String[] statusMap = {"Confirmado", "Preparando", "A caminho", "Entregue"};
        for (int i = 0; i < statusMap.length; i++) {
            if (status.equalsIgnoreCase(statusMap[i])) {
                return index < i;
            }
        }
        return false;
    }

    private double calcularProgresso(String status) {
        switch (status.toLowerCase()) {
            case "confirmado": return 0.25;
            case "preparando": return 0.5;
            case "a caminho": return 0.75;
            case "entregue": return 1.0;
            default: return 0.0;
        }
    }

    private String getTextoProgresso(String status) {
        switch (status.toLowerCase()) {
            case "confirmado": return "Pedido confirmado!";
            case "preparando": return "Preparando seu pedido...";
            case "a caminho": 
                if (pedido.getEntregador() != null) {
                    return pedido.getEntregador().getNome() + " está a caminho!";
                }
                return "Seu pedido está a caminho!";
            case "entregue": return "Entrega realizada com sucesso!";
            default: return "Processando seu pedido...";
        }
    }

    private String getTempoEstimado(String status) {
        switch (status.toLowerCase()) {
            case "confirmado": return "Tempo estimado: 30-40 minutos";
            case "preparando": return "Tempo estimado: 20-30 minutos";
            case "a caminho": return "Chegando em 5-15 minutos!";
            case "entregue": return "Entregue! Obrigado pela preferência!";
            default: return "Aguardando processamento";
        }
    }
    
    private void aplicarEstilosInline(VBox containerPrincipal) {
        // Estilos inline minimalistas para garantir funcionalidade
        containerPrincipal.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 20;");
        
        for (javafx.scene.Node node : containerPrincipal.getChildren()) {
            if (node.getStyleClass().contains("card")) {
                node.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 25; " +
                             "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
            }
            if (node.getStyleClass().contains("titulo")) {
                node.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(TelaEntregaApp.class, args);
    }

    public static class TelaEntregaApp extends Application {
        public void start(Stage stage) {
            Pedido pedidoTeste = new Pedido(101);
            TelaAcompanharEntrega telaEntrega = new TelaAcompanharEntrega(stage, pedidoTeste);
            telaEntrega.mostrar();
        }
    }
}