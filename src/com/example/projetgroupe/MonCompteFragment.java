package com.example.projetgroupe;

import java.sql.Connection;

import modele.UserDB;
import myconnections.DBConnection;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MonCompteFragment extends Fragment {
	private Boolean flag = false;
	private Connection con = null;
	private EditText newPseudo, nouveau = null;
	private EditText nouveauCarnet2 = null;
	private AlertDialog alert = null;
	private EditPseudoDB epDB = null;
	private EditMdpDB epDM = null;
	private DesinscriptionDB acDD = null;
	private UserDB tmpUser = null;

	public MonCompteFragment() {
	}

	public MonCompteFragment(UserDB tmpUser) {
		super();
	}

	private Button btn_mycompte_pseudochange = null,
			btn_mycompte_mdpasse = null, btn_mycompte_des = null,
			btn_mycompte_re = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_moncompte,
				container, false);
		tmpUser = (UserDB) getActivity().getIntent().getSerializableExtra(
				"user");
		btn_mycompte_pseudochange = (Button) rootView
				.findViewById(R.id.btn_mycompte_pseudochange);
		btn_mycompte_pseudochange
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						flag = false;
						newPseudo = new EditText(getActivity());
						newPseudo.setHint(getResources().getString(R.string.pseudo));

						alert = new AlertDialog.Builder(getActivity())
								.setTitle(getResources().getString(R.string.Npseudo))
								.setMessage(getResources().getString(R.string.VNpseudo))
								.setView(newPseudo)
								.setPositiveButton(getResources().getString(R.string.ChangeANL),
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												epDB = new EditPseudoDB(
														(ActivityPrincipale) getActivity());
												epDB.execute();

											}
										})
								.setNegativeButton(getResources().getString(R.string.ChangeANUL),
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
											}
										}).show();

					}
				});

		btn_mycompte_mdpasse = (Button) rootView
				.findViewById(R.id.btn_mycompte_mdpasse);
		btn_mycompte_mdpasse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				flag = true;
				nouveau = new EditText(getActivity());
				nouveauCarnet2 = new EditText(getActivity());

				// Set the default text to a link of the Queen
				nouveau.setHint(getResources().getString(R.string.mdp));
				nouveauCarnet2.setHint(getResources().getString(R.string.Vmdp));

				alert = new AlertDialog.Builder(getActivity())
						.setTitle(getResources().getString(R.string.Nmdp))
						.setMessage(getResources().getString(R.string.VNmdp))
						.setView(nouveau)

						.setPositiveButton(getResources().getString(R.string.ChangeANL),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										alert = new AlertDialog.Builder(
												getActivity())
												.setTitle(
														getResources().getString(R.string.Nmdp))
												.setMessage(
														getResources().getString(R.string.VANmdp))
												.setView(nouveauCarnet2)

												.setPositiveButton(
														getResources().getString(R.string.ChangeANL),
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int whichButton) {
																epDM = new EditMdpDB(
																		(ActivityPrincipale) getActivity());
																epDM.execute();
															}
														})
												.setNegativeButton(
														getResources().getString(R.string.ChangeANUL),
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int whichButton) {
															}
														}).show();
									}
								})
						.setNegativeButton(getResources().getString(R.string.ChangeANUL),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();

			}
		});

		btn_mycompte_des = (Button) rootView
				.findViewById(R.id.btn_mycompte_des);
		btn_mycompte_des.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nouveau = new EditText(getActivity());

				alert = new AlertDialog.Builder(getActivity())
						.setTitle(getResources().getString(R.string.desin))
						.setMessage(getResources().getString(R.string.desinV))
						.setPositiveButton(getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										acDD = new
										DesinscriptionDB((ActivityPrincipale)
										getActivity());
										acDD.execute();

									}
								})
						.setNegativeButton(getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();

			}
		});


		return rootView;

	}

	/*
	 * Mettre a jour le pseudo
	 */

	class EditPseudoDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		ActivityPrincipale act = null;
		String varTmp = newPseudo.getText().toString();

		public EditPseudoDB(ActivityPrincipale activityPrincipale) {
			act = activityPrincipale;
			link(activityPrincipale);
			// TODO Auto-generated constructor stub
		}

		private void link(ActivityPrincipale activityPrincipale) {
			// TODO Auto-generated method stub
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(getActivity());
			pgd.setMessage(getResources().getString(R.string.load));
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
			}
			if (con == null) {
				resultat = getResources().getString(R.string.checkie);
				return false;
			}
			UserDB.setConnection(con);
			if(!varTmp.isEmpty()){
				tmpUser.setPseudo(varTmp);
				try {
					tmpUser.update();
					ok = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				resultat = getResources().getString(R.string.pseudoV);

			}
			
			return ok;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				Toast.makeText(getActivity(), getResources().getString(R.string.maj), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	
	
	/*
	 * Mettre a jour le mot de passe
	 */

	class EditMdpDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		ActivityPrincipale act = null;
		String varTmp = nouveau.getText().toString();
		String varTmp2 = nouveauCarnet2.getText().toString();
		
		public EditMdpDB(ActivityPrincipale activityPrincipale) {
			act = activityPrincipale;
			link(activityPrincipale);
			// TODO Auto-generated constructor stub
		}

		private void link(ActivityPrincipale activityPrincipale) {
			// TODO Auto-generated method stub
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(getActivity());
			pgd.setMessage(getResources().getString(R.string.load));
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
			}
			if (con == null) {
				resultat = getResources().getString(R.string.checkie);
				return false;
			}
			UserDB.setConnection(con);
			if(!varTmp.isEmpty()){
				if(!varTmp2.isEmpty()){
					if(varTmp.equals(varTmp2)){
						tmpUser.setPassword(varTmp);
						try {
							tmpUser.update();
							ok = true;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						resultat = getResources().getString(R.string.mdpI);
					}
					
				}else{
					resultat = getResources().getString(R.string.mdpN);
				}
				
			}else{
				resultat = getResources().getString(R.string.mdpN);
			}
			return ok;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				Toast.makeText(getActivity(), getResources().getString(R.string.maj), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/* ==================< Tache pour la désinscription>=============== */
	class DesinscriptionDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		ActivityPrincipale act = null;

		public DesinscriptionDB(ActivityPrincipale activityPrincipale) {
			act = activityPrincipale;
			link(activityPrincipale);
			// TODO Auto-generated constructor stub
		}

		private void link(ActivityPrincipale activityPrincipale) {
			// TODO Auto-generated method stub

		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(getActivity());
			pgd.setMessage(getResources().getString(R.string.load));
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();

		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
			}
			if (con == null) {
				resultat = getResources().getString(R.string.checkie);
				return false;
			}
			UserDB.setConnection(con);
			try {
				tmpUser.delete();
				ok = true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
			}
			return ok;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				getActivity().finish();
				Toast.makeText(getActivity(),getResources().getString(R.string.DE),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}