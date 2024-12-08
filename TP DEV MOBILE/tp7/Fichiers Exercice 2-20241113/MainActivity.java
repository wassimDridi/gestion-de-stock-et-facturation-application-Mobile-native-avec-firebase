package com.samihadhri.myapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnNouveau;
    private ListView lstPersonne;
    private ArrayAdapter<Personne> adpPersonne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiser();
    }

    private void initialiser() {
        btnNouveau= findViewById(R.id.btnNouveau);
        lstPersonne= findViewById(R.id.lstPersonne);
        adpPersonne= new ArrayAdapter<Personne>(this, android.R.layout.simple_list_item_1);
        lstPersonne.setAdapter(adpPersonne);
        ecouteurs();
    }

    private void ecouteurs() {
        btnNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this,Nouveau.class);
               
            }
        });
    }

    ActivityResultLauncher<Intent> ActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {




                }
            });
}
