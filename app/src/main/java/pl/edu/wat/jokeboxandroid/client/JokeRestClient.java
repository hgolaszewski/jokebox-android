package pl.edu.wat.jokeboxandroid.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.edu.wat.jokeboxandroid.client.service.JokeRestService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hubert on 27.06.2017.
 */

public class JokeRestClient {

    private JokeRestService jokeRestService;
    private String baseUrl = "http://192.168.0.80:8080";

    public JokeRestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        init();
    }

    public JokeRestClient(){
        init();
    }

    public JokeRestService getApiService() {
        return jokeRestService;
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
        jokeRestService = rstAdapter.create(JokeRestService.class);
    }

}
