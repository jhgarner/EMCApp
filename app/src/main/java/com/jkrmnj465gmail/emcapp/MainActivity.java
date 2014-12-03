package com.jkrmnj465gmail.emcapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String names[] = {"Empire news", "Empire events", "Empire updates", "Help and support",
            "Suggestion box", "Introduce yourself", "Share your EMC creations", "Public member events",
            "Community discussions", "Wilderness frontier", "General Minecraft discussion", "Marketplace discussion",
            "Products, businesses, and services", "Auctions", "reverse auctions", "Let's play videos",
            "Artists gallery", "Shutter talk", "Writers' corner", "The jukebox", "Gaming", "Miscellaneous"};
    private String descriptions[] = {"Official news about the server", "Events held by staff", "Updates to the server",
            "Get help with your problems", "Suggest features for the Empire", "Get to know your fellow players",
            "See cool builds by players", "Events hosted by your fellow players", "Talk about EMC",
            "Wilderness discussions", "Anything Minecraft related", "Talk about the EMC economy",
            "Businesses, buyers, and sellers", "Kind of like Ebay", "The auction hoster pays a player for an item",
            "Videos made by community members", "Show your arttistic side", "Show your cool pictures",
            "Stories by members", "Anything song related", "Gaming discussions", "Anything else"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.sections);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        SectionAdapter adapter = new SectionAdapter(names, descriptions);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
