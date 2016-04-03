package com.example.alma.roommates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String userNameText,email,mImageUrl;
    private LoginButton loginButton;
    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            checkIfUserIsJoinedToApartment(currentUser);
        } else {
            // show the signup or login screen
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, mPermissions, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException err) {
                            if(err==null){
                                if (user == null) {
                                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                                } else if (user.isNew()) {
                                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                                    getUserDetailsFromFB();
                                } else {
                                    Log.d("MyApp", "User logged in through Facebook!");
                                    ParseUser parseUser = ParseUser.getCurrentUser();
                                    checkIfUserIsJoinedToApartment(parseUser);
                                }
                            }
                            else{
                                Log.d("Error", err.getMessage());
                            }

                        }
                    });

                }
            });
        }

    }

    private void getUserDetailsFromFB(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    userNameText = object.getString("name");
                    email = object.getString("email");
                    final JSONObject mPicture = object.getJSONObject("picture");
                    final JSONObject mPictureData = mPicture.getJSONObject("data");
                    //this is the URL to the image
                    mImageUrl = mPictureData.getString("url");
                    Log.d("MyApp", mImageUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saveFacebookUserToParse();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,picture.width(250).height(250)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void saveFacebookUserToParse(){
        final ParseUser parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(userNameText);
        parseUser.setEmail(email);
        parseUser.put("image", mImageUrl);
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    checkIfUserIsJoinedToApartment(parseUser);
                }
                else{
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }
    protected void checkIfUserIsJoinedToApartment(ParseUser currentUser){
        Apartment app = (Apartment)currentUser.getParseObject("Apartment");

        if (app==null) {
            Log.d("MyApp", "user is not connected to an apratment");
            Intent intent = new Intent(MainActivity.this, AddApartment.class);
            startActivity(intent);
            //finish();
        } else {
            Log.d("MyApp", "app id is NOT null go to welcome");
            Intent intent = new Intent(MainActivity.this, Welcome.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
