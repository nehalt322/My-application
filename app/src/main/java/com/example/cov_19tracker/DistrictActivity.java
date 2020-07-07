package com.example.cov_19tracker;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static com.example.cov_19tracker.stateactivity.STATE_NAME;


public class DistrictActivity extends AppCompatActivity implements DistrictAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private DistrictAdapter adapter;
    private ArrayList<District_Items> districtItems;
    private RequestQueue requestQueue;
    SwipeRefreshLayout districtrefreshLayout ;
    ScrollView scrollView;


    public static final String DISTRICT_NAME = "dt_name";
    public static final String DISTRICT_CONFIRMED = "dt_confirmed";
    public static final String DISTRICT_ACTIVE = "dt_active";
    public static final String DISTRICT_DECEASED = "dt_deceased";
    public static final String DISTRICT_NEW_CONFIRMED = "dt_deltaconfirmed";
    public static final String DISTRICT_NEW_RECOVERED = "dt_deltarecovered";
    public static final String DISTRICT_NEW_DECEASED = "dt_deltadeceased";
    //public static final String STATE_LAST_UPDATE = "stateLastUpdate";
    public static final String DISTRICT_RECOVERED = "dt_recovered";
    private String stateName;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.district);
        Intent newintent = getIntent();
        stateName = newintent.getStringExtra(STATE_NAME);


        Objects.requireNonNull(getSupportActionBar()).setTitle("District");

        districtrefreshLayout= findViewById(R.id.districtRefresh_layout);
        scrollView = findViewById(R.id.districtScrollView);
        recyclerView = findViewById(R.id.district_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        districtItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);




        parseItems();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.district_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.districtaction_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userinput = newText.toLowerCase();
                ArrayList<District_Items> mdistrictListFull = new ArrayList<>();
                for(District_Items district : districtItems){
                    if(district.getMdistrict().toLowerCase().contains(userinput)){
                        mdistrictListFull.add(district);
                    }
                }
                adapter.updateList(mdistrictListFull);
                return false;

            }

        });
        return true;
    }



    private void parseItems() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json",
                null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 1; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                if (stateName.toLowerCase().equals(jsonObject.getString("state").toLowerCase())) {
                                        JSONArray jsonArray2 = jsonObject.getJSONArray("districtData");
                                        for (int j = 0; j < jsonArray2.length(); j++) {
                                            JSONObject jsonObject2 = jsonArray2.getJSONObject(j);
                                            String dt_name = jsonObject2.getString("district");
                                            String dt_confirmed = jsonObject2.getString("confirmed");
                                            String dt_active = jsonObject2.getString("active");
                                            String dt_deceased = jsonObject2.getString("deceased");
                                            String dt_recovered = jsonObject2.getString("recovered");

                                            JSONObject jsonObject3 = jsonObject2.getJSONObject("delta");
                                            String dt_deltaconfirmed = jsonObject3.getString("confirmed");
                                            String dt_deltarecovered = jsonObject3.getString("recovered");
                                            String dt_deltadeceased = jsonObject3.getString("deceased");


                                            int new_confirmed = Integer.parseInt(dt_confirmed);
                                            dt_confirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(new_confirmed);

                                            int new_active = Integer.parseInt(dt_active);
                                            dt_active = NumberFormat.getInstance(new Locale("en", "IN")).format(new_active);

                                            int new_recovered = Integer.parseInt(dt_recovered);
                                            dt_recovered = NumberFormat.getInstance(new Locale("en", "IN")).format(new_recovered);

                                            int new_deceased = Integer.parseInt(dt_deceased);
                                            dt_deceased = NumberFormat.getInstance(new Locale("en", "IN")).format(new_deceased);


                                            int new_deltaconfirmed = Integer.parseInt(dt_deltaconfirmed);
                                            if (new_deltaconfirmed >= 0) {
                                                dt_deltaconfirmed = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltaconfirmed));
                                                dt_deltaconfirmed = ("\u002B" + dt_deltaconfirmed);
                                            } else {
                                                dt_deltaconfirmed = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltaconfirmed));
                                                dt_deltaconfirmed = ("\u002D" + dt_deltaconfirmed);

                                            }


                                            int new_deltarecovered = Integer.parseInt(dt_deltarecovered);
                                            if (new_deltarecovered >= 0) {
                                                dt_deltarecovered = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltarecovered));
                                                dt_deltarecovered = ("\u002B" + dt_deltarecovered);
                                            } else {
                                                dt_deltarecovered = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltarecovered));
                                                dt_deltarecovered = ("\u002D" + dt_deltarecovered);

                                            }

                                            int new_deltadeceased = Integer.parseInt(dt_deltadeceased);
                                            if (new_deltadeceased >= 0) {
                                                dt_deltadeceased = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltadeceased));
                                                dt_deltadeceased = ("\u002B" + dt_deltadeceased);
                                            } else {
                                                dt_deltadeceased = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltadeceased));
                                                dt_deltadeceased = ("\u002D" + dt_deltadeceased);

                                            }


                                            districtItems.add(new District_Items(dt_active, dt_confirmed, dt_recovered, dt_deceased, dt_deltaconfirmed, dt_deltarecovered, dt_deltadeceased, dt_name));
                                        }
                                    }
                                }

                            adapter = new DistrictAdapter(DistrictActivity.this,districtItems);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListner(DistrictActivity.this);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("CoV", "Something went wrong ");

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void OnItemClick(int position) {
        Intent districtIntent = new Intent(this, DistrictAnalysis.class);
        District_Items clickedItem = districtItems.get(position);

        districtIntent.putExtra(DISTRICT_NAME, clickedItem.getMdistrict());
        districtIntent.putExtra(DISTRICT_CONFIRMED, clickedItem.getMconfirmed());
        districtIntent.putExtra(DISTRICT_ACTIVE, clickedItem.getMactive());
        districtIntent.putExtra(DISTRICT_DECEASED, clickedItem.getMdeceased());
        districtIntent.putExtra(DISTRICT_NEW_CONFIRMED, clickedItem.getMnewconfirmed());
        districtIntent.putExtra(DISTRICT_NEW_RECOVERED, clickedItem.getMnewrecovered());
        districtIntent.putExtra(DISTRICT_NEW_DECEASED, clickedItem.getMnewdeceased());
        //   stateIntent.putExtra(STATE_LAST_UPDATE, clickedItem.getLastupdate());
        districtIntent.putExtra(DISTRICT_RECOVERED, clickedItem.getMrecovered());

        startActivity(districtIntent);

    }
}
