package com.blossomcafe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
        Text logoTexto = null;
        
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(270);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.getStyleClass().add("logo-texto");
        }

        Text slogan = new Text("Entre pétalas e café, nasce o aconchego.");
        slogan.getStyleClass().add("slogan");

        // BOTÕES
        Button btnEntrar = new Button("ENTRAR");
        btnEntrar.getStyleClass().add("btn-entrar");

        Hyperlink linkPedirSemLogin = new Hyperlink("Pedir sem logar");
        linkPedirSemLogin.getStyleClass().add("link-sem-login");

        //pedir sem logar. vai jogar ditero para a tela de menu de produtos
        linkPedirSemLogin.setOnAction(event -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage);
            telaProdutos.mostrar();
        });

        // LAYOUT
        VBox layout = new VBox(28);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        if (logoView != null) {
            layout.getChildren().add(logoView);
        } else {
            layout.getChildren().add(logoTexto);
        }
        layout.getChildren().addAll(slogan, btnEntrar, linkPedirSemLogin);

        // EVENTOS
        btnEntrar.setOnAction(event -> {
            TelaLogin telaLogin = new TelaLogin(stage);
            telaLogin.mostrar();
        });

        // SCENE
        Scene scene = new Scene(layout, 450, 550);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Blossom Café");
        stage.setScene(scene);
        stage.show();
    }
}
