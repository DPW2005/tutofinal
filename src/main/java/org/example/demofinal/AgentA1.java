package org.example.demofinal;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentA1 extends Agent {

    @Override
    protected void setup(){
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive() ;
                if(aclMessage != null){
                    System.out.println(aclMessage.getContent()) ;
                    ACLMessage message = new ACLMessage(ACLMessage.CONFIRM) ;
                    message.addReceiver(aclMessage.getSender());
                    message.setContent("Bonjour merci");
                    send(message);
                }
            }
        });
    }
}
