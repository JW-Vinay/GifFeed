package com.giffedup.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.giffedup.R;
import com.giffedup.model.Content;
import com.giffedup.model.ImageConfigurationModel;
import com.giffedup.utils.ItemClickListener;

import java.util.List;

/**
 * Created by vinay-r on 8/17/15.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private Context mContext;
    private List<? extends Content> mItems;
    private LayoutInflater mLayoutInflater;
    private ItemClickListener mItemClickListener;

    public GridAdapter(Context context, List<? extends Content> items) {
        mContext = context;
        mItems   = items;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.grid_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageConfigurationModel  imageConfigurationModel = mItems.get(position).getDownsizedImage();
        Uri uri = Uri.parse(imageConfigurationModel.getUrl());
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        holder.mImageView.setController(controller);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SimpleDraweeView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (SimpleDraweeView) itemView.findViewById(R.id.imageview);
        }

         @Override
         public void onClick(View v) {
             if(mItemClickListener != null)
                mItemClickListener.onClick(v, getAdapterPosition());
         }
     }

    public void setOnItemClicklistener(ItemClickListener itemClicklistener) {
        this.mItemClickListener = itemClicklistener;
    }
}
