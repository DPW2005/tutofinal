package org.example.demofinal.test;

import jade.core.Runtime ;
import jade.core.Profile ;
import jade.core.ProfileImpl ;
import jade.util.leap.Properties ;
import jade.util.ExtendedProperties ;
import jade.wrapper.AgentContainer ;
import jade.wrapper.AgentController;

public class ConsommateurContainer {
    public static void main(String[] args) throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI, "false") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer agentContainer = runtime.createAgentContainer(profile) ;
        AgentController agentController = agentContainer.createNewAgent("Consommateur", ConsommateurAgent.class.getName(), new Object[]{}) ;
        agentController.start() ;
    }
}