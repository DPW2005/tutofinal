package org.example.demofinal.test;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentA2 extends Agent {

    @Override
    protected void setup(){
        ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM) ;
        aclMessage.addReceiver(new AID("AgentA1",AID.ISLOCALNAME));
        aclMessage.setContent("Bonjour mon bro");
        send(aclMessage);
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
