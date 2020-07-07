package com.example.cov_19tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.Objects;

import static com.example.cov_19tracker.stateactivity.STATE_ACTIVE;
import static com.example.cov_19tracker.stateactivity.STATE_CONFIRMED;
import static com.example.cov_19tracker.stateactivity.STATE_DECEASED;
import static com.example.cov_19tracker.stateactivity.STATE_LAST_UPDATE;
import static com.example.cov_19tracker.stateactivity.STATE_NAME;
import static com.example.cov_19tracker.stateactivity.STATE_NEW_CONFIRMED;
import static com.example.cov_19tracker.stateactivity.STATE_NEW_DECEASED;
import static com.example.cov_19tracker.stateactivity.STATE_NEW_RECOVERED;
import static com.example.cov_19tracker.stateactivity.STATE_RECOVERED;

public class StateAnalysis extends AppCompatActivity {

    public TextView stateconfirmed;
    public TextView stateactive;
    public TextView staterecovered;
    public TextView statedeceased;
    public TextView newstateconfirmed;
    public TextView newrecovered;
    public TextView newdeceased;
    public TextView lastupdate;
    SwipeRefreshLayout staterefreshLayout ;
    ScrollView scrollView;
    boolean isRefreshed;
    Button btntodistrictactivity;
    AnimatedPieView State_piechart;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stateanalysis);



        stateconfirmed = findViewById(R.id.tvstate_confirmed);
        stateactive = findViewById(R.id.tvstate_active);
        staterecovered = findViewById(R.id.tvstate_recovered);
        statedeceased = findViewById(R.id.tvstate_deceased);
        newstateconfirmed = findViewById(R.id.tvstate_newconfirmed);
        newrecovered = findViewById(R.id.tvstate_newrecovered);
        newdeceased = findViewById(R.id.tvstate_newdeceased);
        lastupdate = findViewById(R.id.tvstatedate);
        staterefreshLayout= findViewById(R.id.stateRefresh_layout);
        scrollView = findViewById(R.id.stateScrollView);
        btntodistrictactivity = findViewById(R.id.StateActvity_Button);
        State_piechart = findViewById(R.id.State_PieChart);








        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if(scrollY == 0) staterefreshLayout.setEnabled(true);
                else staterefreshLayout.setEnabled(false);

            }
        });

        staterefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;

                Intent intent = getIntent();
                String stateName = intent.getStringExtra(STATE_NAME);
                String stateConfirmed = intent.getStringExtra(STATE_CONFIRMED);
                String stateActive = intent.getStringExtra(STATE_ACTIVE);
                String stateDeceased = intent.getStringExtra(STATE_DECEASED);
                String stateNewConfirmed = intent.getStringExtra(STATE_NEW_CONFIRMED);
                String stateNewRecovered = intent.getStringExtra(STATE_NEW_RECOVERED);
                String stateNewDeceased = intent.getStringExtra(STATE_NEW_DECEASED);
                String stateLastUpdate = intent.getStringExtra(STATE_LAST_UPDATE);
                String stateRecovery = intent.getStringExtra(STATE_RECOVERED);


                AnimatedPieViewConfig config = new AnimatedPieViewConfig();
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateConfirmed).replaceAll(",", "")), Color.parseColor("#FFA000"), "Confirmed"));
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateActive).replaceAll(",", "")), Color.parseColor("#2962FF"), "Active"));
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateRecovery).replaceAll(",", "")), Color.parseColor("#1B5E20"), "Recovered"));
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateDeceased).replaceAll(",", "")), Color.parseColor("#F44336"), "Deceased"));

                config.strokeMode(false);
                config.animOnTouch(true);
                config.splitAngle(3);
                config.drawText(true);
                config.textSize(30);


                State_piechart.applyConfig(config);
                State_piechart.start();


                stateconfirmed.setText(stateConfirmed);
                stateactive.setText(stateActive);
                staterecovered.setText(stateRecovery);
                statedeceased.setText(stateDeceased);
                newstateconfirmed.setText(stateNewConfirmed);
                newrecovered.setText(stateNewRecovered);
                newdeceased.setText(stateNewDeceased);
                lastupdate.setText(stateLastUpdate);
                staterefreshLayout.setRefreshing(false);
                Toast.makeText(StateAnalysis.this, "Data Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        final Intent intent = getIntent();
        final String stateName = intent.getStringExtra(STATE_NAME);
        String stateConfirmed = intent.getStringExtra(STATE_CONFIRMED);
        String stateActive = intent.getStringExtra(STATE_ACTIVE);
        String stateDeceased = intent.getStringExtra(STATE_DECEASED);
        String stateNewConfirmed = intent.getStringExtra(STATE_NEW_CONFIRMED);
        String stateNewRecovered = intent.getStringExtra(STATE_NEW_RECOVERED);
        String stateNewDeceased = intent.getStringExtra(STATE_NEW_DECEASED);
        String stateLastUpdate = intent.getStringExtra(STATE_LAST_UPDATE);
        String stateRecovery = intent.getStringExtra(STATE_RECOVERED);



        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateConfirmed).replaceAll(",", "")), Color.parseColor("#FFA000"), "Confirmed"));
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateActive).replaceAll(",", "")), Color.parseColor("#2962FF"), "Active"));
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateRecovery).replaceAll(",", "")), Color.parseColor("#1B5E20"), "Recovered"));
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(stateDeceased).replaceAll(",", "")), Color.parseColor("#F44336"), "Deceased"));

        config.strokeMode(false);
        config.animOnTouch(true);
        config.splitAngle(3);
        config.drawText(true);
        config.textSize(30);


        State_piechart.applyConfig(config);
        State_piechart.start();


        stateconfirmed.setText(stateConfirmed);
        stateactive.setText(stateActive);
        staterecovered.setText(stateRecovery);
        statedeceased.setText(stateDeceased);
        newstateconfirmed.setText(stateNewConfirmed);
        newrecovered.setText(stateNewRecovered);
        newdeceased.setText(stateNewDeceased);
        lastupdate.setText(stateLastUpdate);

        Objects.requireNonNull(getSupportActionBar()).setTitle(stateName);





        btntodistrictactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CoV", "On clicked : Clicked btngotoStateActivity");
                Intent newintent = new Intent(StateAnalysis.this ,DistrictActivity.class);
                newintent.putExtra(STATE_NAME,stateName);
                startActivity(newintent);
            }
        });











    }




}
