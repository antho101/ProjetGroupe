package com.example.projetgroupe;

import java.sql.Connection;

import modele.Session;
import modele.UserDB;
import myconnections.DBConnection;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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
	private EditText nouveau = null;
	private EditText nouveauCarnet2 = null;
	private AlertDialog alert = null;
	private ModifierUserPseudoDB acDB = null;
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
		tmpUser = (UserDB) getActivity().getIntent().getSerializableExtra("user");
		Log.d("", tmpUser.toString());
		btn_mycompte_pseudochange = (Button) rootView
				.findViewById(R.id.btn_mycompte_pseudochange);
		btn_mycompte_pseudochange
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						flag = false;
						nouveau = new EditText(getActivity());
						// Set the default text to a link of the Queen
						nouveau.setHint("Pseudo");

						alert = new AlertDialog.Builder(getActivity())
								.setTitle("Nouveau pseudo")
								.setMessage("Veuillez entrer le nouveau pseudo")								
								.setView(nouveau)
								

								.setPositiveButton("Changer",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {

												acDB = new ModifierUserPseudoDB((ActivityPrincipale)
														 getActivity());
														 acDB.execute();
											}
										})
								.setNegativeButton("Annuler",
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

				
				flag=true;
				nouveau = new EditText(getActivity());
				nouveauCarnet2= new EditText(getActivity());
				
				// Set the default text to a link of the Queen
				nouveau.setHint("mot de passe");
				nouveauCarnet2.setHint("Vérification du mot de passe");

				alert = new AlertDialog.Builder(getActivity())
						.setTitle("Nouveau mot de passe")
						.setMessage("Veuillez entrer le nouveau mot de passe")								
						.setView(nouveau)		

						.setPositiveButton("Changer",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										alert = new AlertDialog.Builder(getActivity())
										.setTitle("Nouveau mot de passe")
										.setMessage("Veuillez entrer à nouveau le mot de passe")									
										.setView(nouveauCarnet2)	

										.setPositiveButton("Changer",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int whichButton) {

														 acDB = new ModifierUserPseudoDB((ActivityPrincipale)
														 getActivity());
														 acDB.execute();
													}
												})
										.setNegativeButton("Annuler",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int whichButton) {
													}
												}).show();
									}
								})
						.setNegativeButton("Annuler",
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
						.setTitle("Se desinscrire")
						.setMessage("Voulez-vous vraiment vous désinscrire?")
						.setPositiveButton("Oui",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										acDD = new DesinscriptionDB((ActivityPrincipale)
												 getActivity());
												 acDB.execute();
										
									}
								})
						.setNegativeButton("Non",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();

			}
		});
		btn_mycompte_re = (Button) rootView
				.findViewById(R.id.btn_mycompte_re);
		btn_mycompte_re.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().getFragmentManager().popBackStack();
				
			}
		});

		return rootView;

	}

	/*
	 * ==================< Tache pour modifier le pseudo et le mot de
	 * passe>===============
	 */

	class ModifierUserPseudoDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		String varTmp = nouveau.getText().toString();
		String varTmp2 = nouveauCarnet2.getText().toString();
		ActivityPrincipale act = null;

		public ModifierUserPseudoDB(ActivityPrincipale activityPrincipale) {
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
			pgd.setMessage("chargement en cours");
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();

		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
			}
			if (con == null) {
				resultat = "Erreur : vérifier la connexion internet !";
				return false;
			}
			UserDB.setConnection(con);
			if (!varTmp.isEmpty()) {
				
				if(flag==false){
					/*tmpUser = new UserDB(tmpUser.getId_user(), varTmp, tmpUser
							.getMail(),  tmpUser
							.getPassword());
					try {
						tmpUser.update();
						ok = true;
					} catch (Exception e) {
						System.out.println(e.getMessage());
						// TODO Auto-generated catch block
					}*/
				}
				else{
					if(!varTmp2.isEmpty()){
						//Log.d("", " var 1 :"+varTmp + "var 2: "+varTmp2);
						//SystemClock.sleep(7000);

						if(varTmp.equals(varTmp2)){
							/*tmpUser = new UserDB(tmpUser.getId_user(), tmpUser.getPseudo(), tmpUser.getMail(),  varTmp);
							try {
								tmpUser.update();
								ok = true;
							} catch (Exception e) {
								System.out.println(e.getMessage());
								// TODO Auto-generated catch block
							}
							resultat = "Mot de passe ok :  !"+varTmp;*/
						}
						else{
							resultat = "Mot de passe différents !";
						}
					}
					else{
						resultat = "Champ vide !";
					}
					
				}		
			} else {
				resultat = "Champ vide !";
			}

			return ok;

		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok && flag==false) {
				Toast.makeText(getActivity(),
						"Votre pseudo a bien été changer", Toast.LENGTH_SHORT)
						.show();
			} 
			else if(ok && flag==true){
				Toast.makeText(getActivity(),
						"Votre mot de passe a bien été changer", Toast.LENGTH_SHORT)
						.show();
			}
				else {
			
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
			pgd.setMessage("chargement en cours");
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();

		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
			}
			if (con == null) {
				resultat = "Erreur : vérifier la connexion internet !";
				return false;
			}
			UserDB.setConnection(con);
			//UserDB u = new UserDB(tmpUser.getId_user());
			try {
				//u.delete();
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
				Toast.makeText(getActivity(), "Desinscription effectué",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}