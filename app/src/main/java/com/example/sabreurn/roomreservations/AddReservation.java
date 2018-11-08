package com.example.sabreurn.roomreservations;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddReservation extends AppCompatActivity {
	public OnDateSetListener fromDateListener;
	public OnTimeSetListener fromTimeListener;
	public OnDateSetListener toDateListener;
	public OnTimeSetListener toTimeListener;
	String fromDateYearString;
	String fromDateMonthString;
	String fromDateDayString;
	String fromTimeHourString;
	String fromTimeMinuteString;
	String toDateYearString;
	String toDateMonthString;
	String toDateDayString;
	String toTimeHourString;
	String toTimeMinuteString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reservation);
	}

	public void addReservation(View view) {
//		String fromTimeString = ((EditText)findViewById(R.id.addReservation_fromTimeString_EditText)).getText().toString();
		String fromTimeString = fromDateYearString + "-" + fromDateMonthString + "-" + fromDateDayString
				+ " " + fromTimeHourString + ":" + fromTimeMinuteString;

//		String toTimeString = ((EditText)findViewById(R.id.addReservation_toTimeString_EditText)).getText().toString();
		String toTimeString = toDateYearString + "-" + toDateMonthString + "-" + toDateDayString
				+ " " + toTimeHourString + ":" + toTimeMinuteString;

//		String userId = ((EditText)findViewById(R.id.addReservation_userId_EditText)).getText().toString();
		String purpose = ((EditText)findViewById(R.id.addReservation_purpose_EditText)).getText().toString();
		String roomIdString = ((EditText)findViewById(R.id.addReservation_roomId_EditText)).getText().toString();
		int roomId = Integer.parseInt(roomIdString);
		FirebaseUser loggedinUser = FirebaseAuth.getInstance().getCurrentUser();

		TextView msgView = findViewById(R.id.addReservation_message_TextView);

		try {
			JSONObject json = new JSONObject();
			json.put("fromTimeString", fromTimeString);
			json.put("toTimeString", toTimeString);
			json.put("userId", loggedinUser.getEmail());
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

	public void setDateFrom(View view) {
		fromDateListener = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				Calendar cal = new GregorianCalendar(year, month, dayOfMonth);

				final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
				((TextView)findViewById(R.id.addReservation_fromDate_TextView)).setText(dateFormat.format(cal.getTime()));
				fromDateYearString = "" + cal.get(Calendar.YEAR);

				int fromDateMonth = cal.get(Calendar.MONTH) + 1; //lmao 0-based
				fromDateMonthString = fromDateMonth < 10 ? "0" + fromDateMonth : "" + fromDateMonth;

				int fromDateDay = cal.get(Calendar.DAY_OF_MONTH);
				fromDateDayString = fromDateDay < 10 ? "0" + fromDateDay : "" + fromDateDay;

				Log.w("SETDATEFROM", fromDateYearString);
				Log.w("SETDATEFROM", fromDateMonthString);
				Log.w("SETDATEFROM", fromDateDayString);
			}
		};

		DatePickerFragment dateFragment = new DatePickerFragment();
		dateFragment.setCallback(fromDateListener);
		dateFragment.show(getSupportFragmentManager(), "datefrom");
	}

	public void setTimeFrom(View view) {
		fromTimeListener = new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				((TextView)findViewById(R.id.addReservation_fromTime_TextView)).setText(hourOfDay + ":" + minute);
				fromTimeHourString = "" + hourOfDay;
				fromTimeMinuteString = "" + minute;
				Log.w("SETTIMEFROM", "" + fromTimeHourString);
				Log.w("SETTIMEFROM", "" + fromTimeMinuteString);
			}
		};

		TimePickerFragment timeFragment = new TimePickerFragment();
		timeFragment.setCallback(fromTimeListener);
		timeFragment.show(getSupportFragmentManager(), "timefrom");
	}

	public void setDateTo(View view) {
		toDateListener = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				Calendar cal = new GregorianCalendar(year, month, dayOfMonth);

				final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
				((TextView)findViewById(R.id.addReservation_toDate_TextView)).setText(dateFormat.format(cal.getTime()));
				toDateYearString = "" + cal.get(Calendar.YEAR);
				int toDateMonth = cal.get(Calendar.MONTH) + 1;
				toDateMonthString = toDateMonth < 10 ? "0" + toDateMonth : "" + toDateMonth;
				int toDateDay = cal.get(Calendar.DAY_OF_MONTH);
				toDateDayString = toDateDay < 10 ? "0" + toDateDay : "" + toDateDay;
				Log.w("SETDATETO", toDateYearString);
				Log.w("SETDATETO", toDateMonthString);
				Log.w("SETDATETO", toDateDayString);
			}
		};

		DatePickerFragment dateFragment = new DatePickerFragment();
		dateFragment.setCallback(toDateListener);
		dateFragment.show(getSupportFragmentManager(), "dateto");
	}

	public void setTimeTo(View view) {
		toTimeListener = new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				((TextView)findViewById(R.id.addReservation_toTime_TextView)).setText(hourOfDay + ":" + minute);
				toTimeHourString = "" + hourOfDay;
				toTimeMinuteString = "" + minute;
				Log.w("SETTIMEFROM", "" + toTimeHourString);
				Log.w("SETTIMEFROM", "" + toTimeMinuteString);
			}
		};

		TimePickerFragment timeFragment = new TimePickerFragment();
		timeFragment.setCallback(toTimeListener);
		timeFragment.show(getSupportFragmentManager(), "timeto");
	}
}
