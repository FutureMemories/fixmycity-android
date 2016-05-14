package se.futurememories.fixmycity.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mattias on 14/05/16.
 */
public class MyLatLng {
    @SerializedName("long")
    private double aLong;
    @SerializedName("lat")
    private double lat;

    public double getaLong() {
        return aLong;
    }

    public double getLat() {
        return lat;
    }
}
