package com.zaher.news247.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Space;

/**
 * Created by New on 2/26/2018.
 */

public class Source implements Parcelable {

    String id;
    String name;

    public Source(){
        super();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);

    }

    private Source(Parcel parcel){
        id = parcel.readString();
        name = parcel.readString();
    }
    public static final Parcelable.Creator<Source> CREATOR
            = new Parcelable.Creator<Source>(){
      public Source createFromParcel(Parcel parcel){
          return new Source(parcel);
      }
      public Source[] newArray(int size){
          return new Source[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
