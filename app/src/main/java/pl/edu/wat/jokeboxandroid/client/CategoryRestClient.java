package pl.edu.wat.jokeboxandroid.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.edu.wat.jokeboxandroid.client.service.CategoryRestService;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Hubert on 27.06.2017.
 */

public class CategoryRestClient {

    private String baseUrl = "http://192.168.43.61:8080";
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
        Retrofit rstAdapter = new Retrofit .Builder()
                .baseUrl(this.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        categoryRestService = rstAdapter.create(CategoryRestService.class);
    }

}
