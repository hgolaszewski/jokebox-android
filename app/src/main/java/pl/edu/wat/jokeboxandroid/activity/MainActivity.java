package pl.edu.wat.jokeboxandroid.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import pl.edu.wat.jokeboxandroid.client.AdministrationRestClient;
import pl.edu.wat.jokeboxandroid.client.CategoryRestClient;
import pl.edu.wat.jokeboxandroid.client.service.AdministrationRestService;
import pl.edu.wat.jokeboxandroid.client.service.CategoryRestService;
import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDto;
import pl.edu.wat.jokeboxandroid.viewModel.LoginPasswordVM;
import pl.edu.wat.jokeboxandroid.viewModel.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    static LinearLayout buttonContainer;
    static CategoryRestService categoryRestService;
    static AdministrationRestService administrationRestService;
    static Intent adminpanel;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonContainer.removeAllViews();
        getCategories();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdministrationRestClient administrationRestClient = new AdministrationRestClient();
        administrationRestService = administrationRestClient.getApiService();

        FloatingActionButton myFab = findViewById(R.id.floating);

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(AdminManagementActivity.token != null){
                    MainActivity.this.startActivity(MainActivity.this.adminpanel);
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("PASSWORD");
                    alertDialog.setMessage("Enter password:");

                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                                        final Token token = response.body();
                                        Toast.makeText(MainActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                                        MainActivity.this.adminpanel = new Intent(MainActivity.this, AdminManagementActivity.class);
                                        MainActivity.this.adminpanel.putExtra("token", token.getValue());
                                        MainActivity.this.startActivity(MainActivity.this.adminpanel);
                                    } else {
                                        if(response.code() == 401){
                                            Toast.makeText(MainActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.cancel();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Token> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
            }

        });

        buttonContainer = findViewById(R.id.categories);

        CategoryRestClient categoryRestClient = new CategoryRestClient();
        categoryRestService = categoryRestClient.getApiService();

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

    private void getCategories(){
        Call<List<SimpleCategoryDto>> listCall = categoryRestService.listAllCategory();
        listCall.enqueue(new Callback<List<SimpleCategoryDto>>() {

            @Override
            public void onResponse(Call<List<SimpleCategoryDto>> call, Response<List<SimpleCategoryDto>> response) {
                List<SimpleCategoryDto> simpleCategoryDtos = null;
                if(response.isSuccessful()){
                    simpleCategoryDtos = response.body();

                    if(simpleCategoryDtos.isEmpty()){
                        Toast.makeText(MainActivity.this, "No categories!", Toast.LENGTH_SHORT).show();
                    }

                    for(final SimpleCategoryDto simpleCategoryDto: simpleCategoryDtos) {

                        Button button = new Button(MainActivity.this);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                                intent.putExtra("requestparam", simpleCategoryDto.getRequestparam());
                                startActivity(intent);
                            }});

                        button.setText(simpleCategoryDto.getName());
                        buttonContainer.addView(button);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SimpleCategoryDto>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
