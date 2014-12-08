package com.example.projetgroupe;

import modele.Session;

import com.example.projetgroupe.ActivityInscription.MyAccesInscriptionDB;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;

public class MonCompteFragment extends Fragment {
	private EditText mEditText;

	public MonCompteFragment() {
	}

	private Button btn_mycompte_pseudochange = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.fragment_moncompte);

		btn_mycompte_pseudochange = (Button) findViewById(R.id.btn_mycompte_pseudochange);
		btn_mycompte_pseudochange
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
					}
				});*/

	}

}