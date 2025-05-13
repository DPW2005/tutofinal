package org.example.demofinal.medecin;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
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

public class MedecinContainer extends Application {

    protected String fichierEcriture = "org/example/demofinal/fichier/sendByPatient.txt";
    protected String fichierLecture = "org/example/demofinal/fichier/sendByExpertDoctor.txt";
    protected MedecinInterface medecinInterface = new MedecinInterface() ;
    private MedecinAgent medecinAgent ;
    GESTION_CABINET_MEDICAL_FINAL gestionCabinetMedicalFinal = new GESTION_CABINET_MEDICAL_FINAL() ;

    public void setMedecinAgent(MedecinAgent medecinAgent) {
        this.medecinAgent = medecinAgent;
    }

    public void viewMessage(GuiEvent guiEvent) {
        if(guiEvent.getType() == 1){
            String message = guiEvent.getParameter(0).toString() ;
            gestionCabinetMedicalFinal.addItem(message);
            System.out.println(message);
        }
    }

    public String formatMessage(String content) {
        String formatedMessage = ("{\"pb\":\""+content+"\"}") ;
        return formatedMessage ;
    }

    public static void main(String[] args){
        launch(MedecinContainer.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        medecinInterface.start(stage);
        medecinInterface.sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User userSelected = medecinInterface.table.getSelectionModel().getSelectedItem() ;
                String message = medecinInterface.inputField.getText() ;
                System.out.println("Je suis le medecin, j'ai saisi : {"+message+"} pour le patient : "+ userSelected.getNom());
                ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                aclMessage.setContent(message);
                aclMessage.addReceiver(new AID("PatientAgent",AID.ISLOCALNAME));
                //medecinAgent.send(aclMessage);
                gestionCabinetMedicalFinal.addItem(message);
                medecinInterface.inputField.clear();
            }
        });
    }

    private void startContainer() throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer medecinContainer = runtime.createAgentContainer(profile) ;
        AgentController medecinController = medecinContainer.createNewAgent("MedecinAgent", MedecinAgent.class.getName(), new Object[]{this}) ;
        medecinController.start() ;
    }
}
