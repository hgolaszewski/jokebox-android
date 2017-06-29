package pl.edu.wat.jokeboxandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.client.AdministrationRestClient;
import pl.edu.wat.jokeboxandroid.client.service.AdministrationRestService;

public class AdminManagementActivity extends AppCompatActivity {

    static String token;
    static AdministrationRestService administrationRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_management);
        AdminManagementActivity.token = getIntent().getExtras().get("token").toString();

        AdministrationRestClient administrationRestClient = new AdministrationRestClient();
        administrationRestService = administrationRestClient.getApiService();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.categories);

        Button button = new Button(this);
        button.setText("Zarządzaj kategoriami i żartmi");
        Button button2 = new Button(this);
        button2.setText("Zarządzaj bazą danych");
        linearLayout.addView(button);
        linearLayout.addView(button2);

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
