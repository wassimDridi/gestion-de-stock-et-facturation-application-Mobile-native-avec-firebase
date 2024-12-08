package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SuppressionFacture extends AppCompatActivity {

    private FirebaseFirestore db; // Instance Firestore
    private Spinner spinnerFactureIdsSF;
    private Button btnConfirmDeleteSF;
    private List<String> factureIds; // Liste des IDs Firestore des factures

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression_facture);

        // Initialisation Firestore et des composants UI
        db = FirebaseFirestore.getInstance();
        spinnerFactureIdsSF = findViewById(R.id.spinnerFactureIdsSF);
        btnConfirmDeleteSF = findViewById(R.id.btnConfirmDeleteSF);

        // Charger les factures dans le Spinner
        loadFactureIds();

        // Gestion du clic sur le bouton de suppression
        btnConfirmDeleteSF.setOnClickListener(v -> deleteFacture());
    }

    // Charger les IDs des factures dans le Spinner
    private void loadFactureIds() {
        factureIds = new ArrayList<>();
        db.collection("factures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        factureIds.add(doc.getId()); // Ajoute l'ID Firestore à la liste
                    }

                    if (factureIds.isEmpty()) {
                        Toast.makeText(this, "Aucune facture trouvée.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Configurer l'adaptateur pour le Spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this, android.R.layout.simple_spinner_item, factureIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFactureIdsSF.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur de chargement des factures : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Supprimer la facture sélectionnée
    private void deleteFacture() {
        if (spinnerFactureIdsSF.getSelectedItem() == null) {
            Toast.makeText(this, "Aucune facture sélectionnée.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer l'ID Firestore de la facture sélectionnée
        String selectedFactureId = spinnerFactureIdsSF.getSelectedItem().toString();

        db.collection("factures").document(selectedFactureId).delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Facture supprimée avec succès.", Toast.LENGTH_SHORT).show();
                    factureIds.remove(selectedFactureId); // Supprimer l'ID de la liste
                    loadFactureIds(); // Actualiser le Spinner
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erreur lors de la suppression : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
