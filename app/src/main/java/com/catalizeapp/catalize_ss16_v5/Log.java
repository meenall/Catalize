/**
 * Self explanatory
 */

package com.catalizeapp.catalize_ss16_v5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.catalize.backend.model.introductionApi.model.Introduction;
import com.catalizeapp.catalize_ss16_v5.utils.DbManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Log extends AppCompatActivity {


    @BindView(R.id.log_list)
    ListView logList;
    private LogAdapter listAdapter;
    private List<Introduction> introList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("user");
    private DatabaseReference introRef = database.getReference("introduction");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        ButterKnife.bind(this);
        // Create ArrayAdapter using the planet list.
        introList = new ArrayList<>();
        listAdapter = new LogAdapter(this, R.layout.log_item, DbManager.introductionList);

        // Set the ArrayAdapter as the ListView's adapter.
        logList.setAdapter(listAdapter);


    }

    @Override
    public void onBackPressed() {
        Intent intentReportBug = new Intent(Log.this, Nav.class); //
        startActivity(intentReportBug);
    }
}
