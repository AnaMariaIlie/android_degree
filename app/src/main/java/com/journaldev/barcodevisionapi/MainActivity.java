package com.journaldev.barcodevisionapi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journaldev.barcodevisionapi.Util.Constants;
import com.journaldev.barcodevisionapi.models.Drug;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView tvresult;
    public static TextView tvresult1;
    public static TextView tvresult2;
    private Button scan_button;
    private Button scan_button1;
    private Button scan_button2;
    private LinearLayout first_layout;
    private LinearLayout second_layout;
    private LinearLayout third_layout;
    private Button rest_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvresult = findViewById(R.id.tvresult);
        tvresult1 = findViewById(R.id.tvresult1);
        tvresult2 = findViewById(R.id.tvresult2);

        first_layout = findViewById(R.id.first_layout);
        second_layout = findViewById(R.id.second_layout);
        third_layout = findViewById(R.id.third_layout);

        scan_button = findViewById(R.id.btn);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra(Constants.TEXT_VIEW_RESULT, "tvresult");
                startActivity(intent);
            }
        });

        scan_button1 = findViewById(R.id.btn1);
        scan_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                second_layout.setAlpha(1);
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra(Constants.TEXT_VIEW_RESULT, "tvresult1");
                startActivity(intent);
            }
        });

        scan_button2 = findViewById(R.id.btn2);
        scan_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                third_layout.setAlpha(1);
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra(Constants.TEXT_VIEW_RESULT, "tvresult2");
                startActivity(intent);
            }
        });

        rest_button = findViewById(R.id.rest_call);
        rest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*BootRestClient.simplyGET( new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        System.out.println(statusCode + "********************");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                        // Pull out the first event on the public timeline
                        JSONObject firstEvent = null;
                        try {
                            firstEvent = (JSONObject) timeline.get(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String tweetText = statusCode + "";
                        try {
                            tweetText = firstEvent.getString("id");
                        } catch (JSONException e) {
                           e.printStackTrace();
                       }

                        ObjectMapper m = new ObjectMapper();
                        try {
                            Drug drug = m.readValue(firstEvent.toString(), Drug.class);
                            System.out.println(drug.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });*/


                RequestParams requestParams = new RequestParams();
                requestParams.put("first", tvresult.getText());
                requestParams.put("second", tvresult1.getText());
                requestParams.put("third", tvresult2.getText());


                BootRestClient.post(requestParams, new JsonHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getApplicationContext(), "Please check your network connection",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                        System.out.println(statusCode + "********************");

                        // Pull out the first event on the public timeline
                        JSONObject firstEvent = null;
                        try {
                            firstEvent = (JSONObject) timeline.get(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String tweetText = statusCode + "";
                        try {
                            tweetText = firstEvent.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /*ObjectMapper m = new ObjectMapper();
                        try {
                            Drug drug = m.readValue(firstEvent.toString(), Drug.class);
                            System.out.println(drug.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                });

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        navigationView.setItemIconTintList(null);


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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
