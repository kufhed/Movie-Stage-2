package co.id.kufhed.movie;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by madinf on 7/8/17.
 */

public class MovieApi {

    OkHttpClient client;
    MovieApiInterface movieApiInterface;

    public MovieApi(){

    }

    public void getPopularMovie(MovieApiInterface movieApiInterface){
        this.movieApiInterface = movieApiInterface;
        this.client = new OkHttpClient();
        new AsyncPopularMovie().execute();
    }

    public void getTopRatedMovie(MovieApiInterface movieApiInterface){
        this.movieApiInterface = movieApiInterface;
        this.client = new OkHttpClient();
        new AsyncTopRatedMovie().execute();

    }

    public void getTrailerMovie(MovieApiInterface movieApiInterface, String movieId){
        this.movieApiInterface = movieApiInterface;
        this.client = new OkHttpClient();
        new AsyncTraillerMovie().execute(movieId);

    }

    public void getReviewMovie(MovieApiInterface movieApiInterface, String movieId){
        this.movieApiInterface = movieApiInterface;
        this.client = new OkHttpClient();
        new AsyncReviewMovie().execute(movieId);

    }

    private class AsyncTopRatedMovie extends AsyncTask<String, Void, String>{
        private Exception exceptionToBeThrown;

        @Override
        protected String doInBackground(String... strings) {

            try{
                Request request = new Request.Builder()
                        .url(APIUrl.TOP_RATE_URL+"?language=en-US&page=1&api_key="+Constant.API_KEY)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception ex){
                exceptionToBeThrown =ex;
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!="" && exceptionToBeThrown==null){
                MovieApi.this.movieApiInterface.onHandleResult(s, null);

            }else{
                MovieApi.this.movieApiInterface.onHandleResult(null, exceptionToBeThrown);
            }

        }
    }

    private class AsyncPopularMovie extends AsyncTask<String, Void, String>{
        private Exception exceptionToBeThrown;

        @Override
        protected String doInBackground(String... strings) {

            try{
                Request request = new Request.Builder()
                        .url(APIUrl.POPULER_URL+"?language=en-US&page=1&api_key="+Constant.API_KEY)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception ex){
                exceptionToBeThrown =ex;
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!="" && exceptionToBeThrown==null){
                MovieApi.this.movieApiInterface.onHandleResult(s, null);

            }else{
                MovieApi.this.movieApiInterface.onHandleResult(null, exceptionToBeThrown);
            }

        }
    }

    private class AsyncTraillerMovie extends AsyncTask<String, Void, String>{
        private Exception exceptionToBeThrown;
        public String movieId = "";

        @Override
        protected String doInBackground(String... strings) {

            String url = APIUrl.BASE_URL+movieId+APIUrl.VIDEOS_URL+"?api_key="+Constant.API_KEY;
            try{
                Request request = new Request.Builder()
                        .url(APIUrl.BASE_URL+strings[0]+"/"+APIUrl.VIDEOS_URL+"?api_key="+Constant.API_KEY)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception ex){
                exceptionToBeThrown =ex;
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!="" && exceptionToBeThrown==null){
                MovieApi.this.movieApiInterface.onHandleResult(s, null);

            }else{
                MovieApi.this.movieApiInterface.onHandleResult(null, exceptionToBeThrown);
            }

        }
    }

    private class AsyncReviewMovie extends AsyncTask<String, Void, String>{
        private Exception exceptionToBeThrown;
        public String movieId = "";

        @Override
        protected String doInBackground(String... strings) {

//            String url = APIUrl.BASE_URL+movieId+APIUrl.VIDEOS_URL+"?api_key="+Constant.API_KEY;
            try{
                Request request = new Request.Builder()
                        .url(APIUrl.BASE_URL+strings[0]+"/"+APIUrl.REVIEW_URL+"?api_key="+Constant.API_KEY)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception ex){
                exceptionToBeThrown =ex;
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!="" && exceptionToBeThrown==null){
                MovieApi.this.movieApiInterface.onHandleResult(s, null);

            }else{
                MovieApi.this.movieApiInterface.onHandleResult(null, exceptionToBeThrown);
            }

        }
    }
}

