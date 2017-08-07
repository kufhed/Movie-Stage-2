package co.id.kufhed.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by madinf on 8/7/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public Context appContext;
    public List<ReviewModel> listMovie;

    RecycleViewItemClick recycleViewItemClick;



    class ViewHolder extends RecyclerView.ViewHolder {

        protected View rootView;
        protected TextView author;
        protected TextView review;

        private void initView(View rootView) {
            author = (TextView) rootView.findViewById(R.id.author);
            review = (TextView) rootView.findViewById(R.id.review);
        }

        public ViewHolder(View v) {
            super(v);
            initView(v);
        }
    }

    public ReviewAdapter(List<ReviewModel> listMovie, Context c, RecycleViewItemClick recycleViewItemClick) {
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
        final ReviewModel dh = listMovie.get(position);
        holder.author.setText(dh.getAuthor());
        holder.review.setText(dh.getReview());



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_review, parent, false);
        return new ViewHolder(mItemView);
    }

    interface RecycleViewItemClick {
        void onItemClick(int position);
    }
}

