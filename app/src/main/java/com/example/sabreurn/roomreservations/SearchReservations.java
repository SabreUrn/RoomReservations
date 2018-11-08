package com.example.sabreurn.roomreservations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SearchReservations extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView listHeader = new TextView(this);
		listHeader.setText("Reservations");
		listHeader.setTextAppearance(this, android.R.style.TextAppearance_Large);
		ListView listView = findViewById(R.id.mainReservationsListview);
		listView.addHeaderView(listHeader);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ReadTask task = new ReadTask();
		task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations");
	}

	private class ReadTask extends ReadHttpTask {
		@Override
		protected void onPostExecute(CharSequence jsonSeq) {
			TextView messageTextView = findViewById(R.id.mainMessageTextview);

			Gson gson = new GsonBuilder().create();
			final Reservation[] reservations = gson.fromJson(jsonSeq.toString(), Reservation[].class);

			ListView listView = findViewById(R.id.mainReservationsListview);
			ReservationListItemAdapter adapter = new ReservationListItemAdapter(getBaseContext(), R.layout.reservationlist_item, reservations);
			listView.setAdapter(adapter);
			/* //for clicking item to inspect it
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				}
			});
			*/
		}

		@Override
		protected void onCancelled(CharSequence msg) {
			TextView msgTextView = findViewById(R.id.mainMessageTextview);
			msgTextView.setText(msg);
			Log.e("RESERVTIONS", msg.toString());
		}
	}
}
