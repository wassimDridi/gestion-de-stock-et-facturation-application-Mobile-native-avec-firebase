package com.example.scannerajouter;

import java.util.List;

public class Facture {
    private int id;
    private List<Produit> listProduit;
    private double totalPrix;
    private int quantite;
    private String nomClient;

    // Constructor with parameters including quantite
    public Facture(int id, List<Produit> listProduit, double totalPrix, int quantite, String nomClient) {
        this.id = id;
        this.listProduit = listProduit;
        this.totalPrix = totalPrix;
        this.quantite = quantite;
        this.nomClient = nomClient;
    }

    // Constructor without quantite
    public Facture(int id, List<Produit> listProduit, double totalPrix, String nomClient) {
        this(id, listProduit, totalPrix, 0, nomClient);
    }

    // Default constructor
    public Facture() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Produit> getListProduit() {
        return listProduit;
    }

    public void setListProduit(List<Produit> listProduit) {
        this.listProduit = listProduit;
    }

    public double getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(double totalPrix) {
        this.totalPrix = totalPrix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }
}
