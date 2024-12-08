package com.samihadhri.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Nouveau extends AppCompatActivity {
    private EditText edNom;
    private EditText edPrenom;
    private EditText edAge;
    private Button btnValider;
    private Button btnAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau);
        intialiser();
    }

    private void intialiser() {
        edNom = findViewById(R.id.edNom);
        edPrenom = findViewById(R.id.edPrenom);
        edAge = findViewById(R.id.edAge);
        btnValider = findViewById(R.id.btnValider);
        btnAnnuler = findViewById(R.id.btnAnnuler);
        ecouteur();
    }

    private void ecouteur() {
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                valider();
            }
        });

        btnAnnuler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                annuler();
            }
        });

    }

    protected void valider() {





    }

    protected void annuler() {


    }
}
