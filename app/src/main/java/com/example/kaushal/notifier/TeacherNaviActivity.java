package com.example.kaushal.notifier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherNaviActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText teachername;
    String ID;
    FloatingActionButton fab;
    String setEditText;
    TextView t_username,t_role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_navi);
        //setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        setTitle("Schedular");
        SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
        String teacher_username=preferences.getString("username",null);
        String teacher_role=preferences.getString("role",null);

        //teachername= (EditText) findViewById(R.id.teacherName);
//        SharedPreferences preferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
//        ID =preferences.getString("id",null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        t_username=(TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        t_role=(TextView) navigationView.getHeaderView(0).findViewById(R.id.role);
        t_username.setText(teacher_username);
        t_role.setText(teacher_role);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            moveTaskToBack(true);
                            System.exit(0);
                            break;


                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }

                }
            };


            AlertDialog.Builder builder = new AlertDialog.Builder(TeacherNaviActivity.this);
            builder.setMessage("Do you want to exist").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_navi, menu);
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

        if (id == R.id.nav_profile) {

            Toast.makeText(this, "open Profile", Toast.LENGTH_SHORT).show();
            final CreateNotification createNotification=new CreateNotification(this);
            createNotification.sendNotificationTopic("Test","new teacher teast","-KxiGSz2TzkYAo53UKSf");

        } else if (id == R.id.nav_my_schedule) {
            Toast.makeText(this, "open my schedule", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_all_schedule) {
            Toast.makeText(this, "open all schedule", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isLoggedIn",false);
            editor.remove("role");
            editor.remove("id");
            editor.remove("token");
            editor.remove("username");
            editor.commit();
            UnSubscribeTopic.unSubscribe(TeacherNaviActivity.this);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void viewStudent(MenuItem item) {
//        Intent intent=new Intent(TeacherNaviActivity.this,ViewStudentActivity.class);
//        startActivity(intent);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new ViewStudentFragment());
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    public void createSchedule(MenuItem item) {

        //setTeacherName();
        SharedPreferences pre=getSharedPreferences(MyConstant.SHARED_FILE,MODE_PRIVATE);
         String id=pre.getString("id",null);

        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new AddScheduleFragment());
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


//        Intent intent=new Intent(TeacherNaviActivity.this,ScheduleActivity.class);
//        startActivity(intent);

    }

    public void displayMySchedule(MenuItem item) {
//        Intent intent=new Intent(TeacherNaviActivity.this,ViewMyScheduleActivity.class);
//        startActivity(intent);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new ViewTeacherMyScheduleFragment());
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

   }

    public void teacherAllSchedule(MenuItem item) {

//        Intent in=new Intent(TeacherNaviActivity.this,ViewMainScheduleActivity.class);
//        startActivity(in);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new ViewAllScheduleFragment());
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    public void teacher_profile(MenuItem item) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new ChangPasswordFragment());
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

//        Intent intent=new Intent(TeacherNaviActivity.this,ChangePassword.class);
//        startActivity(intent);
    }

    public void ViewQueryReport(MenuItem item) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new ViewQueryReportFragment());
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }
//    public void setTeacherName()
//    {
//        DatabaseReference teacherRef= FirebaseDatabase.getInstance().getReference("mainteacher").child(ID);
//
//        teacherRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot userDataSnapShot:dataSnapshot.getChildren()){
//                    Teacher tt=userDataSnapShot.getValue(Teacher.class);
//                    setEditText=tt.getT_Name();
//                }
//                Toast.makeText(TeacherNaviActivity.this, ""+setEditText, Toast.LENGTH_SHORT).show();
//                Log.d("TAG", "StringName:"+setEditText);
//
//                teachername.setText(setEditText);
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


}
