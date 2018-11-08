package com.example.sabreurn.roomreservations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
	public static final String PREF_FILE_NAME = "loginPref";
	public static final String EMAIL = "EMAIL";
	public static final String PASSWORD = "PASSWORD";
	private SharedPreferences preferences;
	private EditText emailField;
	private EditText passwordField;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		emailField = findViewById(R.id.login_email_EditText);
		passwordField = findViewById(R.id.login_password_EditText);
		mAuth = FirebaseAuth.getInstance();

		//sets username and password from preferences if they exist
		preferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
		String email = preferences.getString(EMAIL, null);
		String password = preferences.getString(PASSWORD, null);
		if(email != null && password != null) {
			emailField.setText(email);
			passwordField.setText(password);
			login();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Intent refresh = new Intent(this, Login.class);
		startActivity(refresh);
		this.finish();
	}

	public void loginButtonClicked(View view) {
		login();
	}

	public void login() {
		final String email = emailField.getText().toString();
		final String password = passwordField.getText().toString();

		mAuth.signInWithEmailAndPassword(email, password)
			.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if(task.isSuccessful()) {
						//sign in success
						Log.d("LOGIN", "signInWithEmail:success");

						CheckBox checkbox = findViewById(R.id.login_remember_Checkbox);
						SharedPreferences.Editor editor = preferences.edit();
						if(checkbox.isChecked()) {
							editor.putString(EMAIL, email);
							editor.putString(PASSWORD, password);
						} else {
							editor.remove(EMAIL);
							editor.remove(PASSWORD);
						}
						editor.apply();

						FirebaseUser user = mAuth.getCurrentUser();
						Intent main = new Intent(Login.this, MainActivity.class);
						startActivityForResult(main, 666);
					} else {
						//sign in fail
						Log.w("LOGIN", "signInWithEmail:fail", task.getException());
						Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
}
