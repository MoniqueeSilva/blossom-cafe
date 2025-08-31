package com.blossomcafe.app;

import com.blossomcafe.view.TelaInicial;
import com.blossomcafe.model.Cafe;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    public void start(Stage primaryStage) {
        // Mostra a tela inicial
        TelaInicial telaInicial = new TelaInicial(primaryStage);
        telaInicial.mostrar();
    }

    public static void main(String[] args) {
        launch(args);  // Inicializa o JavaFX
        
        // Exemplo de criação de café (opcional, só pra teste)
        Cafe cafe1 = new Cafe(1, "Café Expresso", 7.50, true, "Expresso");
        System.out.println("Café criado: " + cafe1.getNome());
    }
}
