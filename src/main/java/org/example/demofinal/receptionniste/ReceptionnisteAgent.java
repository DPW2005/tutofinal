package org.example.demofinal.receptionniste;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
import org.example.demofinal.Consultation;
import org.example.demofinal.User;

public class ReceptionnisteAgent extends GuiAgent {

    private ReceptionnisteContainer receptionnisteContainer ;

    @Override
    protected void setup(){
        receptionnisteContainer = (ReceptionnisteContainer) getArguments()[0] ;
        receptionnisteContainer.setReceptionnisteAgent(this);
        System.out.println("Initialisation de l'agent "+this.getAID().getName());
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive() ;
                if(aclMessage != null){
                    String sender = aclMessage.getSender().getName() ;
                    String message = aclMessage.getContent() ;
                    System.out.println("On a recu un message de quelqu'un : "+sender);
                    if(message.startsWith("CONSULTATION:")){
                        String info = message.substring(13) ;
                        String[] elements = info.split(",") ;
                        for(String e : elements){System.out.println(e);}
                        User user = new User() ;
                        user.numero = receptionnisteContainer.nbreLignePatient + 1 ;
                        user.nom = elements[0] ;
                        user.age = Integer.parseInt(elements[1]) ;
                        user.sexe = elements[2] ;
                        receptionnisteContainer.receptionnisteInterface.users.add(user) ;
                        receptionnisteContainer.writePatientFile(user);
                        System.out.println("Utilisateur ajoute");
                    }
                    if(message.startsWith("ACCEPTE:")){
                        String info = message.substring(8) ;
                        String[] elements = info.split(",") ;
                        Consultation consultation = new Consultation() ;
                        consultation.numero = receptionnisteContainer.nbreLigneConsultation + 1 ;
                        consultation.patientName = elements[0] ;
                        consultation.lieu = elements[1] ;
                        consultation.date = elements[2] ;
                        consultation.status = elements[3] ;
                        receptionnisteContainer.receptionnisteInterface.consultations.add(consultation) ;
                        receptionnisteContainer.writeConsultationFile(consultation);
                        System.out.println("Consultation ajoute");
                        ACLMessage reponsePatient = new ACLMessage() ;
                        reponsePatient.setContent("La demande de consultation du patient : "+consultation.patientName+" a ete acceptee") ;
                        reponsePatient.addReceiver(new AID("PatientAgent",AID.ISLOCALNAME));
                        send(reponsePatient);
                        System.out.println("Validation de consultation de "+consultation.patientName);
                    }
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
        if(guiEvent.getType() == 1){
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST) ;
            String livre = guiEvent.getParameter(0).toString() ;
            aclMessage.setContent(livre);
            aclMessage.addReceiver(new AID("rma",AID.ISLOCALNAME));
            send(aclMessage);
        }
    }
}
