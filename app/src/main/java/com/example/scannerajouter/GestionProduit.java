package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GestionProduit extends AppCompatActivity implements View.OnClickListener {

    // Déclaration des boutons
    private Button btnAjoutP;
    private Button btnRetourP;
    private Button btnConsultationP;
    private Button btnModificationP;
    private Button btnSuppressionP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_produit); // Assurez-vous que ce fichier correspond au bon fichier XML
        initialiser(); // Initialisation des composants
        ecouteurs(); // Ajout des écouteurs pour les boutons
    }

    // Initialiser les boutons
    private void initialiser() {
        btnAjoutP = findViewById(R.id.btnAjoutP);
        btnRetourP = findViewById(R.id.btnretourP);
        btnConsultationP = findViewById(R.id.btnConsultationP);
        btnModificationP = findViewById(R.id.btnModificationP);
        btnSuppressionP = findViewById(R.id.btnSuppressionP);
    }

    // Ajouter les écouteurs
    private void ecouteurs() {
        btnAjoutP.setOnClickListener(this);
        btnRetourP.setOnClickListener(this);
        btnConsultationP.setOnClickListener(this);
        btnModificationP.setOnClickListener(this);
        btnSuppressionP.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = null;

        switch (view.getId()) {
            case R.id.btnAjoutP: // Bouton pour Ajouter Produit
                i = new Intent(GestionProduit.this, AjoutProduit.class);
                break;

            case R.id.btnConsultationP: // Bouton pour Consulter Produit
                i = new Intent(GestionProduit.this, ConsultationProduit.class);
                break;

            case R.id.btnModificationP: // Bouton pour Modifier Produit
                i = new Intent(GestionProduit.this, ModificationProduit.class);
                break;

            case R.id.btnSuppressionP: // Bouton pour Supprimer Produit
                i = new Intent(GestionProduit.this, SuppressionProduit.class);
                break;

            case R.id.btnretourP: // Bouton pour Retour
                finish(); // Ferme l'activité actuelle et revient à la précédente
                break;
        }

        // Lancer l'activité si l'intent n'est pas nul
        if (i != null) {
            startActivity(i);
        }
    }
}
