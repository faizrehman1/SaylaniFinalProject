package com.example.samikhan.saylani_final_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samikhan.saylani_final_project.Model.UserModels;
import com.example.samikhan.saylani_final_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;
    private TextView user_name, user_email;
    private ListView feed_list;
    private ArrayList<Fragment> fragments;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        fragments = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();

        LayoutInflater inflater = (LayoutInflater) HomeActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.nav_header_home, null);
        user_name = (TextView) view.findViewById(R.id.user_name_drawer);
        user_email = (TextView) view.findViewById(R.id.user_email_drawer);

        if(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag) != null) {
            HomeActivity.this.getSupportFragmentManager()
                    .beginTransaction().
                    remove(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag)).commit();
            fragmentManager.beginTransaction().add(R.id.container_frag, new Feed_fragment()).addToBackStack(null).commit();

        }else {
            fragmentManager.beginTransaction().add(R.id.container_frag, new Feed_fragment()).addToBackStack(null).commit();
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.addHeaderView(view);
        navigationView.setNavigationItemSelectedListener(this);

        firebase.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    UserModels use = dataSnapshot.getValue(UserModels.class);
                    Log.d("dataaName", dataSnapshot.getValue().toString());
                    Log.d("name", use.getFname());

                    user_name.setText(use.getFname() + " " + use.getLname());
                    user_email.setText(use.getEmail());

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            if(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag) != null) {
                HomeActivity.this.getSupportFragmentManager()
                        .beginTransaction().
                        remove(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag)).commit();
                fragmentManager.beginTransaction().add(R.id.container_frag, new Feed_fragment()).addToBackStack(null).commit();

            }else {
                fragmentManager.beginTransaction().add(R.id.container_frag, new Feed_fragment()).addToBackStack(null).commit();
            }

        } else if (id == R.id.nav_request) {
            if(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag) != null) {
                HomeActivity.this.getSupportFragmentManager()
                        .beginTransaction().
                        remove(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag)).commit();
                fragmentManager.beginTransaction().add(R.id.container_frag, new My_post_Fragment()).addToBackStack(null).commit();

            }else {
                fragmentManager.beginTransaction().add(R.id.container_frag, new My_post_Fragment()).addToBackStack(null).commit();
            }

        } else if (id == R.id.nav_postreq) {
            if(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag) != null) {
                HomeActivity.this.getSupportFragmentManager()
                        .beginTransaction().
                        remove(HomeActivity.this.getSupportFragmentManager().findFragmentById(R.id.container_frag)).commit();
                fragmentManager.beginTransaction().add(R.id.container_frag, new Post_Blood_Req()).addToBackStack(null).commit();

            }else {
                fragmentManager.beginTransaction().add(R.id.container_frag, new Post_Blood_Req()).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_notification) {

        }  else if (id == R.id.nav_logout) {
            mAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
