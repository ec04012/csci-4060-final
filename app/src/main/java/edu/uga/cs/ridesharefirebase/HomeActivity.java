package edu.uga.cs.ridesharefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
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
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerToggle.syncState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(HomeActivity.this, "clicked on "+item.getItemId(), Toast.LENGTH_SHORT).show();

                switch(item.getItemId()) {
                    case R.id.nav_profile:
                    {
                        Toast.makeText(HomeActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        replaceFragment(new ProfileFragment());
                        break;
                    }
                    case R.id.nav_selectedRides:
                    {
                        Toast.makeText(HomeActivity.this, "selected Rides", Toast.LENGTH_SHORT).show();
                        //replaceFragment(new ProfileFragment());
                        break;
                    }
                    case R.id.nav_offer:
                    {
                        Toast.makeText(HomeActivity.this, "offer", Toast.LENGTH_SHORT).show();
                        //replaceFragment(new ProfileFragment());
                        break;
                    }
                    case R.id.nav_browse:
                    {
                        Toast.makeText(HomeActivity.this, "browse", Toast.LENGTH_SHORT).show();
                        //replaceFragment(new ProfileFragment());
                        break;
                    }
                    case R.id.nav_request:
                    {
                        Toast.makeText(HomeActivity.this, "request", Toast.LENGTH_SHORT).show();
                        //replaceFragment(new ProfileFragment());
                        break;
                    }
                }
                return false;
            }
        });

    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }



    private void replaceFragment(Fragment fragment ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

}