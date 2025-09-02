package com.blossomcafe.view;

import com.blossomcafe.controller.ClienteController;
import com.blossomcafe.model.Cliente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaLogin {
    private Stage stage;

    public TelaLogin(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        // LOGO
        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(220);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            Text logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #4C2B0B;");
        }

        // Título
        Text titulo = new Text("Faça seu login");
        titulo.setStyle("-fx-font-size: 18px; -fx-fill: #4C2B0B;");

        // Campos de entrada
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("seu@email.com");
        campoEmail.setStyle("-fx-padding: 8; -fx-background-radius: 5; -fx-border-radius: 5;");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Sua senha");
        campoSenha.setStyle("-fx-padding: 8; -fx-background-radius: 5; -fx-border-radius: 5;");

        Hyperlink linkEsqueciSenha = new Hyperlink("Esqueci minha senha");
        linkEsqueciSenha.setStyle("-fx-text-fill: #4C2B0B;");

        Button btnEntrar = new Button("ENTRAR");
        btnEntrar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-background-radius: 5;");

        Text textoCadastro = new Text("Não tem uma conta?");
        textoCadastro.setStyle("-fx-fill: #4C2B0B;");

        Hyperlink linkCadastrar = new Hyperlink("Cadastre-se");
        linkCadastrar.setStyle("-fx-text-fill: #4C2B0B;");

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: #4C2B0B; -fx-border-color: #4C2B0B; -fx-border-width: 1; -fx-border-radius: 5;");

        // Layout do formulário
        VBox layoutForm = new VBox(15);
        layoutForm.setAlignment(Pos.CENTER);
        layoutForm.setPadding(new Insets(30));
        layoutForm.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 10;");

        if (logoView != null) {
            layoutForm.getChildren().add(logoView);
        }

        layoutForm.getChildren().addAll(
                titulo,
                new Label("E-mail:"), campoEmail,
                new Label("Senha:"), campoSenha,
                linkEsqueciSenha,
                btnEntrar,
                textoCadastro, linkCadastrar,
                btnVoltar
        );

        VBox layoutPrincipal = new VBox();
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(40));
        layoutPrincipal.setStyle("-fx-background-color: #EADED0;");
        layoutPrincipal.getChildren().add(layoutForm);

        // ================= EVENTOS =================
        ClienteController controller = new ClienteController();

        btnEntrar.setOnAction(event -> {
            String email = campoEmail.getText().trim();
            String senha = campoSenha.getText().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                mostrarAlerta("❌ Erro", "Por favor, preencha todos os campos.");
                return;
            }

            if (!email.contains("@")) {
                mostrarAlerta("❌ Erro", "Por favor, insira um e-mail válido.");
                return;
            }

            try {
                Cliente logado = controller.fazerLogin(email, senha);

                if (logado != null) {
                    mostrarAlerta("✅ Sucesso", "Login realizado com sucesso! 🌸\n\nBem-vindo(a) " + logado.getNome() + "!");
                    TelaProdutos telaProdutos = new TelaProdutos(stage);
                    telaProdutos.mostrar();
                } else {
                    mostrarAlerta("❌ Erro", "E-mail ou senha incorretos.");
                }
            } catch (IllegalArgumentException e) {
                mostrarAlerta("❌ Erro", e.getMessage());
            }
        });

        btnVoltar.setOnAction(event -> {
            TelaInicial telaInicial = new TelaInicial(stage);
            telaInicial.mostrar();
        });

        linkEsqueciSenha.setOnAction(event -> {
            mostrarAlerta("📧 Recuperar Senha", "Em breve você poderá recuperar sua senha por aqui!");
        });

        linkCadastrar.setOnAction(event -> {
            TelaCadastro telaCadastro = new TelaCadastro(stage);
            telaCadastro.mostrar();
        });

        // ================= SCENE ==================
        Scene scene = new Scene(layoutPrincipal, 500, 600);
        stage.setTitle("Blossom Café - Login");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}