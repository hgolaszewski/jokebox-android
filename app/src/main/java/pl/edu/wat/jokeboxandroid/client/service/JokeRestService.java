package pl.edu.wat.jokeboxandroid.client.service;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Created by Hubert on 27.06.2017.
 */

public interface JokeRestService {

    @GET("/joke/{category}")
    Call<List<SimpleJokeDto>> listJokeByCategory(@Path("category") String category);

    @PUT("/joke/{id}/{likeOrUnlike}")
    Call<SimpleJokeDto> markJoke(@Path("id") int id, @Path("likeOrUnlike") String likeOrUnlike);

}
