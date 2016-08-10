package com.catalizeapp.catalize_ss16_v5.utils;

import com.catalize.backend.model.introductionApi.model.Introduction;
import com.catalize.backend.model.userApi.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Marcus on 7/4/16.
 */
public class DbManager {
    public static  final String TAG = DbManager.class.getSimpleName();





    static  FirebaseStorage storage = FirebaseStorage.getInstance();

    private  static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference userRef = database.getReference("user");
    private static  DatabaseReference introRef = database.getReference("introduction");

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

    public  static  void addIntroduction(Introduction introduction){
        introRef.child(introduction.getUid()).setValue(introduction);


    }
    public  static  void addUser(User user){
        userRef.child(user.getUid()).setValue(user);

    }

}
