package Squelettes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.samihadhri.firestore.Livre;
import com.samihadhri.firestore.R;

public class Modification extends AppCompatActivity {
    private FirebaseFirestore db;
    private Spinner spLivre;
    private EditText edTitre;
    private EditText edNbPage;
    private Button btnModifier;
    private Button btnRetour;
    private ArrayAdapter<Livre> adpLivre;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        initialiser();
        ecouteurs();
        remplir();
    }

    private void initialiser() {
        db = FirebaseFirestore.getInstance();
        spLivre = findViewById(R.id.spLivre);
        edTitre = findViewById(R.id.edTitre);
        edNbPage = findViewById(R.id.edNbPage);
        btnModifier = findViewById(R.id.btnModifier);
        btnRetour = findViewById(R.id.btnRetour);
        adpLivre = new ArrayAdapter<Livre>(this, android.R.layout.simple_list_item_1);
        adpLivre.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spLivre.setAdapter(adpLivre);
    }

    private void ecouteurs() {
        // TODO Auto-generated method stub
        btnModifier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                modifierLivre();

            }
        });
        btnRetour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spLivre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                actualiser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    protected void actualiser() {
        if (spLivre.getSelectedItemPosition() >= 0) {
            Livre l = (Livre) spLivre.getSelectedItem();
            edTitre.setText(l.getTitre());
            edNbPage.setText(l.getNbpage() + "");
        }
    }

    private void remplir() {

    }
    protected void modifierLivre() {

    }
}