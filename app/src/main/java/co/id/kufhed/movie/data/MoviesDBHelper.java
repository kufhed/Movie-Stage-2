package co.id.kufhed.movie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by madinf on 8/7/17.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies_db";
    public static final int DATABASE_VERSION = 4;

    public static final String DEBUG_LOG = MoviesDBHelper.class.getSimpleName();

    public MoviesDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE "+ MoviesContract.MoviesEntry.TABLE_MOVIES+
                " ("+ MoviesContract.MoviesEntry.MOVIE_ID+" TEXT PRIMARY KEY , "+
                MoviesContract.MoviesEntry.MOVIE_NAME+" TEXT NOT NULL, "+
                MoviesContract.MoviesEntry.MOVIE_POSTER+" TEXT NOT NULL, "+
                MoviesContract.MoviesEntry.MOVIE_SYNOPSIS+" TEXT NOT NULL, "+
                MoviesContract.MoviesEntry.MOVIE_VOTING_AVARAGE+" TEXT NOT NULL, "+
                MoviesContract.MoviesEntry.MOVIE_VOTING_COUNT+" TEXT NOT NULL, "+
                MoviesContract.MoviesEntry.MOVIE_BANNER+" TEXT NOT NULL, "+
                MoviesContract.MoviesEntry.MOVIE_RELEASE_DATE+" TEXT NOT NULL, "+
                MoviesContract.MoviesEntry.MOVIE_VIDEOS+" TEXT NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(DEBUG_LOG, "Update version of sqlit: "+i+", "+i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_MOVIES);

        onCreate(sqLiteDatabase);
    }
}
