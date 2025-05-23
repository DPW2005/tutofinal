package org.example.demofinal.medecin;

import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
import org.example.demofinal.User;

import java.util.ArrayList;
import java.util.List;

public class MedecinAgent extends GuiAgent {

    private MedecinContainer medecinContainer ;

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
                    String sender = aclMessage.getSender().getName() ;
                    String message = aclMessage.getContent() ;
                    System.out.println("On a recu un message de quelqu'un : "+sender);
                    if(message.startsWith("DEMANDE:")){
                        String info = message.substring(8) ;
                        String[] elements = info.split(",") ;
                        for(String e : elements){System.out.println(e);}
                        User user = new User() ;
                        user.numero = medecinContainer.medecinInterface.users.size() + 1 ;
                        user.nom = elements[0] ;
                        user.age = Integer.parseInt(elements[1]) ;
                        user.sexe = elements[2] ;
                        medecinContainer.medecinInterface.users.add(user) ;
                        medecinContainer.writeDemandeFile();
                        System.out.println("Utilisateur ajoute");
                    }
                    if(message.startsWith("DISCUSSION:")){
                        String info = message.substring(11) ;
                        String[] elements = info.split(",") ;
                        medecinContainer.writeInDiscussion(elements[0],elements[1]);
                    }
                    if(message.startsWith("SYMPTOME:")){
                        String info = message.substring(9) ;
                        String[] elements = info.split(",") ;
                        List<String> symptomes = new ArrayList<>() ;
                        for(int i = 1 ; i < elements.length ; i++){
                            symptomes.add(elements[i]) ;
                        }
                        String diagnostic = ExpertSystem.diagnostiquer(symptomes) ;
                        medecinContainer.writeDiagnostic(elements[0],diagnostic);
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
        throw new UnsupportedOperationException("NOT SUPPORT YET") ;
    }

    public void writeMessage(String fileName,String contenu){

    }
}
