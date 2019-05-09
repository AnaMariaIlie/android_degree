package com.journaldev.barcodevisionapi;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MapsActivity extends AppCompatActivity {

    ImageButton searchHospitals;
    ImageButton searchPharma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchHospitals = findViewById(R.id.search_hospitals);
        searchPharma = findViewById(R.id.search_ph);

        Intent intent = getIntent();

        searchHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createIntent("spitale");

            }
        });

        searchPharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createIntent("farmacii");

            }
        });


    }

    private void createIntent(String loc) {
        String fl = "geo:0,0?q=" + loc;
        Uri gmmIntentUri = Uri.parse(fl);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}
