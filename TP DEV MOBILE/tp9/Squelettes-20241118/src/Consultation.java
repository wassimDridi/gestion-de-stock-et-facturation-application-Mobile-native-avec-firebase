package Squelettes;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.samihadhri.firestore.Livre;
import com.samihadhri.firestore.R;

public class Consultation extends AppCompatActivity {

    private FirebaseFirestore db;
    private ListView lstLivre;
    private Button btnRetour;
    private ArrayAdapter<Livre> adpLivre;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        initialiser();
        ecouteurs();
        remplir();
    }

    private void initialiser() {
        db = FirebaseFirestore.getInstance();
        lstLivre = findViewById(R.id.lstLivre);
        btnRetour = findViewById(R.id.btnRetour);
        adpLivre = new ArrayAdapter<Livre>(this, android.R.layout.simple_list_item_1);
        lstLivre.setAdapter(adpLivre);
    }

    private void ecouteurs() {
        btnRetour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void remplir() {

    }
}