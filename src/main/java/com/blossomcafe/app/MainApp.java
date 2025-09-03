package com.blossomcafe.app;

import com.blossomcafe.view.TelaInicial;

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
    }
}