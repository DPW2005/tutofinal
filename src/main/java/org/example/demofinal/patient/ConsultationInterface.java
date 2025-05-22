package org.example.demofinal.patient;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ConsultationInterface extends Application {

    TextArea messagesArea = new TextArea();
    TextArea inputArea = new TextArea();
    Button envoyerBtn = new Button("Envoyer");
    Button partirBtn1 = new Button("Partir");
    TextArea diagnosticArea = new TextArea();
    TextArea ordonnanceArea = new TextArea();
    Button partirBtn2 = new Button("Partir");

    @Override
    public void start(Stage stage) {
        stage.setTitle("Patient");

        // Onglet 1 : Consultation
        VBox consultationBox = new VBox(10);
        consultationBox.setPadding(new Insets(10));
        consultationBox.setAlignment(Pos.TOP_CENTER);

        Label waitingLabel = new Label("En attente de Consultation");
        waitingLabel.setStyle("-fx-font-style: italic;");

        // Zone de discussion
        HBox chatBox = new HBox(10);
        chatBox.setPrefHeight(200);
        chatBox.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.PURPLE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        messagesArea.setEditable(false);
        messagesArea.setWrapText(true);

        chatBox.getChildren().addAll(messagesArea);

        // Zone de saisie et boutons
        HBox sendBox = new HBox(5);
        sendBox.setAlignment(Pos.CENTER_LEFT);

        inputArea.setPrefRowCount(2);
        inputArea.setWrapText(true);
        inputArea.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.GREEN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox btnBox = new VBox(10);
        btnBox.setAlignment(Pos.CENTER);

        envoyerBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        partirBtn1.setStyle("-fx-background-color: purple; -fx-text-fill: white;");

        btnBox.getChildren().addAll(envoyerBtn, partirBtn1);
        sendBox.getChildren().addAll(inputArea, btnBox);

        consultationBox.getChildren().addAll(waitingLabel, chatBox, sendBox);

        // Onglet 2 : Prescription
        VBox prescriptionBox = new VBox(15);
        prescriptionBox.setPadding(new Insets(15));
        prescriptionBox.setAlignment(Pos.TOP_LEFT);

        Label diagnosticLabel = new Label("Diagnostic :");
        diagnosticArea.setPrefHeight(80);
        diagnosticArea.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.PURPLE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label ordonnanceLabel = new Label("Ordonnance :");
        ordonnanceArea.setPrefHeight(80);
        ordonnanceArea.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.PURPLE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        partirBtn2.setStyle("-fx-background-color: purple; -fx-text-fill: white;");
        HBox partirBox2 = new HBox(partirBtn2);
        partirBox2.setAlignment(Pos.CENTER);

        prescriptionBox.getChildren().addAll(
                diagnosticLabel, diagnosticArea,
                ordonnanceLabel, ordonnanceArea,
                partirBox2
        );

        // TabPane
        TabPane tabPane = new TabPane();
        Tab consultationTab = new Tab("Consultation", consultationBox);
        consultationTab.setClosable(false);
        Tab prescriptionTab = new Tab("Prescription(s)", prescriptionBox);
        prescriptionTab.setClosable(false);

        tabPane.getTabs().addAll(consultationTab, prescriptionTab);

        // Scene
        Scene scene = new Scene(tabPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

