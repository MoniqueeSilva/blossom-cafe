package com.blossomcafe.app;

import com.blossomcafe.view.TelaInicial;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    
public void start(Stage primaryStage) {
    // Em vez de mostrar a tela principal diretamente,
    // mostre a tela inicial
    TelaInicial telaInicial = new TelaInicial(primaryStage);
    telaInicial.mostrar();
}
    
    // MÉTODO MAIN CORRETO - não mude esta assinatura!
    public static void main(String[] args) {
        launch(args);
    }
}