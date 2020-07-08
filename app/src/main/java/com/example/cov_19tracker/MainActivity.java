package com.example.cov_19tracker;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    TextView confirmed , active , recovered , deceased , tvrecovered , tvactive , tvconfirmed , tvdeceased , tvtested , newconfirmed , newrecovered , newdeceased , newtested ,lastupdate;
    String nconfirmed , nactive , nrecovered , ndeceased , ntested , deltaconfirmed , deltarecovered , deltadeceased , deltatest , lastupdated;
    SwipeRefreshLayout refreshLayout ;

    public boolean isRefreshed;
    ScrollView scrollView;
    AnimatedPieView mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Cov", "Starting Main_Activity");

        Button btngotoStateActivity = findViewById(R.id.mainactivitybutton);
        //Toolbar toolbar = findViewById(R.id.myToolbar);
        //setSupportActionBar(toolbar);
        confirmed  = findViewById(R.id.confirmed );
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.recovered);
        deceased = findViewById(R.id.deceased);
        tvactive = findViewById(R.id.tvactive);
        tvconfirmed = findViewById(R.id.tvconfirmed);
        tvrecovered = findViewById(R.id.tvrecovered);
        tvdeceased = findViewById(R.id.tvdeceased);
        newconfirmed = findViewById(R.id.newconfirmed);
        tvtested = findViewById(R.id.tvtested);
        newrecovered = findViewById(R.id.newrecovered);
        newdeceased = findViewById(R.id.newdeceased);
        newtested = findViewById(R.id.newtested);
        lastupdate = findViewById(R.id.tvdate);
        refreshLayout = findViewById(R.id.refresh_layout);
        scrollView = findViewById(R.id.mainActvity_Scrollview);
        mPieChart = findViewById(R.id.Main_PieChart);





        btngotoStateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CoV", "On clicked : Clicked btngotoStateActivity");
                Intent intent = new Intent(MainActivity.this , stateactivity.class);
                startActivity(intent);
            }
        });


        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if(scrollY == 0) refreshLayout.setEnabled(true);
                else refreshLayout.setEnabled(false);

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                fetchData();
                refreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Data Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        fetchData();

    }




    private void fetchData() {

    RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, "https://api.covid19india.org/data.json",
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("statewise");
                            JSONObject statewise = jsonArray.getJSONObject(0);

                            JSONArray jsonArray1 = response.getJSONArray("tested");
                            int length = jsonArray1.length();
                            JSONObject tested = jsonArray1.getJSONObject(length - 1);

                            if(isRefreshed) {



                                Log.d("CoV", "The Response after being refreshed: " + statewise.getString("active"));
                                nconfirmed = (statewise.getString("confirmed"));
                                nactive = (statewise.getString("active"));
                                nrecovered = (statewise.getString("recovered"));
                                ndeceased = (statewise.getString("deaths"));
                                deltaconfirmed = (statewise.getString("deltaconfirmed"));
                                deltarecovered = (statewise.getString("deltarecovered"));
                                deltadeceased = (statewise.getString("deltadeaths"));
                                lastupdated = (statewise.getString("lastupdatedtime"));
                                lastupdate.setText(lastupdated);








                                ntested = (tested.getString("totalsamplestested"));
                                deltatest = (tested.getString("samplereportedtoday"));

                                int new_confirmed = Integer.parseInt(nconfirmed);
                                nconfirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(new_confirmed);
                                tvconfirmed.setText(nconfirmed);
                                Log.d("CoV", "The Response is: " + nconfirmed);


                                int new_active = Integer.parseInt(nactive);
                                nactive = NumberFormat.getInstance(new Locale("en", "IN")).format(new_active);
                                tvactive.setText(nactive);


                                int new_recovered = Integer.parseInt(nrecovered);
                                nrecovered = NumberFormat.getInstance(new Locale("en", "IN")).format(new_recovered);
                                tvrecovered.setText(nrecovered);


                                int new_deceased = Integer.parseInt(ndeceased);
                                ndeceased = NumberFormat.getInstance(new Locale("en", "IN")).format(new_deceased);
                                tvdeceased.setText(ndeceased);


                                int new_test = Integer.parseInt(ntested);
                                ntested = NumberFormat.getInstance(new Locale("en", "IN")).format(new_test);
                                tvtested.setText(ntested);

                                int ndelta_confirmed = Integer.parseInt(deltaconfirmed);
                                if (ndelta_confirmed >= 0){
                                    deltaconfirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_confirmed);
                                    newconfirmed.setText(String.format("+%s", deltaconfirmed));
                                }else {
                                    ndelta_confirmed = -ndelta_confirmed;
                                    deltaconfirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_confirmed);
                                    newconfirmed.setText(String.format("-%s", deltaconfirmed));

                                }

                                int ndelta_recovered = Integer.parseInt(deltarecovered);
                                if (ndelta_recovered >= 0){
                                deltarecovered = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_recovered);
                                newrecovered.setText(String.format("+%s", deltarecovered));
                                }else {
                                    ndelta_recovered =  - ndelta_recovered;
                                    deltarecovered = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_recovered);
                                    newrecovered.setText(String.format("-%s", deltarecovered));

                                }

                                int ndelta_deceased = Integer.parseInt(deltadeceased);
                                if (ndelta_deceased >= 0){
                                    deltadeceased = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_deceased);
                                    newdeceased.setText(String.format("+%s", deltadeceased));
                                }else {
                                    ndelta_deceased =  - ndelta_deceased;
                                    deltadeceased = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_deceased);
                                    newdeceased.setText(String.format("-%s", deltadeceased));

                                }

                                int ndelta_test = Integer.parseInt(deltatest);
                                if (ndelta_test >= 0){
                                    deltatest = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_test);
                                    newtested.setText(String.format("+%s", deltatest));
                                }else {
                                    ndelta_test =  - ndelta_test;
                                    deltatest = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_test);
                                    newtested.setText(String.format("-%s", deltatest));

                                }






                                AnimatedPieViewConfig config = new AnimatedPieViewConfig();
                                config.addData(new SimplePieInfo(new_confirmed, Color.parseColor("#FFA000"),"Confirmed"));
                                config.addData(new SimplePieInfo(new_recovered, Color.parseColor("#1B5E20"),"Recovered"));
                                config.addData(new SimplePieInfo(new_deceased, Color.parseColor("#F44336"),"Deceased"));
                                config.addData(new SimplePieInfo(new_active, Color.parseColor("#2962FF"),"Active"));
                                config.strokeMode(false);
                                config.animOnTouch(true);
                                config.splitAngle(3);
                                config.duration(10);
                                config.drawText(true);
                                config.textSize(20);




                                mPieChart.applyConfig(config);
                                mPieChart.start();



                            }
                            else{
                                Log.d("CoV", "The Response is: " + statewise.getString("active"));
                                nconfirmed = (statewise.getString("confirmed"));
                                nactive = (statewise.getString("active"));
                                nrecovered = (statewise.getString("recovered"));
                                ndeceased = (statewise.getString("deaths"));
                                deltaconfirmed = (statewise.getString("deltaconfirmed"));
                                deltarecovered = (statewise.getString("deltarecovered"));
                                deltadeceased = (statewise.getString("deltadeaths"));
                                lastupdated = (statewise.getString("lastupdatedtime"));
                                lastupdate.setText(lastupdated);




                                ntested = (tested.getString("totalsamplestested"));
                                deltatest = (tested.getString("samplereportedtoday"));

                                int new_confirmed = Integer.parseInt(nconfirmed);
                                nconfirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(new_confirmed);
                                tvconfirmed.setText(nconfirmed);
                                Log.d("CoV", "The Response is: " + nconfirmed);


                                int new_active = Integer.parseInt(nactive);
                                nactive = NumberFormat.getInstance(new Locale("en", "IN")).format(new_active);
                                tvactive.setText(nactive);


                                int new_recovered = Integer.parseInt(nrecovered);
                                nrecovered = NumberFormat.getInstance(new Locale("en", "IN")).format(new_recovered);
                                tvrecovered.setText(nrecovered);


                                int new_deceased = Integer.parseInt(ndeceased);
                                ndeceased = NumberFormat.getInstance(new Locale("en", "IN")).format(new_deceased);
                                tvdeceased.setText(ndeceased);


                                int new_test = Integer.parseInt(ntested);
                                ntested = NumberFormat.getInstance(new Locale("en", "IN")).format(new_test);
                                tvtested.setText(ntested);

                                int ndelta_confirmed = Integer.parseInt(deltaconfirmed);
                                if (ndelta_confirmed >= 0){
                                    deltaconfirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_confirmed);
                                    newconfirmed.setText(String.format("+%s", deltaconfirmed));
                                }else {
                                    ndelta_confirmed =  - ndelta_confirmed;
                                    deltaconfirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_confirmed);
                                    newconfirmed.setText(String.format("-%s", deltaconfirmed));

                                }

                                int ndelta_recovered = Integer.parseInt(deltarecovered);
                                if (ndelta_recovered >= 0){
                                    deltarecovered = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_recovered);
                                    newrecovered.setText(String.format("+%s", deltarecovered));
                                }else {
                                    ndelta_recovered =  - ndelta_recovered;
                                    deltarecovered = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_recovered);
                                    newrecovered.setText(String.format("-%s", deltarecovered));

                                }


                                int ndelta_deceased = Integer.parseInt(deltadeceased);
                                if (ndelta_deceased >= 0){
                                    deltadeceased = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_deceased);
                                    newdeceased.setText(String.format("+%s", deltadeceased));
                                }else {
                                    ndelta_deceased =  - ndelta_deceased;
                                    deltadeceased = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_deceased);
                                    newdeceased.setText(String.format("-%s", deltadeceased));

                                }

                                int ndelta_test = Integer.parseInt(deltatest);
                                if (ndelta_test >= 0){
                                    deltatest = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_test);
                                    newtested.setText(String.format("+%s", deltatest));
                                }else {
                                    ndelta_test =  - ndelta_test;
                                    deltatest = NumberFormat.getInstance(new Locale("en", "IN")).format(ndelta_test);
                                    newtested.setText(String.format("-%s", deltatest));

                                }



                                AnimatedPieViewConfig config = new AnimatedPieViewConfig();
                                config.addData(new SimplePieInfo(new_confirmed, Color.parseColor("#FFA000"),"Confirmed"));
                                config.addData(new SimplePieInfo(new_recovered, Color.parseColor("#1B5E20"),"Recovered"));
                                config.addData(new SimplePieInfo(new_deceased, Color.parseColor("#F44336"),"Deceased"));
                                config.addData(new SimplePieInfo(new_active, Color.parseColor("#2962FF"),"Active"));
                                config.strokeMode(false);
                                config.animOnTouch(true);
                                config.splitAngle(3);
                                config.drawText(true);
                                config.textSize(20);


                                mPieChart.applyConfig(config);
                                mPieChart.start();


                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("CoV", "Something went wrong ");
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


}
