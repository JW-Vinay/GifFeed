package com.giffedup.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giffedup.R;
import com.giffedup.model.Content;
import com.giffedup.model.ImageConfigurationModel;
import com.giffedup.model.StoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akshay on 10/27/15.
 */
public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.StoryListViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<StoryModel> items;

    public StoryListAdapter(Context context, List<StoryModel> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public StoryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.story_list_item, parent, false);
        StoryListViewHolder holder = new StoryListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StoryListViewHolder holder, int position) {
        StoryModel storymodel = items.get(position);
        ImageConfigurationModel icm = storymodel.getDownsizedStill();
        //Uri uri;
        Uri uri = Uri.parse(icm.getUrl());
        holder.title.setText(storymodel.getTitle());
        //holder.icon.setImageResource(uri);
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_home)
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class StoryListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        public StoryListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.storyListText);
            icon = (ImageView) itemView.findViewById(R.id.storyListIcon);

        }
    }
}
