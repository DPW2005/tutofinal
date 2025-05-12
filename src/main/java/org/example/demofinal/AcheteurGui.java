package org.example.demofinal;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AcheteurGui extends Application {

    protected AcheteurAgent acheteurAgent ;
    protected ObservableList<String> observableList ;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        BorderPane borderPane = new BorderPane() ;
        VBox vBox = new VBox() ;
        observableList = FXCollections.observableArrayList() ;
        ListView<String> listView = new ListView<>(observableList) ;
        vBox.getChildren().add(listView) ;
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane,400,300) ;
        stage.setScene(scene);
        stage.show();
    }

    private void startContainer() throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer agentContainer = runtime.createAgentContainer(profile) ;
        AgentController agentController = agentContainer.createNewAgent("Acheteur", AcheteurAgent.class.getName(), new Object[]{this}) ;
        agentController.start() ;
    }

    public void logMessage(ACLMessage aclMessage){
        Platform.runLater(()->{
            observableList.add(aclMessage.getContent()) ;
        });
    }
}
