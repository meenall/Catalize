/**
 * Self explanatory
 */

package com.catalizeapp.catalize_ss16_v5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Log extends AppCompatActivity {

    private String personName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        sharedPreferences = this.getSharedPreferences("com.catalizeapp.catalize_ss16_v5", Context.MODE_PRIVATE);

    }
}
