package com.catalizeapp.catalize_ss16_v5.utils;

import com.catalize.backend.model.introductionApi.model.Introduction;
import com.catalize.backend.model.userApi.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Marcus on 7/4/16.
 */
public class DbManager {
    public static  final String TAG = DbManager.class.getSimpleName();





    private   FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  DatabaseReference userRef = database.getReference("user");
    private   DatabaseReference introRef = database.getReference("introduction");

    private static DbManager singleton = null;


    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private DbManager() {


    }
    public static DbManager getInstance( ) {
        if(singleton == null){
            singleton = new DbManager();
        }

        return singleton;
    }

    public    void addIntroduction(Introduction intro){
        introRef.child(intro.getUid()).setValue(intro);

    }
    public    void addUser(final  User user){
        userRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User temp = dataSnapshot.getValue(User.class);
                if(temp == null){
                    userRef.child(user.getUid()).setValue(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
