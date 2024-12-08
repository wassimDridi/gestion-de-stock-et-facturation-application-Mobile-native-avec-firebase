package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ConsultationFacture extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText inputClientName;
    private Spinner spinnerFactureId;
    private Button btnConsulterFacture;
    private TextView tvFactureDetails;

    private List<String> factureIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_facture);

        // Initialisation de Firestore
        db = FirebaseFirestore.getInstance();

        // Initialisation des composants UI
        inputClientName = findViewById(R.id.inputClientName);
        spinnerFactureId = findViewById(R.id.spinnerFactureId);
        btnConsulterFacture = findViewById(R.id.btnConsulterFacture);
        tvFactureDetails = findViewById(R.id.tvFactureDetails);

        // Charger les IDs des factures dans le Spinner
        loadFactureIds();

        // Action sur le bouton "Consulter"
        btnConsulterFacture.setOnClickListener(view -> {
            String clientName = inputClientName.getText().toString().trim();
            String selectedFactureId = spinnerFactureId.getSelectedItem() != null
                    ? spinnerFactureId.getSelectedItem().toString()
                    : "";

            if (!clientName.isEmpty()) {
                // Rechercher la facture par nom du client
                loadFactureByClientName(clientName);
            } else if (!selectedFactureId.isEmpty()) {
                // Rechercher la facture par ID
                loadFactureById(selectedFactureId);
            } else {
                Toast.makeText(this, "Veuillez entrer un nom de client ou sélectionner un ID de facture.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFactureIds() {
        factureIds = new ArrayList<>();
        db.collection("factures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        factureIds.add(doc.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this, android.R.layout.simple_spinner_item, factureIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFactureId.setAdapter(adapter);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur de chargement des factures : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void loadFactureById(String factureId) {
        db.collection("factures").document(factureId).get()
                .addOnSuccessListener(this::showFactureDetails)
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void loadFactureByClientName(String clientName) {
        db.collection("factures")
                .whereEqualTo("nomClient", clientName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        showFactureDetails(doc);
                    } else {
                        Toast.makeText(this, "Aucune facture trouvée pour le nom : " + clientName, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void showFactureDetails(DocumentSnapshot doc) {
        if (doc != null && doc.exists()) {
            String nomClient = doc.getString("nomClient");
            String produits = doc.get("listProduit") != null ? doc.get("listProduit").toString() : "N/A";
            double totalPrix = doc.getDouble("totalPrix") != null ? doc.getDouble("totalPrix") : 0.0;
            int quantite = doc.getLong("quantite") != null ? doc.getLong("quantite").intValue() : 0;

            String details = "Nom du client : " + nomClient +
                    "\nProduits : " + produits +
                    "\nPrix total : " + totalPrix + " TND" +
                    "\nQuantité : " + quantite;

            tvFactureDetails.setText(details);
        } else {
            Toast.makeText(this, "Facture introuvable.", Toast.LENGTH_SHORT).show();
        }
    }
}
