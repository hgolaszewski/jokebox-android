package pl.edu.wat.jokeboxandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Hubert on 27.06.2017.
 */

public class CategoryRestClient {

    private String baseUrl = "http://192.168.0.80:8080";
    private CategoryRestService categoryRestService;

    public CategoryRestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        init();
    }

    public CategoryRestClient() {
        init();
    }

    public CategoryRestService getApiService() {
        return categoryRestService;
    }

    private void init(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();
        RestAdapter rstAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(this.baseUrl)
                .setConverter(new GsonConverter(gson))
                .build();
        categoryRestService = rstAdapter.create(CategoryRestService.class);
    }

}
