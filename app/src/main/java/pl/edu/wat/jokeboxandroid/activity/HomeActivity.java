package pl.edu.wat.jokeboxandroid.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import pl.edu.wat.jokeboxandroid.R;

public class HomeActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(AdminManagementActivity.token != null){
            try{
                AdminManagementActivity.administrationRestService.logOut(AdminManagementActivity.token).execute();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_home);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(HomeActivity.this, MainActivity.class);
                HomeActivity.this.startActivity(mainIntent);
                HomeActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
