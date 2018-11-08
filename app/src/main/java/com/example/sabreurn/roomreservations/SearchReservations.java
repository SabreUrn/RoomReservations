package com.example.sabreurn.roomreservations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SearchReservations extends AppCompatActivity {
	EditText roomIdField;
	EditText dateField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_reservations);

		roomIdField = findViewById(R.id.search_roomID_EditText);
		dateField = findViewById(R.id.search_date_EditText);
	}

	public void search(View view) {
		String roomIdString = roomIdField.getText().toString();
		String dateString = dateField.getText().toString();
		if(roomIdString.isEmpty()) {
			Toast.makeText(this, "Must enter room ID.", Toast.LENGTH_SHORT).show();
			return;
		}
		int roomId = Integer.parseInt(roomIdString);
		Intent searchIntent = new Intent(this, SearchReservationsResults.class);
		searchIntent.putExtra("ROOMID", roomId);
		Log.w("DATE", "" + dateString);
		if(!dateString.isEmpty()) searchIntent.putExtra("DATE", dateString);
		startActivity(searchIntent);
	}
}
