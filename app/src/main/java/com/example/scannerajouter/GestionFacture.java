package com.example.scannerajouter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GestionFacture extends AppCompatActivity {

    private Button btnAjoutF;
    private Button btnSuppressionF;
    private Button btnModificationF;
    private Button btnConsultationF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_facture);

        // Initialize buttons
        btnAjoutF = findViewById(R.id.btnAjoutF);
        btnSuppressionF = findViewById(R.id.btnSuppressionF);
        btnModificationF = findViewById(R.id.btnModificationF);
        btnConsultationF = findViewById(R.id.btnConsultationF);

        // Set click listeners
        btnAjoutF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionFacture.this, AjoutFacture.class);
                startActivity(intent);
            }
        });

        btnSuppressionF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionFacture.this, SuppressionFacture.class);
                startActivity(intent);
            }
        });

        btnModificationF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionFacture.this, ModificationFacture.class);
                startActivity(intent);
            }
        });

        btnConsultationF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionFacture.this, ConsultationFacture.class);
                startActivity(intent);
            }
        });
    }
}
