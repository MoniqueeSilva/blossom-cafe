package com.blossomcafe.view;

import com.blossomcafe.model.Cliente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TelaPerfil {
    private Stage stage;
    private Cliente cliente;

    public TelaPerfil(Stage stage, Cliente cliente) {
        this.stage = stage;
        this.cliente = cliente;
    }

    public void mostrar() {
        // Fundo gradiente
        BackgroundFill gradientFill = new BackgroundFill(
            Color.web("#EADED0"), CornerRadii.EMPTY, Insets.EMPTY
        );
        Background background = new Background(gradientFill);

        // Topo com foto do usuário
        VBox topo = new VBox(10);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(20));

        // Foto do usuário (círculo)
        ImageView fotoUsuario = null;
        try {
            Image userImage = new Image(getClass().getResource("/images/user-default.png").toString());
            fotoUsuario = new ImageView(userImage);
            fotoUsuario.setFitHeight(100);
            fotoUsuario.setFitWidth(100);
            
            Circle clip = new Circle(50, 50, 50);
            fotoUsuario.setClip(clip);
        } catch (Exception e) {
            System.err.println("Imagem padrão não encontrada, usando placeholder");
            // Placeholder circular
            Circle placeholder = new Circle(50, Color.LIGHTGRAY);
            fotoUsuario = new ImageView();
        }

        Label nomeUsuario = new Label(cliente.getNome());
        nomeUsuario.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");

        Label emailUsuario = new Label(cliente.getEmail());
        emailUsuario.setStyle("-fx-font-size: 14px; -fx-text-fill: #6B4C35;");

        VBox headerInfo = new VBox(5, nomeUsuario, emailUsuario);
        headerInfo.setAlignment(Pos.CENTER);

        VBox header = new VBox(15, fotoUsuario, headerInfo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30));
        header.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 15;");

        // Seções de informações
        VBox secoes = new VBox(10);
        secoes.setPadding(new Insets(20));
        secoes.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        secoes.getChildren().addAll(
            criarItemPerfil("📍 Endereços", "Gerir seus endereços de entrega"),
            criarItemPerfil("🎟️ Cupons", "Cupons e descontos disponíveis"),
            criarItemPerfil("🛒 Histórico", "Seus pedidos anteriores"),
            criarItemPerfil("⚙️ Configurações", "Preferências da conta")
        );

        // Botões de ação
        Button btnEditar = new Button("Editar Perfil");
        btnEditar.setStyle("-fx-background-color: #4C2B0B; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8 20;");
        
        Button btnVoltar = new Button("Voltar ao Menu");
        btnVoltar.setStyle("-fx-background-color: #CCB9A3; -fx-text-fill: #4C2B0B; -fx-background-radius: 20;");
        btnVoltar.setOnAction(e -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage);
            telaProdutos.mostrar();
        });

        VBox botoes = new VBox(10, btnEditar, btnVoltar);
        botoes.setAlignment(Pos.CENTER);
        botoes.setPadding(new Insets(20, 0, 10, 0));

        // Layout final
        VBox layoutPrincipal = new VBox(20, header, secoes, botoes);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setBackground(background);

        Scene scene = new Scene(layoutPrincipal, 400, 600);
        
        // Carregar CSS
        try {
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("CSS não carregado: " + e.getMessage());
        }
        
        stage.setTitle("Perfil - " + cliente.getNome());
        stage.setScene(scene);
        stage.show();
    }

    private HBox criarItemPerfil(String titulo, String descricao) {
        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4C2B0B;");
        
        Label lblDesc = new Label(descricao);
        lblDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #6B4C35;");
        
        VBox textos = new VBox(2, lblTitulo, lblDesc);
        
        HBox item = new HBox(textos);
        item.setPadding(new Insets(15));
        item.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 10; -fx-cursor: hand;");
        item.setOnMouseEntered(e -> item.setStyle("-fx-background-color: #EADED0; -fx-background-radius: 10; -fx-cursor: hand;"));
        item.setOnMouseExited(e -> item.setStyle("-fx-background-color: #F8F2EA; -fx-background-radius: 10; -fx-cursor: hand;"));
        
        return item;
    }
    public static void main(String[] args) {
        javafx.application.Application.launch(TelaPerfilApp.class, args);
    }

    public static class TelaPerfilApp extends javafx.application.Application {
        public void start(Stage stage) {
            Cliente clienteTeste = new Cliente(1, "Monique", "11999999999", "monique@email.com", "12345678900", "1234");
            // clienteTeste.setEndereco("Rua das Flores, 123");
            TelaPerfil telaPerfil = new TelaPerfil(stage, clienteTeste);
            telaPerfil.mostrar();
        }
    }
}