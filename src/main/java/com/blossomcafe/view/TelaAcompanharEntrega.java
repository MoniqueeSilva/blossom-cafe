package com.blossomcafe.view;

import com.blossomcafe.model.Pedido;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
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
    private Timeline timelineProgressao;
    private VBox timelineContainer;
    private Label labelProgresso;
    private ProgressBar barraProgresso;
    private Label tempoEstimado;
    private VBox progressoContainer;
    private Label porcentagemLabel;

    public TelaAcompanharEntrega(Stage stage, Pedido pedido) {
        this.stage = stage;
        this.pedido = pedido;
    }

    public void mostrar() {
        stage.setTitle("Acompanhamento de Entrega - Blossom Café");

        // Container Principal
        VBox containerPrincipal = new VBox();
        containerPrincipal.getStyleClass().add("container-principal");

        // Cabeçalho
        HBox cabecalho = new HBox();
        cabecalho.getStyleClass().add("cabecalho");

        // Logo
        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResource("/images/logo-blossom-small.png").toString());
            logoView = new ImageView(logoImage);
            logoView.getStyleClass().add("logo");
        } catch (Exception e) {
            Label logoFallback = new Label("🌺");
            logoFallback.getStyleClass().add("logo-fallback");
            cabecalho.getChildren().add(logoFallback);
        }

        if (logoView != null) {
            cabecalho.getChildren().add(logoView);
        }

        // Card Principal
        VBox card = new VBox();
        card.getStyleClass().add("card");

        // Título
        Label titulo = new Label("Acompanhe sua Entrega");
        titulo.getStyleClass().add("titulo");

        // Número do Pedido
        Label numeroPedido = new Label("Pedido #" + pedido.getId());
        numeroPedido.getStyleClass().add("numero-pedido");

        // Timeline Visual
        timelineContainer = new VBox();
        timelineContainer.getStyleClass().add("timeline-container");

        // Barra de Progresso
        progressoContainer = new VBox();
        progressoContainer.getStyleClass().add("progresso-container");

        labelProgresso = new Label();
        labelProgresso.getStyleClass().add("label-progresso");

        barraProgresso = new ProgressBar();
        barraProgresso.getStyleClass().add("barra-progresso");

        StackPane barraContainer = new StackPane();
        barraContainer.getStyleClass().add("barra-container");
        barraContainer.getChildren().add(barraProgresso);

        porcentagemLabel = new Label();
        porcentagemLabel.getStyleClass().add("porcentagem-label");

        progressoContainer.getChildren().addAll(labelProgresso, barraContainer, porcentagemLabel);

        // Tempo Estimado
        tempoEstimado = new Label();
        tempoEstimado.getStyleClass().add("tempo-estimado");

        // Botões
        HBox botoesContainer = new HBox();
        botoesContainer.getStyleClass().add("botoes-container");

        Button btnVoltar = new Button("← Voltar ao Menu");
        btnVoltar.getStyleClass().add("btn-voltar");
        btnVoltar.setOnAction(e -> {
            pararProgressaoAutomatica();
            TelaProdutos telaProdutos = new TelaProdutos(stage, null);
            telaProdutos.mostrar();
        });

        Button btnAcelerar = new Button("⏩ Simular Progresso");
        btnAcelerar.getStyleClass().add("btn-acelerar");
        btnAcelerar.setOnAction(e -> avancarStatus());

        botoesContainer.getChildren().addAll(btnVoltar, btnAcelerar);

        // Montagem Final
        card.getChildren().addAll(titulo, numeroPedido, timelineContainer, progressoContainer, tempoEstimado, botoesContainer);
        containerPrincipal.getChildren().addAll(cabecalho, card);

        // Atualizar interface
        atualizarInterface();

        // Iniciar progressão automática
        iniciarProgressaoAutomatica();

        Scene scene = new Scene(containerPrincipal, 550, 700);
        scene.getStylesheets().add(getClass().getResource("/css/acompanharEntrega.css").toExternalForm());

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
            case "confirmado": novoStatus = "Preparando"; break;
            case "preparando": novoStatus = "A caminho"; break;
            case "a caminho": novoStatus = "Entregue"; break;
            case "entregue": pararProgressaoAutomatica(); return;
            default: novoStatus = "Confirmado";
        }

        pedido.setStatus(novoStatus);
        atualizarInterface();

        if (novoStatus.equalsIgnoreCase("Entregue")) {
            pararProgressaoAutomatica();
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

            Label icone = new Label(icones[i]);
            icone.getStyleClass().add("etapa-icone");

            Pane conector = new Pane();
            conector.getStyleClass().add("etapa-conector");
            if (i == etapas.length - 1) {
                conector.setVisible(false);
            }

            VBox conteudoEtapa = new VBox();
            conteudoEtapa.getStyleClass().add("etapa-conteudo");

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
            case "a caminho": return "Seu pedido está a caminho! 🚗";
            case "entregue": return "Entrega realizada com sucesso! 🎉";
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