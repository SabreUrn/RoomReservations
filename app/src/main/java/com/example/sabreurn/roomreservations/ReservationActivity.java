package com.example.sabreurn.roomreservations;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReservationActivity extends AppCompatActivity {
	private Reservation reservation;
	private TextView roomIdView;
	private TextView fromTimeView;
	private TextView toTimeView;
	private TextView userIdView;
	private TextView purposeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation);

		Intent intent = getIntent();
		reservation = (Reservation)intent.getSerializableExtra("RESERVATION");

		TextView headingView = findViewById(R.id.inspect_heading_TextView);
		headingView.setText("Reservation ID " + reservation.getId());

		roomIdView = findViewById(R.id.inspect_roomId_TextView);
		roomIdView.setText("Room ID: " + reservation.getRoomId()); //tfw no internal string conversion

		fromTimeView = findViewById(R.id.inspect_fromTime_TextView);
		fromTimeView.setText("From time: " + reservation.getFromTimeString());

		toTimeView = findViewById(R.id.inspect_toTime_TextView);
		toTimeView.setText("To time: " + reservation.getToTimeString());

		userIdView = findViewById(R.id.inspect_userId_TextView);
		userIdView.setText("User ID: " + reservation.getUserId());

		purposeView = findViewById(R.id.inspect_purpose_TextView);
		purposeView.setText("Purpose: " + reservation.getPurpose());
	}

	public void deleteReservation(View view) {
		DeleteTask task = new DeleteTask();
		task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations/" + reservation.getId());
		finish();
	}

	private class DeleteTask extends AsyncTask<String, Void, CharSequence> {
		@Override
		protected CharSequence doInBackground(String... urls) {
			String urlString = urls[0];
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("DELETE");
				int response = conn.getResponseCode();
				if(response % 100 != 2) {
					throw new IOException("Response code: " + response);
				}
				return "Nothing";
			} catch(MalformedURLException e) {
				return e.getMessage() + " " + urlString;
			} catch(IOException e) {
				return e.getMessage();
			}
		}

		@Override
		protected void onCancelled(CharSequence charSequence) {
			super.onCancelled(charSequence);
			TextView msgView = findViewById(R.id.inspect_message_TextView);
			msgView.setText(charSequence);
		}
	}
}
