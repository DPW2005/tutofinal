package org.example.demofinal.patient;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PatientInterface extends Application {

    TextField nomField = new TextField();
    TextField prenomField = new TextField();
    TextField ageField = new TextField();
    TextField adresseField = new TextField();
    TextField telField = new TextField();
    Button btnConsulter = new Button("Consulter");
    ToggleGroup sexeGroup = new ToggleGroup();
    RadioButton rbF = new RadioButton("F");
    RadioButton rbM = new RadioButton("M");

    @Override
    public void start(Stage stage) {
        stage.setTitle("PATIENT");

        // Titre
        Label titleLabel = new Label("Mes Informations");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Champs
        nomField.setPromptText("Entrez votre nom");

        prenomField.setPromptText("Entrez votre prénom");

        ageField.setPromptText("Âge");

        // Sexe (radio buttons)
        rbF.setToggleGroup(sexeGroup);
        rbM.setToggleGroup(sexeGroup);
        HBox sexeBox = new HBox(10, rbF, rbM);
        sexeBox.setAlignment(Pos.CENTER_LEFT);

        adresseField.setPromptText("Entrez votre adresse");

        telField.setPromptText("+237");

        // Boutons
        btnConsulter.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        // Grille de formulaire
        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(new Label("Nom(s)"), 0, 0);
        formGrid.add(nomField, 1, 0);

        formGrid.add(new Label("Prénom(s)"), 0, 1);
        formGrid.add(prenomField, 1, 1);

        formGrid.add(new Label("Âge"), 0, 2);
        formGrid.add(ageField, 1, 2);

        formGrid.add(new Label("Sexe"), 0, 3);
        formGrid.add(sexeBox, 1, 3);

        formGrid.add(new Label("Adresse"), 0, 4);
        formGrid.add(adresseField, 1, 4);

        formGrid.add(new Label("Téléphone"), 0, 5);
        formGrid.add(telField, 1, 5);

        // Boutons en bas
        VBox buttonBox = new VBox(10, btnConsulter);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        // Layout principal
        VBox layout = new VBox(10, titleLabel, formGrid, buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 350, 450);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

