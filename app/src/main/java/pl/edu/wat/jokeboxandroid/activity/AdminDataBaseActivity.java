package pl.edu.wat.jokeboxandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.model.OKResponseDto;
import pl.edu.wat.jokeboxandroid.viewModel.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDataBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_base);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.categories);

        Button button = new Button(this);
        button.setText("Aktualizuj bazę żartów");
        Button button2 = new Button(this);
        button2.setText("Wyczyść bazę żartów");
        linearLayout.addView(button);
        linearLayout.addView(button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OKResponseDto> response = AdminManagementActivity.administrationRestService.fillDatabase(new Token(AdminManagementActivity.token));
                response.enqueue(new Callback<OKResponseDto>() {
                    @Override
                    public void onResponse(Call<OKResponseDto> call, Response<OKResponseDto> response) {
                        Toast.makeText(AdminDataBaseActivity.this, "Baza danych została zaktualizowana!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OKResponseDto> call, Throwable t) {
                        Toast.makeText(AdminDataBaseActivity.this, "Błąd", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OKResponseDto> response = AdminManagementActivity.administrationRestService.cleanJokes(new Token(AdminManagementActivity.token));
                response.enqueue(new Callback<OKResponseDto>() {
                    @Override
                    public void onResponse(Call<OKResponseDto> call, Response<OKResponseDto> response) {
                        Toast.makeText(AdminDataBaseActivity.this, "Baza danych została wyczyszczona!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OKResponseDto> call, Throwable t) {
                        Toast.makeText(AdminDataBaseActivity.this, "Błąd", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
