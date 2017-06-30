package pl.edu.wat.jokeboxandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.client.AdministrationRestClient;
import pl.edu.wat.jokeboxandroid.client.service.AdministrationRestService;
import pl.edu.wat.jokeboxandroid.model.OKResponseDto;
import pl.edu.wat.jokeboxandroid.viewModel.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManagementActivity extends Activity {

    public static Token token = null;
    public static AdministrationRestService administrationRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_management);
        AdminManagementActivity.token = new Token(getIntent().getExtras().get("token").toString());

        FloatingActionButton myFab = findViewById(R.id.floating);

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OKResponseDto> call = AdminManagementActivity.administrationRestService.logOut(AdminManagementActivity.token);
                call.enqueue(new Callback<OKResponseDto>() {
                    @Override
                    public void onResponse(Call<OKResponseDto> call, Response<OKResponseDto> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AdminManagementActivity.this, "Logout success!", Toast.LENGTH_SHORT).show();
                            AdminManagementActivity.token = null;
                            AdminManagementActivity.this.finish();
                        } else {
                            Toast.makeText(AdminManagementActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OKResponseDto> call, Throwable t) {
                        Toast.makeText(AdminManagementActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        AdministrationRestClient administrationRestClient = new AdministrationRestClient();
        administrationRestService = administrationRestClient.getApiService();

        LinearLayout linearLayout = findViewById(R.id.management);

        Button addCategory = new Button(AdminManagementActivity.this);
        addCategory.setText("Dodaj kategorię");
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminManagementActivity.this, AdminAddCategoryActivity.class);
                startActivity(intent);
            }
        });

        Button button = new Button(this);
        button.setText("Usuwanie kategorii i żartów");
        Button button2 = new Button(this);
        button2.setText("Zarządzaj bazą danych");
        linearLayout.addView(button);
        linearLayout.addView(button2);
        linearLayout.addView(addCategory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminManagementActivity.this, AdminPanelActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminManagementActivity.this, AdminDataBaseActivity.class);
                startActivity(intent);
            }
        });
    }
}
