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
        colNom.setPrefWidth(100);

        TableColumn<User, Integer> colAge = new TableColumn<>("Age");
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colAge.setPrefWidth(60);

        TableColumn<User, String> colSexe = new TableColumn<>("Sexe");
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colSexe.setPrefWidth(60);

        TableColumn<User, Void> colAction = new TableColumn<>("Action");
        colAction.setPrefWidth(100);
        colAction.setCellFactory(param -> new TableCell<>() {
            final Button btnC = new Button("C");
            final Button btnR = new Button("R");
            final HBox pane = new HBox(5, btnC, btnR);

            {
                btnC.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    System.out.println("Consulter: " + user.getNom());
                });
                btnR.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    System.out.println("Rédiger: " + user.getNom());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });

        table.getColumns().addAll(colNumero, colNom, colAge, colSexe, colAction);
        table.setPrefHeight(120);

        // Ajout des données
        table.setItems(users);

        // Tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(
                new Tab("DISCUSSION"),
                new Tab("DIAGNOSTIC-PRESCRIPTION"),
                new Tab("INFOS-PATIENTS"),
                new Tab("INFOS-CONSULTATIONS")
        );
        tabPane.setPrefHeight(30);

        // Zone de texte pour discussion
        TextArea discussionArea = new TextArea();
        discussionArea.setPrefHeight(100);

        // Champ de saisie + bouton envoyer
        inputField.setPrefWidth(400);

        HBox inputBox = new HBox(5, inputField, sendButton);
        inputBox.setPadding(new Insets(5, 0, 0, 0));

        VBox discussionBox = new VBox(tabPane, discussionArea, inputBox);

        // Boutons Terminé / Rendez-vous
        HBox buttonBox = new HBox(10);
        Button btnTermine = new Button("Terminé");
        Button btnRdv = new Button("Rendez-Vous");
        buttonBox.getChildren().addAll(btnTermine, btnRdv);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        root.getChildren().addAll(new Label("LISTE DES CONSULTATIONS"), table, discussionBox, buttonBox);

        Scene scene = new Scene(root, 525, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
