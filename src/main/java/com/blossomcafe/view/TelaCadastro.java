package com.blossomcafe.view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaCadastro {
    private Stage stage;

    public TelaCadastro(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        // ==================== LOGO ====================
        ImageView logoView = null;
        try {
            // ⚡ MESMA LOGO DA TELA DE LOGIN
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(200);  // Tamanho um pouco menor para o cadastro
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            // Fallback para texto se a imagem não carregar
            Text logoTxt = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTxt.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            logoTxt.setStyle("-fx-fill: #4C2B0B;");
        }

        // ==================== TÍTULO ====================
        Text titulo = new Text("Criar Nova Conta");
        titulo.setFont(Font.font("Arial", 18));
        titulo.setStyle("-fx-fill: #4C2B0B;");

        // ==================== CAMPOS DO FORMULÁRIO ====================
        Label labelNome = new Label("Nome completo:");
        TextField campoNome = new TextField();
        campoNome.setPromptText("Seu nome completo");
        campoNome.setPrefWidth(250);

        Label labelEmail = new Label("E-mail:");
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("seu@email.com");
        campoEmail.setPrefWidth(250);

        Label labelTelefone = new Label("Telefone:");
        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("(83) 99999-8888");
        campoTelefone.setPrefWidth(250);

        Label labelCPF = new Label("CPF:");
        TextField campoCPF = new TextField();
        campoCPF.setPromptText("123.456.789-00");
        campoCPF.setPrefWidth(250);

        Label labelEndereco = new Label("Endereço:");
        TextArea campoEndereco = new TextArea();
        campoEndereco.setPromptText("Rua, número, bairro, cidade");
        campoEndereco.setPrefWidth(250);
        campoEndereco.setPrefHeight(60);
        campoEndereco.setWrapText(true);

        Label labelSenha = new Label("Senha:");
        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Mínimo 4 caracteres");
        campoSenha.setPrefWidth(250);

        Label labelConfirmarSenha = new Label("Confirmar senha:");
        PasswordField campoConfirmarSenha = new PasswordField();
        campoConfirmarSenha.setPromptText("Digite a senha novamente");
        campoConfirmarSenha.setPrefWidth(250);

        // ==================== BOTÕES ====================
        Button btnCadastrar = new Button("✅ Criar Conta");
        btnCadastrar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        btnCadastrar.setPrefWidth(250);

        Button btnVoltar = new Button("← Voltar para Login");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-text-fill: #4C2B0B; -fx-border-color: #4C2B0B; -fx-border-width: 1;");
        btnVoltar.setPrefWidth(250);

        // ==================== LAYOUT ====================
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setPadding(new Insets(20));
        formulario.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 10;");

        // Adiciona componentes ao grid
        formulario.add(labelNome, 0, 0);
        formulario.add(campoNome, 1, 0);
        formulario.add(labelEmail, 0, 1);
        formulario.add(campoEmail, 1, 1);
        formulario.add(labelTelefone, 0, 2);
        formulario.add(campoTelefone, 1, 2);
        formulario.add(labelCPF, 0, 3);
        formulario.add(campoCPF, 1, 3);
        formulario.add(labelEndereco, 0, 4);
        formulario.add(campoEndereco, 1, 4);
        formulario.add(labelSenha, 0, 5);
        formulario.add(campoSenha, 1, 5);
        formulario.add(labelConfirmarSenha, 0, 6);
        formulario.add(campoConfirmarSenha, 1, 6);
        formulario.add(btnCadastrar, 1, 7);
        formulario.add(btnVoltar, 1, 8);

        VBox layoutPrincipal = new VBox(15);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setStyle("-fx-background-color: #EADED0;");
        
        // ⚡ ADICIONA A LOGO NO TOPO
        if (logoView != null) {
            layoutPrincipal.getChildren().add(logoView);
        }
        
        layoutPrincipal.getChildren().addAll(titulo, formulario);

        // ==================== EVENTOS ====================
        btnCadastrar.setOnAction(event -> {
            if (validarCampos(campoNome, campoEmail, campoTelefone, campoCPF, campoEndereco, campoSenha, campoConfirmarSenha)) {
                String mensagem = "Cadastro simulado:\n" +
                                 "Nome: " + campoNome.getText() + "\n" +
                                 "Email: " + campoEmail.getText() + "\n" +
                                 "Telefone: " + campoTelefone.getText() + "\n" +
                                 "CPF: " + campoCPF.getText() + "\n" +
                                 "Endereço: " + campoEndereco.getText();
                
                mostrarAlerta("✅ Cadastro Realizado", mensagem);
                limparCampos(campoNome, campoEmail, campoTelefone, campoCPF, campoEndereco, campoSenha, campoConfirmarSenha);
            }
        });

        btnVoltar.setOnAction(event -> {
            TelaLogin telaLogin = new TelaLogin(stage);
            telaLogin.mostrar();
        });

        // ==================== EXIBIR TELA ====================
        Scene scene = new Scene(layoutPrincipal, 500, 800); // Aumentei a altura para caber a logo
        stage.setTitle("Blossom Café - Cadastro");
        stage.setScene(scene);
        stage.show();
    }

    // ==================== MÉTODOS AUXILIARES ====================
    private boolean validarCampos(TextField nome, TextField email, TextField telefone,
                                 TextField cpf, TextArea endereco, 
                                 PasswordField senha, PasswordField confirmarSenha) {
        
        if (nome.getText().trim().isEmpty()) {
            mostrarAlerta("Erro", "Por favor, digite seu nome completo.");
            return false;
        }

        if (email.getText().trim().isEmpty() || !email.getText().contains("@")) {
            mostrarAlerta("Erro", "Por favor, digite um e-mail válido.");
            return false;
        }

        if (telefone.getText().trim().isEmpty()) {
            mostrarAlerta("Erro", "Por favor, digite seu telefone.");
            return false;
        }

        if (cpf.getText().trim().isEmpty()) {
            mostrarAlerta("Erro", "Por favor, digite seu CPF.");
            return false;
        }

        if (endereco.getText().trim().isEmpty()) {
            mostrarAlerta("Erro", "Por favor, digite seu endereço.");
            return false;
        }

        if (senha.getText().length() < 4) {
            mostrarAlerta("Erro", "A senha deve ter pelo menos 4 caracteres.");
            return false;
        }

        if (!senha.getText().equals(confirmarSenha.getText())) {
            mostrarAlerta("Erro", "As senhas não coincidem.");
            return false;
        }

        return true;
    }

    private void limparCampos(TextField nome, TextField email, TextField telefone,
                             TextField cpf, TextArea endereco, 
                             PasswordField senha, PasswordField confirmarSenha) {
        nome.clear();
        email.clear();
        telefone.clear();
        cpf.clear();
        endereco.clear();
        senha.clear();
        confirmarSenha.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
