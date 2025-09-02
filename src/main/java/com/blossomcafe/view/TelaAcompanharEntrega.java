package com.blossomcafe.view;

import com.blossomcafe.model.Pedido;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaAcompanharEntrega {
    private Stage stage;
    private Pedido pedido;

    public TelaAcompanharEntrega(Stage stage, Pedido pedido) {
        this.stage = stage;
        this.pedido = pedido;
    }

    public void mostrar() {
        stage.setTitle("Acompanhamento de Entrega - Blossom Café");

        // ====================== CONTAINER PRINCIPAL ======================
        VBox containerPrincipal = new VBox(30);
        containerPrincipal.setAlignment(Pos.TOP_CENTER);
        containerPrincipal.setPadding(new Insets(40, 30, 40, 30));
        containerPrincipal.setStyle("-fx-background-color: linear-gradient(to bottom, #F8F6F2 0%, #E8E2D6 100%);");

        // ====================== CABEÇALHO ======================
        HBox cabecalho = new HBox();
        cabecalho.setAlignment(Pos.CENTER);
        cabecalho.setPadding(new Insets(0, 0, 20, 0));

        // Logo
        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResource("/images/logo-blossom-small.png").toString());
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(60);
            logoView.setFitHeight(60);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            // Fallback para ícone textual
            Label logoFallback = new Label("🌺");
            logoFallback.setStyle("-fx-font-size: 32px;");
            cabecalho.getChildren().add(logoFallback);
        }

        if (logoView != null) {
            cabecalho.getChildren().add(logoView);
        }

        // ====================== CARD PRINCIPAL ======================
        VBox card = new VBox(25);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(35, 40, 35, 40));
        card.setStyle("-fx-background-color: white; " +
                     "-fx-background-radius: 20; " +
                     "-fx-border-radius: 20; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 5);");
        card.setMaxWidth(450);

        // Título
        Label titulo = new Label("Acompanhe sua Entrega");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");

        // Número do Pedido
        Label numeroPedido = new Label("Pedido #" + pedido.getId());
        numeroPedido.setStyle("-fx-font-size: 16px; -fx-text-fill: #6B4C35; -fx-font-weight: 500;");

        // ====================== TIMELINE VISUAL ======================
        VBox timelineContainer = new VBox(15);
        timelineContainer.setAlignment(Pos.CENTER_LEFT);
        timelineContainer.setPadding(new Insets(0, 20, 0, 20));

        // Etapas da entrega
        String[] etapas = {"Confirmado", "Preparando", "Saiu para entrega", "Entregue"};
        String[] icones = {"✅", "👨‍🍳", "🚗", "🎉"};
        String[] descricoes = {
            "Pedido confirmado e em processamento",
            "Seu pedido está sendo preparado",
            "Seu entregador está a caminho!",
            "Entrega realizada com sucesso!"
        };

        for (int i = 0; i < etapas.length; i++) {
            HBox etapaBox = new HBox(15);
            etapaBox.setAlignment(Pos.CENTER_LEFT);

            // Ícone
            Label icone = new Label(icones[i]);
            icone.setStyle("-fx-font-size: 20px;");
            icone.setMinWidth(30);

            // Conector (linha)
            Pane conector = new Pane();
            conector.setMinHeight(2);
            conector.setPrefWidth(25);
            if (i < etapas.length - 1) {
                conector.setStyle("-fx-background-color: #D4C6B0;");
            }

            // Conteúdo da etapa
            VBox conteudoEtapa = new VBox(5);
            Label labelEtapa = new Label(etapas[i]);
            Label labelDesc = new Label(descricoes[i]);
            
            // Estilo baseado no status atual
            boolean etapaAtiva = isEtapaAtiva(i, pedido.getStatus());
            boolean etapaConcluida = isEtapaConcluida(i, pedido.getStatus());
            
            String corTexto = etapaAtiva ? "#4C2B0B" : (etapaConcluida ? "#6B4C35" : "#A89F8E");
            String pesoFonte = etapaAtiva ? "bold" : "normal";
            
            labelEtapa.setStyle("-fx-font-size: 16px; -fx-font-weight: " + pesoFonte + "; -fx-text-fill: " + corTexto + ";");
            labelDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: " + corTexto + ";");

            conteudoEtapa.getChildren().addAll(labelEtapa, labelDesc);
            etapaBox.getChildren().addAll(icone, conector, conteudoEtapa);
            timelineContainer.getChildren().add(etapaBox);
        }

        // ====================== BARRA DE PROGRESSO ANIMADA ======================
        VBox progressoContainer = new VBox(10);
        progressoContainer.setAlignment(Pos.CENTER);
        progressoContainer.setPadding(new Insets(20, 0, 0, 0));

        Label labelProgresso = new Label();
        labelProgresso.setStyle("-fx-font-size: 14px; -fx-text-fill: #6B4C35;");

        ProgressBar barraProgresso = new ProgressBar();
        barraProgresso.setPrefWidth(350);
        barraProgresso.setPrefHeight(12);
        barraProgresso.setStyle("-fx-accent: #8B4513; -fx-background-radius: 10; -fx-border-radius: 10;");

        // Animação da barra de progresso
        double progresso = calcularProgresso(pedido.getStatus());
        Timeline animacao = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(barraProgresso.progressProperty(), 0)),
            new KeyFrame(Duration.seconds(1.5), new KeyValue(barraProgresso.progressProperty(), progresso))
        );
        animacao.play();

        // Texto do progresso
        String textoProgresso = getTextoProgresso(pedido.getStatus());
        labelProgresso.setText(textoProgresso);

        progressoContainer.getChildren().addAll(labelProgresso, barraProgresso);

        // ====================== TEMPO ESTIMADO ======================
        Label tempoEstimado = new Label("⏰ Tempo estimado: " + getTempoEstimado(pedido.getStatus()));
        tempoEstimado.setStyle("-fx-font-size: 14px; -fx-text-fill: #6B4C35; -fx-font-style: italic;");

        // ====================== BOTÕES ======================
        HBox botoesContainer = new HBox(20);
        botoesContainer.setAlignment(Pos.CENTER);

        Button btnVoltar = criarBotao("← Voltar ao Menu", "#6B4C35", "white");
        btnVoltar.setOnAction(e -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage);
            telaProdutos.mostrar();
        });

        botoesContainer.getChildren().addAll(btnVoltar);

        // ====================== MONTAGEM FINAL ======================
        card.getChildren().addAll(titulo, numeroPedido, timelineContainer, progressoContainer, tempoEstimado, botoesContainer);
        containerPrincipal.getChildren().addAll(cabecalho, card);

        Scene scene = new Scene(containerPrincipal, 550, 700);
        
        // Carregar CSS se existir
        try {
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS não carregado: " + e.getMessage());
        }

        stage.setScene(scene);
        stage.show();
    }

    // ====================== MÉTODOS AUXILIARES ======================
    
    private Button criarBotao(String texto, String corFundo, String corTexto) {
        Button botao = new Button(texto);
        botao.setStyle("-fx-background-color: " + corFundo + "; " +
                      "-fx-text-fill: " + corTexto + "; " +
                      "-fx-font-weight: bold; " +
                      "-fx-background-radius: 20; " +
                      "-fx-padding: 10 20; " +
                      "-fx-cursor: hand;");
        botao.setOnMouseEntered(e -> botao.setStyle("-fx-background-color: " + 
            (corFundo.equals("transparent") ? "#F5F0E6" : escurecerCor(corFundo)) + 
            "; -fx-text-fill: " + corTexto + "; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 10 20; -fx-cursor: hand;"));
        botao.setOnMouseExited(e -> botao.setStyle("-fx-background-color: " + corFundo + 
            "; -fx-text-fill: " + corTexto + "; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 10 20; -fx-cursor: hand;"));
        return botao;
    }

    private String escurecerCor(String cor) {
        if (cor.equals("#6B4C35")) return "#5A3D2A";
        if (cor.equals("transparent")) return "#F5F0E6";
        return cor;
    }

    private boolean isEtapaAtiva(int index, String status) {
        String[] statusMap = {"Confirmado", "Preparando", "Saiu para entrega", "Entregue"};
        for (int i = 0; i <= index; i++) {
            if (statusMap[i].equalsIgnoreCase(status.replace(" ", ""))) {
                return i == index;
            }
        }
        return false;
    }

    private boolean isEtapaConcluida(int index, String status) {
        String[] statusMap = {"Confirmado", "Preparando", "Saiu para entrega", "Entregue"};
        String statusNormalizado = status.replace(" ", "").replace("A caminho", "Saiuparaentrega");
        
        for (int i = 0; i < statusMap.length; i++) {
            if (statusNormalizado.contains(statusMap[i].toLowerCase())) {
                return index < i;
            }
        }
        return false;
    }

    private double calcularProgresso(String status) {
        switch (status.toLowerCase()) {
            case "confirmado": return 0.25;
            case "preparando": return 0.5;
            case "a caminho": case "saiu para entrega": return 0.75;
            case "entregue": return 1.0;
            default: return 0.1;
        }
    }

    private String getTextoProgresso(String status) {
        switch (status.toLowerCase()) {
            case "confirmado": return "Pedido confirmado!";
            case "preparando": return "Preparando seu pedido...";
            case "a caminho": return "Seu pedido está a caminho!";
            case "entregue": return "Entrega realizada com sucesso!";
            default: return "Processando seu pedido...";
        }
    }

    private String getTempoEstimado(String status) {
        switch (status.toLowerCase()) {
            case "confirmado": return "30-40 minutos";
            case "preparando": return "20-30 minutos";
            case "a caminho": return "5-15 minutos";
            case "entregue": return "Entregue!";
            default: return "Aguardando processamento";
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