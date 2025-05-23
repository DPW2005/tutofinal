package org.example.demofinal.medecin;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.demofinal.User;
import java.util.ArrayList ;

public class ConsultationInterface extends Application {

    VBox textArea = new VBox() ;
    TextArea diagnosticArea = new TextArea() ;
    TextField message = new TextField() ;
    Button envoyerBtn = new Button("Envoyer");
    Button diagnosticButton = new Button("Envoyer diagnostic") ;
    String patientName ;

    @Override
    public void start(Stage stage) {
        stage.setTitle("MEDECIN");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        VBox consultation = new VBox(10) ;
        consultation.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(textArea) ;
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);

        HBox inputbox = new HBox(5,message, envoyerBtn) ;
        // Label principal
        Label demandeLabel = new Label("CONSULTATION DU PATIENT "+patientName) ;
        Label diagnosticLabel = new Label("DIAGNOSTIC DU PATIENT "+patientName) ;

        textArea.setPrefHeight(200);

        diagnosticArea.setWrapText(true);
        diagnosticArea.setPrefHeight(300);

        envoyerBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        root.getChildren().addAll(demandeLabel, scrollPane, inputbox) ;
        consultation.getChildren().addAll(diagnosticLabel, diagnosticArea, diagnosticButton) ;
        // TabPane
        TabPane tabPane = new TabPane();
        Tab discussionTab = new Tab("CONSULTATION", root);
        discussionTab.setClosable(false);
        Tab diagnosticTab = new Tab("DIAGNOSTIC", consultation);
        diagnosticTab.setClosable(false);

        tabPane.getTabs().addAll(discussionTab, diagnosticTab);

        // Scene
        Scene scene = new Scene(tabPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

