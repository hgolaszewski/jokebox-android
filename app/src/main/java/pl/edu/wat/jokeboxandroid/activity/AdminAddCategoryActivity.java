package pl.edu.wat.jokeboxandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import pl.edu.wat.jokeboxandroid.R;
import pl.edu.wat.jokeboxandroid.model.Category;
import pl.edu.wat.jokeboxandroid.model.SimpleCategoryDtoInput;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);

        Button button = (Button) findViewById(R.id.addcatbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.catname);
                EditText param = (EditText) findViewById(R.id.param);
                EditText address = (EditText) findViewById(R.id.address);
                if(name.length() > 0 && param.length() > 0 && address.length() > 0){
                    SimpleCategoryDtoInput simpleCategoryDtoInput = new SimpleCategoryDtoInput(name.getText().toString(), param.getText().toString(), address.getText().toString(), AdminManagementActivity.token);
                    if(address.getText().toString().contains("dowcipy.pl")){
                        Call<Category> categoryCall = AdminManagementActivity.administrationRestService.addCategory(simpleCategoryDtoInput);
                        categoryCall.enqueue(new Callback<Category>() {
                            @Override
                            public void onResponse(Call<Category> call, Response<Category> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(AdminAddCategoryActivity.this, "Category successfully added!", Toast.LENGTH_SHORT).show();
                                } else if (response.code() == 400){
                                    Toast.makeText(AdminAddCategoryActivity.this, "Category already exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AdminAddCategoryActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Category> call, Throwable t) {
                                Toast.makeText(AdminAddCategoryActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(AdminAddCategoryActivity.this, "Wrong website. Must be dowcipy.pl!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminAddCategoryActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
