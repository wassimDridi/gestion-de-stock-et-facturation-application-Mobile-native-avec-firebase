package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutProduit extends AppCompatActivity {

    private FirebaseFirestore db; // Firestore instance
    private EditText inputDesignation, inputPrix, inputQuantite;
    private Button btnScan, btnSubmitProduit;
    private String scannedCodeBar = null; // Store the scanned barcode value
    private GmsBarcodeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        inputDesignation = findViewById(R.id.inputDesignation);
        inputPrix = findViewById(R.id.inputPrix);
        inputQuantite = findViewById(R.id.inputQuantite);
        btnScan = findViewById(R.id.btnScan);
        btnSubmitProduit = findViewById(R.id.btnSubmitProduit);

        // Initialize Scanner
        initScanner();

        // Set up event listeners
        btnScan.setOnClickListener(view -> scanBarcode());
        btnSubmitProduit.setOnClickListener(view -> ajouterProduit());
    }

    // Initialize the barcode scanner
    private void initScanner() {
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE,
                        Barcode.FORMAT_EAN_13,
                        Barcode.FORMAT_ALL_FORMATS)
                .build();

        scanner = GmsBarcodeScanning.getClient(this, options);
    }

    // Scan a barcode
    private void scanBarcode() {
        scanner.startScan()
                .addOnSuccessListener(barcode -> {
                    scannedCodeBar = barcode.getRawValue();
                    Toast.makeText(this, "Code-barres scanné: " + scannedCodeBar, Toast.LENGTH_SHORT).show();
                })
                .addOnCanceledListener(() ->
                        Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur de scan : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    // Ajouter un produit à Firestore
    private void ajouterProduit() {
        String designation = inputDesignation.getText().toString().trim();
        String prixString = inputPrix.getText().toString().trim();
        String quantiteString = inputQuantite.getText().toString().trim();

        // Validate fields
        if (designation.isEmpty() || prixString.isEmpty() || quantiteString.isEmpty() || scannedCodeBar == null) {
            Toast.makeText(this, "Veuillez remplir tous les champs et scanner un code-barres.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double prix = Double.parseDouble(prixString);
            int quantite = Integer.parseInt(quantiteString);

            // Create product object
            Produit produit = new Produit(designation, prix, quantite, scannedCodeBar);

            // Add product to Firestore
            db.collection("produits").add(produit)
                    .addOnSuccessListener(documentReference ->
                            Toast.makeText(this, "Produit ajouté avec succès !", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erreur d'ajout : " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Prix ou quantité invalide.", Toast.LENGTH_SHORT).show();
        }
    }
}
