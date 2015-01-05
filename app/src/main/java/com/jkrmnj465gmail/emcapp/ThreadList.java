package com.jkrmnj465gmail.emcapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class ThreadList extends ActionBarActivity implements GetThreads.fillUiInterface {
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    int sectionSelection;
    int section;
    URL threadUrl = null;

    String[][] threads;
    String[][] tempThreads;
    Bitmap[] avatars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_thread);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Intent intent = getIntent();
        sectionSelection = intent.getIntExtra(SectionAdapter.SECTION, 0);
        try {
            switch (sectionSelection){
                case 0:
                        threadUrl = new URL("http://empireminecraft.com/forums/empire-news.9/");
                break;
                case 1:
                    threadUrl = new URL("http://empireminecraft.com/forums/empire-events.53/");
                break;
                case 2:
                    threadUrl = new URL("http://empireminecraft.com/forums/empire-updates/");
                break;
                case 3:
                    threadUrl = new URL("http://empireminecraft.com/forums/empire-help-support.11/");
                break;
                case 4:
                    threadUrl = new URL("http://empireminecraft.com/forums/the-suggestion-box.48/");
                break;
                case 5:
                    threadUrl = new URL("http://empireminecraft.com/forums/introduce-yourself.5/");
                break;
                case 6:
                    threadUrl = new URL("http://empireminecraft.com/forums/share-your-emc-creations.6/");
                break;
                case 7:
                    threadUrl = new URL("http://empireminecraft.com/forums/public-member-events.54/");
                break;
                case 8:
                    threadUrl = new URL("http://empireminecraft.com/forums/community-discussion.7/");
                break;
                case 9:
                    threadUrl = new URL("http://empireminecraft.com/forums/wilderness-frontier.58/");
                break;
                case 10:
                    threadUrl = new URL("http://empireminecraft.com/forums/general-minecraft-discussion.18/");
                break;
                case 11:
                    threadUrl = new URL("http://empireminecraft.com/forums/marketplace-discussion.55/");
                break;
                case 12:
                    threadUrl = new URL("http://empireminecraft.com/forums/products-businesses-services.39/");
                break;
                case 13:
                    threadUrl = new URL("http://empireminecraft.com/forums/community-auctions.42/");
                break;
                case 14:
                    threadUrl = new URL("http://empireminecraft.com/forums/reverse-auctions.84/");
                break;
                case 15:
                    threadUrl = new URL("http://empireminecraft.com/forums/share-your-lets-plays-and-other-videos.60/");
                break;
                case 16:
                    threadUrl = new URL("http://empireminecraft.com/forums/artists-gallery.73/");
                break;
                case 17:
                    threadUrl = new URL("http://empireminecraft.com/forums/shutter-talk.74/");
                break;
                case 18:
                    threadUrl = new URL("http://empireminecraft.com/forums/writers-corner.75/");
                break;
                case 19:
                    threadUrl = new URL("http://empireminecraft.com/forums/the-jukebox.79/");
                break;
                case 20:
                    threadUrl = new URL("http://empireminecraft.com/forums/gaming.20/");
                break;
                case 21:
                    threadUrl = new URL("http://empireminecraft.com/forums/miscellaneous.29/");
                break;
                default:
                    threadUrl = new URL("http://empireminecraft.com/forums/miscellaneous.29/");
                break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("test", String.valueOf(threadUrl));
        AsyncTask test = new GetThreads(findViewById(R.id.loadingThread), this, findViewById(R.id.thread_list), this).execute(threadUrl);
       /* try {
            tempThreads = (String[][])test.get();
            Log.w("IS this right?", tempThreads[49][0]);
            String[][] sizedThreads = new String[Integer.parseInt(tempThreads[49][0])][3];
            for(int i = 0; i<Integer.parseInt(tempThreads[49][0]) ; i++){
                sizedThreads[i] = tempThreads[i];
            }
            mRecyclerView = (RecyclerView) findViewById(R.id.thread_list);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            ThreadListAdapter adapter = new ThreadListAdapter(Sizedthreads, avatars);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        mRecyclerView = (RecyclerView) findViewById(R.id.thread_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread_list, menu);
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
    public void onFinish(String[][] threads, Bitmap[] avatars) {

        ThreadListAdapter adapter = new ThreadListAdapter(threads, avatars);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));
    }



}
