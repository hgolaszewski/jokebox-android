package pl.edu.wat.jokeboxandroid;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;
import retrofit.http.GET;

/**
 * Created by Hubert on 27.06.2017.
 */

public interface CategoryRestService {

    @GET("/category")
    List<SimpleCategoryDto> listAllCategory();

}
