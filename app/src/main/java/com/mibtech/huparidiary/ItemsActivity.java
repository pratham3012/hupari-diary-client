package com.mibtech.huparidiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mibtech.huparidiary.adapters.itemAdapter;
import com.mibtech.huparidiary.model.items;
import com.mibtech.huparidiary.network.ItemsJson;
import com.mibtech.huparidiary.ui.ItemUploadDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

public class ItemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    String url="https://mibtechnologies.in/hupariapp/db_item_upload.php?catnamewa=sqlrank";
    List<items> myDataset;
    public static final int PICK_IMAGE = 1;
    Bitmap     bmp;
    boolean canDelete=false;
    public static InterstitialAd mInterstitialAd;
public static ProgressBar bar;
     String catName;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        bar =findViewById(R.id.pBar);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4847927719334423/3520937911");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        Intent intent =getIntent();
    catName=    intent.getStringExtra("CATNAME");
        swipeRefreshLayout = findViewById(R.id.swiperefreshcat);
        String url="https://mibtechnologies.in/hupariapp/db_item_upload.php?catnamewa="+catName;
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(catName+" page");
        }
        toolbar.setSubtitle(catName+" choose");
        toolbar.inflateMenu(R.menu.menu);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Log.i("TAG", "onCreate: ");
        RequestQueue queue = Volley.newRequestQueue(this);

        myDataset = new ArrayList();

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                       doYourUpdate();
                    }
                }
        );
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                GsonBuilder gsonBuilder=new GsonBuilder();
//                Gson gson =gsonBuilder.create();
//                CategoryJson categoryJson=gson.fromJson(response,CategoryJson.class);
//                Log.d("TAG", "onResponse: "+categoryJson.getName());
//                Log.d("TAG", "onResponse: "+categoryJson.getUid().trim());
                GsonBuilder builder = new GsonBuilder();
                Gson gson1 = builder.create();

                ItemsJson[] itemsJsons = gson1.fromJson(response, ItemsJson[].class);
                for (ItemsJson itemJson : itemsJsons) {
                    Log.i("TAG", "onResponse: " + itemJson.getName());
                    items item = new items(itemJson.getName(),itemJson.getStars(),itemJson.getRatings(),itemJson.getRanks(),itemJson.getAddress(),
                                            itemJson.getPhone(),itemJson.getStatus(),itemJson.getImage(),catName,itemJson.getEmail(),itemJson.getDescription());
                    myDataset.add(item);
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "onErrorResponse:", error);
            }
        });
        queue.add(request);

        // specify an adapter (see also next example)
        mAdapter = new itemAdapter(this, myDataset,catName);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                bmp = BitmapFactory.decodeStream(inputStream);
                Picasso.get().load(data.getData()).into(ItemUploadDialog.itemImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    private void doYourUpdate() {
        // TODO implement a refresh
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="https://mibtechnologies.in/hupariapp/db_item_upload.php?catnamewa="+catName;

        myDataset=new ArrayList();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


//                GsonBuilder gsonBuilder=new GsonBuilder();
//                Gson gson =gsonBuilder.create();
//                CategoryJson categoryJson=gson.fromJson(response,CategoryJson.class);
//                Log.d("TAG", "onResponse: "+categoryJson.getName());
//                Log.d("TAG", "onResponse: "+categoryJson.getUid().trim());
                GsonBuilder builder=new GsonBuilder();

                Gson gson1= builder.create();

                ItemsJson[] itemsJsons = gson1.fromJson(response, ItemsJson[].class);
                for (ItemsJson itemJson : itemsJsons) {
                    Log.i("TAG", "onResponse: " + itemJson.getName());
                    items item = new items(itemJson.getName(),itemJson.getStars(),itemJson.getRatings(),itemJson.getRanks(),itemJson.getAddress(),
                            itemJson.getPhone(),itemJson.getStatus(),itemJson.getImage(),catName,itemJson.getEmail(),itemJson.getDescription());
                    myDataset.add(item);
                }
                mAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "onErrorResponse:",error );
            }
        });



        queue.add(request);
        // specify an adapter (see also next example)
        mAdapter = new itemAdapter(this,myDataset,catName);
        recyclerView.setAdapter(mAdapter);


        swipeRefreshLayout.setRefreshing(false); // Disables the refresh icon

    }

}