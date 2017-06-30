package pl.edu.wat.jokeboxandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.model.OKResponseDto;
import pl.edu.wat.jokeboxandroid.viewModel.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDataBaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_base);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.database);

        Button button = new Button(this);
        button.setText("Aktualizuj bazę żartów");
        Button button2 = new Button(this);
        button2.setText("Wyczyść bazę żartów");
        linearLayout.addView(button);
        linearLayout.addView(button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OKResponseDto> response = AdminManagementActivity.administrationRestService.fillDatabase(AdminManagementActivity.token);
                response.enqueue(new Callback<OKResponseDto>() {
                    @Override
                    public void onResponse(Call<OKResponseDto> call, Response<OKResponseDto> response) {
                        Toast.makeText(AdminDataBaseActivity.this, "Database updated!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OKResponseDto> call, Throwable t) {
                    }
                });

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OKResponseDto> response = AdminManagementActivity.administrationRestService.cleanJokes(AdminManagementActivity.token);
                response.enqueue(new Callback<OKResponseDto>() {
                    @Override
                    public void onResponse(Call<OKResponseDto> call, Response<OKResponseDto> response) {
                        Toast.makeText(AdminDataBaseActivity.this, "Database cleared!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OKResponseDto> call, Throwable t) {
                        Toast.makeText(AdminDataBaseActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
