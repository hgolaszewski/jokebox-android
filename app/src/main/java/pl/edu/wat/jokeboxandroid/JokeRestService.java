package pl.edu.wat.jokeboxandroid;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Hubert on 27.06.2017.
 */

public interface JokeRestService {

    @GET("/joke/{category}")
    List<SimpleJokeDto> listJokeByCategory(@Path("category") String category);

    @PUT("/joke/{id}/{likeOrUnlike}")
    SimpleJokeDto markJoke(@Path("id") int id, @Path("likeOrUnlike") String likeOrUnlike);

}
