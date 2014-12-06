package com.example.projetgroupe;

import java.sql.Connection;

import modele.UserDB;
import myconnections.DBConnection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityInscription extends Activity {

	private Connection con = null;
	UserDB u = null;
	private EditText pseudo, log, mdp;
	private String logTmp, mdpTmp, pseudoTmp;
	private Button btn_register_sub = null, btn_register_b = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_inscription);
		btn_register_sub = (Button) findViewById(R.id.btn_register_sub);
		btn_register_sub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyAccesInscriptionDB adb = new MyAccesInscriptionDB(
						ActivityInscription.this);
				adb.execute();
			}
		});
		btn_register_b = (Button) findViewById(R.id.btn_register_b);
		btn_register_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityInscription.this.finish();
			}
		});

	}

	class MyAccesInscriptionDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		ActivityInscription activityParent = null;

		public MyAccesInscriptionDB(ActivityInscription activityInscription) {
			// TODO Auto-generated constructor stub
			activityParent = activityInscription;
		}

		public MyAccesInscriptionDB(MainActivity pActivity) {

			link(pActivity);
			// TODO Auto-generated constructor stub
		}

		private void link(MainActivity pActivity) {
			// TODO Auto-generated method stub

		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(ActivityInscription.this);
			pgd.setMessage("inscription en cours");
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();

		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
				if (con == null) {
					resultat = "Erreur : vérifier la connexion internet !";
					return false;
				}

				UserDB.setConnection(con);
			}
			log = (EditText) findViewById(R.id.mail);
			mdp = (EditText) findViewById(R.id.password);
			pseudo = (EditText) findViewById(R.id.Pseudo);
			logTmp = log.getText().toString();
			mdpTmp = mdp.getText().toString();
			pseudoTmp = pseudo.getText().toString();
			logTmp="hh@gmail.be";
			mdpTmp="hh";
			pseudoTmp="hh";
			// ligne pour éviter de tapper h24 les logins quand on try le projet
			// logTmp = "alex7170@gmail.com";
			// mdpTmp = "azerty";
			String email = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$";
                   
			if (!pseudoTmp.isEmpty()) {
				if (!logTmp.isEmpty()) {
					if (logTmp.matches(email)) {
						if (!mdpTmp.isEmpty()) {

							try {
								u = new UserDB(pseudoTmp, logTmp, mdpTmp);
								u.create();
								ok = true;

							} catch (Exception e) {
								resultat = e.getMessage();
								ok = false;
							}

						} else {
							ok = false;
							resultat = "Veuillez entrez un mot de passe.";						
						}
					}
					else{
						ok = false;
						resultat = "Veuillez entrez une adresse email valide.";
					
					}

				} else {
					ok = false;
					resultat = "Veuillez entre votre email.";
				}
			} else {
				ok = false;
				resultat = "Veuillez entrez votre pseudo.";
			}
			return ok;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			pgd.dismiss();
			if (ok) {
				pgd.dismiss();
				Toast.makeText(getApplicationContext(), "Inscription réussi, veuillez vous connecter !",
						Toast.LENGTH_SHORT).show();
				activityParent.finish();
			} else {
				Toast.makeText(getApplicationContext(), resultat,
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
