package com.journaldev.barcodevisionapi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.journaldev.barcodevisionapi.Util.Constants;
import com.journaldev.barcodevisionapi.models.Interaction;

import java.util.ArrayList;

public class ScrollActivity extends AppCompatActivity {

    ScrollView scroll_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view);
        scroll_layout = findViewById(R.id.layout_scroll);

        Intent intent = getIntent();
        ArrayList<Interaction> interactions = intent.getParcelableArrayListExtra(Constants.INTERACTIONS_ARRAY);

        for (Interaction i : interactions) {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(50, 50, 50, 0);

            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ll.setBackground(getDrawable(R.drawable.border_s));
            }
            ll.setAlpha(0.8f);


            constructTextView(ll, i.getFirstIngredientName().toUpperCase());
            constructTextView(ll, i.getSecondIngredientName().toUpperCase());
            constructTextView(ll, i.getDescription());
            constructTextView(ll, i.getToxicityLevel());

            ll.setLayoutParams(layoutParams);
            scroll_layout.addView(ll);
        }
    }

    private void constructTextView(LinearLayout ll, String name) {
        TextView item = new TextView(this);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(30, 30, 0, 30);
        item.setLayoutParams(textParams);
        item.setTextColor(Color.BLACK);
        item.setGravity(Gravity.CENTER);
        item.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            item.setBackground(getDrawable(R.drawable.border_txt));
        }
        System.out.println(name);
        item.setText(name);
        ll.addView(item);
    }
}
