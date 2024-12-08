package com.bcs;


public class MainActivity extends AppCompatActivity {
    private Button btnScan;
    private ListView lstBc;
    private ArrayAdapter<String> adpBc;
    private GmsBarcodeScanner scanner;
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
        btnScan=findViewById(R.id.btnScan);
        lstBc=findViewById(R.id.lstBc);
        adpBc=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lstBc.setAdapter(adpBc);
        initScanner();
        ajouterEcouteurs();
    }

    private void initScanner() {
        
    }

    private void ajouterEcouteurs() {
        btnScan.setOnClickListener(view -> scan());
    }

    private void scan() {
        
    }
}