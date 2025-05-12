module org.example.demofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires jade;
    requires java.json;


    opens org.example.demofinal to javafx.fxml;
    exports org.example.demofinal;
}