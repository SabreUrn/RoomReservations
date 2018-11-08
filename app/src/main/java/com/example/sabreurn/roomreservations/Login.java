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
//	public static final String PREF_FILE_NAME = "logiPref";
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
//		preferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
//		String username = preferences.getString(USERNAME, null);
//		String password = preferences.getString(PASSWORD, null);
//		if(username != null && password != null) {
//			usernameField.setText(username);
//			passwordField.setText(password);
//		}
	}

	public void loginButtonClicked(View view) {
		String email = emailField.getText().toString();
		String password = passwordField.getText().toString();

		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if(task.isSuccessful()) {
							//sign in success
							Log.d("LOGIN", "signInWithEmail:success");
							FirebaseUser user = mAuth.getCurrentUser();
							Intent intent = new Intent(Login.this, MainActivity.class);
//							intent.putExtra(EMAIL, user.getEmail());
							startActivity(intent);
						} else {
							//sign in fail
							Log.w("LOGIN", "signInWithEmail:fail", task.getException());
							Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
						}
					}
				});

//		boolean ok = PasswordChecker.Check(username, password);
//		if(ok) {
//			CheckBox checkBox = findViewById(R.id.login_remember_CheckBox);
//			SharedPreferences.Editor editor = preferences.edit();
//			if(checkBox.isChecked()) {
//				editor.putString(USERNAME, username);
//				editor.putString(PASSWORD, password);
//			} else {
//				editor.remove(USERNAME);
//				editor.remove(PASSWORD);
//			}
//			editor.apply();
//			Intent intent = new Intent(this, MainActivity.class);
//			intent.putExtra(USERNAME, username);
//			startActivity(intent);
//		} else {
//			usernameField.setError("Wrong username or password.");
//			TextView msgView = findViewById(R.id.login_message_TextView);
//			msgView.setText("Username or password invalid.");
//		}
	}
}
