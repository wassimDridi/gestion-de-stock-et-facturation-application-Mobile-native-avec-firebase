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

public class ModificationProduit extends AppCompatActivity {

    private FirebaseFirestore db; // Instance Firestore
    private Spinner spinnerProductIds;
    private EditText inputDesignation, inputPrix, inputQuantite;
    private Button btnSaveChanges;

    private Map<String, String> productIdMap; // Map to store product IDs and Firestore document IDs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_produit);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        spinnerProductIds = findViewById(R.id.spinnerProductIds);
        inputDesignation = findViewById(R.id.inputDesignation);
        inputPrix = findViewById(R.id.inputPrix);
        inputQuantite = findViewById(R.id.inputQuantite);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        productIdMap = new HashMap<>();

        // Load products into Spinner
        loadProducts();

        // Set event listener for Save Changes button
        btnSaveChanges.setOnClickListener(view -> saveChanges());

        // Populate fields when a product is selected
        spinnerProductIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProductId = spinnerProductIds.getSelectedItem().toString();
                if (productIdMap.containsKey(selectedProductId)) {
                    loadProductDetails(productIdMap.get(selectedProductId));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aucune action requise si aucun élément n'est sélectionné
            }
        });

    }

    // Load products into Spinner
    private void loadProducts() {
        db.collection("produits")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<String> productIds = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String id = document.getId();
                        String codeBar = document.getString("codeBar");
                        productIds.add(codeBar);
                        productIdMap.put(codeBar, id);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProductIds.setAdapter(adapter);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur lors du chargement des produits.", Toast.LENGTH_SHORT).show());
    }

    // Load product details into input fields
    private void loadProductDetails(String documentId) {
        db.collection("produits").document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        inputDesignation.setText(documentSnapshot.getString("designation"));
                        inputPrix.setText(String.valueOf(documentSnapshot.getDouble("prix")));
                        inputQuantite.setText(String.valueOf(documentSnapshot.getLong("quantite")));
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur lors du chargement du produit.", Toast.LENGTH_SHORT).show());
    }

    // Save changes to Firestore
    private void saveChanges() {
        String selectedProductId = spinnerProductIds.getSelectedItem().toString();
        String documentId = productIdMap.get(selectedProductId);

        String designation = inputDesignation.getText().toString().trim();
        String prixString = inputPrix.getText().toString().trim();
        String quantiteString = inputQuantite.getText().toString().trim();

        // Validate fields
        if (designation.isEmpty() || prixString.isEmpty() || quantiteString.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double prix = Double.parseDouble(prixString);
            int quantite = Integer.parseInt(quantiteString);

            // Update product details in Firestore
            Map<String, Object> updatedProduct = new HashMap<>();
            updatedProduct.put("designation", designation);
            updatedProduct.put("prix", prix);
            updatedProduct.put("quantite", quantite);

            db.collection("produits").document(documentId)
                    .update(updatedProduct)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Produit mis à jour avec succès !", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erreur lors de la mise à jour : " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Prix ou quantité invalide.", Toast.LENGTH_SHORT).show();
        }
    }
}
