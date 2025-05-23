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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.demofinal.Consultation;
import org.example.demofinal.User;
import org.example.demofinal.gestion_cabinet_medical_final.GESTION_CABINET_MEDICAL_FINAL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MedecinContainer extends Application {

    private String consultationFile = "C:\\Users\\USER\\Documents\\Github\\DemoFinal\\src\\main\\java\\org\\example\\demofinal\\fichier\\consultation.txt" ;
    private String demandeFile = "C:\\Users\\USER\\Documents\\Github\\DemoFinal\\src\\main\\java\\org\\example\\demofinal\\fichier\\demande.txt" ;
    private String medecinMessage = "C:\\Users\\USER\\Documents\\Github\\DemoFinal\\src\\main\\java\\org\\example\\demofinal\\fichier\\sendByExpertDoctor.txt" ;
    protected MedecinInterface medecinInterface = new MedecinInterface() ;
    protected ConsultationInterface consultationInterface = new ConsultationInterface() ;
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
        readDemandeFile();
        readConsultationFile();
        medecinInterface.start(stage);
        medecinInterface.btnAccepter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String lieu = medecinInterface.lieuField.getText() ;
                String date = medecinInterface.dateField.getText() ;
                medecinInterface.lieuField.clear();
                medecinInterface.dateField.clear();
                if(lieu != "" && date != ""){
                    User userSelected = medecinInterface.demandeTable.getSelectionModel().getSelectedItem() ;
                    medecinInterface.demandeTable.getItems().remove(userSelected) ;
                    writeDemandeFile();
                    Consultation consultation = new Consultation() ;
                    consultation.numero = medecinInterface.consultations.size() + 1 ;
                    consultation.patientName = userSelected.nom ;
                    consultation.lieu = lieu ;
                    consultation.date = date ;
                    consultation.status = "Traitement..." ;
                    medecinInterface.consultations.add(consultation) ;
                    ACLMessage aclMessage = new ACLMessage() ;
                    aclMessage.setContent("ACCEPTE:"+consultation.patientName+","+consultation.lieu+","+consultation.date+","+consultation.status);
                    aclMessage.addReceiver(new AID("ReceptionnisteAgent",AID.ISLOCALNAME));
                    medecinAgent.send(aclMessage);
                }
                else{
                    System.out.println("Nous ne pouvons pas valider la consultation. Veuillez renseigner le lieu et la date") ;
                }
            }
        });
        medecinInterface.consulterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Consultation consultationSelected = medecinInterface.consultationTable.getSelectionModel().getSelectedItem() ;
                consultationInterface.patientName = consultationSelected.patientName ;
                Stage stage1 = new Stage() ;
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.initOwner(stage);
                consultationInterface.start(stage1);
                //readMessage(consultationInterface.patientName);
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
                aclMessage.addReceiver(new AID("PatientAgent",AID.ISLOCALNAME));
                medecinAgent.send(aclMessage);
            }
        });
        consultationInterface.diagnosticButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String diagnostic = consultationInterface.diagnosticArea.getText() ;
                consultationInterface.diagnosticArea.clear();
                System.out.println(diagnostic);
                ACLMessage aclMessage = new ACLMessage() ;
                aclMessage.setContent("DIAGNOSTIC:"+consultationInterface.patientName+","+diagnostic);
                aclMessage.addReceiver(new AID("PatientAgent",AID.ISLOCALNAME));
                medecinAgent.send(aclMessage);
            }
        });
    }

    private void startContainer() throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        properties.setProperty(Profile.CONTAINER_NAME, "MedecinContainer") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer medecinContainer = runtime.createAgentContainer(profile) ;
        AgentController medecinController = medecinContainer.createNewAgent("MedecinAgent", MedecinAgent.class.getName(), new Object[]{this}) ;
        medecinController.start() ;
    }

    public void readDemandeFile(){
        try{
            FileReader fr = new FileReader(demandeFile) ;
            BufferedReader br = new BufferedReader(fr) ;
            String[] lignes = br.readLine().split(";") ;
            for(String ligne : lignes){
                String[] elements = ligne.split(",") ;
                User user = new User() ;
                user.numero = Integer.parseInt(elements[0]) ;
                user.nom = elements[1] ;
                user.age = Integer.parseInt(elements[2]) ;
                user.sexe = elements[3] ;
                medecinInterface.users.add(user) ;
            }
            br.close();
            fr.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readConsultationFile(){
        try{
            FileReader fr = new FileReader(consultationFile) ;
            BufferedReader br = new BufferedReader(fr) ;
            String[] lignes = br.readLine().split(";") ;
            for(String ligne : lignes){
                String[] elements = ligne.split(",") ;
                Consultation consultation = new Consultation() ;
                consultation.numero = Integer.parseInt(elements[0]) ;
                consultation.patientName = elements[1] ;
                consultation.lieu = elements[2] ;
                consultation.date = elements[3] ;
                consultation.status = elements[4] ;
                medecinInterface.consultations.add(consultation) ;
            }
            br.close();
            fr.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readMessage(String patientName){
        try{
            FileReader fr = new FileReader(medecinMessage) ;
            BufferedReader br = new BufferedReader(fr) ;
            String[] lignes = br.readLine().split(";") ;
            for(String ligne : lignes){
                String[] elements = ligne.split(",") ;
                if(elements[0] == consultationInterface.patientName || elements[0] == "Moi"){
                    writeInDiscussion(elements[0],elements[1]);
                }
            }
            br.close();
            fr.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void writeDemandeFile(){
        try {
            FileWriter fw = new FileWriter(demandeFile) ;
            BufferedWriter bw = new BufferedWriter(fw) ;
            String message = medecinInterface.users.stream().map(User::toString).collect(Collectors.joining(";"));
            bw.write(message);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeConsultationFile(){
        try {
            FileWriter fw = new FileWriter(consultationFile) ;
            BufferedWriter bw = new BufferedWriter(fw) ;
            String message = medecinInterface.consultations.stream().map(Consultation::toString).collect(Collectors.joining(";")); ;
            bw.write(message);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void writeInDiscussion(String sender,String message){
        Platform.runLater(() -> {
            if(sender.equals(consultationInterface.patientName)){
                Label newMessage = new Label(sender+" : "+message) ;
                consultationInterface.textArea.getChildren().add(newMessage) ;
                writeMessage(sender, message);
            }
            else{
                writeMessage(sender, message);
            }
        });
    }
}
