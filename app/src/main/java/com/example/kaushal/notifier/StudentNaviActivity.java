package com.example.kaushal.notifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class StudentNaviActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView s_username,s_role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       setContentView(R.layout.activity_student_navi);
       // setContentView(R.layout.nav_header_student_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




       s_username= (TextView) findViewById(R.id.username);
        s_role= (TextView) findViewById(R.id.role);



        SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        String student_username=preferences.getString("username",null);
        String student_role=preferences.getString("role",null);
        Log.d("TAG", "onCreate:"+student_username+"\n"+student_role);

        //s_username.setText(student_username);
        //s_role.setText(student_role);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_navi, menu);
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
        if (id == R.id.nav_profile_student) {
                Toast.makeText(this, "open Profile", Toast.LENGTH_SHORT).show();
            }

        else if (id == R.id.nav_view_schedule_student) {
            Toast.makeText(this, "View schedule", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_query) {
            Toast.makeText(this, "Send Query Report", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.nav_logout_student) {
            SharedPreferences sharedPreferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isLoggedIn",false);
            editor.putBoolean("isLoggedIn",false);
            editor.remove("role");
            editor.remove("id");
            editor.remove("token");
            editor.remove("username");
            editor.commit();
            UnSubscribeTopic.unSubscribe(StudentNaviActivity.this);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }













            //            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void viewMainSchedule(MenuItem item) {
        Intent intent=new Intent(StudentNaviActivity.this,ViewMainScheduleActivity.class);
        startActivity(intent);

    }
}
