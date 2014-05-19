package com.empireminecraft.emcapp;

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
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;

public class Post extends Activity {
	public final static String PICTURES = "com.empireminecraft.emcapp.PIC";
	public String[] preGalleryUrl = new String[50];
	public int[] picLocations = new int[100];
	int n = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
		String selection = intent.getStringExtra(Thread.THREAD);
		URL postUrl = null;//the url
		try {
			int ii = 0;
			char[] preUrl = new char[selection.length() - 1];//turns the url into a char array
			for(int i = 1; i<selection.length() - 9; i++){
				preUrl[ii] = selection.charAt(i);
				ii++;
			}
			String preUrlString = new String(preUrl);
			selection = "http://empireminecraft.com/" + preUrlString;
			postUrl = new URL(selection);//We finally have the URL and can do stuff with it
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		GetThreadData task = new GetThreadData();
		task.execute(postUrl);//calld an async task to scrape the HTML
		String post = null;
		try {
			post = task.get();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int foundPicStart = -1;//This is where things start to get important. Everything before here is for another thing
		
		for(int i = 0; i<post.length(); i++){//finds the images
			if(post.regionMatches(i, "<img src=\"", 0, 10)){
				//Log.d("the i value in the loop that matches", "it is "+ i);
				i += 10;
				picLocations[n++] = i;//gets the beginning of the url for the image
				Log.d("BAlAL", i + "");
				foundPicStart = 0;
			}
			else if(post.regionMatches(i, "\"", 0, 1) && foundPicStart != -1){
				Log.d("Second", i + "");
				picLocations[n++] = i;
				//Log.d("the i value in the loop that comes with the quote", "quote at "+ i);
				foundPicStart = -1;
			}
			
		}
		
		int j = 0;
		if(n > 1){
			preGalleryUrl = new String[n/2];
			for(int i = 0; i<n/2; i++){//finishes the image finding
				preGalleryUrl[i] = post.substring(picLocations[j], picLocations[j+1]);
				j+=2;
				Log.d("IS this working????", n + "");
				if(!preGalleryUrl[i].regionMatches(i, "http", 0, 4)){
					Log.d("this is being called","Yes. this is happening.");
					preGalleryUrl[i] = "http://empireminecraft.com/" + preGalleryUrl[i];
				}
			}
		}
		TextView postHolder = (TextView)findViewById(R.id.post);
		postHolder.setText(Html.fromHtml(post));
		postHolder.setMovementMethod(LinkMovementMethod.getInstance());
		
		
	}
	public void Gallery(View view) {
		if(n>1){
			Bundle b = new Bundle();
			Intent intent = new Intent(this, GalleryView.class);
			b.putStringArray(PICTURES, preGalleryUrl);
			intent.putExtras(b);
	        startActivity(intent);
		}
		else{
			Context context = getApplicationContext();
	        CharSequence text = "No images to show";
	        int duration = Toast.LENGTH_SHORT;

	        Toast toast = Toast.makeText(context, text, duration);
	        toast.show();
		}
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
		getMenuInflater().inflate(R.menu.post, menu);
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

}
