package org.example.demofinal.patient;

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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PatientContainer extends Application {

    private String medecinMessage = "C:\\Users\\USER\\Documents\\Github\\DemoFinal\\src\\main\\java\\org\\example\\demofinal\\fichier\\sendByExpertDoctor.txt" ;
    protected PatientInterface patientInterface = new PatientInterface() ;
    protected ConsultationInterface consultationInterface = new ConsultationInterface() ;
    private PatientAgent patientAgent ;

    public void setPatientAgent(PatientAgent patientAgent) {
        this.patientAgent = patientAgent;
    }

    public void viewMessage(GuiEvent guiEvent) {
        if(guiEvent.getType() == 1){
            String message = guiEvent.getParameter(0).toString() ;
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
        patientInterface.btnConsulter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nom = patientInterface.nomField.getText() ;
                String age = patientInterface.ageField.getText() ;
                RadioButton s = (RadioButton) patientInterface.sexeGroup.getSelectedToggle() ;
                String sexe =s.getText() ;
                consultationInterface.patientName = nom ;
                Stage stage1 = new Stage() ;
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.initOwner(stage);
                consultationInterface.start(stage1);
                //readMessage(consultationInterface.patientName);
                ACLMessage aclMessage = new ACLMessage() ;
                aclMessage.setContent("CONSULTATION:"+nom+","+age+","+sexe);
                aclMessage.addReceiver(new AID("ReceptionnisteAgent",AID.ISLOCALNAME));
                patientAgent.send(aclMessage);
            }
        });
        consultationInterface.envoyerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String message = consultationInterface.message.getText() ;
                consultationInterface.message.clear();
                Label newMessage = new Label("Moi : "+message) ;
                consultationInterface.textArea.getChildren().add(newMessage) ;
                //writeMessage("Moi",message);
                ACLMessage aclMessage = new ACLMessage() ;
                aclMessage.setContent("DISCUSSION:"+consultationInterface.patientName+","+message);
                aclMessage.addReceiver(new AID("MedecinAgent",AID.ISLOCALNAME));
                patientAgent.send(aclMessage);
            }
        });
        consultationInterface.symptomeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> symptomeSelected = new ArrayList<>() ;
                for(CheckBox cb : consultationInterface.listeCheckBox){
                    if(cb.isSelected()){
                        symptomeSelected.add(cb.getText()) ;
                        cb.setSelected(false);
                    }
                }
                if(symptomeSelected != null){
                    ACLMessage aclMessage = new ACLMessage() ;
                    aclMessage.setContent("SYMPTOME:"+consultationInterface.patientName+","+String.join(",",symptomeSelected));
                    aclMessage.addReceiver(new AID("MedecinAgent",AID.ISLOCALNAME));
                    patientAgent.send(aclMessage);
                }
                else{
                    System.out.println("Veuillez selectionner des symptomes avant de valider");
                }
            }
        });
    }

    private void startContainer() throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        properties.setProperty(Profile.CONTAINER_NAME, "PatientContainer") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer patientContainer = runtime.createAgentContainer(profile) ;
        AgentController patientController = patientContainer.createNewAgent("PatientAgent", PatientAgent.class.getName(), new Object[]{this}) ;
        patientController.start() ;
    }

    public void writeInDiscussion(String sender,String message){
        Platform.runLater(() -> {
            if(sender.equals(consultationInterface.patientName)){
                Label newMessage = new Label("Medecin : "+message) ;
                consultationInterface.textArea.getChildren().add(newMessage) ;
                //writeMessage(sender, message);
            }
            else{
                //writeMessage(sender, message);
            }
        });
    }

    public void writeDiagnostic(String sender,String message){
        Platform.runLater(() -> {
            if(sender.equals(consultationInterface.patientName)){
                Label newMessage = new Label("Medecin : "+message) ;
                consultationInterface.diagnosticArea.setText(message); ;
                //writeMessage(sender, message);
            }
            else{
                //writeMessage(sender, message);
            }
        });
    }

    public void writeMessage(String receiver,String msg){
        try{
            FileWriter fw = new FileWriter(medecinMessage) ;
            BufferedWriter bw = new BufferedWriter(fw) ;
            BufferedReader br = new BufferedReader(new FileReader(medecinMessage)) ;
            String[] messages = br.readLine().split(";") ;
            String message = br.readLine()+";"+receiver+","+msg+","+messages.length+1 ;
            bw.write(message);
            bw.close();
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
