package com.example.sabreurn.roomreservations;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
	private SharedPreferences preferences;
	private TextView emailTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		emailTextView = findViewById(R.id.user_email_TextView);

		preferences = getSharedPreferences(Login.PREF_FILE_NAME, MODE_PRIVATE);
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if(user == null) {
			//just in case we somehow end up here without being authenticated
			finish();
		}
		String email = user.getEmail();
		emailTextView.setText(email);
	}

	public void goAddReservation(View view) {
		Intent intent = new Intent(this, AddReservation.class);
		startActivity(intent);
	}

	public void goSearchReservations(View view) {
		Intent intent = new Intent(this, SearchReservations.class);
		startActivity(intent);
	}

	public void logout(View view) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(Login.EMAIL);
		editor.remove(Login.PASSWORD);
		editor.apply();
		setResult(Activity.RESULT_OK);
		finish();

//		Intent login = new Intent(null, Login.class);
//		login.setFlags(login.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
//		startActivity(login);
//		this.finish();
	}
}
