package org.example.demofinal.medecin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class MedecinFichier {

    public void write(String fileName, String contenu) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(contenu);
            System.out.println("Le contenu a ete ecrit dans le fichier avec succes");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String read(String fileName) {
        StringBuilder contenu = new StringBuilder() ;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line ;
            while ((line = reader.readLine()) != null){
                contenu.append(line).append("\n") ;
            }
        }
        catch(Exception e ){
            e.printStackTrace();
        }
        return contenu.toString() ;
    }
}