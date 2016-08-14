package com.catalize.backend.endpoints;

import com.catalize.backend.model.Introduction;
import com.catalize.backend.model.User;
import com.catalize.backend.utils.Util;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
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

import javax.inject.Named;
import javax.servlet.ServletException;

import static com.catalize.backend.utils.Util.sendIntroduction;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "introductionApi",
        version = "v1",
        resource = "introduction",
        namespace = @ApiNamespace(
                ownerDomain = "model.backend.catalize.com",
                ownerName = "model.backend.catalize.com",
                packagePath = ""
        )
)
public class IntroductionEndpoint {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("introduction");
    DatabaseReference userRef = database.getReference("user");


    private static final Logger logger = Logger.getLogger(IntroductionEndpoint.class.getName());

    /**
     * This method gets the <code>Introduction</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Introduction</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getIntroduction")
    public Introduction getIntroduction(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getIntroduction method");
        return null;
    }

    /**
     * This inserts a new <code>Introduction</code> object.
     *
     * @param introduction The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertIntroduction")
    public Introduction insertIntroduction(final Introduction introduction) {
        // TODO: Implement this function
        logger.info("Calling insertIntroduction method");

        setupIntro(introduction);

        return introduction;
    }
    @ApiMethod(name = "textTest",
    path = "test/text")
    public Introduction textTest() {
        // TODO: Implement this function
        logger.info("Calling insertIntroduction method");
        final Introduction introduction   = new Introduction();
        introduction.introducerId = "e96f67ea-bbef-4524-b26d-47ce76db892a";
        introduction.aContact = "+19013357660";
        introduction.aName = "Marcus";
        introduction.bContact = "+16786979252";
        introduction.bName  = "Johnson";

        setupIntro(introduction);

        return introduction;
    }
    @ApiMethod(name = "emailTest",
            path = "test/email")
    public Introduction emailTest() {
        // TODO: Implement this function
        logger.info("Calling insertIntroduction method");
        final Introduction introduction   = new Introduction();
        introduction.introducerId = "e96f67ea-bbef-4524-b26d-47ce76db892a";
        introduction.aContact = "marcus.johnson226@gmail.com";
        introduction.aName = "Marcus";
        introduction.bContact = "marcus.johnson629@gmail.com";
        introduction.bName  = "Johnson";

        setupIntro(introduction);

        return introduction;
    }
    @ApiMethod(name = "bothText",
            path = "test/both")
    public Introduction bothTest() {
        // TODO: Implement this function
        logger.info("Calling insertIntroduction method");
        final Introduction introduction   = new Introduction();
        introduction.introducerId = "e96f67ea-bbef-4524-b26d-47ce76db892a";
        introduction.aContact = "marcus.johnson226@gmail.com";
        introduction.aName = "Marcus";
        introduction.bContact = "+16786979252";
        introduction.bName  = "Johnson";

        setupIntro(introduction);

        return introduction;
    }



    private void setupIntro(final Introduction introduction) {
        userRef.child(introduction.introducerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user.introductions == null){
                    user.introductions  =  0;
                }
                user.introductions ++;



                if(user.introList == null || user.introList.size() == 0){
                    user.introList = new ArrayList<String>();
                }
                user.introList.add(introduction.uid);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("introductions",user.introductions);
                map.put("introList",user.introList);
                userRef.child(user.uid).updateChildren(map);

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
        //ref.orderByChild("active").equalTo(false).addChildEventListener(listener);
        Util.findNumber(introduction);


    }
    private final ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
            Introduction intro = dataSnapshot.getValue(Introduction.class);
            logger.info("Introduction added: " + intro.toString());
            System.out.println("Introduction added: " + intro.toString());
            if(intro !=null){
                if(intro.expired==false && intro.active == false){

                    try {
                        sendIntroduction(intro);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }


                }
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            //Phone number added. send message
            Introduction intro = dataSnapshot.getValue(Introduction.class);


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