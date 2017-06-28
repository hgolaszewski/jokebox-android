package pl.edu.wat.jokeboxandroid;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Hubert on 27.06.2017.
 */

public interface JokeRestService {

    @GET("/joke/{category}")
    List<SimpleJokeDto> pageJokeByCategory(@Path("category") String category);

}
