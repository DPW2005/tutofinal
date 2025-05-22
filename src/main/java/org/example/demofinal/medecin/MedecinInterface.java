package org.example.demofinal.medecin;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.demofinal.User;

public class MedecinInterface extends Application {

    Button sendButton = new Button("Envoyer");
    TextField inputField = new TextField();

    TableView<User> table = new TableView<>();
    ObservableList<User> users = FXCollections.observableArrayList(
            new User(1, "Lea", 20, "F"),
            new User(1, "Line", 21, "F"),
            new User(1, "Nassair", 22, "M"));

    @Override
    public void start(Stage stage) {
        stage.setTitle("MEDECIN");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Titre
        Label title = new Label("BUREAU DU MEDECIN");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        root.getChildren().add(title);

        TableColumn<User, Integer> colNumero = new TableColumn<>("Numero");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setPrefWidth(70);

        TableColumn<User, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(170);

        TableColumn<User, Integer> colAge = new TableColumn<>("Age");
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colAge.setPrefWidth(60);

        TableColumn<User, String> colSexe = new TableColumn<>("Sexe");
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colSexe.setPrefWidth(60);



        table.getColumns().addAll(colNumero, colNom, colAge, colSexe);
        table.setPrefHeight(300);

        // Ajout des données
        table.setItems(users);



        // Boutons Terminé / Rendez-vous
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll();
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        root.getChildren().addAll(new Label("LISTE DES CONSULTATIONS"), table, buttonBox);

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
