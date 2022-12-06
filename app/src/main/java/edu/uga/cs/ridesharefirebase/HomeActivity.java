package edu.uga.cs.ridesharefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item )) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected()

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerToggle.syncState();
    } // onPostCreate()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            replaceFragment(new ProfileFragment());
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //replaceFragment(new OfferRideFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(HomeActivity.this, "clicked on "+item.getItemId(), Toast.LENGTH_SHORT).show();
                switch(item.getItemId()) {
                    case R.id.nav_profile:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        replaceFragment(new ProfileFragment());
                        break;
                    }
                    case R.id.nav_logout:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        LogOutDialog logOutDialog = new LogOutDialog();
                        logOutDialog.show(getSupportFragmentManager(), "dialog");
                        break;
                    }
                    case R.id.nav_selectedRides:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //Toast.makeText(HomeActivity.this, "selected Rides", Toast.LENGTH_SHORT).show();
                        Toast.makeText(HomeActivity.this, "My Rides", Toast.LENGTH_SHORT).show();
                        replaceFragment(new BrowseUnconfirmedRidesFragment());
                        break;
                    }

                    case R.id.nav_confirmedRide:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Toast.makeText(HomeActivity.this, "Confirmed Rides", Toast.LENGTH_SHORT).show();
                        replaceFragment(new ConfrimedRidesFragment());
                        break;
                    }

                    case R.id.nav_browse_offer:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //Toast.makeText(HomeActivity.this, "browse offer", Toast.LENGTH_SHORT).show();
                        Toast.makeText(HomeActivity.this, "Browse Ride Offers", Toast.LENGTH_SHORT).show();
                        replaceFragment(new BrowseRideOfferFragment());
                        break;
                    }
                    case R.id.nav_browse_request:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Toast.makeText(HomeActivity.this, "Browse Ride Requests", Toast.LENGTH_SHORT).show();
                        replaceFragment(new BrowseRideRequestFragment());
                        break;
                    }
                    case R.id.nav_offer:
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //Toast.makeText(HomeActivity.this, "", Toast.LENGTH_SHORT).show();
                        //replaceFragment(new ProfileFragment());
                        replaceFragment(new OfferRideFragment());
                        break;
                    }
                    case R.id.nav_request:
                    {

                        drawerLayout.closeDrawer(GravityCompat.START);
                        //Toast.makeText(HomeActivity.this, "request", Toast.LENGTH_SHORT).show();
                        replaceFragment(new RequestRideFragment());
                        break;
                    }
                } // switch statement, to handle every item in navigation drawer
                return false;
            } // // onNavigationItemSelected()
        }); // navigationView.setNavigationItemSelectedListener()
    } // HomeActivity.onViewCreated()

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            //Toast.makeText(this, "Are you sure you want to log out?",Toast.LENGTH_SHORT).show();

            // TODO: need to implement the log out feature. I guess in theory you can just do the super.onBackPressed.
            LogOutDialog logOutDialog = new LogOutDialog();
            logOutDialog.show(getSupportFragmentManager(), "dialog");

            //super.onBackPressed();
        }
        //super.onBackPressed();
    } // onBackPressed()

    private void replaceFragment(Fragment fragment ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    } // replaceFragment()


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged( newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }
} // HomeActivity