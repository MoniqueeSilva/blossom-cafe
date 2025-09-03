package com.blossomcafe.view;

import com.blossomcafe.controller.ClienteController;
import com.blossomcafe.model.Cliente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaEditarPerfil {
    private Stage stage;
    private Cliente cliente;

    public TelaEditarPerfil(Stage stage, Cliente cliente) {
        this.stage = stage;
        this.cliente = cliente;
    }

    public void mostrar() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField(cliente.getNome());

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField(cliente.getEmail());

        Label lblTelefone = new Label("Telefone:");
        TextField txtTelefone = new TextField(cliente.getTelefone());

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnSalvar.setOnAction(e -> {
            cliente.setNome(txtNome.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setTelefone(txtTelefone.getText());

            ClienteController controller = new ClienteController();
            boolean sucesso = controller.atualizarCliente(cliente);

            if (sucesso) {
                mostrarAlerta("Sucesso", "Perfil atualizado com sucesso!");
                // Volta para tela de perfil
                TelaPerfil telaPerfil = new TelaPerfil(stage, cliente);
                telaPerfil.mostrar();
            } else {
                mostrarAlerta("Erro", "Não foi possível atualizar o perfil.");
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: transparent; -fx-border-color: #4C2B0B; -fx-text-fill: #4C2B0B; -fx-padding: 10 20;");
        btnCancelar.setOnAction(e -> {
            TelaPerfil telaPerfil = new TelaPerfil(stage, cliente);
            telaPerfil.mostrar();
        });

        layout.getChildren().addAll(lblNome, txtNome, lblEmail, txtEmail, lblTelefone, txtTelefone, btnSalvar, btnCancelar);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Editar Perfil - " + cliente.getNome());
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
