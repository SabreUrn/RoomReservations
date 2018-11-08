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

public class SearchReservationsResults extends AppCompatActivity {
	private int roomId;
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_reservations_results);

		Intent intent = getIntent();
		roomId = intent.getIntExtra("ROOMID", 0);
		date = intent.getStringExtra("DATE");
		Log.w("DATE", "" + date);

		TextView listHeader = new TextView(this);
		listHeader.setText("Search Results");
		listHeader.setTextAppearance(this, android.R.style.TextAppearance_Large);
		ListView listView = findViewById(R.id.results_reservations_ListView);
		listView.addHeaderView(listHeader);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ReadTask task = new ReadTask();
		if(date != null && !date.isEmpty()) {
			task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations/room/" + roomId + "/date/" + date);
		} else {
			task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations/room/" + roomId);
		}
	}

	private class ReadTask extends ReadHttpTask {
		@Override
		protected void onPostExecute(CharSequence jsonSeq) {
			TextView messageTextView = findViewById(R.id.results_message_TextView);

			Gson gson = new GsonBuilder().create();
			final Reservation[] reservations = gson.fromJson(jsonSeq.toString(), Reservation[].class);

			ListView listView = findViewById(R.id.results_reservations_ListView);
			ReservationListItemAdapter adapter = new ReservationListItemAdapter(getBaseContext(), R.layout.reservationlist_item, reservations);
			listView.setAdapter(adapter);
			//for clicking item to inspect it
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent inspect = new Intent(getBaseContext(), ReservationActivity.class);
					Reservation reservation = (Reservation)parent.getItemAtPosition(position);
					inspect.putExtra("RESERVATION", reservation);
					startActivity(inspect);
				}
			});
		}

		@Override
		protected void onCancelled(CharSequence msg) {
			TextView msgTextView = findViewById(R.id.results_message_TextView);
			msgTextView.setText(msg);
			Log.e("RESERVATIONS", msg.toString());
		}
	}
}
