package com.example.sabreurn.roomreservations;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SearchReservations extends AppCompatActivity {
	OnDateSetListener dateListener;
	String dateYearString;
	String dateMonthString;
	String dateDayString;
	String date;

	EditText roomIdField;
//	EditText dateField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_reservations);

		roomIdField = findViewById(R.id.search_roomID_EditText);
//		dateField = findViewById(R.id.search_date_EditText);
	}

	public void search(View view) {
		String roomIdString = roomIdField.getText().toString();
//		String dateString = dateField.getText().toString();
		if(roomIdString.isEmpty()) {
			Toast.makeText(this, "Must enter room ID.", Toast.LENGTH_SHORT).show();
			return;
		}
		int roomId = Integer.parseInt(roomIdString);
		Intent searchIntent = new Intent(this, SearchReservationsResults.class);
		searchIntent.putExtra("ROOMID", roomId);
		Log.w("DATE", "" + date);
		if(date != null && !date.isEmpty()) searchIntent.putExtra("DATE", date);
		startActivity(searchIntent);
	}

	public void setDate(View view) {
		dateListener = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				Calendar cal = new GregorianCalendar(year, month, dayOfMonth);

				final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
				((TextView)findViewById(R.id.search_date_TextView)).setText(dateFormat.format(cal.getTime()));
				dateYearString = "" + cal.get(Calendar.YEAR);

				int dateMonth = cal.get(Calendar.MONTH) + 1;
				dateMonthString = dateMonth < 10 ? "0" + dateMonth :  "" + dateMonth;

				int dateDay = cal.get(Calendar.DAY_OF_MONTH);
				dateDayString = dateDay < 10 ? "0" + dateDay :  "" + dateDay;

				date = dateYearString + "-" + dateMonthString + "-" + dateDayString;
			}
		};

		DatePickerFragment dateFragment = new DatePickerFragment();
		dateFragment.setCallback(dateListener);
		dateFragment.show(getSupportFragmentManager(), "date");
	}
}
