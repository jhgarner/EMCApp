package com.empireminecraft.emcapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class GetThreadData extends AsyncTask<URL, Integer, String>{
	private static final int NOT_FOUND = 0;
	public static final int IN_POST = 1;
	public static final int DONE = 2;
	public static final int WAITING = 3;
	public static final int WAITING_MORE=4;
	@Override
	protected String doInBackground(URL... selection) {
		URL section = null;
		String[] post = null;
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
		
		int foundStart = NOT_FOUND;
		int commentNum = 1;
		char[] postContent = new char[100000];
		int postSize = 0;
		try {
			why = new BufferedReader(
				new InputStreamReader(section.openStream()));
		} catch (IOException e1) {
			post = new String[1];
			post[0] = "an error has occured";
			abort = true;
			e1.printStackTrace();
		}
		if(abort == false){
			post = new String[20];
			try {
				/*---------------------
				 * 
				 * Lots of complex stuff happens here that involves the HTML
				 * I will not be explaining this stuff because it involves a basic understanding of the website's HTML
				 * Basically, I am looking for the parts that show the message and am saving it to be parsed by a built in thing
				 * 
				 *---------------------
				 */
				while ((inputLine = in.readLine()) != null && foundStart != DONE){
					for (int i = 0; i <= (inputLine.length() - 1); i++) {
						
				           
				           if(foundStart == IN_POST || foundStart == WAITING){
				        	   if(inputLine.regionMatches(i, "<div class=\"attribution type\">", 0, 37)){
				        		   foundStart = WAITING;
				        	   }
				        	   
				        		   postSize++;
				        	   
		        			   
		        			   if(inputLine.regionMatches(i, "blockquote", 0, 10) && foundStart != WAITING){
		        				   foundStart = NOT_FOUND;
		        				  
		        			   }
		        			   if(inputLine.regionMatches(i, "blockquote", 0, 10) && foundStart == WAITING){
		        				   foundStart = WAITING_MORE;
		        				  
		        			   }
		        			   if(inputLine.regionMatches(i, "blockquote", 0, 10) && foundStart == WAITING_MORE){
		        				   foundStart = IN_POST;
		        				   commentNum++;
		        			   }
		        			   postContent[postSize] = inputLine.charAt(i);
					       
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
