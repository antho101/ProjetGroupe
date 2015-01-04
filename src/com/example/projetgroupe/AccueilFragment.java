package com.example.projetgroupe;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.projetgroupe.ShakeDetector.OnShakeListener;

import modele.NoteDB;
import modele.UserDB;
import myconnections.DBConnection;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class AccueilFragment extends Fragment {
	Connection con = null;
	ListView list_note;
	ArrayList<NoteDB>listFullNote;
	ArrayList<NoteDB> list_note_obj = null;
	ArrayList<String> list_note_titre = null;
	AlertDialog alert = null;
	ArrayAdapter<String> adapter = null;
	
	private ProgressDialog pgd = null;
	
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
	public AccueilFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_accueil, container,
				false);

		mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
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

		UserDB tmpUser = (UserDB) getActivity().getIntent().getSerializableExtra("user");
		
		list_note_titre = new ArrayList<String>();
		list_note_obj = new ArrayList<NoteDB>();
		

		list_note = (ListView) rootView.findViewById(R.id.list_note);
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				list_note_titre) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text = (TextView) view
						.findViewById(android.R.id.text1);		
				
					if (listFullNote.get(position).getId_categorie()== 1) {
						text.setBackgroundColor(Color.GREEN);
					} else {
						text.setBackgroundColor(Color.RED);
					}
				
				return view;
				
			}
			
		};
		list_note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NoteDB noteToSend = listFullNote.get(position);
				Intent noteIntent = new Intent(getActivity(),
						NoteDetail.class);
				noteIntent.putExtra("note", noteToSend);
				startActivity(noteIntent);
				
			}
		});
		list_note.setAdapter(adapter);
		
		refreshData();
		return rootView;
		
	}
	
	protected void handleShakeEvent(int count) {
		// TODO Auto-generated method stub
		refreshData();
		
	}
	
	
	/*
	 * ===========================================
	 * Refresh
	 * ===========================================
	 */
	protected void refreshData(){
		list_note_titre.clear();
		UserDB o =  (UserDB) getActivity().getIntent().getSerializableExtra("user");
		listFullNote = o.getFullNote();
		
		for (int i = 0; i < listFullNote.size(); i++) {
				list_note_titre.add(listFullNote.get(i).getTitre());
			
		}
		
		adapter.notifyDataSetChanged();

		
		Toast.makeText(getActivity(),
				getResources().getString(R.string.maj),
				Toast.LENGTH_SHORT).show();
	}
}