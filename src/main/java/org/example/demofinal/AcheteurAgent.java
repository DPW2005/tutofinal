package org.example.demofinal;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class AcheteurAgent extends GuiAgent {

    protected AcheteurGui gui ;

    @Override
    protected void setup(){
        if(getArguments().length == 1 ){
            gui = (AcheteurGui) getArguments()[0] ;
            gui.acheteurAgent = this ;
        }
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour() ;
        addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive() ;
                if(aclMessage != null){
                    gui.logMessage(aclMessage);
                }
                else{
                    block();
                }
            }
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
