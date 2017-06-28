package pl.edu.wat.jokeboxandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;
import pl.edu.wat.jokeboxandroid.model.SimpleJokeDto;

public class ScrollingActivity extends AppCompatActivity {

    JokeRestService jokeRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);



        JokeRestClient jokeRestClient = new JokeRestClient("http://192.168.0.80:8080");
        jokeRestService = jokeRestClient.getApiService();
        JokeAsyncTask categoryAsyncTask = new JokeAsyncTask();
        categoryAsyncTask.execute(getIntent().getExtras().getString("requestparam"));

    }

    private class JokeAsyncTask extends AsyncTask<String, Integer, List<SimpleJokeDto>> {

        @Override
        protected List<SimpleJokeDto> doInBackground(String... params) {
            String requestParam = params[0];
            List<SimpleJokeDto> simpleJokeDtos = null;
            try {
                simpleJokeDtos = jokeRestService.pageJokeByCategory(requestParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return simpleJokeDtos;
        }

        @Override
        protected void onPostExecute(List<SimpleJokeDto> simpleJokeDtos) {
            super.onPostExecute(simpleJokeDtos);


            for(final SimpleJokeDto simpleJokeDto: simpleJokeDtos){
                LinearLayout jokesContainer = (LinearLayout) findViewById(R.id.jokes);

                TextView textView = new TextView(ScrollingActivity.this);
                textView.setText(simpleJokeDto.getContent());
                jokesContainer.addView(textView);

                ImageView img = new ImageView(ScrollingActivity.this);
                img.setImageResource(R.drawable.separator);

                jokesContainer.addView(img);
            }

        }
    }
}
