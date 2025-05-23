package org.example.demofinal.patient;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ConsultationInterface extends Application {

    VBox textArea = new VBox() ;
    TextArea diagnosticArea = new TextArea() ;
    TextField message = new TextField() ;
    Button envoyerBtn = new Button("Envoyer");
    String patientName ;
    List<CheckBox> listeCheckBox = new ArrayList<>() ;
    Button symptomeButton = new Button("Envoyer Symptomes") ;

    @Override
    public void start(Stage stage) {
        stage.setTitle("PATIENT");

        String[] listeSymptome = {"Fievre","Toux","Fatigue","Maux de tete","Nausees"} ;


        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        VBox symptomes = new VBox(10) ;
        symptomes.setPadding(new Insets(10));

        VBox consultation = new VBox(10) ;
        consultation.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(textArea) ;
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);

        for(String s : listeSymptome){
            CheckBox cb = new CheckBox(s) ;
            listeCheckBox.add(cb) ;
            symptomes.getChildren().add(cb) ;
        }
        symptomeButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        symptomes.getChildren().add(symptomeButton) ;

        HBox inputbox = new HBox(5,message, envoyerBtn) ;
        // Label principal
        Label demandeLabel = new Label("CONSULTATION DU PATIENT "+patientName) ;
        Label diagnosticLabel = new Label("DIAGNOSTIC DU PATIENT "+patientName) ;

        textArea.setPrefHeight(200);

        diagnosticArea.setEditable(false);
        diagnosticArea.setWrapText(true);
        diagnosticArea.setPrefHeight(300);

        envoyerBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        root.getChildren().addAll(demandeLabel, scrollPane, inputbox) ;
        consultation.getChildren().addAll(diagnosticLabel, diagnosticArea) ;
        // TabPane
        TabPane tabPane = new TabPane();
        Tab discussionTab = new Tab("CONSULTATION", root);
        discussionTab.setClosable(false);
        Tab symptomeTab = new Tab("SYMPTOMES",symptomes) ;
        symptomeTab.setClosable(false);
        Tab diagnosticTab = new Tab("DIAGNOSTIC", consultation);
        diagnosticTab.setClosable(false);

        tabPane.getTabs().addAll(discussionTab, symptomeTab, diagnosticTab);

        // Scene
        Scene scene = new Scene(tabPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

