package org.example.demofinal.medecin;

import java.nio.file.*;

public class EcouteurFichierMedecin {

    public static void main(String[] args) throws Exception{
        Path filePath = Paths.get("org/example/demofinal/fichier/medecin.txt") ;
        WatchService watchService = FileSystems.getDefault().newWatchService();
        filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY) ;
        System.out.println("Surveillance du fichier : "+filePath) ;
        while (true){
            WatchKey key = watchService.take();
            for(WatchEvent<?> event : key.pollEvents()){
                if(event.context().toString().equals(filePath.getFileName().toString())){
                    System.out.println("Ecouteur fichier. Fichier modifier = true !");
                    MedecinAgent.fichierModifierMedecin = true ;
                }
            }
            boolean valid = key.reset();
            if (!valid) {
                break ;
            }
        }
        watchService.close();
    }
}
