package org.example.demofinal.test;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;

public class EssaiContainer {
    public static void main(String[] args) throws Exception{
        Runtime runtime = Runtime.instance() ;
        Properties properties = new ExtendedProperties() ;
        properties.setProperty(Profile.GUI,"true") ;
        Profile profile = new ProfileImpl(properties) ;
        AgentContainer mainContainer = runtime.createMainContainer(profile) ;
        //AgentController agentController1 = mainContainer.createNewAgent("AgentA1", AgentA1.class.getName(), new Object[]{}) ;
        //AgentController agentController2 = mainContainer.createNewAgent("AgentA2", AgentA2.class.getName(), new Object[]{}) ;
        mainContainer.start() ;
        //agentController1.start();
        //agentController2.start();
    }
}
