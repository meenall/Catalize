package com.catalizeapp.catalize_ss16_v5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    String firstName;
    String lastName;
    String personEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final SharedPreferences prefs = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);

        firstName = prefs.getString("first_name","");
        lastName = prefs.getString("last_name","");
        personEmail  = prefs.getString("email", "");

        final EditText firstNameEdited = (EditText) findViewById(R.id.first_name_edit);
        final EditText lastNameEdited = (EditText) findViewById(R.id.last_name_edit);
        final EditText emailEdited = (EditText) findViewById(R.id.email_edit);

        firstNameEdited.setHint(firstName);
        lastNameEdited.setHint(lastName);
        emailEdited.setHint(personEmail);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("first_name", firstNameEdited.getText().toString()).apply();
                editor.putString("last_name", lastNameEdited.getText().toString()).apply();
                editor.putString("email", emailEdited.getText().toString()).apply();
                editor.apply();
                Intent intent = new Intent(SettingsActivity.this, Nav.class);
                startActivity(intent);
            }
        };
        Button btn1 = (Button)findViewById(R.id.save_prefs);
        btn1.setOnClickListener(listener);


    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
