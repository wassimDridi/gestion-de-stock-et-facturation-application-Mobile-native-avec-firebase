package com.samihadhri.vignette;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edM;
    private Spinner spT;
    private SeekBar seekP;
    private RadioGroup rdgC;
    private RadioButton rdE;
    private Button btnA, btnE, btnV;
    private ListView lstA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiser();
    }
    private void initialiser() {
        edM = findViewById(R.id.edM);
        spT = findViewById(R.id.spT);
        seekP = findViewById(R.id.seekP);
        rdgC = findViewById(R.id.rdgC);
        rdE = findViewById(R.id.rdE);
        btnA = findViewById(R.id.btnA);
        btnE = findViewById(R.id.btnE);
        btnV = findViewById(R.id.btnV);
        lstA = findViewById(R.id.lstA);

    }
    private void ecouteurs() {
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouter();
            }
        });
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effacer();
            }
        });
        btnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vider();
            }
        });
        lstA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                afficher(position);
            }
        });
    }
    private void ajouter() {

    }

    private void effacer() {

    }

    private void vider() {

    }

    private void afficher(int position) {

    }
}
