package pl.edu.wat.jokeboxandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;

public class MainActivity extends AppCompatActivity {

    LinearLayout buttonContainer;
    static CategoryRestService categoryRestService;
    static CategoryAsyncTask categoryAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonContainer = (LinearLayout) findViewById(R.id.categories);

        CategoryRestClient categoryRestClient = new CategoryRestClient();
        categoryRestService = categoryRestClient.getApiService();
        categoryAsyncTask = new CategoryAsyncTask();
        categoryAsyncTask.execute(categoryRestService);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) { return true; }
        return super.onOptionsItemSelected(item);
    }


    private class CategoryAsyncTask extends AsyncTask<CategoryRestService, Integer, List<SimpleCategoryDto>> {

        @Override
        protected List<SimpleCategoryDto> doInBackground(CategoryRestService... params) {
            List<SimpleCategoryDto> simpleCategoryDtos = null;
            try {
                CategoryRestService categoryRestService = params[0];
                simpleCategoryDtos = categoryRestService.listAllCategory();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return simpleCategoryDtos;
        }

        @Override
        protected void onPostExecute(List<SimpleCategoryDto> simpleCategoryDtos) {
            super.onPostExecute(simpleCategoryDtos);

            for(final SimpleCategoryDto simpleCategoryDto: simpleCategoryDtos){

                Button button = new Button(MainActivity.this);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                        intent.putExtra("requestparam", simpleCategoryDto.getRequestparam());
                        startActivity(intent);
                    }
                });
                button.setText(simpleCategoryDto.getName());
                buttonContainer.addView(button);
            }

        }

    }

}
