package org.example.demofinal.receptionniste;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import org.example.demofinal.User;
import org.example.demofinal.gestion_cabinet_medical_final.GESTION_CABINET_MEDICAL_FINAL;

public class ReceptionnisteContainer extends Application {

    private ReceptionnisteAgent receptionnisteAgent ;
    protected ReceptionnisteInterface receptionnisteInterface = new ReceptionnisteInterface() ;
    GESTION_CABINET_MEDICAL_FINAL gestionCabinetMedicalFinal = new GESTION_CABINET_MEDICAL_FINAL() ;

    public void setReceptionnisteAgent(ReceptionnisteAgent receptionnisteAgent) {
        this.receptionnisteAgent = receptionnisteAgent;
    }

    public static void main(String[] args) throws Exception{
        launch(ReceptionnisteContainer.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        receptionnisteInterface.start(stage);
        receptionnisteInterface.btnAccepter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User userSelected = receptionnisteInterface.demandeTable.getSelectionModel().getSelectedItem() ;
                receptionnisteInterface.demandeTable.getItems().remove(userSelected) ;
                userSelected.setStatus("En attente");
                receptionnisteInterface.users2.add(userSelected) ;
                receptionnisteInterface.etatTable.setItems(receptionnisteInterface.users2);
                System.out.println("Validation de la demande de consultation de : "+userSelected.getNom());
                ACLMessage aclMessage = new ACLMessage(ACLMessage.CONFIRM) ;
                aclMessage.setContent("Votre demande de consultation a ete acceptee");
                aclMessage.addReceiver(new AID("PatientAgent",AID.ISLOCALNAME));
                //receptionnisteAgent.send(aclMessage);
                gestionCabinetMedicalFinal.addItem(aclMessage.getContent());
            }
        });
    }

    private void startContainer() throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer receptionnisteContainer = runtime.createAgentContainer(profile) ;
        AgentController receptionnisteController = receptionnisteContainer.createNewAgent("ReceptionnisteAgent", ReceptionnisteAgent.class.getName(), new Object[]{this}) ;
        receptionnisteController.start() ;
    }
}
