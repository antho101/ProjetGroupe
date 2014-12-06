package com.example.projetgroupe;

import java.util.ArrayList;

import modele.CarnetDB;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CarnetFragment extends Fragment {
	ListView list_carnet;
	ArrayList<CarnetDB> list_carnet_obj = null;
	ArrayList<String> list_carnet_titre = null;
	int posAjoutCarnet = -1;

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
			posAjoutCarnet = list_carnet_obj.size();
			list_carnet_titre.add("Ajouter un carnet");
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
				if (position == posAjoutCarnet) {
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
						System.out.println("i " + i);
						System.out.println("l " + l);
					}
				});
		return rootView;
	}
}