package pl.edu.wat.jokeboxandroid.client.service;

import pl.edu.wat.jokeboxandroid.model.Category;
import pl.edu.wat.jokeboxandroid.model.Joke;
import pl.edu.wat.jokeboxandroid.model.OKResponseDto;
import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDtoInput;
import pl.edu.wat.jokeboxandroid.model.SimpleJokeDtoInput;
import pl.edu.wat.jokeboxandroid.viewModel.LoginPasswordVM;
import pl.edu.wat.jokeboxandroid.viewModel.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Created by Hubert on 28.06.2017.
 */

public interface AdministrationRestService {

    @POST("/admin/authenticate")
    Call<Token> authenticate(@Body LoginPasswordVM loginPasswordVM);

    @POST("/admin/logout")
    Call<OKResponseDto> logOut(@Body Token token);

    @POST("/admin/category")
    Call<Category> addCategory(@Body SimpleCategoryDtoInput simpleCategoryDtoInput);

    @PATCH("/admin/category/{id}")
    Call<Category> deleteCategory(@Path("id") int id, @Body Token token);

    @POST("/admin/joke")
    Call<Joke> addJoke(@Body SimpleJokeDtoInput simpleJokeDtoInput);

    @PATCH("/admin/joke/{id}")
    Call<Joke> deleteJoke(@Path("id") int id, @Body Token token);

    @PUT("/admin/resetDataBase")
    Call<OKResponseDto> fillDatabase(@Body Token token);

    @PATCH("/admin/cleanJokes")
    Call<OKResponseDto> cleanJokes(@Body Token token);

}
