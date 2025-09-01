package com.blossomcafe.view;

import com.blossomcafe.model.Pedido;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaAcompanharEntrega {
    private Stage stage;
    private Pedido pedido;

    public TelaAcompanharEntrega(Stage stage, Pedido pedido) {
        this.stage = stage;
        this.pedido = pedido;
    }

    public void mostrar() {
        stage.setTitle("Acompanhar Entrega");

        // ====================== Título ======================
        Label titulo = new Label("Acompanhar Entrega");
        titulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");

        // ====================== Status ======================
        Label statusLabel = new Label("Status do pedido: " + pedido.getStatus());
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4C2B0B;");

        // ====================== Barra de progresso ======================
        ProgressBar barraProgresso = new ProgressBar();
        barraProgresso.setPrefWidth(300);
        barraProgresso.setStyle("-fx-accent: #4C2B0B;");

        switch (pedido.getStatus()) {
            case "Preparando":
                barraProgresso.setProgress(0.33);
                break;
            case "A caminho":
                barraProgresso.setProgress(0.66);
                break;
            case "Entregue":
                barraProgresso.setProgress(1.0);
                break;
            default:
                barraProgresso.setProgress(0);
        }

        // ====================== Botão Voltar ======================
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: #4C2B0B; -fx-border-color: #4C2B0B; -fx-border-width: 1; -fx-border-radius: 5;");
        btnVoltar.setPrefWidth(100);
        btnVoltar.setOnAction(e -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage); // volta pra tela de produtos
            telaProdutos.mostrar();
        });

        // ====================== Layout principal ======================
        VBox layout = new VBox(20, titulo, statusLabel, barraProgresso, btnVoltar);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #EADED0;");

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

     public static void main(String[] args) {
        Application.launch(TelaEntregaApp.class, args);
    }

    public static class TelaEntregaApp extends Application {
        @Override
        public void start(Stage stage) {
            Pedido pedidoTeste = new Pedido(101);
            TelaAcompanharEntrega telaEntrega = new TelaAcompanharEntrega(stage, pedidoTeste);
            telaEntrega.mostrar();
        }
    }

}
