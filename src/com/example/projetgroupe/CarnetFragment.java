package com.example.projetgroupe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CarnetFragment extends Fragment {
	ListView list_carnet;
	String[] values = new String[] { "Android List View",
			"Adapter implementation", "Simple List View In Android",
			"Create List View Android", "Android Example",
			"List View Source Code", "List View Array Adapter",
			"Android Example List View", "Simple List View In Android",
			"Create List View Android", "Android Example",
			"List View Source Code", "List View Array Adapter",
			"Android Example List View", "Simple List View In Android",
			"Create List View Android", "Android Example",
			"List View Source Code", "List View Array Adapter",
			"Android Example List View", "Simple List View In Android",
			"Create List View Android", "Android Example",
			"List View Source Code", "List View Array Adapter",
			"Android Example List View" };

	public CarnetFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_carnet, container,
				false);
		list_carnet = (ListView) rootView.findViewById(R.id.list_carnet);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	              android.R.layout.simple_list_item_1, android.R.id.text1, values);
		list_carnet.setAdapter(adapter); 
		return rootView;
	}
}