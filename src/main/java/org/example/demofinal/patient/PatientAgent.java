package org.example.demofinal.patient;

import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
import javafx.stage.Stage;

public class PatientAgent extends GuiAgent {

    private PatientContainer patientContainer ;

    @Override
    protected void setup(){
        patientContainer = (PatientContainer) getArguments()[0] ;
        patientContainer.setPatientAgent(this);
        System.out.println("Initialisation de l'agent "+this.getAID().getName());
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive() ;
                if(aclMessage != null){
                    String sender = aclMessage.getSender().getName() ;
                    String message = aclMessage.getContent() ;
                    System.out.println("On a recu le message de quelqu'un : "+sender);
                    if(message.startsWith("DISCUSSION:")){
                        String info = message.substring(11) ;
                        String[] elements = info.split(",") ;
                        patientContainer.writeInDiscussion(elements[0],elements[1]);
                    }
                    if(message.startsWith("CONFIRMATION:")){
                        System.out.println(message.substring(13));
                    }
                    if(message.startsWith("DIAGNOSTIC:")){
                        String info = message.substring(11) ;
                        String[] elements = info.split(",") ;
                        patientContainer.writeDiagnostic(elements[0],elements[1]);
                    }
                }
            }
        });
    }

    protected void takeDown(){
        System.out.println("Destruction de l'agent") ;
    }

    @Override
    protected void beforeMove(){
        try{
            System.out.println("Avant la migration......du container "+this.getContainerController().getContainerName()) ;
        }
        catch(ControllerException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void afterMove(){
        try{
            System.out.println("Apres la migration......du container "+this.getContainerController().getContainerName()) ;
        }
        catch(ControllerException e){
            e.printStackTrace() ;
        }
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
