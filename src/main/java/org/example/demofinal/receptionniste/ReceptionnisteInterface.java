package org.example.demofinal.receptionniste;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.demofinal.User;

public class ReceptionnisteInterface extends Application {


    Button btnAccepter = new Button("Accepté");
    TableView<User> demandeTable = new TableView<>();
    TableView<User> etatTable = new TableView<>();
    ObservableList<User> users1 = FXCollections.observableArrayList() ;
    ObservableList<User> users2 = FXCollections.observableArrayList() ;

    @Override
    public void start(Stage stage) {

        for (int i = 1; i <= 20; i++) {
            users1.add(new User(i, (i % 3 == 0) ? "Nassair" : (i % 2 == 0 ? "Line" : "Lea"), 20 + (i % 3), (i % 2 == 0) ? "F" : "M"));
        }
        stage.setTitle("RECEPTIONNISTE");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

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
        demandeTable.setItems(users1);

        // Label secondaire
        Label etatLabel = new Label("ETAT DE CONSULTATION");

        // TableView 2 : État de consultation
        etatTable.setPlaceholder(new Label("Aucun contenu dans la table"));
        etatTable.setPrefHeight(150);

        TableColumn<User, Integer> colEtatNumero = new TableColumn<>("Numéro");
        colEtatNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colEtatNumero.setPrefWidth(70);

        TableColumn<User, String> colEtatNom = new TableColumn<>("Nom");
        colEtatNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEtatNom.setPrefWidth(100);

        TableColumn<User, Integer> colEtatAge = new TableColumn<>("Age");
        colEtatAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEtatAge.setPrefWidth(60);

        TableColumn<User, String> colEtatSexe = new TableColumn<>("Sexe");
        colEtatSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colEtatSexe.setPrefWidth(60);

        TableColumn<User, String> colEtatStatut = new TableColumn<>("Statut");
        colEtatStatut.setCellValueFactory(data -> new ReadOnlyStringWrapper("En attente"));
        colEtatStatut.setPrefWidth(100);

        etatTable.getColumns().addAll(colEtatNumero, colEtatNom, colEtatAge, colEtatSexe, colEtatStatut);
        etatTable.setItems(users2);
        // Organisation dans l'interface
        root.getChildren().addAll(demandeLabel, demandeTable, btnAccepter, etatLabel, etatTable);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


