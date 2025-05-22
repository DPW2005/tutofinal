package org.example.demofinal.patient;

import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

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
                    System.out.println("On a recu le message de quelqu'un : "+sender);
                    switch (sender){
                        case("ReceptionnisteAgent@192.168.56.1:1099/JADE") :
                            if(aclMessage.getPerformative() == 4){
                                GuiEvent guiEvent = new GuiEvent(this,2) ;
                                guiEvent.addParameter(aclMessage.getContent());
                                patientContainer.viewMessage(guiEvent) ;
                            }
                            break;
                        case("MedecinAgent@192.168.56.1:1099/JADE") :
                            if(aclMessage.getPerformative() == 7){
                                GuiEvent guiEvent = new GuiEvent(this,1) ;
                                guiEvent.addParameter(aclMessage.getContent());
                                patientContainer.viewMessage(guiEvent) ;
                            }
                            break;
                        default: break;
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
