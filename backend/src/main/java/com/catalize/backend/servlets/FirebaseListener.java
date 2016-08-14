package com.catalize.backend.servlets;

import com.catalize.backend.model.Introduction;
import com.catalize.backend.model.User;
import com.catalize.backend.utils.Util;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static com.catalize.backend.utils.Util.sendIntroduction;

/**
 * Created by Marcus on 8/2/16.
 */
public class FirebaseListener extends HttpServlet {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference introRef = database.getReference("introduction");
    DatabaseReference userRef = database.getReference("user");
    private static final Logger logger = Logger.getLogger(FirebaseListener.class.getName());


    @Override
    public void init() throws ServletException {
        super.init();
        String setup = System.getProperty("setup","");
        if(setup.length() == 0 ){
            System.setProperty("setup","setup");
            //startListeners();
        }

    }


    private void startListeners() {
        introRef.orderByChild("active").equalTo(false).addChildEventListener(listener);
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if(dataSnapshot.getValue().getClass()==User.class) {


                    User user = dataSnapshot.getValue(User.class);
                    logger.info("User added: " + user.toString());
                    System.out.println("User added: " + user.toString());

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setupIntro(final Introduction introduction) {
        userRef.child(introduction.introducerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.introductions ++;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("introductions",user.introductions);

                userRef.child(user.uid).updateChildren(map);
                if(user.introList == null || user.introList.size() == 0){
                    user.introList = new ArrayList<String>();
                }
                user.introList.add(introduction.uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                logger.warning(databaseError.getMessage());

            }
        });
        if(introduction.aContact.contains("@")){
            introduction.aText = false;
        }
        else {
            introduction.aText = true;
            introduction.aContact = Util.formatNumber(introduction.aContact);

        }
        if(introduction.bContact.contains("@")){
            introduction.bText = false;
        }
        else {
            introduction.bText = true;
            introduction.bContact = Util.formatNumber(introduction.bContact);

        }
        introduction.acceptCode = introduction.uid.substring(0,4);
        Util.findNumber(introduction);

    }
    private final  ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
            Introduction introduction = dataSnapshot.getValue(Introduction.class);
            setupIntro(introduction);
            logger.info("Introduction added: " + introduction.toString());
            System.out.println("Introduction added: " + introduction.toString());

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            //Phone number added. send message
            Introduction intro = dataSnapshot.getValue(Introduction.class);
            if(intro !=null){
                if(intro.expired==false && intro.active == false){

                    try {
                        sendIntroduction(intro);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                    intro.active = true;
                    Map<String, Object> introUpdate = new HashMap<String, Object>();
                    introUpdate.put("active",intro.active);
                    introRef.child(intro.uid).updateChildren(introUpdate, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError != null){
                                logger.warning(databaseError.getMessage());

                            }
                        }
                    });

                }
            }

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {}

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

        @Override
        public void onCancelled(DatabaseError databaseError) {
            logger.warning(databaseError.getMessage());

        }
    };


}
