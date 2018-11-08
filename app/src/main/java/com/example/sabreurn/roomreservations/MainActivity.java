package com.example.sabreurn.roomreservations;

import android.content.Intent;
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
	private TextView emailTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		emailTextView = findViewById(R.id.user_email_TextView);

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if(user == null) {
			//just in case we somehow end up here without being authenticated
			Intent login = new Intent(null, Login.class);
			login.setFlags(login.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(login);
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
}
