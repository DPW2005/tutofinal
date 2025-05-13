package org.example.demofinal;

public  class User {
    private Integer numero;
    private String nom;
    private Integer age;
    private String sexe;
    private String status ;

    public User(Integer numero, String nom, Integer age, String sexe) {
        this.numero = numero;
        this.nom = nom;
        this.age = age;
        this.sexe = sexe;
    }

    public Integer getNumero() { return numero; }
    public String getNom() { return nom; }
    public Integer getAge() { return age; }
    public String getSexe() { return sexe; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
