package se.futurememories.fixmycity.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Mattias on 13/01/16.
 *
 */
public class ApiManager {

    private static FixMyCityService api;
    private static Retrofit retrofit;
    private ApiManager() {}

    public static Retrofit getRestManager() {
        return retrofit;
    }

    public static FixMyCityService getRestInstance() {
        if (api == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl("http://192.81.222.241/")
                    .build();
            api = retrofit.create(FixMyCityService.class);

        }
        return api;
    }
}