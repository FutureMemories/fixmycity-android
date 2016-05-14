package se.futurememories.fixmycity.model;

import com.google.android.gms.maps.model.LatLng;

import se.futurememories.fixmycity.R;

/**
 * Created by Mattias on 14/05/16.
 */
public class MyMarker {

    private String type;
    private String category;
    private String comment;
    private String image;
    private LatLng position;


    public MyMarker(String type, String category, String comment, String image, LatLng position) {
        this.type = type;
        this.category = category;
        this.comment = comment;
        this.image = image;
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }

    public String getImage() {
        return image;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getImageType() {
        switch (type) {
            case "Förslag":
                return R.drawable.suggestion_marker;
            case "Fråga":
                return R.drawable.question_marker;
            case "Beröm":
                return R.drawable.praise_marker;
            case "Problem":
                return R.drawable.problem_marker;
            case "Skadat":
                return R.drawable.broken_marker;
            case "Renhållning":
                return R.drawable.dirty_marker;
            default:
                return R.drawable.suggestion_marker;
        }
    }
}