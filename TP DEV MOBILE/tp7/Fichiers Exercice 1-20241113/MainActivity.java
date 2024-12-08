package com.samihadhri.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends Activity implements OnClickListener {
    private EditText edTexte;
    private Button btnPasser1;
    private Button btnPasser2;
    private CheckBox check;
    private SeekBar seek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiser();
    }

    private void initialiser() {
        edTexte = findViewById(R.id.edTexte);
        btnPasser1 = findViewById(R.id.btnPasser1);
        btnPasser2 = findViewById(R.id.btnPasser2);
        check = findViewById(R.id.check);
        seek = findViewById(R.id.seek);


    }

    @Override
    public void onClick(View view) {

    }

    protected void passer1() {





    }

    protected void passer2() {






    }

}
