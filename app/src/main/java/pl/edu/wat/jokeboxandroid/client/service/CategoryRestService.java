package pl.edu.wat.jokeboxandroid.client.service;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hubert on 28.06.2017.
 */
public interface CategoryRestService {

    @GET("/category")
    Call<List<SimpleCategoryDto>> listAllCategory();

}
