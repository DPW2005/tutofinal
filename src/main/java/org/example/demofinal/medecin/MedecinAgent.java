package org.example.demofinal.medecin;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class MedecinAgent extends GuiAgent {

    public static boolean fichierModifierMedecin = false;
    private MedecinContainer medecinContainer ;
    protected MedecinFichier medecinFichier = new MedecinFichier() ;
    protected String fichierEcriture = "org/example/demofinal/fichier/sendByPatient.txt";
    protected String fichierLecture = "org/example/demofinal/fichier/sendByExpertDoctor.txt";

    @Override
    protected void setup(){
        medecinContainer = (MedecinContainer) getArguments()[0] ;
        medecinContainer.setMedecinAgent(this);
        System.out.println("Initialisation de l'agent "+this.getAID().getName());
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive() ;
                if(aclMessage != null){
                    int i = 0 ;
                    System.out.println("Reception du message : "+aclMessage.getContent());
                    GuiEvent guiEvent = new GuiEvent(this,1) ;
                    guiEvent.addParameter(aclMessage.getContent());
                    medecinContainer.viewMessage(guiEvent) ;
                    String formatMessage = medecinContainer.formatMessage(aclMessage.getContent()) ;
                    medecinFichier.write(medecinContainer.fichierEcriture,formatMessage) ;
                    String reponseExperta = medecinFichier.read(medecinContainer.fichierLecture) ;
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM) ;
                    message.setContent(reponseExperta);
                    message.addReceiver(new AID("PatientAgent",AID.ISLOCALNAME));
                    send(message);
                    GuiEvent guiEvent1 = new GuiEvent(this,1) ;
                    guiEvent1.addParameter(message.getContent());
                    medecinContainer.viewMessage(guiEvent1);
                    fichierModifierMedecin = false ;
                }
                else{
                    block();
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
        throw new UnsupportedOperationException("NOT SUPPORT YET") ;
    }

    public void writeMessage(String fileName,String contenu){

    }
}
