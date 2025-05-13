package org.example.demofinal.test;

import jade.wrapper.ControllerException ;
import jade.core.Agent ;

public class ConsommateurAgent extends Agent {

    @Override
    protected void setup(){
        System.out.println("Initialisation de l'agent "+this.getAID().getName()) ;
    }

    @Override
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
}