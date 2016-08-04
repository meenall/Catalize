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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText firstNameEdited = (EditText) findViewById(R.id.first_name_edit);
        final EditText lastNameEdited = (EditText) findViewById(R.id.last_name_edit);
        final EditText emailEdited = (EditText) findViewById(R.id.email_edit);

        final SharedPreferences prefs = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);

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

}
