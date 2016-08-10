package com.catalize.backend.endpoints;

import com.catalize.backend.model.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userApi",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "model.backend.catalize.com",
                ownerName = "model.backend.catalize.com",
                packagePath = ""
        )
)
public class UserEndpoint {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("user");
    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    /**
     * This method gets the <code>User</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>User</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getUser")
    public User getUser(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getUser method");
        return null;
    }

    /**
     * This inserts a new <code>User</code> object.
     *
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUser")
    public User insertUser(User user) {
        // TODO: Implement this function
        logger.info("Calling insertUser method");
        ref.child(user.uid).setValue(user);

        return user;
    }
}