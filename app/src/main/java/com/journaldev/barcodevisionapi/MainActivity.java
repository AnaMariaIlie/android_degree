package com.journaldev.barcodevisionapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
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
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.journaldev.barcodevisionapi.Util.Constants;
import com.journaldev.barcodevisionapi.models.Interaction;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

                final RequestParams requestParams = new RequestParams();
                requestParams.put("first", tvresult.getText());
                requestParams.put("second", tvresult1.getText());
                requestParams.put("third", tvresult2.getText());


                BootRestClient.post(requestParams, new JsonHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getApplicationContext(), "An error occurred.",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int g, Header[] fd, JSONObject f) {
                        ArrayList<Interaction> res = new ArrayList<>();
                        ArrayList<String> pairs = new ArrayList<>();


                        ObjectMapper mapper = new ObjectMapper();
                        TypeFactory typeFactory = mapper.getTypeFactory();
                        MapType mapType = typeFactory.constructMapType(LinkedHashMap.class, String.class,  Interaction.class);
                        try {
                            LinkedHashMap<String, Interaction> map = mapper.readValue(new StringReader(f.toString()), mapType);
                            for (Map.Entry<String, Interaction> entry : map.entrySet()) {
                                String string = entry.getKey();
                                String[] arr = string.split("###");
                                pairs.add(arr[0]);
                                pairs.add(arr[1]);
                                res.add(entry.getValue());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getApplicationContext(), ScrollActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(Constants.INTERACTIONS_ARRAY, res);
                        bundle.putStringArrayList(Constants.INTERACTIONS_PAIR, pairs);
                        intent.putExtras(bundle);
                        startActivity(intent);


                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                        ArrayList<Interaction> interactions = processObjects(timeline);
                        for (Interaction i: interactions) {
                            System.out.println(i);
                        }

                        Intent intent = new Intent(getApplicationContext(), ScrollActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(Constants.INTERACTIONS_ARRAY, interactions);
                        intent.putExtras(bundle);
                        startActivity(intent);

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

    public ArrayList<Interaction> processObjects(JSONArray timeline) {

        ArrayList<Interaction> res = new ArrayList<>();

        int i;
        for (i = 0; i < timeline.length(); i++) {
            JSONObject interactionObj = null;
            try {
                interactionObj = (JSONObject) timeline.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            ObjectMapper m = new ObjectMapper();
            try {
                Interaction interaction = m.readValue(interactionObj.toString(), Interaction.class);
                res.add(interaction);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return res;

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
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            String url = "http://www.twitter.com/intent/tweet?url=Tweet&text=DrugsChecker";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
