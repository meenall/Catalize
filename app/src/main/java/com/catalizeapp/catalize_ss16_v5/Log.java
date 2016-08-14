/**
 * Self explanatory
 */

package com.catalizeapp.catalize_ss16_v5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.catalize.backend.model.introductionApi.model.Introduction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        listAdapter = new LogAdapter(this, R.layout.log_item, introList);

        // Set the ArrayAdapter as the ListView's adapter.
        logList.setAdapter(listAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userRef.child(user.getUid()).child("introList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String introID = dataSnapshot.getValue(String.class);
                if (introID != null) {
                    introRef.child(introID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Introduction intro = dataSnapshot.getValue(Introduction.class);
                            listAdapter.addIntro(intro);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intentReportBug = new Intent(Log.this, Nav.class); //
        startActivity(intentReportBug);
    }
}
