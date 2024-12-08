package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModificationFacture extends AppCompatActivity {

    private FirebaseFirestore db; // Instance Firestore
    private Spinner spinnerFactureIds;
    private EditText inputPrixTotal, inputRemise;
    private Button btnSaveChanges;

    private Map<String, String> factureIdMap; // Map to store invoice IDs and Firestore document IDs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_facture);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        spinnerFactureIds = findViewById(R.id.spinnerFactureIds);
        inputPrixTotal = findViewById(R.id.inputPrixTotal);
        inputRemise = findViewById(R.id.inputRemise);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        factureIdMap = new HashMap<>();

        // Load invoices into Spinner
        loadFactures();

        // Save button click listener
        btnSaveChanges.setOnClickListener(view -> saveChanges());

        // Populate fields when an invoice is selected
        spinnerFactureIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFactureId = spinnerFactureIds.getSelectedItem().toString();
                if (factureIdMap.containsKey(selectedFactureId)) {
                    loadFactureDetails(factureIdMap.get(selectedFactureId));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    // Load invoices into Spinner
    private void loadFactures() {
        db.collection("factures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<String> factureIds = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String id = document.getId();
                        String codeFacture = document.getString("codeFacture");
                        factureIds.add(codeFacture);
                        factureIdMap.put(codeFacture, id);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, factureIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFactureIds.setAdapter(adapter);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur lors du chargement des factures.", Toast.LENGTH_SHORT).show());
    }

    // Load invoice details into fields
    private void loadFactureDetails(String documentId) {
        db.collection("factures").document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        inputPrixTotal.setText(String.valueOf(documentSnapshot.getDouble("prixTotal")));
                        inputRemise.setText(String.valueOf(documentSnapshot.getDouble("remise")));
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur lors du chargement de la facture.", Toast.LENGTH_SHORT).show());
    }

    // Save changes to Firestore
    private void saveChanges() {
        String selectedFactureId = spinnerFactureIds.getSelectedItem().toString();
        String documentId = factureIdMap.get(selectedFactureId);

        String prixTotalString = inputPrixTotal.getText().toString().trim();
        String remiseString = inputRemise.getText().toString().trim();

        // Validate fields
        if (prixTotalString.isEmpty() || remiseString.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double prixTotal = Double.parseDouble(prixTotalString);
            double remise = Double.parseDouble(remiseString);

            // Update invoice details in Firestore
            Map<String, Object> updatedFacture = new HashMap<>();
            updatedFacture.put("prixTotal", prixTotal);
            updatedFacture.put("remise", remise);

            db.collection("factures").document(documentId)
                    .update(updatedFacture)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Facture mise à jour avec succès !", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erreur lors de la mise à jour : " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Prix ou remise invalide.", Toast.LENGTH_SHORT).show();
        }
    }
}
