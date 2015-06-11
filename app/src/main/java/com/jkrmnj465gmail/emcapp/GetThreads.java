package com.jkrmnj465gmail.emcapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jack on 12/6/2014.
 */
public class GetThreads extends AsyncTask<URL, Integer, String[][]> {
    public String[][] threads = new String[50][3];
    public Bitmap avatars[];
    Matcher avatarMatcher;
    Pattern avatarFinder;
    Pattern threadFinder;
    Pattern titleFinder;
    Matcher urlMatcher;
    Matcher titleMatcher;
    View loading;
    View recycler;
    Context context;
    ProgressBar spinner;
    fillUiInterface callback;
    int numThread;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public GetThreads(View loading, Context context, View recycler, fillUiInterface callback){
        this.loading = loading;
        this.context = context;
        this.recycler = recycler;
        this.callback = callback;
        Log.w("Loading",String.valueOf(loading));
        Log.w("context",String.valueOf(context));
        Log.w("recycler",String.valueOf(recycler));
    }
    @Override
    protected void onPreExecute(){
        spinner = (ProgressBar) loading;
        spinner.setVisibility(View.VISIBLE);
        Log.w("Dose this get run", "YESITDOES");
    }
    @Override
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
        //Are we connected to the internet?
        boolean abort = false;
        //Opening the connection
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
        //Stores the number of threads in the section. Varies because of pins
        numThread = 0;
        //Small variables used to tell the parser what to look for
        int avatarEndUrl = 0;
        boolean foundStart = false;
        int start = 0;
        boolean firstQuote = false;
        int webLoc = 0;
        int urlEnd = 0;

        //When finding is equal to 0, it is looking for avatar. 1 looks for url. 2 looks for title.
        int finding = 0;

        avatarFinder = Pattern.compile("data-avatarHtml=\"true\"><img src=\"");
        threadFinder = Pattern.compile("<a href=\"threads/");
        titleFinder = Pattern.compile("/preview\">");

        //Time to start scraping the file but only if we are connected to the internet
        if(!abort){
            try {
                while ((inputLine = in.readLine()) != null) {
                    switch (finding) {
                        case 0:
                            //A matcher for finding the avatars
                            avatarMatcher = avatarFinder.matcher(inputLine);
                            //Log.d("did?", String.valueOf(htmlInput.find()));
                            //Did we get a match?
                            if (avatarMatcher.find()) {
                                //If we got a match, we need to find where the url ends
                                for (int i = avatarMatcher.end(); i < inputLine.length(); i++) {
                                    if (inputLine.charAt(i) == '\"') {
                                        avatarEndUrl = i;
                                        break;
                                    }
                                }
                                //Grab the url from the line

                                threads[numThread][2] = inputLine.substring(avatarMatcher.end(), avatarEndUrl);
                                Log.w("Avatar", threads[numThread][2]);
                                finding++;
                            }
                            break;
                        case 1:
                            urlMatcher = threadFinder.matcher(inputLine);
                            if (urlMatcher.find()) {
                                for (int i = 15; i < inputLine.length(); i++) {
                                    if (inputLine.charAt(i) == '\"') {
                                        urlEnd = i - 1;
                                        break;
                                    }
                                }
                                threads[numThread][0] = inputLine.substring(13, urlEnd);
                                Log.w("url", threads[numThread][0]);
                                finding++;
                            }
                            break;
                        case 2:
                            titleMatcher = titleFinder.matcher(inputLine);
                            if (titleMatcher.find()) {
                                threads[numThread][1] = inputLine.substring(titleMatcher.end(), inputLine.length() - 4);
                                Log.w("title", threads[numThread][1]);
                                numThread++;
                                finding = 0;
                            }
                            break;
                    }


                }
                //This specific index is used to tell other parts of the app how many threads there were.
                threads[49][0] = Integer.toString(numThread);
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
            avatars = new Bitmap[numThread];
            for (int i = 0; i < numThread; i++) {
                URL url = null;
                try {
                    if(threads[i][2].charAt(4) == ':') {
                        url = new URL(threads[i][2]);
                    }
                    else{
                        url = new URL("http://empireminecraft.com/" + threads[i][2]);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    avatars[i] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return threads;
    }
    @Override
    protected void onPostExecute(String[][] threads){
        String[][] sizedThreads = new String[numThread][3];
        for(int i = 0; i<numThread; i++){
            sizedThreads[i] = threads[i];
        }
        spinner.setVisibility(View.GONE);
        callback.onFinish(sizedThreads, avatars);


    }
    public interface fillUiInterface {
        public void onFinish(String[][] threads, Bitmap[] avatars);
    }

}
