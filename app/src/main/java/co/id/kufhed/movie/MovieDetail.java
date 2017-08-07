package co.id.kufhed.movie;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import co.id.kufhed.movie.data.MoviesContract;

public class MovieDetail extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>, TrailerAdapter.RecycleViewItemClick {

    protected ImageView imageBanner;
    protected Toolbar toolbar;
    protected CollapsingToolbarLayout collapsingToolbar;
    protected AppBarLayout appBarLayout;
    protected ImageView imagePoster;
    protected TextView durationTextView;
    protected TextView releaseDateTextView;
    protected TextView ratingTextView;
    protected TextView synopsisTextView;
    protected TextView titleMovie;
    protected LinearLayout favoriteButton;
    protected ProgressBar progress;
    protected RecyclerView recyler;
    protected TextView textError;
    protected ImageView refreshButton;
    protected ImageView favoriteIcon;
    protected FloatingActionButton fab;
    private MovieModel movieModel;
    private ArrayList<TraillerModel> traillerModels;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private int CURSOR_LOADER_ID = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movieInstance")) {
            movieModel = getIntent().getExtras().getParcelable("movieModel");

        } else {
            movieModel = savedInstanceState.getParcelable("movieInstance");

        }
        super.setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movieModel.getTitle());
        setTitle(movieModel.getTitle());
        initView();
        if (savedInstanceState == null || !savedInstanceState.containsKey("movieInstance")) {
            Cursor c = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                    new String[]{MoviesContract.MoviesEntry.MOVIE_ID},
                    null,
                    null,
                    null);
            getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        }else if(savedInstanceState == null || !savedInstanceState.containsKey("trailerList")){
            traillerModels = new ArrayList<>();

        }else{
            traillerModels = savedInstanceState.getParcelableArrayList("trailerList");
            adapter = new TrailerAdapter(traillerModels, MovieDetail.this, this);
            recyler.setAdapter(adapter);
        }
        if (movieModel.isFavorite() == 1) {
            this.favoriteIcon.setBackgroundColor(getResources().getColor(R.color.favoriteSelect));
        }
        releaseDateTextView.setText(movieModel.getRelease_date());
        durationTextView.setText(movieModel.getVote_count());
        ratingTextView.setText(movieModel.getVote_average());
        synopsisTextView.setText(movieModel.getOverview());
        titleMovie.setText(movieModel.getTitle());
        Picasso.with(this).load(APIUrl.BASE_URL_IMAGE + movieModel.getBackdrop_path()).into(imageBanner);
        Picasso.with(this).load(APIUrl.BASE_URL_IMAGE + movieModel.getPoster_path()).into(imagePoster);
        getDataVideos(movieModel.getId());


    }

    private void initView() {
        imageBanner = (ImageView) findViewById(R.id.imageBanner);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        imagePoster = (ImageView) findViewById(R.id.imagePoster);
        durationTextView = (TextView) findViewById(R.id.durationTextView);
        releaseDateTextView = (TextView) findViewById(R.id.releaseDateTextView);
        ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        synopsisTextView = (TextView) findViewById(R.id.synopsisTextView);
        titleMovie = (TextView) findViewById(R.id.titleMovie);
        favoriteButton = (LinearLayout) findViewById(R.id.favoriteButton);
        favoriteButton.setOnClickListener(this);
        progress = (ProgressBar) findViewById(R.id.progress);
        recyler = (RecyclerView) findViewById(R.id.recyler);
        layoutManager = new LinearLayoutManager(this);
        recyler.setLayoutManager(layoutManager);
        textError = (TextView) findViewById(R.id.textError);
        refreshButton = (ImageView) findViewById(R.id.refreshButton);
        traillerModels = new ArrayList<>();
        favoriteIcon = (ImageView) findViewById(R.id.favoriteIcon);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    private void getDataVideos(String movieId) {
        progress.setVisibility(View.VISIBLE);
        recyler.setVisibility(View.GONE);
        textError.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        MovieApi movieApi = new MovieApi();
        movieApi.getTrailerMovie(new MovieApiInterface() {
            @Override
            public void onHandleResult(String result, Exception ex) {
                progress.setVisibility(View.GONE);
                recyler.setVisibility(View.VISIBLE);
                if (ex != null) {
                    textError.setVisibility(View.VISIBLE);
                    refreshButton.setVisibility(View.VISIBLE);
                    textError.setText("Opps! something bad, please try again");
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectTrailer = jsonArray.getJSONObject(i);
                                TraillerModel traillerModel = new TraillerModel();
                                traillerModel.setKey(objectTrailer.getString("key"));
                                traillerModel.setName(objectTrailer.getString("name"));
                                traillerModels.add(traillerModel);
                            }
                            adapter = new TrailerAdapter(traillerModels, MovieDetail.this,MovieDetail.this);
                            recyler.setVisibility(View.VISIBLE);
                            recyler.setAdapter(adapter);

                        } else {
                            textError.setText("No Data Found");
                            textError.setVisibility(View.VISIBLE);
                            refreshButton.setVisibility(View.VISIBLE);

                        }
                    } catch (Exception e) {
                        textError.setVisibility(View.VISIBLE);
                        refreshButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, movieId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movieInstance", movieModel);
        outState.putParcelableArrayList("trailerList", traillerModels);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        } else if (item.getItemId() == R.id.action_review) {
            Intent intent = new Intent(MovieDetail.this, ReviewActivity.class);
            intent.putExtra("movieId", movieModel.getId());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.favoriteButton) {
            if (movieModel.isFavorite() == 1) {
                int result = getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI, MoviesContract.MoviesEntry.MOVIE_ID + "=?", new String[]{movieModel.getId()});
                if (result > 0) {
                    favoriteIcon.setColorFilter(R.color.white);
                }
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_NAME, movieModel.getTitle());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_POSTER, movieModel.getPoster_path());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_SYNOPSIS, movieModel.getOverview());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_VOTING_AVARAGE, movieModel.getVote_average());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_VOTING_COUNT, movieModel.getVote_count());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_BANNER, movieModel.getBackdrop_path());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_RELEASE_DATE, movieModel.getRelease_date());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_VIDEOS, movieModel.getVideo());
                contentValues.put(MoviesContract.MoviesEntry.MOVIE_ID, movieModel.getId());
                getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);
                favoriteIcon.setColorFilter(R.color.favoriteSelect);

            }

        }else if(view.getId() == R.id.fab){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I watching "+movieModel.getTitle());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        outerbreak:
        while (data.moveToNext()) {
            Log.d("MoviewModel", data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_ID)));
            if (data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_ID)).equalsIgnoreCase(movieModel.getId())) {
                favoriteIcon.setColorFilter(R.color.favoriteSelect);
                movieModel.setFavorite(1);
                break outerbreak;
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }


    @Override
    public void onItemClick(int position) {
        Intent applicationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + traillerModels.get(position).getKey()));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + traillerModels.get(position).getKey()));
        try {
            startActivity(applicationIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(browserIntent);
        }
    }
}
