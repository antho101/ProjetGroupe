package com.example.projetgroupe;

import java.sql.Connection;

import modele.UserDB;
import myconnections.DBConnection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity{
    DBConnection dbc = new DBConnection();
    Connection con = dbc.getConnection();
    
	private Button button1=null;
	private EditText log, mdp;
	private String logTmp, mdpTmp;
	
	public  final  static  int CHOOSE_BUTTON_REQUEST=0;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		log=(EditText) findViewById(R.id.mail);
		mdp=(EditText) findViewById(R.id.password);
		button1= (Button)findViewById(R.id.button1);
		button1.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					logTmp=log.getText().toString();
					mdpTmp=mdp.getText().toString();
					UserDB.setConnection(con);
					UserDB u =new UserDB(logTmp,mdpTmp);
					Toast.makeText(getApplicationContext(), "User : "+ u.toString(), Toast.LENGTH_SHORT).show();
					try {
						if(u.checkLogin()){
							Toast.makeText(getApplicationContext(), "Connexion réussis", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(), "Connexion refusé", Toast.LENGTH_SHORT).show();
						}
						//startActivityForResult(secondeActivite,CHOOSE_BUTTON_REQUEST);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					
								
				}
			}
		);
	}
}