package co.id.kufhed.movie.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by madinf on 8/7/17.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "co.id.kufhed.movie.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final class MoviesEntry implements BaseColumns{
        public static final String TABLE_MOVIES = "movies";
        public static final String _ID = "_id";
        public static final String MOVIE_NAME= "movie_name";
        public static final String MOVIE_POSTER = "movie_poster";
        public static final String MOVIE_SYNOPSIS = "movie_synopsis";
        public static final String MOVIE_RELEASE_DATE ="release_date";
        public static final String MOVIE_VIDEOS = "videos";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_BANNER = "movie_banner";
        public static final String MOVIE_VOTING_COUNT= "movie_voting_count";
        public static final String MOVIE_VOTING_AVARAGE= "movie_voting_average";

        public static final Uri CONTENT_URI= BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIES).build();
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        public static Uri buildFlavorsUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
