package com.example.projetgroupe;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.projetgroupe.MainActivity.MyAccesDB;

import modele.CarnetDB;
import modele.Session;
import modele.UserDB;
import myconnections.DBConnection;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CarnetFragment extends Fragment {
	Connection con = null;
	ListView list_carnet;
	ArrayList<CarnetDB> list_carnet_obj = null;
	ArrayList<String> list_carnet_titre = null;
	EditText nouveauCarnet = null;

	public CarnetFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_carnet, container,
				false);
		list_carnet_titre = new ArrayList<String>();
		list_carnet_obj = new ArrayList<CarnetDB>();
		list_carnet_obj.add(new CarnetDB(1, "Maison", 24, null));
		list_carnet_obj.add(new CarnetDB(2, "Ecole", 24, null));
		list_carnet_obj.add(new CarnetDB(3, "Travail", 24, null));

		if (list_carnet_obj.size() < 5) {
			list_carnet_titre.add("+ Ajouter un carnet ["
					+ list_carnet_obj.size() + "/5]");
		}
		for (int i = 0; i < 5; i++) {
			if (i < list_carnet_obj.size()) {
				list_carnet_titre.add(list_carnet_obj.get(i).getTitre());
			}
		}
		list_carnet = (ListView) rootView.findViewById(R.id.list_carnet);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				list_carnet_titre) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text = (TextView) view
						.findViewById(android.R.id.text1);
				if (list_carnet_obj.size() < 5 && position == 0) {
					text.setTextColor(Color.GRAY);
				} else {
					text.setTextColor(Color.BLACK);
				}
				return view;
			}
		};
		list_carnet.setAdapter(adapter);
		nouveauCarnet = new EditText(getActivity());
		list_carnet
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						System.out.println("i " + i);
						if (i == 0 && list_carnet_obj.size() < 5) {
							System.out.println("Ajout d'un carnet");

							// Set the default text to a link of the Queen
							nouveauCarnet.setHint("Nom du carnet");

							new AlertDialog.Builder(getActivity())
									.setTitle("Nouveau carnet")
									.setMessage(
											"Veuillez entrer le nom du carnet")
									.setView(nouveauCarnet)
									.setPositiveButton(
											"Créer",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {

													AjoutCarnetDB adb = new AjoutCarnetDB(
															(ActivityPrincipale) getActivity());
													adb.execute();
												}
											})
									.setNegativeButton(
											"Annuler",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
												}
											}).show();
						} else {
							System.out.println(list_carnet_obj.get(i - 1)
									.toString());
						}
					}
				});
		return rootView;
	}

	class AjoutCarnetDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		String varTmp = nouveauCarnet.getText().toString();

		public AjoutCarnetDB(ActivityPrincipale activityPrincipale) {

			link(activityPrincipale);
			// TODO Auto-generated constructor stub
		}

		private void link(ActivityPrincipale activityPrincipale) {
			// TODO Auto-generated method stub

		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(getActivity());
			pgd.setMessage("chargement en cours");
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
				CarnetDB.setConnection(con);
				if (!varTmp.isEmpty()) {
					CarnetDB carnet = new CarnetDB(varTmp, Session.getUser()
							.getId_user());
					try {
						carnet.create();
						ok = true;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					resultat = "Champ vide !";
				}
			}

			return ok;

		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			pgd.dismiss();
			if (ok) {
				Toast.makeText(getActivity(),
						"le carnet [" + varTmp + "] a été crée !",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}

	}
}