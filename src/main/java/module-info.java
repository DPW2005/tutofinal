module org.example.demofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires jade;
    requires java.json;
    requires java.desktop;


    opens org.example.demofinal to javafx.fxml;
    exports org.example.demofinal;
    exports org.example.demofinal.medecin;
    exports org.example.demofinal.gestion_cabinet_medical_final;
    exports org.example.demofinal.test;
    opens org.example.demofinal.test to javafx.fxml;
}