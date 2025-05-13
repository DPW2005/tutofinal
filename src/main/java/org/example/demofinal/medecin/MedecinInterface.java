package org.example.demofinal.medecin;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MedecinInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Medecin");

        // Top Section: Consultation List
        Label consultationLabel = new Label("Liste Consultations");

        // TableView
        TableView<Consultation> consultationTable = new TableView<>();
        ObservableList<Consultation> consultations = FXCollections.observableArrayList();
        consultationTable.setItems(consultations);

        // Columns
        TableColumn<Consultation, Integer> numeroCol = new TableColumn<>("Numero");
        numeroCol.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Consultation, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Consultation, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Consultation, String> sexeCol = new TableColumn<>("Sexe");
        sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));


        consultationTable.getColumns().addAll(numeroCol, nomCol, ageCol, sexeCol);

        VBox consultationLayout = new VBox(3, consultationLabel, consultationTable);
        consultationLayout.setPadding(new Insets(10));

        // Center Section: Tabs and Input
        TabPane tabPane = new TabPane();

        Tab discussionTab = new Tab("Discussion");
        discussionTab.setContent(new StackPane(new Label("Discussion Content")));

        Tab diagnosticPrescriptionTab = new Tab("DIAGNOSTIC-PRESCRIPTION");
        diagnosticPrescriptionTab.setContent(new StackPane(new Label("Diagnostic/Prescription Content")));

        Tab infosPatientsTab = new Tab("INFOS-PATIENTS");
        infosPatientsTab.setContent(new StackPane(new Label("Patient Information Content")));

        Tab infosConsultationsTab = new Tab("INFOS-CONSULTATIONS");
        infosConsultationsTab.setContent(new StackPane(new Label("Consultation Information Content")));

        tabPane.getTabs().addAll(discussionTab, diagnosticPrescriptionTab, infosPatientsTab, infosConsultationsTab);

        TextArea zoneSaisie = new TextArea() ;
        TextField inputTextField = new TextField();
        Button envoyerButton = new Button("Envoyer");
        HBox inputLayout = new HBox(10, zoneSaisie, inputTextField, envoyerButton);
        inputLayout.setMaxHeight(200);
        inputLayout.setAlignment(Pos.CENTER_RIGHT);
        inputLayout.setPadding(new Insets(10));

        VBox centerLayout = new VBox(10, tabPane, inputLayout);

        // Bottom Section: Buttons
        Button termineButton = new Button("Terminé");
        Button rendezVousButton = new Button("Rendez-Vous");
        HBox bottomLayout = new HBox(10, termineButton, rendezVousButton);
        bottomLayout.setAlignment(Pos.CENTER_RIGHT);
        bottomLayout.setPadding(new Insets(10));

        // Main Layout
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(consultationLayout);
        mainPane.setCenter(centerLayout);
        mainPane.setBottom(bottomLayout);

        Scene scene = new Scene(mainPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class Consultation {
        private final int numero;
        private final String nom;
        private final int age;
        private final String sexe;

        public Consultation(int numero, String nom, int age, String sexe) {
            this.numero = numero;
            this.nom = nom;
            this.age = age;
            this.sexe = sexe;
        }

        public int getNumero() {
            return numero;
        }

        public String getNom() {
            return nom;
        }

        public int getAge() {
            return age;
        }

        public String getSexe() {
            return sexe;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}