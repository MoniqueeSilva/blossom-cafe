package com.blossomcafe.view;

import com.blossomcafe.controller.ClienteController;
import com.blossomcafe.model.Cliente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        Text logoTexto = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(220);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            logoTexto = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTexto.getStyleClass().add("logo-texto");
        }

        // Título
        Text titulo = new Text("Faça seu login");
        titulo.getStyleClass().add("titulo-login");

        // Campos de entrada
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("seu@email.com");
        campoEmail.getStyleClass().add("input-campo");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Sua senha");
        campoSenha.getStyleClass().add("input-campo");

        CheckBox checkLembrar = new CheckBox("Lembrar meus dados");
        checkLembrar.getStyleClass().add("checkbox");

        Hyperlink linkEsqueciSenha = new Hyperlink("Esqueci minha senha");
        linkEsqueciSenha.getStyleClass().add("link");

        Button btnEntrar = new Button("ENTRAR");
        btnEntrar.getStyleClass().add("btn-entrar");

        Text textoCadastro = new Text("Não tem uma conta?");
        textoCadastro.getStyleClass().add("texto-cadastro");

        Hyperlink linkCadastrar = new Hyperlink("Cadastre-se");
        linkCadastrar.getStyleClass().add("link");

        Button btnVoltar = new Button("← Voltar");
        btnVoltar.getStyleClass().add("btn-voltar");

        // Layout do formulário
        VBox layoutForm = new VBox(15);
        layoutForm.setAlignment(Pos.CENTER);
        layoutForm.setPadding(new Insets(30));
        layoutForm.getStyleClass().add("layout-form");

        if (logoView != null) {
            layoutForm.getChildren().add(logoView);
        } else {
            layoutForm.getChildren().add(logoTexto);
        }

        layoutForm.getChildren().addAll(
                titulo,
                new Label("E-mail:"), campoEmail,
                new Label("Senha:"), campoSenha,
                checkLembrar, linkEsqueciSenha,
                btnEntrar,
                textoCadastro, linkCadastrar,
                btnVoltar
        );

        VBox layoutPrincipal = new VBox();
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(40));
        layoutPrincipal.getChildren().add(layoutForm);

        // ================= EVENTOS =================
        ClienteController controller = new ClienteController();

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

            Cliente logado = controller.fazerLogin(email, senha);

            if (logado != null) {
                mostrarAlerta("Sucesso", "Login realizado com sucesso! 🌸");
                TelaProdutos telaProdutos = new TelaProdutos(stage);
                telaProdutos.mostrar();
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

        linkCadastrar.setOnAction(event -> {
            TelaCadastro telaCadastro = new TelaCadastro(stage);
            telaCadastro.mostrar();
        });

        // ================= SCENE ==================
        Scene scene = new Scene(layoutPrincipal, 500, 650);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
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
