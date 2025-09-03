package com.blossomcafe.view;

import com.blossomcafe.controller.ClienteController;
import com.blossomcafe.model.Cliente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private ClienteController clienteController;

    public TelaCadastro(Stage stage) {
        this.stage = stage;
        this.clienteController = new ClienteController();
    }

    public void mostrar() {
        //LOGO
        ImageView logoView = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo-blossom.jpeg"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(200);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
        } catch (Exception e) {
            Text logoTxt = new Text("🌺 BLOSSOM CAFÉ 🌸");
            logoTxt.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            logoTxt.getStyleClass().add("logo-texto");
        }

        // TÍTULO
        Text titulo = new Text("Criar Nova Conta");
        titulo.getStyleClass().add("titulo");

        // CAMPOS DO FORMULÁRIO
        Label labelNome = new Label("Nome completo:");
        TextField campoNome = new TextField();
        campoNome.setPromptText("Seu nome completo");
        campoNome.getStyleClass().add("campo-texto");

        Label labelEmail = new Label("E-mail:");
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("seu@email.com");
        campoEmail.getStyleClass().add("campo-texto");

        Label labelTelefone = new Label("Telefone:");
        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("(83) 99999-8888");
        campoTelefone.getStyleClass().add("campo-texto");

        Label labelCPF = new Label("CPF:");
        TextField campoCPF = new TextField();
        campoCPF.setPromptText("123.456.789-00");
        campoCPF.getStyleClass().add("campo-texto");

        Label labelSenha = new Label("Senha:");
        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Mínimo 4 caracteres");
        campoSenha.getStyleClass().add("campo-texto");

        Label labelConfirmarSenha = new Label("Confirmar senha:");
        PasswordField campoConfirmarSenha = new PasswordField();
        campoConfirmarSenha.setPromptText("Digite a senha novamente");
        campoConfirmarSenha.getStyleClass().add("campo-texto");

        // BOTÕES
        Button btnCadastrar = new Button("✅ Criar Conta");
        btnCadastrar.getStyleClass().add("btn-primario");

        Button btnVoltar = new Button("← Voltar para Login");
        btnVoltar.getStyleClass().add("btn-secundario");

        // LAYOUT
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setPadding(new Insets(20));
        formulario.getStyleClass().add("formulario");

        // Adiciona componentes ao grid
        formulario.add(labelNome, 0, 0);
        formulario.add(campoNome, 1, 0);
        formulario.add(labelEmail, 0, 1);
        formulario.add(campoEmail, 1, 1);
        formulario.add(labelTelefone, 0, 2);
        formulario.add(campoTelefone, 1, 2);
        formulario.add(labelCPF, 0, 3);
        formulario.add(campoCPF, 1, 3);
        formulario.add(labelSenha, 0, 4);
        formulario.add(campoSenha, 1, 4);
        formulario.add(labelConfirmarSenha, 0, 5);
        formulario.add(campoConfirmarSenha, 1, 5);
        formulario.add(btnCadastrar, 1, 6);
        formulario.add(btnVoltar, 1, 7);

        VBox layoutPrincipal = new VBox(15);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.getStyleClass().add("layout-principal");

        if (logoView != null) {
            layoutPrincipal.getChildren().add(logoView);
        }

        layoutPrincipal.getChildren().addAll(titulo, formulario);

        // EVENTOS
        btnCadastrar.setOnAction(event -> {
            if (validarCampos(campoNome, campoEmail, campoTelefone, campoCPF, campoSenha, campoConfirmarSenha)) {
                try {
                    boolean cadastrado = clienteController.cadastrarCliente(
                        campoNome.getText().trim(),
                        campoTelefone.getText().trim(),
                        campoEmail.getText().trim(),
                        campoCPF.getText().trim(),
                        campoSenha.getText()
                    );

                    if (cadastrado) {
                        Cliente clienteLogado = clienteController.fazerLogin(
                            campoEmail.getText().trim(),
                            campoSenha.getText()
                        );

                        if (clienteLogado != null) {
                            mostrarAlerta("✅ Sucesso", "Conta criada e login realizado com sucesso!");
                            TelaProdutos telaProdutos = new TelaProdutos(stage, clienteLogado);
                            telaProdutos.mostrar();
                        } else {
                            mostrarAlerta("⚠️ Aviso", "Conta criada, mas não foi possível fazer login automaticamente.");
                            TelaLogin telaLogin = new TelaLogin(stage);
                            telaLogin.mostrar();
                        }
                    } else {
                        mostrarAlerta("❌ Erro", "Não foi possível criar a conta. Tente novamente.");
                    }
                } catch (Exception e) {
                    mostrarAlerta("❌ Erro", "Erro: " + e.getMessage());
                }
            }
        });

        btnVoltar.setOnAction(event -> {
            TelaLogin telaLogin = new TelaLogin(stage);
            telaLogin.mostrar();
        });

        //EXIBIR TELA 
        Scene scene = new Scene(layoutPrincipal, 500, 650);
        scene.getStylesheets().add(getClass().getResource("/css/cadastro.css").toExternalForm());

        stage.setTitle("Blossom Café - Cadastro");
        stage.setScene(scene);
        stage.show();
    }

    // MÉTODOS AUXILIARES
    private boolean validarCampos(TextField nome, TextField email, TextField telefone,
                                 TextField cpf, PasswordField senha, PasswordField confirmarSenha) {
        if (nome.getText().trim().isEmpty()) {
            mostrarAlerta("❌ Erro", "Por favor, digite seu nome completo.");
            return false;
        }

        if (email.getText().trim().isEmpty() || !email.getText().contains("@")) {
            mostrarAlerta("❌ Erro", "Por favor, digite um e-mail válido.");
            return false;
        }

        if (telefone.getText().trim().isEmpty()) {
            mostrarAlerta("❌ Erro", "Por favor, digite seu telefone.");
            return false;
        }

        if (cpf.getText().trim().isEmpty()) {
            mostrarAlerta("❌ Erro", "Por favor, digite seu CPF.");
            return false;
        }

        if (senha.getText().length() < 4) {
            mostrarAlerta("❌ Erro", "A senha deve ter pelo menos 4 caracteres.");
            return false;
        }

        if (!senha.getText().equals(confirmarSenha.getText())) {
            mostrarAlerta("❌ Erro", "As senhas não coincidem.");
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}

