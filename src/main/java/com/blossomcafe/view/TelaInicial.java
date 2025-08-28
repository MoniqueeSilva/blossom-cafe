package com.blossomcafe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaInicial {
    private Stage stage;

    public TelaInicial(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        // LOGO COM IMAGEM
        ImageView logoView = null;
        
        try {
            // Carrega a imagem da pasta resources
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(270);  // Ajuste o tamanho conforme needed
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            // Fallback para texto se a imagem não carregar
            Text logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.setFont(Font.font("Arial", FontWeight.BOLD, 36));
            logoTexto.setStyle("-fx-fill: #4C2B0B;");
            logoView = new ImageView();
            // Você pode adicionar o texto como fallback
        }

        Text slogan = new Text("Entre pétalas e café, nasce o aconchego.");
        slogan.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        slogan.setStyle("-fx-fill: #4C2B0B;");

        // BOTÕES
        Button btnEntrar = new Button("ENTRAR");
        btnEntrar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
        btnEntrar.setPrefWidth(200);
        btnEntrar.setPrefHeight(45);

        Hyperlink linkPedirSemLogin = new Hyperlink("Pedir sem logar");
        linkPedirSemLogin.setStyle("-fx-text-fill: #4C2B0B; -fx-border-color: transparent; -fx-underline: true;");

        // ==================== LAYOUT ====================
        VBox layout = new VBox(28);  // Aumentei o espaçamento
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #EADED0;");

        // Adiciona os componentes
        if (logoView != null) {
            layout.getChildren().add(logoView);
        }
        layout.getChildren().addAll(slogan, btnEntrar, linkPedirSemLogin);

        // ==================== EVENTOS ====================
        btnEntrar.setOnAction(event -> {
            TelaLogin telaLogin = new TelaLogin(stage);
            telaLogin.mostrar();
        });

        linkPedirSemLogin.setOnAction(event -> {
            
        });

        // ==================== EXECUÇÃO ====================
        Scene scene = new Scene(layout, 450, 550);  // Aumentei o tamanho
        stage.setTitle("Blossom Café");
        stage.setScene(scene);
        stage.show();
    }
}