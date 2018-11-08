package com.example.sabreurn.roomreservations;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddReservation extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reservation);
	}

	public void addReservation(View view) {
		String fromTimeString = ((EditText)findViewById(R.id.addReservation_fromTimeString_EditText)).getText().toString();
		String toTimeString = ((EditText)findViewById(R.id.addReservation_toTimeString_EditText)).getText().toString();
		String userId = ((EditText)findViewById(R.id.addReservation_userId_EditText)).getText().toString();
		String purpose = ((EditText)findViewById(R.id.addReservation_purpose_EditText)).getText().toString();
		String roomIdString = ((EditText)findViewById(R.id.addReservation_roomId_EditText)).getText().toString();
		int roomId = Integer.parseInt(roomIdString);

		TextView msgView = findViewById(R.id.addReservation_message_TextView);

		try {
			JSONObject json = new JSONObject();
			json.put("fromTimeString", fromTimeString);
			json.put("toTimeString", toTimeString);
			json.put("userId", userId);
			json.put("purpose", purpose);
			json.put("roomId", roomId);

			String jsonString = json.toString();
//			Log.e("JSON", jsonString);
			msgView.setText(jsonString);
			PostReservation task = new PostReservation();
			task.execute("https://anbo-roomreservation.azurewebsites.net/api/reservations", jsonString);
		} catch(JSONException e) {
			msgView.setText(e.getMessage());
		}
	}

	private class PostReservation extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			String urlString = params[0];
			String jsonDocument = params[1];
			try {
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("Accept", "application/json");
				OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
				osw.write(jsonDocument);
				osw.flush();
				osw.close();

				int responseCode = connection.getResponseCode();
				if (responseCode / 100 != 2) {
					String responseMessage = connection.getResponseMessage();
					throw new IOException("HTTP response code: " + responseCode + " " + responseMessage);
				}
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(connection.getInputStream()));
//				String line = reader.readLine();
//				return line;
				return null;
			} catch(MalformedURLException e) {
				cancel(true);
				String msg = e.getMessage() + " " + urlString;
				Log.e("RESERVATION", msg);
//				return msg;
				return null;
			} catch(IOException e) {
				cancel(true);
				Log.e("RESERVATION", e.getMessage());
//				return e.getMessage();
				return null;
			}
		}
	}
}
