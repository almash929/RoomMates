package com.example.alma.roommates;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alma.roommates.entities.Apartment;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by alma on 09/01/2016.
 */
public class AppAddApartmentScreen extends AppCompatActivity {


    private Button creteNewApp,joinExistingApp;
    private TextView newAppName,joinAppId,error,welcome;
    private final ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view and buttons
        setScreenView();
        //link the user to a new apartment
        creteNewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToNewApartment(currentUser);
            }
        });

        //link the user to an existing apartment
        joinExistingApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToExistingApartment(currentUser);
            }
        });
    }

    // Add the current user to a new apartment
    public void addUserToNewApartment(final ParseUser currentUser){
        creteNewApp.setEnabled(false);
        joinExistingApp.setEnabled(false);
        final String appName = newAppName.getText().toString();
        Log.d("MyApp","New apartment name:" + appName);
        if(!appName.isEmpty()){
            final Apartment myAppart = new Apartment();
            myAppart.setName(appName);
            myAppart.setNumOfRoomates(1);
            myAppart.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        currentUser.put("Apartment", myAppart);
                        currentUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d("MyApp", "user added new Apartment sucsessfuly");
                                    Intent intent = new Intent(AppAddApartmentScreen.this, AppMainScreen.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.d("Error", e.getMessage());
                                }
                            }
                        });
                    } else {
                        Log.d("Error", e.getMessage());
                    }
                }
            });
        }
        else{
            creteNewApp.setEnabled(true);
            joinExistingApp.setEnabled(true);
            error.setTextColor(Color.RED);
            error.setText("Please Enter Valid Apartment Name");
        }
    }

    // Add the current user to an existing apartment
    public void addUserToExistingApartment(final ParseUser currentUser){
        final String appId = joinAppId.getText().toString();
        Log.d("MyApp", appId);
        if(!appId.isEmpty()){
            creteNewApp.setEnabled(false);
            joinExistingApp.setEnabled(false);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Apartment");
            //search for the apartment in DB by ID
            query.getInBackground(appId, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        Apartment foundApp = (Apartment)object;
                        currentUser.put("Apartment", foundApp);
                        currentUser.saveInBackground();
                        foundApp.increment("numOfRoomates");
                        foundApp.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                Intent intent = new Intent(AppAddApartmentScreen.this, AppMainScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    } else {
                        joinExistingApp.setEnabled(true);
                        creteNewApp.setEnabled(true);
                        joinAppId.setText("");
                        error.setTextColor(Color.RED);
                        error.setText("Apartment ID is Invalid");
                    }
                }
            });
        }
        else{
            creteNewApp.setEnabled(true);
            joinExistingApp.setEnabled(true);
            error.setTextColor(Color.RED);
            error.setText("Ask Your Roommate for the Apartment ID");
        }
    }

    //set the view of the screen and buttons
    public void setScreenView(){
        setContentView(R.layout.set_user_apartment);
        creteNewApp = (Button)findViewById(R.id.new_app_button);
        joinExistingApp = (Button)findViewById(R.id.join_app_button);
        newAppName = (TextView)findViewById(R.id.new_app_text_field);
        joinAppId = (TextView)findViewById(R.id.join_app_text_field);
        error =(TextView)findViewById(R.id.error_text_field);
        welcome = (TextView) findViewById(R.id.welcome);
        String name = currentUser.getUsername().toString();
        welcome.setText("AppMainScreen "+name);
    }
}
