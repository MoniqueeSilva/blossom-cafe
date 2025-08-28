package com.blossomcafe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaLogin {
    private Stage stage;

    public TelaLogin(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        // ==================== LOGO ====================
        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(220);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            Text logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.setFont(Font.font("Arial", FontWeight.BOLD, 28));
            logoTexto.setStyle("-fx-fill: #4C2B0B;");
        }

        // ==================== FORMULÁRIO ====================
        Text titulo = new Text("Faça seu login");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-fill: #4C2B0B;");

        // Campos de entrada
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("seu@email.com");
        campoEmail.setPrefWidth(250);
        campoEmail.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #D3B8A5; -fx-background-color: #FFFFFF;");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Sua senha");
        campoSenha.setPrefWidth(250);
        campoSenha.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #D3B8A5; -fx-background-color: #FFFFFF;");

        CheckBox checkLembrar = new CheckBox("Lembrar meus dados");
        checkLembrar.setStyle("-fx-text-fill: #4C2B0B;");

        Hyperlink linkEsqueciSenha = new Hyperlink("Esqueci minha senha");
        linkEsqueciSenha.setStyle("-fx-text-fill: #4C2B0B; -fx-border-color: transparent;");

        Button btnEntrar = new Button("ENTRAR");
        btnEntrar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 12;");
        btnEntrar.setPrefWidth(250);

        Text textoCadastro = new Text("Não tem uma conta?");
        textoCadastro.setStyle("-fx=fill: #4C2B0B; -fx-font-size: 12px;");

        Hyperlink linkCadastrar = new Hyperlink("Cadastre-se");
        linkCadastrar.setStyle("-fx-text-fill: #4C2B0B; -fx-font-weight: bold; -fx-border-color: transparent;");

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: #4C2B0B; -fx-border-color: #4C2B0B; -fx-border-width: 1; -fx-border-radius: 5;");
        btnVoltar.setPrefWidth(100);

        // ==================== LAYOUT ====================
        VBox layoutForm = new VBox(15);
        layoutForm.setAlignment(Pos.CENTER);
        layoutForm.setPadding(new Insets(30));
        layoutForm.setStyle("-fx-background-color: #EADED0; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #D3B8A5; -fx-border-width: 1;");

        if (logoView != null) {
            layoutForm.getChildren().add(logoView);
        }
        
        layoutForm.getChildren().addAll(
            titulo,
            new Label("E-mail:"),
            campoEmail,
            new Label("Senha:"),
            campoSenha,
            checkLembrar,
            linkEsqueciSenha,
            btnEntrar,
            textoCadastro,
            linkCadastrar,
            btnVoltar
        );

        VBox layoutPrincipal = new VBox();
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(40));
        layoutPrincipal.setStyle("-fx-background-color: #EADED0;"); // MESMA COR DA TELA INICIAL!
        layoutPrincipal.getChildren().add(layoutForm);

        // ==================== EVENTOS ====================
        btnEntrar.setOnAction(event -> {
            String email = campoEmail.getText().trim();
            String senha = campoSenha.getText().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                mostrarAlerta("Erro", "Por favor, preencha todos os campos.");
                return;
            }

            if (!email.contains("@")) {
                mostrarAlerta("Erro", "Por favor, insira um e-mail válido.");
                return;
            }

            // Simulação de login
            System.out.println("Tentando login: " + email);
            
            if (email.equals("admin@blossom.com") && senha.equals("1234")) {
                mostrarAlerta("Sucesso", "Login realizado com sucesso! 🌸");
                // new TelaPrincipal(stage).mostrar();
            } else {
                mostrarAlerta("Erro", "E-mail ou senha incorretos.");
            }
        });

        btnVoltar.setOnAction(event -> {
            TelaInicial telaInicial = new TelaInicial(stage);
            telaInicial.mostrar();
        });

        linkEsqueciSenha.setOnAction(event -> {
            mostrarAlerta("Recuperar Senha", "Em breve você poderá recuperar sua senha por aqui! 📧");
        });

        // ==================== EXECUÇÃO ====================
        Scene scene = new Scene(layoutPrincipal, 500, 650);
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