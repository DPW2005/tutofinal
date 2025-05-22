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
import org.example.demofinal.Consultation;
import org.example.demofinal.User;
import org.example.demofinal.gestion_cabinet_medical_final.GESTION_CABINET_MEDICAL_FINAL;

import java.io.*;

public class ReceptionnisteContainer extends Application {

    private String consultationFile = "C:\\Users\\USER\\Documents\\Github\\DemoFinal\\src\\main\\java\\org\\example\\demofinal\\fichier\\consultation.txt" ;
    private String patientFile = "C:\\Users\\USER\\Documents\\Github\\DemoFinal\\src\\main\\java\\org\\example\\demofinal\\fichier\\patient.txt" ;
    public int nbreLignePatient ;
    public int nbreLigneConsultation ;
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
        nbreLignePatient = readPatientFile();
        nbreLigneConsultation = readConsultationFile();
        receptionnisteInterface.start(stage);
        receptionnisteInterface.btnAccepter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User userSelected = receptionnisteInterface.demandeTable.getSelectionModel().getSelectedItem() ;
                receptionnisteInterface.demandeTable.getItems().remove(userSelected) ;
                System.out.println("La demande de consultation de : "+userSelected.nom+" est en traitement");
                ACLMessage aclMessage = new ACLMessage() ;
                aclMessage.setContent("DEMANDE:"+userSelected.numero+","+userSelected.nom+","+userSelected.age+","+userSelected.sexe);
                aclMessage.addReceiver(new AID("MedecinAgent",AID.ISLOCALNAME));
                //receptionnisteAgent.send(aclMessage);
            }
        });
    }

    private void startContainer() throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        properties.setProperty(Profile.CONTAINER_NAME, "ReceptionContainer") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer receptionnisteContainer = runtime.createAgentContainer(profile) ;
        AgentController receptionnisteController = receptionnisteContainer.createNewAgent("ReceptionnisteAgent", ReceptionnisteAgent.class.getName(), new Object[]{this}) ;
        receptionnisteController.start() ;
    }

    public int readPatientFile(){
        try{
            FileReader fr = new FileReader(patientFile) ;
            BufferedReader br = new BufferedReader(fr) ;
            String ligne ;
            int nbreLigne = 0 ;
            while((ligne = br.readLine()) != null){
                String[] elements = ligne.split(",") ;
                User user = new User() ;
                user.numero = Integer.parseInt(elements[0]) ;
                user.nom = elements[1] ;
                user.age = Integer.parseInt(elements[2]) ;
                user.sexe = elements[3] ;
                receptionnisteInterface.users.add(user) ;
                nbreLigne++ ;
            }
            return nbreLigne ;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0 ;
        }
    }

    public int readConsultationFile(){
        try{
            FileReader fr = new FileReader(consultationFile) ;
            BufferedReader br = new BufferedReader(fr) ;
            String ligne ;
            int nbreLigne = 0 ;
            while((ligne = br.readLine()) != null){
                String[] elements = ligne.split(",") ;
                Consultation consultation = new Consultation() ;
                consultation.numero = Integer.parseInt(elements[0]) ;
                consultation.patientName = elements[1] ;
                consultation.lieu = elements[2] ;
                consultation.date = elements[3] ;
                consultation.status = elements[4] ;
                receptionnisteInterface.consultations.add(consultation) ;
                nbreLigne++ ;
            }
            return nbreLigne ;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0 ;
        }
    }

    public void writePatientFile(User user){
        try {
            FileWriter fw = new FileWriter(patientFile) ;
            BufferedWriter bw = new BufferedWriter(fw) ;
            String message = user.numero+","+user.nom+","+user.age+","+user.sexe ;
            bw.newLine();
            bw.write(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeConsultationFile(Consultation consultation){
        try {
            FileWriter fw = new FileWriter(consultationFile) ;
            BufferedWriter bw = new BufferedWriter(fw) ;
            String message = consultation.numero+","+consultation.patientName+","+consultation.lieu+","+consultation.date+","+consultation.status ;
            bw.newLine();
            bw.write(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
1,DONGMO,ESSOS,2025-12-06,Traitement...
2,PRINCE,ESSOS,2025-11-06,Traitement...
3,WILLIAMS,ESSOS,2025-10-05,Traitement...
1,PHILLIPE,16,M
2,THERESE,16,F
3,LESLIE,18,F
 */