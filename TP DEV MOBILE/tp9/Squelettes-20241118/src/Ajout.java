package Squelettes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.samihadhri.firestore.R;

public class Ajout extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText edTitre;
    private EditText edNbPage;
    private Button btnAjouter;
    private Button btnRetour;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        initialiser();
        ecouteurs();
    }

    private void initialiser() {
        db = FirebaseFirestore.getInstance();
        edTitre = findViewById(R.id.edTitre);
        edNbPage = findViewById(R.id.edNbPage);
        btnAjouter = findViewById(R.id.btnAjouter);
        btnRetour = findViewById(R.id.btnRetour);
    }

    private void ecouteurs() {
        btnAjouter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ajouterLivre();
            }
        });
        btnRetour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void ajouterLivre() {

    }
}