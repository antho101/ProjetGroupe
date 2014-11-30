package com.example.projetgroupe;

import modele.UserDB;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class connexion extends Activity{
	private Button button1=null;
	private EditText log;
	private EditText mdp;
	private String log2;
	private String mdp2;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accueil);
		log=(EditText) findViewById(R.id.mail);
		log2=log.getText().toString();
		mdp=(EditText) findViewById(R.id.password);
		mdp2=mdp.getText().toString();
		button1= (Button)findViewById(R.id.button1);
		
		button1.setOnClickListener(
			new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UserDB u =new UserDB(log2,mdp2);
					try {
						u.checkLogin();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}
		);
	}
}
