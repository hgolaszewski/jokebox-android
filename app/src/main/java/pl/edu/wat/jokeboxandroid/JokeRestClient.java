package pl.edu.wat.jokeboxandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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
        RestAdapter rstAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(this.baseUrl)
                .setConverter(new GsonConverter(gson))
                .build();
        jokeRestService = rstAdapter.create(JokeRestService.class);
    }

}
