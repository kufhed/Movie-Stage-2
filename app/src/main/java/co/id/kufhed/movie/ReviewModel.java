package co.id.kufhed.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by madinf on 8/7/17.
 */

public class ReviewModel implements Parcelable {
    String author;
    String review;

    public ReviewModel(Parcel in){
        author = in.readString();
        review = in.readString();
    }

    public ReviewModel(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(review);
    }

    public static Parcelable.Creator<ReviewModel> CREATOR = new Parcelable.Creator<ReviewModel>() {

        @Override
        public ReviewModel createFromParcel(Parcel parcel) {
            return new ReviewModel(parcel);
        }

        @Override
        public ReviewModel[] newArray(int i) {
            return new ReviewModel[i];
        }



    };
}
