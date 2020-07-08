package com.example.cov_19tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;
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

import static com.example.cov_19tracker.DistrictActivity.DISTRICT_ACTIVE;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_CONFIRMED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_DECEASED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NAME;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NEW_CONFIRMED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NEW_DECEASED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_NEW_RECOVERED;
import static com.example.cov_19tracker.DistrictActivity.DISTRICT_RECOVERED;

public class DistrictAnalysis extends AppCompatActivity {

    public TextView districtconfirmed;
    public TextView districtactive;
    public TextView districtrecovered;
    public TextView districtdeceased;
    public TextView newdistrictconfirmed;
    public TextView newdistrictrecovered;
    public TextView newdistrictdeceased;
    SwipeRefreshLayout districtrefreshLayout ;
    ScrollView scrollView;
    boolean isRefreshed;
    AnimatedPieView district_PieView;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.districtanalysis);



        districtconfirmed = findViewById(R.id.tvdistrict_confirmed);
        districtactive = findViewById(R.id.tvdistrict_active);
        districtrecovered = findViewById(R.id.tvdistrict_recovered);
        districtdeceased = findViewById(R.id.tvdistrict_deceased);
        newdistrictconfirmed = findViewById(R.id.tvdistrict_newconfirmed);
        newdistrictrecovered = findViewById(R.id.tvdistrict_newrecovered);
        newdistrictdeceased = findViewById(R.id.tvdistrict_newdeceased);
        districtrefreshLayout= findViewById(R.id.districtRefresh_layout);
        scrollView = findViewById(R.id.districtScrollView);
        district_PieView = findViewById(R.id.District_PieChart);



        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if(scrollY == 0) districtrefreshLayout.setEnabled(true);
                else districtrefreshLayout.setEnabled(false);

            }
        });

        districtrefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh() {
                isRefreshed = true;
                Intent intent = getIntent();
                String districtName = intent.getStringExtra(DISTRICT_NAME);
                String districtConfirmed = intent.getStringExtra(DISTRICT_CONFIRMED);
                String districtActive = intent.getStringExtra(DISTRICT_ACTIVE);
                String districtDeceased = intent.getStringExtra(DISTRICT_DECEASED);
                String districtNewConfirmed = intent.getStringExtra(DISTRICT_NEW_CONFIRMED);
                String districtNewRecovered = intent.getStringExtra(DISTRICT_NEW_RECOVERED);
                String districtNewDeceased = intent.getStringExtra(DISTRICT_NEW_DECEASED);
                String districtRecovery = intent.getStringExtra(DISTRICT_RECOVERED);

                AnimatedPieViewConfig config = new AnimatedPieViewConfig();
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtConfirmed).replaceAll(",", "")), Color.parseColor("#FFA000"), "Confirmed"));
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtActive).replaceAll(",", "")), Color.parseColor("#2962FF"), "Active"));
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtRecovery).replaceAll(",", "")), Color.parseColor("#1B5E20"), "Recovered"));
                config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtDeceased).replaceAll(",", "")), Color.parseColor("#F44336"), "Deceased"));

                config.strokeMode(false);
                config.animOnTouch(true);
                config.splitAngle(3);
                config.duration(10);
                config.drawText(true);
                config.textSize(30);


                district_PieView.applyConfig(config);
                district_PieView.start();


                districtconfirmed.setText(districtConfirmed);
                districtactive.setText(districtActive);
                districtrecovered.setText(districtRecovery);
                districtdeceased.setText(districtDeceased);
                newdistrictconfirmed.setText(districtNewConfirmed);
                newdistrictrecovered.setText(districtNewRecovered);
                newdistrictdeceased.setText(districtNewDeceased);
                districtrefreshLayout.setRefreshing(false);
                Toast.makeText(DistrictAnalysis.this, "Data Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        String districtName = intent.getStringExtra(DISTRICT_NAME);
        String districtConfirmed = intent.getStringExtra(DISTRICT_CONFIRMED);
        String districtActive = intent.getStringExtra(DISTRICT_ACTIVE);
        String districtDeceased = intent.getStringExtra(DISTRICT_DECEASED);
        String districtNewConfirmed = intent.getStringExtra(DISTRICT_NEW_CONFIRMED);
        String districtNewRecovered = intent.getStringExtra(DISTRICT_NEW_RECOVERED);
        String districtNewDeceased = intent.getStringExtra(DISTRICT_NEW_DECEASED);
        String districtRecovery = intent.getStringExtra(DISTRICT_RECOVERED);


        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtConfirmed).replaceAll(",", "")), Color.parseColor("#FFA000"), "Confirmed"));
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtActive).replaceAll(",", "")), Color.parseColor("#2962FF"), "Active"));
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtRecovery).replaceAll(",", "")), Color.parseColor("#1B5E20"), "Recovered"));
        config.addData(new SimplePieInfo(Float.parseFloat(Objects.requireNonNull(districtDeceased).replaceAll(",", "")), Color.parseColor("#F44336"), "Deceased"));

        config.strokeMode(false);
        config.animOnTouch(true);
        config.splitAngle(3);
        config.drawText(true);
        config.textSize(30);


        district_PieView.applyConfig(config);
        district_PieView.start();


        districtconfirmed.setText(districtConfirmed);
        districtactive.setText(districtActive);
        districtrecovered.setText(districtRecovery);
        districtdeceased.setText(districtDeceased);
        newdistrictconfirmed.setText(districtNewConfirmed);
        newdistrictrecovered.setText(districtNewRecovered);
        newdistrictdeceased.setText(districtNewDeceased);

        Objects.requireNonNull(getSupportActionBar()).setTitle(districtName);











    }




}
