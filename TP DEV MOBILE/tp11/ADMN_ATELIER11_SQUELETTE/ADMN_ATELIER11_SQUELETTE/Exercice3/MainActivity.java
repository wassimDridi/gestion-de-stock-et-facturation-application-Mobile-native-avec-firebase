package com.traducteur;



public class MainActivity extends AppCompatActivity {
    private EditText edPhraseEn;
    private EditText edPhraseFr;
    private Button btnTraduire;
    private Translator enFrTranslator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initGraphique();
    }

    private void initGraphique() {
        edPhraseEn = findViewById(R.id.edPhraseEn);
        edPhraseFr = findViewById(R.id.edPhraseFr);
        btnTraduire = findViewById(R.id.btnTraduire);
        initTraducteur();
        ajouterEcouteurs();
    }
    private void initTraducteur() {
        
    }
    private void ajouterEcouteurs() {
        btnTraduire.setOnClickListener(view -> traduire());
    }

    private void traduire() {
        
    }


}