package com.blossomcafe.view;

import com.blossomcafe.controller.ClienteController;
import com.blossomcafe.model.Cliente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TelaPerfil {

    private Stage stage;
    private Cliente cliente;
    private ClienteController clienteController;

    public TelaPerfil(Stage stage, Cliente cliente) {
        this.stage = stage;
        this.cliente = cliente;
        this.clienteController = new ClienteController();
    }

    public void mostrar() {
        stage.setTitle("Perfil do Cliente");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5DC;");

        // Topo com foto e nome
        HBox topPane = new HBox(15);
        topPane.setAlignment(Pos.CENTER_LEFT);
        topPane.setPadding(new Insets(20));

        ImageView fotoUsuario = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/images/usuario.png"));
            fotoUsuario.setImage(img);
            fotoUsuario.setFitWidth(80);
            fotoUsuario.setFitHeight(80);
            fotoUsuario.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Imagem do usuário não encontrada");
        }

        Label nomeUsuario = new Label(cliente.getNome());
        nomeUsuario.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #4E342E;");

        topPane.getChildren().addAll(fotoUsuario, nomeUsuario);
        root.setTop(topPane);

        // Tabs para seções
        TabPane tabPane = new TabPane();

        // --- Informações pessoais ---
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(15);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(20));

        TextField nomeField = criarCampo(cliente.getNome());
        TextField emailField = criarCampo(cliente.getEmail());
        TextField telefoneField = criarCampo(cliente.getTelefone());
        // TextField enderecoField = criarCampo(cliente.getEndereco());

        infoGrid.addRow(0, new Label("Nome:"), nomeField);
        infoGrid.addRow(1, new Label("Email:"), emailField);
        infoGrid.addRow(2, new Label("Telefone:"), telefoneField);
        // infoGrid.addRow(3, new Label("Endereço:"), enderecoField);

        Button btnEditar = criarBotao("Editar", "#8D6E63");
        Button btnSalvar = criarBotao("Salvar", "#4E342E");
        btnSalvar.setDisable(true);

        HBox hbBtns = new HBox(15, btnEditar, btnSalvar);
        hbBtns.setAlignment(Pos.CENTER);
        hbBtns.setPadding(new Insets(20, 0, 0, 0));

        VBox infoBox = new VBox(10, infoGrid, hbBtns);
        Tab tabInfo = new Tab("Informações", infoBox);
        tabInfo.setClosable(false);

        // --- Cupons ---
        ListView<String> listaCupons = new ListView<>();
        listaCupons.getItems().addAll("CUPOM10", "FRETEGRATIS", "DESCONTO5"); // Exemplo
        Tab tabCupons = new Tab("Cupons", listaCupons);
        tabCupons.setClosable(false);

        // --- Histórico de pedidos ---
        ListView<String> listaPedidos = new ListView<>();
        // Aqui você pode iterar pelos pedidos reais do cliente
        listaPedidos.getItems().addAll("Pedido #101 - Entregue", "Pedido #102 - A caminho");
        Tab tabHistorico = new Tab("Histórico", listaPedidos);
        tabHistorico.setClosable(false);

        tabPane.getTabs().addAll(tabInfo, tabCupons, tabHistorico);

        root.setCenter(tabPane);

        // Ações dos botões
        btnEditar.setOnAction(e -> {
            nomeField.setEditable(true);
            emailField.setEditable(true);
            telefoneField.setEditable(true);
            // enderecoField.setEditable(true);
            btnSalvar.setDisable(false);
            btnEditar.setDisable(true);
        });

        btnSalvar.setOnAction(e -> {
            cliente.setNome(nomeField.getText());
            cliente.setEmail(emailField.getText());
            cliente.setTelefone(telefoneField.getText());
            // cliente.setEndereco(enderecoField.getText());

            clienteController.atualizarCliente(cliente);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Perfil atualizado com sucesso!");
            alert.showAndWait();

            btnSalvar.setDisable(true);
            btnEditar.setDisable(false);
            nomeField.setEditable(false);
            emailField.setEditable(false);
            telefoneField.setEditable(false);
            // enderecoField.setEditable(false);
        });

        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private TextField criarCampo(String valor) {
        TextField campo = new TextField(valor);
        campo.setEditable(false);
        campo.setStyle("-fx-background-color: #FFF8E1; -fx-border-color: #A1887F; " +
                       "-fx-border-radius: 5; -fx-background-radius: 5;");
        return campo;
    }

    private Button criarBotao(String texto, String cor) {
        Button botao = new Button(texto);
        botao.setStyle("-fx-background-color: " + cor + "; -fx-text-fill: white; " +
                       "-fx-font-weight: bold; -fx-background-radius: 10;");
        botao.setPrefWidth(100);
        return botao;
    }

    // --- MAIN para teste direto ---
    public static void main(String[] args) {
        javafx.application.Application.launch(TelaPerfilApp.class, args);
    }

    public static class TelaPerfilApp extends javafx.application.Application {
        @Override
        public void start(Stage stage) {
            Cliente clienteTeste = new Cliente(1, "Monique", "11999999999", "monique@email.com", "12345678900", "1234");
            // clienteTeste.setEndereco("Rua das Flores, 123");
            TelaPerfil telaPerfil = new TelaPerfil(stage, clienteTeste);
            telaPerfil.mostrar();
        }
    }
}
