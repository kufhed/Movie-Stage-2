package co.id.kufhed.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by madinf on 8/7/17.
 */

public class TraillerModel implements Parcelable{
    String name;
    String key;

    public TraillerModel(Parcel in){
        name = in.readString();
        key = in.readString();
    }

    public TraillerModel(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
    }

    public static Parcelable.Creator<TraillerModel> CREATOR = new Parcelable.Creator<TraillerModel>() {

        @Override
        public TraillerModel createFromParcel(Parcel parcel) {
            return new TraillerModel(parcel);
        }

        @Override
        public TraillerModel[] newArray(int i) {
            return new TraillerModel[i];
        }



    };
}

