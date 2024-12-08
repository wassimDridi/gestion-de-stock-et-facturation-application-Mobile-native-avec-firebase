package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AjoutFacture extends AppCompatActivity {

    private FirebaseFirestore db;
    private GmsBarcodeScanner scanner;
    private EditText inputProductName, inputQuantity, inputClientName, factureTotalPrix;
    private Button btnScan, produitAjout, factureAjout;
    private ListView listViewProducts;

    private ArrayList<Map<String, Object>> productList = new ArrayList<>();
    private double totalPrix = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_facture);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        inputProductName = findViewById(R.id.inputProductName);
        inputQuantity = findViewById(R.id.inputQuantity);
        inputClientName = findViewById(R.id.inputClientName);
        factureTotalPrix = findViewById(R.id.facture_totalprix);
        btnScan = findViewById(R.id.btnScan);
        produitAjout = findViewById(R.id.produit_ajout);
        factureAjout = findViewById(R.id.facture_ajout);
        listViewProducts = findViewById(R.id.listViewProducts);

        // Initialize Scanner
        initScanner();

        // Set up event listeners
        btnScan.setOnClickListener(view -> scanBarcode());
        produitAjout.setOnClickListener(view -> ajouterProduit());
        factureAjout.setOnClickListener(view -> ajouterFacture());
    }

    private void initScanner() {
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();

        scanner = GmsBarcodeScanning.getClient(this, options);
    }

    private void scanBarcode() {
        scanner.startScan()
                .addOnSuccessListener(barcode -> {
                    String codeBar = barcode.getRawValue();
                    getProduitFromFirebase(codeBar);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur de scan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void getProduitFromFirebase(String codeBar) {
        db.collection("produits")
                .whereEqualTo("codeBar", codeBar)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        inputProductName.setText(document.getString("designation"));
                        inputProductName.setEnabled(false); // Make it read-only
                        inputQuantity.requestFocus();
                    } else {
                        Toast.makeText(this, "Produit non trouvé.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void ajouterProduit() {
        String designation = inputProductName.getText().toString();
        String quantityString = inputQuantity.getText().toString();

        if (designation.isEmpty() || quantityString.isEmpty()) {
            Toast.makeText(this, "Veuillez scanner un produit et saisir une quantité.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantiteSaisie;
        try {
            quantiteSaisie = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantité invalide.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantiteSaisie <= 0) {
            Toast.makeText(this, "La quantité doit être supérieure à 0.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérifier si le produit existe et récupérer les données
        db.collection("produits")
                .whereEqualTo("designation", designation)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        int stock = document.getLong("quantite").intValue();
                        double prix = document.getDouble("prix");

                        if (quantiteSaisie > stock) {
                            Toast.makeText(this, "Quantité demandée dépasse le stock disponible.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Mise à jour du total et ajout à la liste
                        totalPrix += quantiteSaisie * prix;
                        factureTotalPrix.setText(String.valueOf(totalPrix));

                        Map<String, Object> produit = new HashMap<>();
                        produit.put("designation", designation);
                        produit.put("quantite", quantiteSaisie);
                        produit.put("prix", prix);
                        productList.add(produit);

                        Toast.makeText(this, "Produit ajouté.", Toast.LENGTH_SHORT).show();
                        inputProductName.setText("");
                        inputQuantity.setText("");
                        inputProductName.setEnabled(true);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void ajouterFacture() {
        String nomClient = inputClientName.getText().toString().trim();

        if (nomClient.isEmpty() || productList.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir le nom du client et ajouter des produits.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> facture = new HashMap<>();
        facture.put("nomClient", nomClient);
        facture.put("produits", productList);
        facture.put("totalPrix", totalPrix);

        db.collection("factures").add(facture)
                .addOnSuccessListener(documentReference -> Toast.makeText(this, "Facture ajoutée avec succès.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur d'ajout de la facture: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
