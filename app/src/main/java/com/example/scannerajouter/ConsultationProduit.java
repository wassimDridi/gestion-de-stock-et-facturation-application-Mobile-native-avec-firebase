package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.ArrayList;
import java.util.List;

public class ConsultationProduit extends AppCompatActivity {

    private Spinner spinnerProductIds;
    private Button btnScan, btnConsultProduct;
    private FirebaseFirestore db;
    private GmsBarcodeScanner scanner;
    private List<String> productIds;
    private String scannedCodeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_produit);

        // Initialize UI components
        spinnerProductIds = findViewById(R.id.spinnerProductIds);
        btnScan = findViewById(R.id.btnScan);
        btnConsultProduct = findViewById(R.id.btnConsultProduct);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize Scanner
        initScanner();

        // Load product IDs into the Spinner
        loadProductIds();

        // Set up listeners
        btnScan.setOnClickListener(view -> scanBarcode());
        btnConsultProduct.setOnClickListener(view -> consultProduct());
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

    // Load product IDs from Firestore
    private void loadProductIds() {
        productIds = new ArrayList<>();
        db.collection("produits")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        productIds.add(doc.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this, android.R.layout.simple_spinner_item, productIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProductIds.setAdapter(adapter);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur de chargement des produits : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Scan a barcode
    private void scanBarcode() {
        scanner.startScan()
                .addOnSuccessListener(barcode -> {
                    scannedCodeBar = barcode.getRawValue();
                    fetchProductByCode(scannedCodeBar);
                })
                .addOnCanceledListener(() -> Toast.makeText(this, "Scan annulé.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur de scan : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    // Fetch product details by scanned code
    private void fetchProductByCode(String codeBar) {
        db.collection("produits")
                .whereEqualTo("codeBar", codeBar)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        showProductDetails(doc);
                    } else {
                        Toast.makeText(this, "Produit non trouvé.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Consult product based on selected ID or scanned code
    private void consultProduct() {
        if (scannedCodeBar != null) {
            fetchProductByCode(scannedCodeBar);
        } else {
            String selectedProductId = spinnerProductIds.getSelectedItem().toString();
            db.collection("produits").document(selectedProductId)
                    .get()
                    .addOnSuccessListener(this::showProductDetails)
                    .addOnFailureListener(e -> Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    // Display product details
    private void showProductDetails(DocumentSnapshot doc) {
        String details = "Désignation: " + doc.getString("designation") +
                "\nPrix: " + doc.getDouble("prix") +
                "\nQuantité: " + doc.getLong("quantite") +
                "\nCode-barres: " + doc.getString("codeBar");

        TextView tvProductDetails = findViewById(R.id.tvProductDetails);
        tvProductDetails.setText(details);
    }

}
