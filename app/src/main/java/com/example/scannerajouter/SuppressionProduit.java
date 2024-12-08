package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppressionProduit extends AppCompatActivity {

    private Spinner spinnerProductIds;
    private Button btnScan, btnConfirmDelete;
    private FirebaseFirestore db;
    private GmsBarcodeScanner scanner;
    private Map<String, String> productIdMap; // Map for product ID and Firestore document ID
    private String scannedCodeBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression_produit);

        // Initialize Firestore and scanner
        db = FirebaseFirestore.getInstance();
        initScanner();

        // Initialize UI components
        spinnerProductIds = findViewById(R.id.spinnerProductIds);
        btnScan = findViewById(R.id.btnScan);
        btnConfirmDelete = findViewById(R.id.btnConfirmDelete);

        productIdMap = new HashMap<>();

        // Load product IDs into the spinner
        loadProductIds();

        // Set up listeners
        spinnerProductIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // No specific action needed here for now
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        btnScan.setOnClickListener(v -> scanBarcode());
        btnConfirmDelete.setOnClickListener(v -> deleteProduct());
    }

    private void initScanner() {
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE,
                        Barcode.FORMAT_EAN_13,
                        Barcode.FORMAT_ALL_FORMATS)
                .enableAutoZoom()
                .build();
        scanner = GmsBarcodeScanning.getClient(this);
    }

    private void loadProductIds() {
        db.collection("produits").get().addOnSuccessListener(querySnapshot -> {
            List<String> productIds = new ArrayList<>();
            for (QueryDocumentSnapshot document : querySnapshot) {
                String codeBar = document.getString("codeBar");
                productIds.add(codeBar);
                productIdMap.put(codeBar, document.getId());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productIds);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerProductIds.setAdapter(adapter);


        }).addOnFailureListener(e ->
                Toast.makeText(this, "Erreur lors du chargement des produits : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void scanBarcode() {
        scanner.startScan()
                .addOnSuccessListener(barcode -> {
                    scannedCodeBar = barcode.getRawValue();
                    Toast.makeText(this, "Code-barres scanné : " + scannedCodeBar, Toast.LENGTH_SHORT).show();
                })
                .addOnCanceledListener(() ->
                        Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur de scan : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    private void deleteProduct() {
        String selectedCodeBar;
        if (scannedCodeBar != null) {
            selectedCodeBar = scannedCodeBar;
        } else {
            selectedCodeBar = spinnerProductIds.getSelectedItem().toString();
        }

        if (!productIdMap.containsKey(selectedCodeBar)) {
            Toast.makeText(this, "Produit non trouvé.", Toast.LENGTH_SHORT).show();
            return;
        }

        String documentId = productIdMap.get(selectedCodeBar);

        db.collection("produits").document(documentId).delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Produit supprimé avec succès.", Toast.LENGTH_SHORT).show();
                    productIdMap.remove(selectedCodeBar);
                    loadProductIds(); // Refresh spinner
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur lors de la suppression : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }
}
