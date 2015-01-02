package com.example.projetgroupe;

import java.sql.Connection;
import java.util.ArrayList;

import modele.CarnetDB;
import modele.NoteDB;
import modele.Session;
import modele.UserDB;
import myconnections.DBConnection;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetgroupe.ShakeDetector.OnShakeListener;

public class CarnetFragment extends Fragment {
	Connection con = null;
	ListView list_carnet;
	ArrayList<CarnetDB> list_carnet_obj = null;
	ArrayList<String> list_carnet_titre = null;
	EditText ModifTitre = null;
	AlertDialog alert = null;
	AjoutCarnetDB acDB = null;
	GetListCarnetDB glcDB = null;
	ArrayAdapter<String> adapter = null;
	private CarnetDB ca = null;
	private ProgressDialog pgd = null;
	private EditText newPseudo;
	private AlertDialog change = null;
	private EditDB epDM = null;
	private DeleteDB delete = null;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	Integer pos;
	private ShakeDetector mShakeDetector;

	public static final CarnetFragment newInstance() {
		CarnetFragment f = new CarnetFragment();
		return f;
	}

	public CarnetFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_carnet, container,
				false);

		mSensorManager = (SensorManager) getActivity().getSystemService(
				Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector();
		mShakeDetector.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
				handleShakeEvent(count);
			}
		});

		UserDB tmpUser = (UserDB) getActivity().getIntent()
				.getSerializableExtra("user");
		System.out.println(" carnet | user : " + tmpUser.toString());

		list_carnet_titre = new ArrayList<String>();
		list_carnet_obj = new ArrayList<CarnetDB>();

		list_carnet = (ListView) rootView.findViewById(R.id.list_carnet);
		adapter = new ArrayAdapter<String>(getActivity(),
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
		list_carnet
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {
						pos = i;
						System.out.println("i " + i);
						if (i == 0 && list_carnet_obj.size() < 5) {
							System.out.println("Ajout d'un carnet");

							final EditText nouveauCarnet = new EditText(
									getActivity());
							// Set the default text to a
							// link of the Queen
							nouveauCarnet.setHint("Nom du carnet");

							alert = new AlertDialog.Builder(getActivity())
									.setTitle("Carnet : ")
									.setMessage(
											"Veuillez entrer le nouveau nom du carnet :")
									.setView(nouveauCarnet)
									.setPositiveButton(
											"Créer",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {

													Log.d("",
															"alert création de carnet : "
																	+ nouveauCarnet
																			.getText()
																			.toString());
													acDB = new AjoutCarnetDB(
															(ActivityPrincipale) getActivity(),

															nouveauCarnet
																	.getText()
																	.toString());
													acDB.execute();
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
						} 
						
						else {
							Intent notego = new Intent(
									getActivity(),ActivityNoteListe.class);
							notego.putExtra("carnet",(CarnetDB) list_carnet_obj.get(pos-1));
							startActivityForResult(notego, 0);
							// chaque fois qu'on va sur le fragment on refresh
							
						}
						list_carnet
						.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

							@SuppressWarnings("rawtypes")
							public boolean onItemLongClick(
									AdapterView parent, View view,
									final int position, long id) {
								final CharSequence[] items = {
										"Delete", "Change title" };
								pos= position;
								ModifTitre = new EditText(
										getActivity());
								final AlertDialog.Builder builder = new AlertDialog.Builder(
										getActivity());

								builder.setTitle("Action:");
								builder.setItems(
										items,
										new DialogInterface.OnClickListener() {

											public void onClick(
													DialogInterface dialog,
													int item) {
												switch (item) {
												case 0:
													delete = new DeleteDB(
															(ActivityPrincipale) getActivity(),
															list_carnet_obj
																	.get(pos - 1));
													delete.execute();
													break;
												case 1:
													change = new AlertDialog.Builder(
															getActivity())
															.setTitle(
																	"Changer le titre du carnet")
															.setView(
																	ModifTitre)

															.setPositiveButton(
																	"Changer",
																	new DialogInterface.OnClickListener() {
																		public void onClick(
																				DialogInterface dialog,
																				int whichButton) {

																			Log.d("",
																					" modif ="
																							+ ModifTitre
																									.getText());

																			epDM = new EditDB(
																					(ActivityPrincipale) getActivity(),
																					list_carnet_obj
																							.get(pos - 1),
																					ModifTitre
																							.getText()
																							.toString());

																			epDM.execute();
																		}
																	})
															.setNegativeButton(
																	"Annuler",
																	new DialogInterface.OnClickListener() {
																		public void onClick(
																				DialogInterface dialog,
																				int whichButton) {
																		}
																	})
															.show();

													break;
												}

											}

										});
								AlertDialog alert = builder
										.create();

								alert.show();
								// do your stuff here
								return false;
							}
						});
					}
				});

		refreshData();
		return rootView;
	}

	protected void handleShakeEvent(int count) {
		// TODO Auto-generated method stub
		refreshData();

	}

	@Override
	public void onResume() {
		super.onResume();
		// Add the following line to register the Session Manager Listener
		// onResume
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		// Add the following line to unregister the Sensor Manager onPause
		mSensorManager.unregisterListener(mShakeDetector);
		super.onPause();
	}

	/*
	 * =========================================== Ajout d'un carnet dans la DB
	 * ===========================================
	 */
	class AjoutCarnetDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		String varTmp;
		ActivityPrincipale act = null;

		public AjoutCarnetDB(ActivityPrincipale activityPrincipale,
				String Ntitre) {
			act = activityPrincipale;
			this.varTmp = Ntitre;
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
			CarnetDB.setConnection(con);
			UserDB o = (UserDB) getActivity().getIntent().getSerializableExtra(
					"user");
			if (!varTmp.isEmpty()) {
				CarnetDB carnet = new CarnetDB(varTmp, o.getId_user());
				try {
					carnet.create();
					ok = true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					// TODO Auto-generated catch block
				}

			} else {
				resultat = "Champ vide !";
			}

			return ok;

		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				glcDB = new GetListCarnetDB((ActivityPrincipale) getActivity());
				glcDB.execute();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*
	 * =========================================== Ajout d'un carnet dans la DB
	 * ===========================================
	 */
	class GetListCarnetDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		private ArrayList<CarnetDB> list_carnet = null;

		public GetListCarnetDB(ActivityPrincipale activityPrincipale) {

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
			CarnetDB.setConnection(con);
			list_carnet = new ArrayList<CarnetDB>();
			try {
				UserDB o = (UserDB) getActivity().getIntent()
						.getSerializableExtra("user");
				list_carnet = CarnetDB.getUser(o.getId_user());

				ok = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return ok;

		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				list_carnet_titre.clear();
				list_carnet_obj = list_carnet;

				if (list_carnet_obj.size() < 5) {
					list_carnet_titre.add("+ Ajouter un carnet ["
							+ list_carnet_obj.size() + "/5]");
				}
				for (int i = 0; i < 5; i++) {
					if (i < list_carnet_obj.size()) {
						list_carnet_titre
								.add(list_carnet_obj.get(i).getTitre());
					}
				}

				adapter.notifyDataSetChanged();

				Toast.makeText(getActivity(), "Mise a jour", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	/*
	 * =========================================== Refresh
	 * ===========================================
	 */
	protected void refreshData() {
		list_carnet_titre.clear();
		UserDB o = (UserDB) getActivity().getIntent().getSerializableExtra(
				"user");
		list_carnet_obj = o.getListCarnet();

		if (list_carnet_obj.size() < 5) {
			list_carnet_titre.add("+ Ajouter un carnet ["
					+ list_carnet_obj.size() + "/5]");
		}
		for (int i = 0; i < 5; i++) {
			if (i < list_carnet_obj.size()) {
				list_carnet_titre.add(list_carnet_obj.get(i).getTitre());
			}
		}

		adapter.notifyDataSetChanged();

		Toast.makeText(getActivity(), "Mise a jour", Toast.LENGTH_SHORT).show();
	}

	/*
	 * =========================================== Supprimer un carnet
	 * ===========================================
	 */

	class DeleteDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		ActivityPrincipale act = null;
		CarnetDB carnet;

		public DeleteDB(ActivityPrincipale activityPrincipale, CarnetDB obj) {
			act = activityPrincipale;
			this.carnet = obj;
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
			// System.out.println("Avant changement : "+tmpUser.toString());
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
			}
			if (con == null) {
				resultat = "Erreur : vérifier la connexion internet !";
				return false;
			}
			CarnetDB.setConnection(con);
			try {
				carnet.delete();
				ok = true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
			}
			// System.out.println("Aprés changement : "+tmpUser.toString());
			return ok;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				Toast.makeText(getActivity(), "suppression effectué",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*
	 * Mettre a jour le carnet
	 */

	class EditDB extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		ActivityPrincipale act = null;
		String varTmp;
		CarnetDB carnet;

		public EditDB(ActivityPrincipale activityPrincipale, CarnetDB obj,
				String Ntitre) {
			this.varTmp = Ntitre;
			this.carnet = obj;
			link(activityPrincipale);
			// TODO Auto-generated constructor stub
		}

		private void link(ActivityPrincipale activityPrincipale) {
			// TODO Auto-generated method stub
		}

		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("", "je suis avant le chargement");
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
			Log.d("", " titre changement" + varTmp);
			CarnetDB.setConnection(con);

			try {
				carnet.setTitre(varTmp);
				carnet.update();
				resultat = "Le carnet a bien été mis a jour";
				ok = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" : " + carnet.toString());
			return ok;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				Toast.makeText(getActivity(), "Changement effectué.",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), resultat, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}