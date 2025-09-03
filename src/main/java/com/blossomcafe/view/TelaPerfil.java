package com.blossomcafe.view;

import com.blossomcafe.model.Cliente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        // Fundo
        BackgroundFill gradientFill = new BackgroundFill(
            Color.web("#EADED0"), CornerRadii.EMPTY, Insets.EMPTY
        );
        Background background = new Background(gradientFill);

        // Topo com foto
        VBox topo = new VBox(10);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(20));

        ImageView fotoUsuario = null;
        try {
            Image userImage = new Image(getClass().getResource("/images/user-default.png").toString());
            fotoUsuario = new ImageView(userImage);
            fotoUsuario.setFitHeight(100);
            fotoUsuario.setFitWidth(100);
            Circle clip = new Circle(50, 50, 50);
            fotoUsuario.setClip(clip);
        } catch (Exception e) {
            fotoUsuario = new ImageView();
        }

        Label nomeUsuario = new Label(cliente.getNome());
        nomeUsuario.getStyleClass().add("nome-usuario");

        Label emailUsuario = new Label(cliente.getEmail());
        emailUsuario.getStyleClass().add("email-usuario");

        VBox headerInfo = new VBox(5, nomeUsuario, emailUsuario);
        headerInfo.setAlignment(Pos.CENTER);

        VBox header = new VBox(15, fotoUsuario, headerInfo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30));
        header.getStyleClass().add("header");

        // Seções
        VBox secoes = new VBox(10);
        secoes.setPadding(new Insets(20));
        secoes.getStyleClass().add("secoes");

        secoes.getChildren().addAll(
            criarItemPerfil("📍 Endereços", "Gerir seus endereços de entrega"),
            criarItemPerfil("🎟️ Cupons", "Cupons e descontos disponíveis"),
            criarItemPerfil("🛒 Histórico", "Seus pedidos anteriores"),
            criarItemPerfil("⚙️ Configurações", "Preferências da conta")
        );

        // Botões
        Button btnEditar = new Button("Editar Perfil");
        btnEditar.getStyleClass().add("btn-primario");
        btnEditar.setOnAction(e -> {
            TelaEditarPerfil telaEditar = new TelaEditarPerfil(stage, cliente);
            telaEditar.mostrar();
        });


        Button btnVoltar = new Button("Voltar ao Menu");
        btnVoltar.getStyleClass().add("btn-secundario");
        btnVoltar.setOnAction(e -> {
            TelaProdutos telaProdutos = new TelaProdutos(stage, cliente);
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
        scene.getStylesheets().add(getClass().getResource("/css/perfil.css").toExternalForm());

        stage.setTitle("Perfil - " + cliente.getNome());
        stage.setScene(scene);
        stage.show();
    }

    private HBox criarItemPerfil(String titulo, String descricao) {
        Label lblTitulo = new Label(titulo);
        lblTitulo.getStyleClass().add("item-titulo");

        Label lblDesc = new Label(descricao);
        lblDesc.getStyleClass().add("item-desc");

        VBox textos = new VBox(2, lblTitulo, lblDesc);

        HBox item = new HBox(textos);
        item.setPadding(new Insets(15));
        item.getStyleClass().add("item-perfil");

        return item;
    }
}
