package pl.edu.wat.jokeboxandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;
import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;

public class ScrollingActivity extends AppCompatActivity {

    static JokeRestService jokeRestService;
    static LinearLayout jokesContainer;
    static ImageView  ratingBar;
    static TextView textView;
    static List<SimpleJokeDto> simpleJokeDtos;
    static int currentJokeIndex = 0;
    static JokeAsyncTask jokeAsyncTask;

    @Override
    protected void onResume() {
        super.onResume();
        currentJokeIndex = 0;
        jokeAsyncTask.execute(getIntent().getExtras().getString("requestparam"));
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
        jokeAsyncTask = new JokeAsyncTask();
    }

    private class JokeAsyncTask extends AsyncTask<String, Integer, List<SimpleJokeDto>> {

        @Override
        protected List<SimpleJokeDto> doInBackground(String... params) {
            try {
                String requestParam = params[0];
                simpleJokeDtos = jokeRestService.listJokeByCategory(requestParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return simpleJokeDtos;
        }

        @Override
        protected void onPostExecute(List<SimpleJokeDto> simpleJokeDtos) {
            super.onPostExecute(simpleJokeDtos);

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

        }

    }

}
