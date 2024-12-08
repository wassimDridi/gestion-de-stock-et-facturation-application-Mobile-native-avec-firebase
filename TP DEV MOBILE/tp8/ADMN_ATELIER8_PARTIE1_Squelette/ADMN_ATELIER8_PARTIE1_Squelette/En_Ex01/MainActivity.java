package com.connexion;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText edUtilisateur;
	private EditText edPass;
	private Button btnValider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

	}

	private void init() {
		edUtilisateur = (EditText) findViewById(R.id.edUtilisateur);
		edPass = (EditText) findViewById(R.id.edPass);
		btnValider = (Button) findViewById(R.id.btnValider);
		ajouterEcouteur();
	}

	private void ajouterEcouteur() {

	}

}
