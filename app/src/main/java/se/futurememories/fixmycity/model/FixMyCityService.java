package se.futurememories.fixmycity.model;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Mattias on 28/12/15.
 * Api interface for Jula
 */
public interface FixMyCityService {

    // Login
    @GET("issues")
    Observable<Response<List<MyMarker>>> getIssues();

    @Multipart
    @Headers("Content-Type: multipart/form-data; boundary=iSBcxYW-SeE_dubqNJw3p6PP59WbDOAj")
    @POST("issues")
    Observable<Response<Integer>> postPhoto(@Part("image") RequestBody image, @Part("type") String problem, @Part("category") String category, @Part("long") double along, @Part("lat") double lat);

}
