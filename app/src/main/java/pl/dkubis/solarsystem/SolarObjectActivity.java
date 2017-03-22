package pl.dkubis.solarsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SolarObjectActivity extends AppCompatActivity {

    public static final String OBJECT_KEY = "object";
    @Bind(R.id.objectImageView)
    ImageView objectImageView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.objectTextView)
    TextView objectTextView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.moonsLabel)
    TextView moonsLabel;
    @Bind(R.id.moonsRecyclerView)
    RecyclerView moonsRecyclerView;
    private SolarObject solarObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar_object);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        boolean hasMovie = !TextUtils.isEmpty(solarObject.getVideo());
        if()
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        solarObject = (SolarObject) getIntent().getSerializableExtra(OBJECT_KEY);

        toolbarLayout.setTitle(solarObject.getName());

        try {
            String text = SolarObject.loadStringFromAssets(this, solarObject.getText());
            objectTextView.setText(Html.fromHtml(text));
        } catch (IOException e) {
            e.printStackTrace();
        }

        objectTextView.setText(solarObject.getText());
        Glide.with(this)
                .load(solarObject.getImagePath())
                .into(objectImageView);

        moonsRecyclerView.setVisibility(solarObject.hasMoons() ? View.VISIBLE : View.GONE);
        moonsLabel.setVisibility(solarObject.hasMoons() ? View.VISIBLE : View.GONE);

        if (solarObject.hasMoons()) {
            SolarObjectsAdapter adapter = new SolarObjectsAdapter(solarObject.getMoons());
            moonsRecyclerView.setAdapter(adapter);
            moonsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            moonsRecyclerView.setNestedScrollingEnabled(false);
        }
    }

    public static void start(Activity activity, SolarObject solarObject) {
        Intent intent = new Intent(activity, SolarObjectActivity.class);
        intent.putExtra(OBJECT_KEY, solarObject);
        activity.startActivity(intent);
    }
}
