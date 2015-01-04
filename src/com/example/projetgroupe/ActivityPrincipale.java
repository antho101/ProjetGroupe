package com.example.projetgroupe;

import java.sql.Connection;
import java.util.ArrayList;

import modele.CarnetDB;
import modele.NavDrawerItem;
import modele.NoteDB;
import modele.UserDB;
import myconnections.DBConnection;
import adapter.NavDrawerListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityPrincipale extends Activity {
	final String UserID = "";
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	UserDB utilisateur = null;
	private Connection con = null;
	private ActionRefreshData ardDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		utilisateur = (UserDB) getIntent().getSerializableExtra("user");

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Accueil
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Carnet
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Mon Compte
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Deconnexion
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	private class SlideMenuClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			displayView(arg2);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_resfresh_data:
			ardDB = new ActionRefreshData(ActivityPrincipale.this);
			ardDB.execute();
			return true;
		case R.id.action_about_us:
			AlertDialog.Builder boite;
			boite = new AlertDialog.Builder(this);
			boite.setTitle("A propos");
			boite.setIcon(R.drawable.ic_launcher);
			boite.setMessage(getResources().getString(R.string.apropos));
			boite.setPositiveButton(getResources().getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			boite.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		/*
		 * // if nav drawer is opened, hide the action items boolean drawerOpen
		 * = mDrawerLayout.isDrawerOpen(mDrawerList);
		 * menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		 */
		return super.onPrepareOptionsMenu(menu);

	}

	private void displayView(int position) {
		boolean logout = false;
		Fragment fragment = null;
		switch (position) {
		case 0:// Accueil
			fragment = new AccueilFragment();
			break;
		case 1:// Carnet
			fragment = new CarnetFragment();
			break;
		case 2:
			fragment = new MonCompteFragment();
			break;
		case 3: // deconnexion
			this.finish();
			logout = true;
			break;
		default:
			break;
		}
		if (fragment != null) {
			FragmentManager fragmentManage = getFragmentManager();
			fragmentManage.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			if (logout) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
				logout = false;
			} else {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.erreurAP),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/*
	 * =========================================== 
	 * Ajout d'un carnet dans la DB
	 * ===========================================
	 */
	class ActionRefreshData extends AsyncTask<String, Integer, Boolean> {
		private String resultat = "";
		private ProgressDialog pgd = null;
		private boolean ok = false;
		ActivityPrincipale act = null;
        ArrayList<CarnetDB> list = null;
        ArrayList<NoteDB> list2 = null;

		public ActionRefreshData(ActivityPrincipale activityPrincipale) {
			act = activityPrincipale;
			link(activityPrincipale);
			// TODO Auto-generated constructor stub
		}

		private void link(ActivityPrincipale activityPrincipale) {
			// TODO Auto-generated method stub

		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(ActivityPrincipale.this);
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
				resultat = getResources().getString(R.string.checkie);;
				return false;
			}
			UserDB.setConnection(con);
			CarnetDB.setConnection(con);
			NoteDB.setConnection(con);
                try {
					list = CarnetDB.getUser(utilisateur.getId_user());
					utilisateur.setListCarnet(list);
	                for (CarnetDB obj : utilisateur.getListCarnet()) {
	                        list2 = NoteDB.getCarnet(obj.getId_carnet());
	                    obj.setListNote(list2);
	                }
	    			resultat = getResources().getString(R.string.synchro);
	                ok = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resultat = getResources().getString(R.string.erreurAP);
				}
			return ok;

		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (ok) {
				Toast.makeText(ActivityPrincipale.this, resultat,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(ActivityPrincipale.this, resultat,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	public void onBackPressed() {
	    // Do Here what ever you want do on back press;
	}

}