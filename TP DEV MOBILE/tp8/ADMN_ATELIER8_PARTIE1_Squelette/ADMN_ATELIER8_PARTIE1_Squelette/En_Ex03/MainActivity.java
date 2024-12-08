package com.profiltel;

import androidx.appcompat.app.AppCompatActivity;

import javax.naming.Context;
import javax.swing.text.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Spinner spSA;
    private CheckBox chVA;
    private SeekBar seekVA;
    private Spinner spSM;
    private CheckBox chVM;
    private SeekBar seekVM;
    private RadioGroup rdgSonClavier;
    private Button btnEnregister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        spSA=findViewById(R.id.spSA);
        chVA=findViewById(R.id.chVA);
        seekVA=findViewById(R.id.seekVA);
        spSM=findViewById(R.id.spSM);
        chVM=findViewById(R.id.chVM);
        seekVM=findViewById(R.id.seekVM);
        rdgSonClavier=findViewById(R.id.rdgSonClavier);
        btnEnregister=findViewById(R.id.btnEnregister);

    }
    
    
}