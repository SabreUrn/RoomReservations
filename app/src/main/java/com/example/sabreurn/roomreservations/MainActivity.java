package com.example.sabreurn.roomreservations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void goAddReservation(View view) {
		Intent intent = new Intent(this, AddReservation.class);
		startActivity(intent);
	}

	public void goSearchReservations(View view) {
		Intent intent = new Intent(this, SearchReservations.class);
		startActivity(intent);
	}
}
