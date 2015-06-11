package com.jkrmnj465gmail.emcapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jack on 11/30/2014.
 */
public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    public final static String SECTION = "com.jkrmnj465gmail.emcapp.SECTION";
    /*
     * This class has the purpose of making Recycler view useful and using certain mandatory methods.
     * Adapter is in charge of taking raw data (such as a string[]) and sorting it for the Recycler view
     * This gives recycler view info and views that are put into each parent.
     */
    //This variable stores what was clicked. -1 means nothing and is just a placeholder. It is public so main activity can get it.
    public int click = -1;
    //These hold the variables for the forum sections.
    private String[] sections;
    private String[] descriptions;

    //A helpful viewholder that gives the adapter access to all of the views and helps with performance
    //Important just to know that any view in the recycler goes here.
    public static final class SectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView section_name;
        TextView section_description;
        public MyViewHolderClicks mListener;

        public SectionViewHolder(View v, MyViewHolderClicks listener){
            super(v);
            mListener = listener;
            this.section_name = (TextView) v.findViewById(R.id.section_name);
            this.section_description = (TextView) v.findViewById(R.id.section_description);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mListener.onSectionClick(v, this.getPosition());
        }

        public interface MyViewHolderClicks {
            public void onSectionClick(View caller, int position);
        }
    }

    // Simple constructor
    public SectionAdapter(String[] sections, String[] descriptions) {
        this.sections = sections;
        this.descriptions = descriptions;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SectionAdapter.SectionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.section_viewholder, viewGroup, false);
        // Do things like create the viewholder and return it. Also creates the onclick listener
        SectionAdapter.SectionViewHolder vh = new SectionViewHolder(v, new SectionAdapter.SectionViewHolder.MyViewHolderClicks() {
            //Modify this to change click behavior
            public void onSectionClick(View caller, int position) {
                click = position;
                Intent intent = new Intent(caller.getContext(),ThreadList.class);
                intent.putExtra(SECTION, click);
                caller.getContext().startActivity(intent);
                Log.e("MAybe", Integer.toString(click));
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.section_name.setText(sections[position]);
        holder.section_description.setText(descriptions[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sections.length;
    }

}
