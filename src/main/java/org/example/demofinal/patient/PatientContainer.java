package org.example.demofinal.patient;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import org.example.demofinal.gestion_cabinet_medical_final.GESTION_CABINET_MEDICAL_FINAL;

public class PatientContainer extends Application {

    protected PatientInterface patientInterface = new PatientInterface() ;
    protected ConsultationInterface consultationInterface = new ConsultationInterface() ;
    private PatientAgent patientAgent ;
    GESTION_CABINET_MEDICAL_FINAL gestionCabinetMedicalFinal = new GESTION_CABINET_MEDICAL_FINAL() ;
    boolean patientFormulaire = false ;
    static  boolean consultationPanel = false ;

    public void setPatientAgent(PatientAgent patientAgent) {
        this.patientAgent = patientAgent;
    }

    public void viewMessage(GuiEvent guiEvent) {
        if(guiEvent.getType() == 1){
            String message = guiEvent.getParameter(0).toString() ;
            gestionCabinetMedicalFinal.addItem(message);
            System.out.println(message);
        }
    }

    public static void main(String[] args){
        launch(PatientContainer.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        startContainer();
        patientInterface.start(stage);
        //patientInterface.sexeGroup.selectedToggleProperty().addListener((observable,oldValue,newValue)-> {patientInterface.rbF,patientInterface.rbM});
        patientInterface.btnEnregistrer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        patientInterface.btnConsulter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        consultationInterface.envoyerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        consultationInterface.partirBtn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        consultationInterface.partirBtn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

    private void startContainer() throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer patientContainer = runtime.createAgentContainer(profile) ;
        AgentController patientController = patientContainer.createNewAgent("PatientAgent", PatientAgent.class.getName(), new Object[]{this}) ;
        patientController.start() ;
    }

}
