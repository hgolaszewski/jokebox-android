package pl.edu.wat.jokeboxandroid.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.client.AdministrationRestClient;
import pl.edu.wat.jokeboxandroid.client.CategoryRestClient;
import pl.edu.wat.jokeboxandroid.client.JokeRestClient;
import pl.edu.wat.jokeboxandroid.client.service.AdministrationRestService;
import pl.edu.wat.jokeboxandroid.client.service.CategoryRestService;
import pl.edu.wat.jokeboxandroid.client.service.JokeRestService;
import pl.edu.wat.jokeboxandroid.model.Category;
import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPanelActivity extends Activity {

    static LinearLayout buttonContainer;
    static CategoryRestService categoryRestService;
    static AdministrationRestService administrationRestService;
    static JokeRestService jokeRestService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        AdministrationRestClient administrationRestClient = new AdministrationRestClient();
        administrationRestService = administrationRestClient.getApiService();

        buttonContainer =  findViewById(R.id.categoriesadmin);

        CategoryRestClient categoryRestClient = new CategoryRestClient();
        categoryRestService = categoryRestClient.getApiService();

        JokeRestClient jokeRestClient = new JokeRestClient();
        jokeRestService = jokeRestClient.getApiService();

        Call<List<SimpleCategoryDto>> listCall = categoryRestService.listAllCategory();
        listCall.enqueue(new Callback<List<SimpleCategoryDto>>() {

            @Override
            public void onResponse(Call<List<SimpleCategoryDto>> call, Response<List<SimpleCategoryDto>> response) {
                List<SimpleCategoryDto> simpleCategoryDtos = null;
                if(response.isSuccessful()){
                    simpleCategoryDtos = response.body();

                    if(simpleCategoryDtos.isEmpty()){
                        Toast.makeText(AdminPanelActivity.this, "No categories!", Toast.LENGTH_SHORT).show();
                        AdminPanelActivity.this.finish();
                        return;
                    }

                    for(final SimpleCategoryDto simpleCategoryDto: simpleCategoryDtos) {

                        LinearLayout linearLayout = new LinearLayout(AdminPanelActivity.this);
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                        final Button button = new Button(AdminPanelActivity.this);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminPanelActivity.this, AdminJokesActivity.class);
                                intent.putExtra("requestparam", simpleCategoryDto.getRequestparam());
                                startActivity(intent);
                            }});

                        button.setText(simpleCategoryDto.getName());

                        final Button button2 = new Button(AdminPanelActivity.this);
                        button2.setText("X");

                        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));


                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Call<Category> callList = AdminManagementActivity.administrationRestService.deleteCategory(simpleCategoryDto.getId(), AdminManagementActivity.token);
                                callList.enqueue(new Callback<Category>() {
                                    @Override
                                    public void onResponse(Call<Category> call, Response<Category> response) {
                                        if(response.isSuccessful()){
                                            ViewGroup layout = (ViewGroup) button.getParent();
                                            layout.removeView(button);
                                            layout.removeView(button2);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Category> call, Throwable t) {

                                    }
                                });
                            }
                        });

                        button2.setMinWidth(50);
                        button.setMinWidth(300);
                        linearLayout.setGravity(Gravity.CENTER);
                        linearLayout.addView(button);
                        linearLayout.addView(button2);
                        buttonContainer.addView(linearLayout);
                    }
                } else {
                    Toast.makeText(AdminPanelActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SimpleCategoryDto>> call, Throwable t) {
                Toast.makeText(AdminPanelActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

}
