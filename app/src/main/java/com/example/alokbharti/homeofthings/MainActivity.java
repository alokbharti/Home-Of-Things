package com.example.alokbharti.homeofthings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ListItem> listItems ;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String url;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "https://newsapi.org/v2/everything?sources=the-times-of-india&q=NGO&apiKey=2012066be1c944409c701878d544b5fc";
        //url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=23.176107,%2080.026518&radius=50000&type=restaurant&key=AIzaSyCfKpEfhwTAHNFoR9RQijZiM943AJM2z10";
        mRecyclerView = (RecyclerView)findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        GetNewsData(url);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                GetNewsData(url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.donate)
        {
            Intent i = new Intent(MainActivity.this,Donate.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.ngo){
            Intent intent = new Intent(MainActivity.this,Ngo.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.claim){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    public void GetNewsData(String url){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data....");
        progressDialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("articles");
                            for(int i =0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                ListItem item = new ListItem(object.getString("title"),object.getString("description")
                                        ,object.getString("urlToImage"));
                                listItems.add(item);

                            }

                            mAdapter = new Adapter(listItems,getApplicationContext());
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("Error");
                            dialog.setMessage("Please check your Internet");
                            dialog.setPositiveButton("Okay",null);
                            dialog.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
