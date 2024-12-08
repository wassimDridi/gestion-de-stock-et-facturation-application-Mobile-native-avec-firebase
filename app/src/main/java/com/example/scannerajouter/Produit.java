package com.example.scannerajouter;

public class Produit {
    private String designation;
    private double prix;
    private int quantite;
    private String codeBar;

    public Produit(String designation, double prix, int quantite, String codeBar) {
        this.designation = designation;
        this.prix = prix;
        this.quantite = quantite;
        this.codeBar = codeBar;
    }

    // Getters et setters nécessaires pour Firestore
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getCodeBar() {
        return codeBar;
    }

    public void setCodeBar(String codeBar) {
        this.codeBar = codeBar;
    }
}
