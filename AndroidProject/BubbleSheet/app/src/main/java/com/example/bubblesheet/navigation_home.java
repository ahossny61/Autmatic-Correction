package com.example.bubblesheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;

import maes.tech.intentanim.CustomIntent;

public class navigation_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_home);
        toolbar = findViewById(R.id.toolbar_createExam);
        drawerLayout = findViewById(R.id.drawar_layout);
        navigationView = findViewById(R.id.nav_view);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setTitle("Home");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new homeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(navigation_home.this, "up-to-bottom");
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_contactus: {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new contatct_Us_Fragment()).commit();
                break;
            }
            case R.id.nav_aboutus: {
                //Todo open about us fregmentw
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new aboutUsFragement()).commit();
                break;
            }
            case R.id.nav_dashboard: {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new dashbordFragment()).commit();
                break;
            }
            case R.id.nav_home: {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new homeFragment()).commit();
                break;
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
