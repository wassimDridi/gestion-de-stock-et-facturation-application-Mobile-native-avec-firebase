package com.param;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final String PROTOCOLE = "http";
	public static final String SERVEUR = "10.0.2.2";
	public static final String PORT = "80";
	public static final String PAGE = "index.html";
	private EditText edProtocole;
	private EditText edServeur;
	private EditText edPort;
	private EditText edPage;
	private Button btnEnregistrer;
	private Button btnAfficher;
	private Button btnEffacer;
	private Button btnParamUsine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		edProtocole = (EditText) findViewById(R.id.edProtocole);
		edServeur = (EditText) findViewById(R.id.edServeur);
		edPort = (EditText) findViewById(R.id.edPort);
		edPage = (EditText) findViewById(R.id.edPage);
		btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);
		btnAfficher = (Button) findViewById(R.id.btnAfficher);
		btnEffacer = (Button) findViewById(R.id.btnEffacer);
		btnParamUsine = (Button) findViewById(R.id.btnParamUsine);
		ajouterEcouteur();

	}

	private void ajouterEcouteur() {

	}

}
