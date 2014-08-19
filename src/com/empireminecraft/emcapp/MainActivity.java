package com.empireminecraft.emcapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String FORUM_CHOICE = "com.empireminecraft.emcapp.FORUM";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//list of forums on the Empire Minecraft website
		String[] forums = {
				"Empire News", "Empire Events", "Help and Support", "Guides", "Suggestion Box",
				"Introductions", "EMC Creations", "Public Events", "Discussions", "Wild", "General",
				"Marketplace", "Products, Businesses, and Services", "Auctions",
				"Let's Play", "Gaming", "Miscellaneous", "Artist's gallery", "Shutter talk", "Writers corner"
		};
		//create the list
		ArrayAdapter adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, forums);
		final ListView listView = (ListView) findViewById(R.id.forum_sections);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener(){
		Context context = getApplicationContext();
        CharSequence text = "Loading...";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
					long arg3) {
				//This gives the toast time to appear
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	Object o = listView.getItemAtPosition(position);
				                String selection = o.toString();
				                Log.d("MAIN", selection);
				                Intent intent = new Intent(MainActivity.this, Thread.class);
				                intent.putExtra(FORUM_CHOICE, selection);
				                
				                startActivity(intent);
				            }
				        }, 
				        50 
				);
                toast.show();
				
			}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}

