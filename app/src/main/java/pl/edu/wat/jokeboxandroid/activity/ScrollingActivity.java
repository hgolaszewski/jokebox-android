package pl.edu.wat.jokeboxandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.client.JokeRestClient;
import pl.edu.wat.jokeboxandroid.component.OnSwipeTouchListener;
import pl.edu.wat.jokeboxandroid.component.RatingEngine;
import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;
import pl.edu.wat.jokeboxandroid.client.service.JokeRestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {

    public static JokeRestService jokeRestService;
    public static LinearLayout jokesContainer;
    public static ImageView  ratingBar;
    public static TextView textView;
    public static List<SimpleJokeDto> simpleJokeDtos;
    public static int currentJokeIndex = 0;

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
                        Toast.makeText(ScrollingActivity.this, "No jokes!", Toast.LENGTH_SHORT).show();
                        ScrollingActivity.this.finish();
                        return;
                    }

                    ImageView separator1 = new ImageView(ScrollingActivity.this);
                    separator1.setImageResource(R.drawable.separator);
                    jokesContainer.addView(separator1);

                    textView = new TextView(ScrollingActivity.this);
                    textView.setText(simpleJokeDtos.get(0).getContent());
                    textView.setTextSize(18);
                    jokesContainer.addView(textView);

                    ImageView separator2 = new ImageView(ScrollingActivity.this);
                    separator2.setImageResource(R.drawable.separator);
                    jokesContainer.addView(separator2);

                    RatingEngine.setRate(simpleJokeDtos.get(0));

                    currentJokeIndex++;

                } else {
                    Toast.makeText(ScrollingActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SimpleJokeDto>> call, Throwable t) {
                Toast.makeText(ScrollingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        jokesContainer = (LinearLayout) findViewById(R.id.jokes);

        ratingBar = new ImageView(ScrollingActivity.this);
        ratingBar.setImageResource(R.drawable.stars0);
        jokesContainer.addView(ratingBar);

        jokesContainer.setOnTouchListener(new OnSwipeTouchListener(ScrollingActivity.this));
        JokeRestClient jokeRestClient = new JokeRestClient();
        jokeRestService = jokeRestClient.getApiService();
    }

}
