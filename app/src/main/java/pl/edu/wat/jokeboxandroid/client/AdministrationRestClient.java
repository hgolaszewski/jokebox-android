package pl.edu.wat.jokeboxandroid.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.edu.wat.jokeboxandroid.client.service.AdministrationRestService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hubert on 28.06.2017.
 */

public class AdministrationRestClient {

    private String baseUrl = "http://192.168.43.61:8080";
    private AdministrationRestService administrationRestService;

    public AdministrationRestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        init();
    }

    public AdministrationRestClient() {
        init();
    }

    public AdministrationRestService getApiService() {
        return administrationRestService;
    }

    private void init(){
        Gson gson = new GsonBuilder()
                //.setLenient()
                .setDateFormat("yyyy'-'MM'-'dd")
                .create();
        Retrofit rstAdapter = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        administrationRestService = rstAdapter.create(AdministrationRestService.class);
    }
}
