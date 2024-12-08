package com.prix;


public class MainActivity extends AppCompatActivity {
    private Button btnSuivant;
    private Button btnAnalyser;
    private ImageView imgProduit;
    private TextView tvPrix;
    private int[] tImage = new int[] { R.drawable.eau_m1,R.drawable.eau_m2,R.drawable.eau_safia };
    private int indiceCourant;
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
        btnSuivant=findViewById(R.id.btnSuivant);
        btnAnalyser=findViewById(R.id.btnAnalyser);
        imgProduit=findViewById(R.id.imgProduit);
        tvPrix=findViewById(R.id.tvPrix);
        indiceCourant=0;
        ajouterEcouteurs();
    }

    private void ajouterEcouteurs() {
        btnAnalyser.setOnClickListener(view -> analyser());
        btnSuivant.setOnClickListener(view -> suivant());
    }
    private void suivant() {
        indiceCourant = (indiceCourant + 1) % tImage.length;
        imgProduit.setImageResource(tImage[indiceCourant]);
        analyser();
    }

    private void analyser() {
        

    }

    private void afficherPrix(List<Barcode> barcodes) {
        
    }

    private String getPrix(String rawValue) {
        String prix="Le prix ";
        return prix;
    }


}