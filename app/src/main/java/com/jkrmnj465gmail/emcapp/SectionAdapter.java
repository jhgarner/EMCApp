package com.jkrmnj465gmail.emcapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jack on 11/30/2014.
 */
public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    private String[] sections;
    private String[] descriptions;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public final class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView section_name;
        TextView section_description;

        public SectionViewHolder(View v){
            super(v);
            this.section_name = (TextView) v.findViewById(R.id.section_name);
            this.section_description = (TextView) v.findViewById(R.id.section_description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
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
        // set the view's size, margins, paddings and layout parameters
        return new SectionAdapter.SectionViewHolder(v);
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
