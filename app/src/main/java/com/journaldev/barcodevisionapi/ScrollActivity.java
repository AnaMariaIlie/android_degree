package com.journaldev.barcodevisionapi;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.journaldev.barcodevisionapi.Util.Constants;
import com.journaldev.barcodevisionapi.models.Interaction;

import java.util.ArrayList;

public class ScrollActivity extends AppCompatActivity {

    LinearLayout scroll_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view);
        scroll_layout = findViewById(R.id.ll_scroll);

        Intent intent = getIntent();
        ArrayList<Interaction> interactions = intent.getParcelableArrayListExtra(Constants.INTERACTIONS_ARRAY);

        for (Interaction i : interactions) {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(50, 50, 50, 50);

            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ll.setBackground(getDrawable(R.drawable.border_s));
            }
            ll.setAlpha(0.8f);


            constructButton(ll, i.getFirstIngredientName().toUpperCase());
            constructButton(ll, i.getSecondIngredientName().toUpperCase());
            constructButton(ll, i.getDescription());
            constructProgressBar(ll, i.getToxicityLevel());


            ll.setLayoutParams(layoutParams);
            scroll_layout.addView(ll);
        }
    }

    private void constructProgressBar(LinearLayout ll, String toxicityLevel) {

        ProgressBar item = (ProgressBar)new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(40, 0, 40, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          //  item.setProgress(80);

            ObjectAnimator animation = null;
            switch (toxicityLevel) {
                case Constants.TOXICITY_LEVEL_HIGH:
                    item.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    animation  = ObjectAnimator.ofInt(item, "progress", 100);
                    break;
                case Constants.TOXICITY_LEVEL_MEDIUM:
                    item.getProgressDrawable().setColorFilter(Color.rgb(255,140,0), PorterDuff.Mode.SRC_IN);
                    animation  = ObjectAnimator.ofInt(item, "progress", 80);
                    break;
                case Constants.TOXICITY_LEVEL_LOW:
                    item.getProgressDrawable().setColorFilter(Color.rgb(255,215,0), PorterDuff.Mode.SRC_IN);
                    animation  = ObjectAnimator.ofInt(item, "progress", 40);
                    break;

                default:
                    item.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                    animation  = ObjectAnimator.ofInt(item, "progress", 100);
                    break;

            }


            animation.setDuration(1000);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }


        TextView t = new TextView(this);
        LinearLayout.LayoutParams tt  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tt.setMargins(40, 0, 30, 30);
        t.setLayoutParams(tt);
        t.setText("Toxicity level: " + toxicityLevel);
        item.setScaleY(2.f);
        item.setLayoutParams(layoutParams);

        ll.addView(item);
        ll.addView(t);
    }


    private void constructButton(LinearLayout ll, String name) {
        Button item = new Button(this);
        LinearLayout.LayoutParams textParams = null;
        item.setAllCaps(false);

        item.setTextColor(Color.parseColor("#303030"));
        item.setGravity(Gravity.CENTER);
        item.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));


        int height = (int) (25 * getApplicationContext().getResources().getDisplayMetrics().density);

        textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textParams.setMargins(30, 0, 30, 0);
        item.setLayoutParams(textParams);

        item.setText(name);
        ll.addView(item);
    }

    private void setBorderWithColor(TextView item, int border_color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            item.setBackground(getDrawable(border_color));
        }
    }
}
