package co.id.kufhed.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener, ReviewAdapter.RecycleViewItemClick {

    protected Toolbar toolbar;
    protected TextView textError;
    protected ProgressBar progress;
    protected ImageView refreshButton;
    protected RecyclerView recycler;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<ReviewModel> reviewModelArrayList;
    String movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_review);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        initView();
        movieId = getIntent().getExtras().getString("movieId");
        if(savedInstanceState==null || !savedInstanceState.containsKey("instanceParcel")){

            reviewModelArrayList = new ArrayList<>();
            getReview();
        }else{
            progress.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);
            textError.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            reviewModelArrayList = savedInstanceState.getParcelableArrayList("instanceParcel");
            adapter = new ReviewAdapter(reviewModelArrayList, this, this);
            recycler.setAdapter(adapter);
        }


    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textError = (TextView) findViewById(R.id.textError);
        progress = (ProgressBar) findViewById(R.id.progress);
        refreshButton = (ImageView) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
    }

    private void getReview(){
        progress.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.GONE);
        textError.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
        MovieApi movieApi = new MovieApi();
        movieApi.getReviewMovie(new MovieApiInterface() {
            @Override
            public void onHandleResult(String result, Exception ex) {
                progress.setVisibility(View.GONE);
                refreshButton.setVisibility(View.GONE);
                textError.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                if(ex!=null){
                    progress.setVisibility(View.GONE);
                    refreshButton.setVisibility(View.VISIBLE);
                    textError.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }else{
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject objectDetail = jsonArray.getJSONObject(i);
                            ReviewModel reviewModel=new ReviewModel();
                            reviewModel.review = objectDetail.getString("content");
                            reviewModel.author = objectDetail.getString("author");
                            reviewModelArrayList.add(reviewModel);
                        }
                        adapter= new ReviewAdapter(reviewModelArrayList, ReviewActivity.this, ReviewActivity.this);
                        recycler.setAdapter(adapter);
                    }catch (Exception e){
                        progress.setVisibility(View.GONE);
                        refreshButton.setVisibility(View.VISIBLE);
                        textError.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                    }
                }
            }
        }, movieId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("instanceParcel", reviewModelArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.refreshButton){
            getReview();
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}
