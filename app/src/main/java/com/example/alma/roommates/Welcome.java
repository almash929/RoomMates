package com.example.alma.roommates;

/**
 * Created by alma on 15/12/2015.
 */
import com.facebook.AccessToken;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Welcome extends AppCompatActivity {

    // Declare Variable
    TextView userName,userApartmentId,userEmail;
    CircleImageView profileImage;
    DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[]titles = {"RoomMates","My Expenses", "My Tasks", "My Balance", "Dashboard"};
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar topToolBar;
    // Retrieve current user from Parse.com
    private ParseUser currentUser = ParseUser.getCurrentUser();
    //FragmentManager mFragmentManager;
    //FragmentTransaction mFragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        mTitle = mDrawerTitle = getTitle();

        topToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setLogo(R.drawable.home);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        //mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.header_list,null, false);
        mDrawerList.addHeaderView(listHeaderView);

        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("My Expenses", R.drawable.dollar));
        listViewItems.add(new ItemObject("My Tasks", R.drawable.tasks));
        listViewItems.add(new ItemObject("My Balance", R.drawable.balance));
        listViewItems.add(new ItemObject("Dashboard", R.drawable.chat));

        mDrawerList.setAdapter(new CustomAdapter(this, listViewItems));

        mDrawerToggle = new ActionBarDrawerToggle(Welcome.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state.*/
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state.*/
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                selectItemFragment(position);
            }
        });
        loadUserDetailsFromParse();
    }

    private void loadUserDetailsFromParse(){
        //define buttons and views
        userName = (TextView)findViewById(R.id.profile_name);
        userApartmentId = (TextView)findViewById(R.id.profile_apartment_id);
        userEmail = (TextView)findViewById(R.id.profile_email);
        profileImage = (CircleImageView)findViewById(R.id.profile_image);
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

    private void selectItemFragment(int position){

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            default:
            case 0:
                fragment = new OneFragment();
                break;
            case 1:
                fragment = new TwoFragment();
                break;
            case 2:
                fragment = new DefaultFragment();
                break;
            case 3:
                fragment = new DefaultFragment();
                break;
            case 4:
                fragment = new DefaultFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(titles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        /*
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.main_fragment_container, new TwoFragment()).commit();
        */
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
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
    public void setScreenView(){
        // Get the view
        setContentView(R.layout.welcome);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
}