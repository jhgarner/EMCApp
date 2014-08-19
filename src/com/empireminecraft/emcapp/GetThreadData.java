package com.empireminecraft.emcapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class GetThreadData extends AsyncTask<URL, Integer, String>{

	@Override
	protected String doInBackground(URL... selection) {
		URL section = null;
		String post = null;
		try {
			section = new URL(selection[0].toString());
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String inputLine ="";
		boolean abort = false;
		BufferedReader why = null;
		try {
			why = new BufferedReader(
				new InputStreamReader(section.openStream()));
		} catch (IOException e1) {
			abort = true;
			e1.printStackTrace();
		}
		BufferedReader in = why;
		
		int foundStart = 0;
		char[] postContent = new char[100000];
		int postSize = 0;
		try {
			why = new BufferedReader(
				new InputStreamReader(section.openStream()));
		} catch (IOException e1) {
			post = null;
			post = "an error has occured";
			abort = true;
			e1.printStackTrace();
		}
		if(abort == false){
			try {
				/*---------------------
				 * 
				 * Lots of complex stuff happens here that involves the HTML
				 * I will not be explaining this stuff because it involves a basic understanding of the website's HTML
				 * Basically, I am looking for the parts that show the message and am saving it to be parsed by a built in thing
				 * 
				 *---------------------
				 */
				while ((inputLine = in.readLine()) != null && foundStart != 2){
					for (int i = 0; i <= (inputLine.length() - 1); i++) {
						
				           
				           if(foundStart == 1 || foundStart == 3){
				        	   if(inputLine.regionMatches(i, "<div class=\"attribution type\">", 0, 37)){
				        		   foundStart = 3;
				        	   }
				        	   
				        		   postSize++;
				        	   
		        			   
		        			   if(inputLine.regionMatches(i, "blockquote", 0, 10) && foundStart != 3){
		        				   foundStart = 2;
		        				  
		        			   }
		        			   if(inputLine.regionMatches(i, "blockquote", 0, 10) && foundStart == 3){
		        				   foundStart = 4;
		        				  
		        			   }
		        			   if(inputLine.regionMatches(i, "blockquote", 0, 10) && foundStart == 4){
		        				   foundStart = 1;
		        				  
		        			   }
		        			   postContent[postSize] = inputLine.charAt(i);
		        			   //if(inputLine.regionMatches(i, "<
					       
				           }
				           else if (inputLine.regionMatches(i, "blockquote", 0, 10)) {
				        	   foundStart = 1;
				        	   i = 10000;
				           }
				           
				        }
		
       
				}
				post = new String(postContent);
				 try {
						in.close();
					} catch (IOException e) {
						post = "Error";
						e.printStackTrace();
					}
				} catch (IOException e) {

					e.printStackTrace();
				}
		}
		return post;
	}

}
