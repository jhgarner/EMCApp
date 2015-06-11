package com.jkrmnj465gmail.emcapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jack on 1/13/2015.
 */
public class GetPosts extends AsyncTask<String, Void, String[][]> {

    String posts[][] = new String[20][2];
    Matcher postMatcher;
    Pattern postFinder;

    protected String[][] doInBackground(String... urls) {

        boolean inPost = false;
        int currentLine = 0;
        int startPost = 0;
        int numPosts = 0;
        URL post = null;
        try {
            post = new URL("http://empireminecraft.com/" + urls[0]);
        } catch (MalformedURLException e2) {
// TODO Auto-generated catch block
            e2.printStackTrace();
        }
        //Are we connected to the internet?
        boolean abort = false;
        //Opening the connection
        BufferedReader why = null;
        try {
            why = new BufferedReader(
                    new InputStreamReader(post.openStream()));
        } catch (IOException e1) {
            posts[0][0] = "An error has occured. Please check your internet connection";
            posts[0][1] = "abort";
            abort = true;
            e1.printStackTrace();
        }
        BufferedReader in = why;

        String inputLine ="";
        if(!abort) {
            postFinder = Pattern.compile("article>");
            try {
                while ((inputLine = in.readLine()) != null) {
                    postMatcher = postFinder.matcher(inputLine);
                    if(postMatcher.find()){
                        if(!inPost) {
                            inPost = true;

                        }
                        else{
                            inPost = false;
                            Log.w("psot", posts[numPosts][0]);
                            numPosts++;
                        }
                        continue;
                    }
                    if(inPost) {
                        posts[numPosts][0] += inputLine;
                    }
                }
                try {
                    in.close();
                } catch (IOException e) {
                    posts[0][0] = "An error has occured. Please check your internet connection";
                    posts[0][1] = "An error has occured. Please check your internet connection";
                    e.printStackTrace();
                }
            } catch (IOException e) {
                posts[0][0] = "An error has occured. Please check your internet connection";
                posts[0][1] = "An error has occured. Please check your internet connection";
                e.printStackTrace();
            }
        }
        return posts;
    }
    protected void onProgressUpdate() {

    }

    protected void onPostExecute(String[][] donePost) {

    }
}

