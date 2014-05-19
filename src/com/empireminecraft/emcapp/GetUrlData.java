package com.empireminecraft.emcapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GetUrlData extends AsyncTask<URL, Integer, String[][]>{
	String[][] threads = new String[50][2];
	protected String[][] doInBackground(URL... selection){
			URL section = null;
			
			try {
				section = new URL(selection[0].toString());
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String[] forums = {
					"http://empireminecraft.com/forums/empire-news.9/", "http://empireminecraft.com/forums/empire-events.53/", "http://empireminecraft.com/forums/empire-events.53/", "http://empireminecraft.com/forums/empire-help-support.11/",
					"http://empireminecraft.com/forums/official-empire-guides.61/", "http://empireminecraft.com/forums/the-suggestion-box.48/",
					"http://empireminecraft.com/forums/introduce-yourself.5/", "http://empireminecraft.com/forums/share-your-emc-creations.6/", "http://empireminecraft.com/forums/public-member-events.54/",
					"http://empireminecraft.com/forums/community-discussion.7/", "http://empireminecraft.com/forums/wilderness-frontier.58/", "http://empireminecraft.com/forums/general-minecraft-discussion.18/",
					"http://empireminecraft.com/forums/marketplace-discussion.55/", "http://empireminecraft.com/forums/products-businesses-services.39/",
					"http://empireminecraft.com/forums/community-auctions.42/", "http://empireminecraft.com/forums/share-your-lets-plays.60/", "http://empireminecraft.com/forums/gaming.20/", "http://empireminecraft.com/forums/miscellaneous.29/"
			};
			boolean abort = false;
			BufferedReader why = null;
			try {
				why = new BufferedReader(
					new InputStreamReader(section.openStream()));
			} catch (IOException e1) {
				threads[0][0] = "An error has occured. Please check your internet connection";
				threads[0][1] = "abort";
				abort = true;
				e1.printStackTrace();
			}
				
			 
			BufferedReader in = why;
			String inputLine ="";
			
			
			int numThread = 0;
			boolean lineFound = false;
			boolean foundStart = false;
			int start = 0;
			boolean firstQuote = false;
      	    int webLoc = 0;
			if(abort == false){
				/*---------------------
				 * 
				 * Lots of complex stuff happens here that involves the HTML
				 * I will not be explaining this stuff because it involves a basic understanding of the website's HTML
				 * Basically, I am looking for the parts that tell me when I am about to get the url to the thread and it's title
				 * 
				 *---------------------
				 */
				try {
					while ((inputLine = in.readLine()) != null){
						firstQuote = false;
						foundStart = false;
						for (int i = 0; i <= (inputLine.length() - 14); i++) {
							
					           if (inputLine.regionMatches(i, "PreviewTooltip", 0, 14)) {
					        	   lineFound = true;
					           }
					           else if(lineFound == true){
					        	  
					        	   for (int ii = 0; ii < (inputLine.length() - 1); ii++) {
					        		    if(!firstQuote){
					        			   
					        			   if (inputLine.regionMatches(ii, "=", 0, 1)){
					        				   
					        				   webLoc = ii + 1;
					        				   firstQuote = true;
					        			   }
					        		    }
					        			else{
					        				 if(inputLine.regionMatches(ii, ">", 0, 1)){
				        					   threads[numThread][1] = inputLine.substring(webLoc, ii);
				        					   firstQuote = false;
				        				   }
				        			   }
					        		   if (!foundStart){
					        			   
					        			   if (inputLine.regionMatches(ii, ">", 0, 1)) {
					        				   start = ii;
					        				   foundStart = true;
					        			   }
					        		   }
					        		   else{
					        			   if (inputLine.regionMatches(ii, "<", 0, 1)) {
					        				  String title = inputLine.substring(start + 1, ii);
					        		
						        				  threads[numThread][0] = title;
						        				  numThread++;
						        				
					        				  foundStart = false;
					        				  lineFound = false;
					        				  break;
								           }
					        		   }
					        		   
							        }
					           }
					           
					        }
						threads[49][0] = Integer.toString(numThread);
			
	       
					}
					 try {
							in.close();
						} catch (IOException e) {
							threads[0][0] = "An error has occured. Please check your internet connection";
							threads[0][1] = "An error has occured. Please check your internet connection";
							e.printStackTrace();
						}
					} catch (IOException e) {
						threads[0][0] = "An error has occured. Please check your internet connection";
						threads[0][1] = "An error has occured. Please check your internet connection";

						e.printStackTrace();
					}
			}
	        return threads;
	        
	}
	protected void onPostExecute(String[] threads){
		
	}
}
