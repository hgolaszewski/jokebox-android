package pl.edu.wat.jokeboxandroid.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.client.AdministrationRestClient;
import pl.edu.wat.jokeboxandroid.client.CategoryRestClient;
import pl.edu.wat.jokeboxandroid.client.JokeRestClient;
import pl.edu.wat.jokeboxandroid.client.service.AdministrationRestService;
import pl.edu.wat.jokeboxandroid.client.service.CategoryRestService;
import pl.edu.wat.jokeboxandroid.client.service.JokeRestService;
import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;
import pl.edu.wat.jokeboxandroid.viewModel.LoginPasswordVM;
import pl.edu.wat.jokeboxandroid.viewModel.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPanelActivity extends Activity {



    static LinearLayout buttonContainer;
    static CategoryRestService categoryRestService;
    static AdministrationRestService administrationRestService;
    static JokeRestService jokeRestService;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);



        AdministrationRestClient administrationRestClient = new AdministrationRestClient();
        administrationRestService = administrationRestClient.getApiService();

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floating);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPanelActivity.this);
                alertDialog.setTitle("PASSWORD");
                alertDialog.setMessage("Enter Password");

                final EditText input = new EditText(AdminPanelActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alertDialog.setView(input);
                alertDialog.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        LoginPasswordVM loginPasswordVM = new LoginPasswordVM();
                        loginPasswordVM.setLogin("admin");
                        loginPasswordVM.setPassword(input.getText().toString());
                        Call<Token> listCall = administrationRestService.authenticate(loginPasswordVM);
                        listCall.enqueue(new Callback<Token>() {
                            @Override
                            public void onResponse(Call<Token> call, Response<Token> response) {
                                if(response.isSuccessful()){
                                    Token token = response.body();
                                    Toast.makeText(AdminPanelActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                                    Intent adminPanel = new Intent(AdminPanelActivity.this, AdminPanelActivity.class);
                                    adminPanel.putExtra("token", token.getValue());
                                    AdminPanelActivity.this.startActivity(adminPanel);

                                } else {
                                    if(response.code() == 401){
                                        Toast.makeText(AdminPanelActivity.this, "Błędne dane logowania!", Toast.LENGTH_SHORT).show();
                                    } else if(response.code() == 403){
                                        Toast.makeText(AdminPanelActivity.this, "Administrator obecnie zalogowany!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AdminPanelActivity.this, "Wystąpił błąd!", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<Token> call, Throwable t) {
                                Toast.makeText(AdminPanelActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                    }
                });

                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }

        });

        buttonContainer = (LinearLayout) findViewById(R.id.categories);

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

                    for(final SimpleCategoryDto simpleCategoryDto: simpleCategoryDtos) {

                        LinearLayout linearLayout = new LinearLayout(AdminPanelActivity.this);
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                        Button button = new Button(AdminPanelActivity.this);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminPanelActivity.this, ScrollingActivity.class);
                                intent.putExtra("requestparam", simpleCategoryDto.getRequestparam());
                                startActivity(intent);
                            }});

                        button.setText(simpleCategoryDto.getName());

                        Button button2 = new Button(AdminPanelActivity.this);
                        button2.setText("X");

                        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));


                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

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
                    Toast.makeText(AdminPanelActivity.this, "Http error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SimpleCategoryDto>> call, Throwable t) {
                Toast.makeText(AdminPanelActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

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
}
