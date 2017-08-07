package co.id.kufhed.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by madinf on 7/9/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public Context appContext;
    public List<MovieModel> listMovie;
    RecycleViewItemClick recycleViewItemClick;

    class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageThumb;

        private void initView(View rootView) {
            imageThumb = (ImageView) rootView.findViewById(R.id.imageThumb);
        }

        public ViewHolder(View v) {
            super(v);
            initView(v);
        }
    }

    public MovieAdapter(List<MovieModel> listMovie, Context c, RecycleViewItemClick recycleViewItemClick) {
        this.listMovie = listMovie;
        this.appContext = c;
        this.recycleViewItemClick = recycleViewItemClick;
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MovieModel dh = listMovie.get(position);
        Picasso.with(appContext).load(APIUrl.BASE_URL_IMAGE+dh.getPoster_path()).into(holder.imageThumb);
        holder.imageThumb.setOnClickListener(new View.OnClickListener() {
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
                inflate(R.layout.adapter_movie, parent, false);
        return new ViewHolder(mItemView);
    }

    interface RecycleViewItemClick{
        void onItemClick(int position);
    }
}

