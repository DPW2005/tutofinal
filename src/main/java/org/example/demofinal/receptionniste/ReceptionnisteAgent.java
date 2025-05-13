package org.example.demofinal.receptionniste;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
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
                    System.out.println("On a recu un message de quelqu'un : "+sender);
                    switch (sender){
                        case("PatientAgent@192.168.56.1:1099/JADE") :
                            if(aclMessage.getPerformative() == 16){
                                String userMessage = aclMessage.getContent() ;
                                JsonParser parser = new JsonParser() ;
                                try{
                                    Object object = parser.parse(userMessage) ;
                                    JsonObject userJson = (JsonObject) object ;
                                    User user = new User(1,userJson.get("name").toString(),Integer.parseInt(userJson.get("age").toString()),userJson.get("sexe").toString()) ;
                                    //gui.consultation.add(consultation) ;
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        default: break;
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
