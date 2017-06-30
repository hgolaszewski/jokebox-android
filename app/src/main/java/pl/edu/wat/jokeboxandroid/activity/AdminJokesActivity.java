package pl.edu.wat.jokeboxandroid.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.client.JokeRestClient;
import pl.edu.wat.jokeboxandroid.client.service.JokeRestService;
import pl.edu.wat.jokeboxandroid.component.AdminOnSwipeListener;
import pl.edu.wat.jokeboxandroid.component.OnSwipeTouchListener;
import pl.edu.wat.jokeboxandroid.component.RatingEngine;
import pl.edu.wat.jokeboxandroid.model.Joke;
import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminJokesActivity extends AppCompatActivity {

    public static JokeRestService jokeRestService;
    public static LinearLayout jokesContainer;
    public static TextView textView;
    public static List<SimpleJokeDto> simpleJokeDtos;
    public static int currentJokeIndex = 0;
    public static TextView likesNumberText;
    public static TextView disLikesNumberText;
    public static AdminOnSwipeListener adminOnSwipeListener;
    public static Button deleteJokeButton;

    @Override
    protected void onResume() {
        super.onResume();

        currentJokeIndex = 0;

        Call<List<SimpleJokeDto>> listCall = jokeRestService.listJokeByCategory(getIntent().getExtras().getString("requestparam"));
        listCall.enqueue(new Callback<List<SimpleJokeDto>>() {

            @Override
            public void onResponse(Call<List<SimpleJokeDto>> call, Response<List<SimpleJokeDto>> response) {
                if(response.isSuccessful()){
                    simpleJokeDtos = response.body();

                    if(simpleJokeDtos.isEmpty()){
                        Toast.makeText(AdminJokesActivity.this, "No jokes!", Toast.LENGTH_SHORT).show();
                        AdminJokesActivity.this.finish();
                        return;
                    }

                    deleteJokeButton = new Button(AdminJokesActivity.this);
                    deleteJokeButton.setText("X");
                    deleteJokeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Call<Joke> jokeCall = AdminManagementActivity.administrationRestService.deleteJoke(simpleJokeDtos.get(0).getId(), AdminManagementActivity.token);
                            jokeCall.enqueue(new Callback<Joke>() {
                                @Override
                                public void onResponse(Call<Joke> call, Response<Joke> response) {
                                    if(response.isSuccessful()){
                                        simpleJokeDtos.remove(0);
                                        adminOnSwipeListener.onSwipeTop();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Joke> call, Throwable t) {

                                }
                            });
                        }
                    });

                    likesNumberText = new TextView(AdminJokesActivity.this);
                    likesNumberText.setText("Likes: " + String.valueOf(simpleJokeDtos.get(0).getLikeNumber()));
                    likesNumberText.setTextColor(Color.GREEN);
                    likesNumberText.setWidth(200);
                    likesNumberText.setTextSize(14);
                    jokesContainer.addView(likesNumberText);


                    ImageView separator1 = new ImageView(AdminJokesActivity.this);
                    separator1.setImageResource(R.drawable.separator);
                    jokesContainer.addView(separator1);

                    textView = new TextView(AdminJokesActivity.this);
                    textView.setText(simpleJokeDtos.get(0).getContent());
                    textView.setTextSize(18);
                    jokesContainer.addView(textView);

                    ImageView separator2 = new ImageView(AdminJokesActivity.this);
                    separator2.setImageResource(R.drawable.separator);
                    jokesContainer.addView(separator2);

                    disLikesNumberText = new TextView(AdminJokesActivity.this);
                    disLikesNumberText.setText("Dislikes: " + String.valueOf(simpleJokeDtos.get(0).getUnlikeNumber()));
                    disLikesNumberText.setTextColor(Color.RED);
                    disLikesNumberText.setWidth(200);
                    disLikesNumberText.setTextSize(14);
                    jokesContainer.addView(disLikesNumberText);

                    jokesContainer.addView(deleteJokeButton);
                    currentJokeIndex++;

                } else {
                    Toast.makeText(AdminJokesActivity.this, "An erro occured!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SimpleJokeDto>> call, Throwable t) {
                Toast.makeText(AdminJokesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_jokes);
        jokesContainer = (LinearLayout) findViewById(R.id.jokeadmin);

        adminOnSwipeListener = new AdminOnSwipeListener(AdminJokesActivity.this);
        jokesContainer.setOnTouchListener(adminOnSwipeListener);

        JokeRestClient jokeRestClient = new JokeRestClient();
        jokeRestService = jokeRestClient.getApiService();
    }

}
