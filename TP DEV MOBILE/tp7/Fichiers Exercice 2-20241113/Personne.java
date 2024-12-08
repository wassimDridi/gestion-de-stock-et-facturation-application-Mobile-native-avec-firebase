package com.samihadhri.myapp;

import java.io.Serializable;

public class Personne implements Serializable {
    private String nom;
    private String prenom;
    private int age;

    public Personne(String nom, String prenom, int age) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + "(" + age + ")";
    }
}
