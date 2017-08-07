package co.id.kufhed.movie;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by madinf on 8/7/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    public Context appContext;
    public List<TraillerModel> listMovie;
    RecycleViewItemClick recycleViewItemClick;



    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        public View view;


        private void initView(View rootView) {
            title = (TextView) rootView.findViewById(R.id.title);
            view = rootView;
        }

        public ViewHolder(View v) {
            super(v);
            initView(v);
        }
    }

    public TrailerAdapter(List<TraillerModel> listMovie, Context c, RecycleViewItemClick recycleViewItemClick) {
        this.listMovie = listMovie;
        this.appContext = c;
        this.recycleViewItemClick = recycleViewItemClick;
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TraillerModel dh = listMovie.get(position);
        holder.title.setText(dh.name);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recycleViewItemClick.onItemClick(position);

            }
        });
       

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_trailler, parent, false);
        return new ViewHolder(mItemView);
    }

    interface RecycleViewItemClick {
        void onItemClick(int position);
    }
}

