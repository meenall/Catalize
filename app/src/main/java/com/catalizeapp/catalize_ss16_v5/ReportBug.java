/**
 * Self explanatory
 */

package com.catalizeapp.catalize_ss16_v5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportBug extends AppCompatActivity {

    private String personName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        sharedPreferences = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);

        //Intent intent = getIntent();
        //final String personName = intent.getStringExtra("name_value").toString();
        final Intent sendIntent2 = new Intent(Intent.ACTION_VIEW);
        final EditText et2=(EditText)findViewById(R.id.editText2);
        et2.setFocusableInTouchMode(true);
        et2.requestFocus();
        Button sendText = (Button) findViewById(R.id.send_report);
        //final String name = LoginActivity.personName;

        personName= sharedPreferences.getString("name","");

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager.getDefault().sendTextMessage("9154719427", null, "Bug Report from" + " " + personName  + "\n" +et2.getText().toString()+ "\n", null, null);
                    SmsManager.getDefault().sendTextMessage("2013751471", null, "Bug Report from" + " " + personName  + "\n" +et2.getText().toString()+ "\n", null, null);
                    Toast.makeText(getApplicationContext(),"Bug Report Sent! Thank you!", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception e) {
                    AlertDialog.Builder alertDialogBuilder = new
                            AlertDialog.Builder(ReportBug.this);
                    AlertDialog dialog = alertDialogBuilder.create();


                    dialog.setMessage(e.getMessage());


                    dialog.show();

                    startActivity(sendIntent2);
                }
            }
        });
    }
}
