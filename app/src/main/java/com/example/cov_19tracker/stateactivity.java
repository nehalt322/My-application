package com.example.cov_19tracker;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class stateactivity extends AppCompatActivity implements StateAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private StateAdapter adapter;
    private ArrayList<state_items> stateItems;
    private RequestQueue requestQueue;
    SwipeRefreshLayout staterefreshLayout ;
    ScrollView scrollView;
    boolean isRefreshed;


    public static final String STATE_NAME = "st_state";
    public static final String STATE_CONFIRMED = "st_confirmed";
    public static final String STATE_ACTIVE = "st_active";
    public static final String STATE_DECEASED = "st_deceased";
    public static final String STATE_NEW_CONFIRMED = "st_deltaconfirmed";
    public static final String STATE_NEW_RECOVERED = "st_deltarecovered";
    public static final String STATE_NEW_DECEASED = "st_deltadeceased";
    public static final String STATE_LAST_UPDATE = "stateLastUpdate";
    public static final String STATE_RECOVERED = "st_recovered";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state);
        Objects.requireNonNull(getSupportActionBar()).setTitle("States");

        staterefreshLayout= findViewById(R.id.stateRefresh_layout);
        scrollView = findViewById(R.id.stateScrollView);
        recyclerView = findViewById(R.id.state_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stateItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

       @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.state_menu, menu);
            returns
            MenuItem menuItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
            return super.onCreateOptionsMenu(menu);
        }*/




        parseItems();


    }



    private void parseItems() {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, "https://api.covid19india.org/data.json",
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            for(int i = 1 ; i < jsonArray.length() ; i++) {
                                JSONObject statewise = jsonArray.getJSONObject(i);

                                String st_active = statewise.getString("active");
                                String st_confirmed = statewise.getString("confirmed");
                                String st_deceased = statewise.getString("deaths");
                                String st_recovered = statewise.getString("recovered");
                                String st_deltaconfirmed = statewise.getString("deltaconfirmed");
                                String st_deltarecovered = statewise.getString("deltarecovered");
                                String st_deltadeceased = statewise.getString("deltadeaths");
                                String st_state = statewise.getString("state");
                                String stateLastUpdate = statewise.getString("lastupdatedtime");


                                int new_confirmed = Integer.parseInt(st_confirmed);
                                st_confirmed = NumberFormat.getInstance(new Locale("en", "IN")).format(new_confirmed);

                                int new_active = Integer.parseInt(st_active);
                                st_active = NumberFormat.getInstance(new Locale("en", "IN")).format(new_active);

                                int new_recovered = Integer.parseInt(st_recovered);
                                st_recovered = NumberFormat.getInstance(new Locale("en", "IN")).format(new_recovered);

                                int new_deceased = Integer.parseInt(st_deceased);
                                st_deceased = NumberFormat.getInstance(new Locale("en", "IN")).format(new_deceased);
                                

                                int new_deltaconfirmed = Integer.parseInt(st_deltaconfirmed);
                                if(new_deltaconfirmed >= 0) {
                                    st_deltaconfirmed = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltaconfirmed));
                                    st_deltaconfirmed = ("\u002B" + st_deltaconfirmed);
                                }else{
                                    st_deltaconfirmed = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltaconfirmed));
                                    st_deltaconfirmed = ("\u002D" + st_deltaconfirmed);

                                }


                                int new_deltarecovered = Integer.parseInt(st_deltarecovered);
                                if(new_deltarecovered >= 0) {
                                    st_deltarecovered = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltarecovered));
                                    st_deltarecovered = ("\u002B" + st_deltarecovered);
                                }else{
                                    st_deltarecovered = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltarecovered));
                                    st_deltarecovered = ("\u002D" + st_deltarecovered);

                                }

                                int new_deltadeceased = Integer.parseInt(st_deltadeceased);
                                if(new_deltadeceased >= 0) {
                                    st_deltadeceased = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltadeceased));
                                    st_deltadeceased = ("\u002B" + st_deltadeceased);
                                }else{
                                    st_deltadeceased = (NumberFormat.getInstance(new Locale("en", "IN")).format(new_deltadeceased));
                                    st_deltadeceased = ("\u002D" + st_deltadeceased);

                                }


                                stateItems.add(new state_items(st_active, st_confirmed, st_recovered, st_deceased, st_deltaconfirmed, st_deltarecovered, st_deltadeceased, st_state , stateLastUpdate ));
                            }

                            adapter = new StateAdapter(stateactivity.this, stateItems);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListner(stateactivity.this);


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
        Intent stateIntent = new Intent(this, StateAnalysis.class);
        state_items clickedItem = stateItems.get(position);

        stateIntent.putExtra(STATE_NAME, clickedItem.getMstate());
        stateIntent.putExtra(STATE_CONFIRMED, clickedItem.getMconfirmed());
        stateIntent.putExtra(STATE_ACTIVE, clickedItem.getMactive());
        stateIntent.putExtra(STATE_DECEASED, clickedItem.getMdeceased());
        stateIntent.putExtra(STATE_NEW_CONFIRMED, clickedItem.getMnewconfirmed());
        stateIntent.putExtra(STATE_NEW_RECOVERED, clickedItem.getMnewrecovered());
        stateIntent.putExtra(STATE_NEW_DECEASED, clickedItem.getMnewdeceased());
        stateIntent.putExtra(STATE_LAST_UPDATE, clickedItem.getMdate());
        stateIntent.putExtra(STATE_RECOVERED, clickedItem.getMrecovered());

        startActivity(stateIntent);

    }
}
