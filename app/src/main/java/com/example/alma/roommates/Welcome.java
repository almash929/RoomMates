package com.example.alma.roommates;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Welcome extends AppCompatActivity {

    // Declare Variable
    private TextView userName,userApartmentId,userEmail;
    private CircleImageView profileImage;
    // Retrieve current user from Parse.com
    private ParseUser currentUser = ParseUser.getCurrentUser();
    // view variables
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(mDrawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the ExpensesTabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.flContent,new ExpensesTabFragment()).commit();

        loadUserDetailsFromParse();
    }

    private void loadUserDetailsFromParse(){
        //define buttons and views
        View header = nvDrawer.getHeaderView(0);
        userName = (TextView) header.findViewById(R.id.profile_name);
        userEmail = (TextView) header.findViewById(R.id.profile_email);
        userApartmentId = (TextView) header.findViewById(R.id.profile_apartment_id);
        profileImage = (CircleImageView) header.findViewById(R.id.profile_image);

        // Get current user details
        String myUserName = currentUser.getUsername().toString();
        String myUserMail = currentUser.getEmail().toString();
        String myUserImageUrl = currentUser.getString("image");
        String myUserAppId = Apartment.getApartmentId(currentUser);

        //Set user details to view
        userName.setText(myUserName);
        userEmail.setText(myUserMail);
        userApartmentId.setText("Apartment ID: "+myUserAppId);
        Picasso.with(Welcome.this).load(myUserImageUrl).resize(200, 200).centerCrop().into(profileImage);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = ExpensesTabFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = FirstFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id ==R.id.action_logout){
            // Logout current user
            ParseUser.logOut();
            // this will now be null
            currentUser = ParseUser.getCurrentUser();
            finish();
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }*/

}