package com.example.scannerajouter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btngestionF;
    private Button btngestionP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiser();
        ecouteurs();

    }
    private void initialiser() {
        btngestionF = findViewById(R.id.btngestionF);
        btngestionP = findViewById(R.id.btngestionP);
    }
    private void ecouteurs() {
        btngestionF.setOnClickListener(this);
        btngestionP.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.btngestionP:
                i = new Intent(MainActivity.this, GestionProduit.class);
                break;
            case R.id.btngestionF:
                i = new Intent(MainActivity.this, GestionFacture.class);
                break;
        }
        startActivity(i);
    }


}