package org.example.demofinal.medecin;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.demofinal.gestion_cabinet_medical_final.GESTION_CABINET_MEDICAL_FINAL;

public class MedecinContainer extends Application {

    protected String fichierEcriture = "org/example/demofinal/fichier/sendByPatient.txt";
    protected String fichierLecture = "org/example/demofinal/fichier/sendByExpertDoctor.txt";
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
