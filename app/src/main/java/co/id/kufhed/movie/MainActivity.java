package co.id.kufhed.movie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import co.id.kufhed.movie.data.MoviesContract;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MovieAdapter.RecycleViewItemClick,LoaderManager.LoaderCallbacks<Cursor> {

    protected Toolbar toolbar;
    protected RecyclerView recyclerView;
    protected ProgressBar loadingProgress;
    protected ImageView refreshButton;
    protected TextView errorTextview;
    protected RelativeLayout relativeMessage;
    private ArrayList<MovieModel> movieModelArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private int CURSOR_LOADER_ID=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        if(savedInstanceState==null || !savedInstanceState.containsKey("movieModel")){
            movieModelArrayList = new ArrayList<>();
            getDataPopularMovies();
        }else{
            loadingProgress.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            errorTextview.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);
            movieModelArrayList = savedInstanceState.getParcelableArrayList("movieModel");
            adapter = new MovieAdapter(movieModelArrayList, MainActivity.this, MainActivity.this);
            recyclerView.setAdapter(adapter);

        }



    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
        refreshButton = (ImageView) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(MainActivity.this);
        errorTextview = (TextView) findViewById(R.id.errorTextview);
        relativeMessage = (RelativeLayout) findViewById(R.id.relativeMessage);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
    }



    private void getDataPopularMovies(){
        movieModelArrayList=new ArrayList<>();
        this.loadingProgress.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);
        this.errorTextview.setVisibility(View.GONE);
        this.refreshButton.setVisibility(View.GONE);
        if(NetworkUtils.getConnectionStatus(MainActivity.this)){
            MovieApi movieApi = new MovieApi();
            movieApi.getPopularMovie(new MovieApiInterface() {
                @Override
                public void onHandleResult(String result, Exception ex) {
                    if(ex!=null){
                        errorTextview.setText(ex.getMessage());
                        loadingProgress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        errorTextview.setVisibility(View.VISIBLE);
                        refreshButton.setVisibility(View.VISIBLE);
                    }else{
                        try{
                            loadingProgress.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            errorTextview.setVisibility(View.GONE);
                            refreshButton.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject objectResult = jsonArray.getJSONObject(i);
                                MovieModel movieModel = new MovieModel();
                                movieModel.setVote_count(objectResult.getString("vote_count"));
                                movieModel.setId(objectResult.getString("id"));
                                movieModel.setVideo(objectResult.getString("video"));
                                movieModel.setVote_average(objectResult.getString("vote_average"));
                                movieModel.setTitle(objectResult.getString("title"));
                                movieModel.setPopularity(objectResult.getString("popularity"));
                                movieModel.setPoster_path(objectResult.getString("poster_path"));
                                movieModel.setOriginal_language(objectResult.getString("original_language"));
                                movieModel.setOriginal_title(objectResult.getString("original_title"));
                                movieModel.setBackdrop_path(objectResult.getString("backdrop_path"));
                                movieModel.setAdult(objectResult.getString("adult"));
                                movieModel.setOverview(objectResult.getString("overview"));
                                movieModel.setRelease_date(objectResult.getString("release_date"));
                                movieModelArrayList.add(movieModel);
                            }
                            adapter = new MovieAdapter(movieModelArrayList, MainActivity.this, MainActivity.this);
                            recyclerView.setAdapter(adapter);


                        }catch (Exception e){
                            e.printStackTrace();
//                        errorTextview.setText(e.getMessage());
                            loadingProgress.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            errorTextview.setVisibility(View.VISIBLE);
                            refreshButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }else{

//                        errorTextview.setText(e.getMessage());
            errorTextview.setText(getString(R.string.nointernet));
            loadingProgress.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            errorTextview.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.VISIBLE);
        }

    }

    private void getDataTopRatedMovies(){
        movieModelArrayList=new ArrayList<>();
        this.loadingProgress.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);
        this.errorTextview.setVisibility(View.GONE);
        this.refreshButton.setVisibility(View.GONE);
        if(NetworkUtils.getConnectionStatus(MainActivity.this)){
            final MovieApi movieApi = new MovieApi();
            movieApi.getTopRatedMovie(new MovieApiInterface() {
                @Override
                public void onHandleResult(String result, Exception ex) {
                    if(ex!=null){
                        loadingProgress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        errorTextview.setVisibility(View.VISIBLE);
                        refreshButton.setVisibility(View.VISIBLE);
                    }else{
                        try{
                            loadingProgress.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            errorTextview.setVisibility(View.GONE);
                            refreshButton.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject objectResult = jsonArray.getJSONObject(i);
                                MovieModel movieModel = new MovieModel();
                                movieModel.setVote_count(objectResult.getString("vote_count"));
                                movieModel.setId(objectResult.getString("id"));
                                movieModel.setVideo(objectResult.getString("video"));
                                movieModel.setVote_average(objectResult.getString("vote_average"));
                                movieModel.setTitle(objectResult.getString("title"));
                                movieModel.setPopularity(objectResult.getString("popularity"));
                                movieModel.setPoster_path(objectResult.getString("poster_path"));
                                movieModel.setOriginal_language(objectResult.getString("original_language"));
                                movieModel.setOriginal_title(objectResult.getString("original_title"));
                                movieModel.setBackdrop_path(objectResult.getString("backdrop_path"));
                                movieModel.setAdult(objectResult.getString("adult"));
                                movieModel.setOverview(objectResult.getString("overview"));
                                movieModel.setRelease_date(objectResult.getString("release_date"));
                                movieModel.setFavorite(0);
                                movieModelArrayList.add(movieModel);
                            }
                            adapter = new MovieAdapter(movieModelArrayList, MainActivity.this, MainActivity.this);
                            recyclerView.setAdapter(adapter);


                        }catch (Exception e){
                            e.printStackTrace();
                            loadingProgress.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            errorTextview.setVisibility(View.VISIBLE);
                            refreshButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }else{
            errorTextview.setText(getString(R.string.nointernet));
            loadingProgress.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            errorTextview.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.VISIBLE);
        }

    }

    void getDataFavorite(){

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.refreshButton) {
            getDataPopularMovies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_popular) {
            getDataPopularMovies();
        }else if(id == R.id.action_toprated){
            getDataTopRatedMovies();
        }else if(id == R.id.action_favorite){
            if(getSupportLoaderManager().getLoader(CURSOR_LOADER_ID)==null){
                getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
            }else{
                getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
            }


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(movieModelArrayList!=null){
            outState.putParcelableArrayList("movieModel", movieModelArrayList);
        }

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        intent.putExtra("movieModel", movieModelArrayList.get(position));
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        this.loadingProgress.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);
        this.errorTextview.setVisibility(View.GONE);
        this.refreshButton.setVisibility(View.GONE);
        return new CursorLoader(this,
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        loadingProgress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorTextview.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        movieModelArrayList = new ArrayList<>();
        while(data.moveToNext()){
            MovieModel movieModel = new MovieModel();
//            movieModel.setVote_count(data.getString(data.getColumnIndex("vote_count")));
            movieModel.setId(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_ID)));
            movieModel.setTitle(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_NAME)));
            movieModel.setPoster_path(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_POSTER)));
            movieModel.setBackdrop_path(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_BANNER)));
            movieModel.setOverview(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_SYNOPSIS)));
            movieModel.setRelease_date(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_RELEASE_DATE)));
            movieModel.setVote_count(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_VOTING_COUNT)));
            movieModel.setVote_average(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_VOTING_AVARAGE)));
            movieModel.setFavorite(0);
            movieModelArrayList.add(movieModel);
        }
        adapter = new MovieAdapter(movieModelArrayList, MainActivity.this, MainActivity.this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
