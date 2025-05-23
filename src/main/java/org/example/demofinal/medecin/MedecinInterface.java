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
import org.example.demofinal.Consultation;
import org.example.demofinal.User;

public class MedecinInterface extends Application {

    TextField lieuField = new TextField();
    TextField dateField = new TextField();
    Button consulterButton = new Button("Consulter") ;
    Button btnAccepter = new Button("Accepte") ;
    TableView<User> demandeTable = new TableView<>() ;
    TableView<Consultation> consultationTable = new TableView<>() ;
    ObservableList<User> users = FXCollections.observableArrayList() ;
    ObservableList<Consultation> consultations = FXCollections.observableArrayList() ;

    @Override
    public void start(Stage stage) {

        stage.setTitle("MEDECIN");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        lieuField.setPromptText("Lieu");
        dateField.setPromptText("AAAA-MM-JJ");
        // Label principal
        Label demandeLabel = new Label("DEMANDE DE CONSULTATION");

        // TableView 1 : Demandes de consultation
        demandeTable.setPrefHeight(150); // Pour forcer le scroll si dépassement

        TableColumn<User, Integer> colNumero = new TableColumn<>("Numéro");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(70);

        TableColumn<User, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(100);

        TableColumn<User, Integer> colAge = new TableColumn<>("Age");
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colAge.setPrefWidth(60);

        TableColumn<User, String> colSexe = new TableColumn<>("Sexe");
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colSexe.setPrefWidth(60);

        demandeTable.getColumns().addAll(colNumero, colNom, colAge, colSexe);

        // Ajouter plusieurs lignes pour voir le scroll
        demandeTable.setItems(users);

        // Label secondaire
        Label etatLabel = new Label("ETAT DE CONSULTATION");

        // TableView 2 : État de consultation
        consultationTable.setPlaceholder(new Label("Aucun contenu dans la table"));
        consultationTable.setPrefHeight(150);

        TableColumn<Consultation, Integer> colEtatNumero = new TableColumn<>("Numéro");
        colEtatNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colEtatNumero.setPrefWidth(70);

        TableColumn<Consultation, String> colEtatPatient = new TableColumn<>("Patient");
        colEtatPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colEtatPatient.setPrefWidth(100);

        TableColumn<Consultation, Integer> colEtatLieu = new TableColumn<>("Lieu");
        colEtatLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colEtatLieu.setPrefWidth(100);

        TableColumn<Consultation, String> colEtatDate = new TableColumn<>("Date");
        colEtatDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEtatDate.setPrefWidth(100);

        TableColumn<Consultation, String> colEtatStatut = new TableColumn<>("Statut");
        colEtatStatut.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEtatStatut.setPrefWidth(100);

        consultationTable.getColumns().addAll(colEtatNumero, colEtatPatient, colEtatLieu, colEtatDate, colEtatStatut);
        consultationTable.setItems(consultations);
        // Organisation dans l'interface
        root.getChildren().addAll(demandeLabel, demandeTable, lieuField, dateField, btnAccepter, etatLabel, consultationTable, consulterButton);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
