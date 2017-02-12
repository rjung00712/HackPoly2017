package com.hackpoly.DynamoDBActivities;

import com.hackpoly.AmazonAws.FoodGameUser;

/**
 * Created by waiphyo on 2/11/17.
 */

public interface LogInActivity {
    /**
     * When the code call database to check log in
     * pass username, password, and implementation of this interface
     *
     * parameter will be null IF
     * user dones't exist.
     * user is not active
     * user and password doesn't match.
     *
     * if not, it will return the user object.
     *
     * @param foodGameUser
     */
    public void execute(FoodGameUser foodGameUser);
}
