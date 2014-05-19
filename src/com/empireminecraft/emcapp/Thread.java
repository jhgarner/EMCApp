package com.empireminecraft.emcapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Thread extends Activity {
	String[] webLoc = null;
	public final static String THREAD = "com.empireminecraft.emcapp.FORUM";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		String selection = intent.getStringExtra(MainActivity.FORUM_CHOICE);
		
	        
		URL section = null;
		String[] threads = null;
		boolean abort = false;
		try {
			//list of forums so the app can identify which was clicked
			String[] forums = {
					"Empire News", "Empire Events", "Help and Support", "Guides", "Suggestion Box",
					"Introductions", "EMC Creations", "Public Events", "Discussions", "Wild", "General",
					"Marketplace", "Products, Businesses, and Services", "Auctions",
					"Let's Play", "Gaming", "Miscellaneous", "Artist's gallery", "Shutter talk", "Writers corner"
			};
			
					if(selection.equals(forums[0])) { section = new URL("http://empireminecraft.com/forums/empire-news.9/");
					}
					else if(selection.equals(forums[1])) { section = new URL("http://empireminecraft.com/forums/empire-events.53/");
					}
					else if(selection.equals(forums[2])) { section = new URL("http://empireminecraft.com/forums/empire-help-support.11/");
					}
					else if(selection.equals(forums[3])) { section = new URL("http://empireminecraft.com/forums/official-empire-guides.61/");
					}
					else if(selection.equals(forums[4])) { section = new URL("http://empireminecraft.com/forums/the-suggestion-box.48/");
					}
					else if(selection.equals(forums[5])) { section = new URL("http://empireminecraft.com/forums/introduce-yourself.5/");
					}
					else if(selection.equals(forums[6])) { section = new URL("http://empireminecraft.com/forums/share-your-emc-creations.6/");
					}
					else if(selection.equals(forums[7])) { section = new URL("http://empireminecraft.com/forums/public-member-events.54/");
					}
					else if(selection.equals(forums[8])) { section = new URL("http://empireminecraft.com/forums/community-discussion.7/");
					}
					else if(selection.equals(forums[9])) { section = new URL("http://empireminecraft.com/forums/wilderness-frontier.58/");
					}
					else if(selection.equals(forums[10])){ section = new URL("http://empireminecraft.com/forums/general-minecraft-discussion.18/");
					}
					else if(selection.equals(forums[11])){ section = new URL("http://empireminecraft.com/forums/marketplace-discussion.55/");
					}
					else if(selection.equals(forums[12])) { section = new URL("http://empireminecraft.com/forums/products-businesses-services.39/");
					}
					else if(selection.equals(forums[13])) { section = new URL("http://empireminecraft.com/forums/community-auctions.42/");
					}
					else if(selection.equals(forums[14])) { section = new URL("http://empireminecraft.com/forums/share-your-lets-plays.60/");
					}
					else if(selection.equals(forums[15])) { section = new URL("http://empireminecraft.com/forums/gaming.20/");
					}
					else if(selection.equals(forums[16])) { section = new URL("http://empireminecraft.com/forums/miscellaneous.29/");
					}
					else if(selection.equals(forums[17])) { section = new URL("http://empireminecraft.com/forums/artists-gallery.73/");
					}
					else if(selection.equals(forums[18])) { section = new URL("http://empireminecraft.com/forums/shutter-talk.74/");
					}
					else if(selection.equals(forums[19])) { section = new URL("http://empireminecraft.com/forums/writers-corner.75/");
					}
					else{
						section = new URL("http://empireminecraft.com/forums/empire-news.9/");
					}
			} catch (MalformedURLException e) {
				abort = true;
				e.printStackTrace();
			}
			GetUrlData task = new GetUrlData();
			task.execute(section);//This is the async task that scrapes the HTML for the necessary information
			
			
			try {
				String[][] returnValues = new String[50][2];//You will notice that the async task returns a 2d array
				returnValues = task.get();
				if(returnValues[0][1] != "abort"){
					int numThreads = Integer.parseInt(returnValues[49][0]);//The total number of threads is stored in this location
					threads = new String[numThreads];
					webLoc = new String[numThreads];
					for(int i=0; i<threads.length; i++){
						threads[i] = returnValues[i][0];
						webLoc[i] = returnValues[i][1];
					}
				}
				else{
					threads = new String[1];
					threads[0] = "An error occured. Please check that you have an internet connection and that the site is up";
				}
			} catch (InterruptedException e) {
				threads = new String[1];
				threads[0] = "An error occured. Please check that you have an internet connection and that the site is up";
				e.printStackTrace();
			} catch (ExecutionException e) {
				threads = new String[1];
				threads[0] = "An error occured. Please check that you have an internet connection and that the site is up";
				e.printStackTrace();
			}
			ArrayAdapter adapter = new ArrayAdapter<String>(this, 
			        android.R.layout.simple_list_item_1, threads);
			final ListView listView = (ListView) findViewById(R.id.threads);
			listView.setAdapter(adapter);
			
			listView.setOnItemClickListener(new OnItemClickListener(){

				
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				final int positionFinal = position;
				Context context = getApplicationContext();
		        CharSequence text = "Loading...";
		        int duration = Toast.LENGTH_SHORT;

		        Toast toast = Toast.makeText(context, text, duration);
		        toast.show();
		        launchActivity(positionFinal);
				
                
			}
			});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thread, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void createUI(String[] threads){
		ArrayAdapter adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, threads);
		final ListView listView = (ListView) findViewById(R.id.threads);
		listView.setAdapter(adapter);
	}
	public void launchActivity(int position){
		
		Intent intent = new Intent(Thread.this, Post.class);
		intent.putExtra(THREAD, webLoc[position]);
        startActivity(intent);

	}
}
