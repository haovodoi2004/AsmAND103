package fpoly.pro1121.asmand103;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.pro1121.asmand103.Adapter.RecAdapter;
import fpoly.pro1121.asmand103.Model.Product;
import fpoly.pro1121.asmand103.Service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rec_view;
    RecAdapter recAdapter;
    ArrayList<Product> list;
    Button Add;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Add = findViewById(R.id.btn_add);
        Add.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, AddProductActivity.class));
        });
        rec_view = findViewById(R.id.rec_view);
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ArrayList<Product>> call = apiService.getAllProduct();
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()){
                    list = response.body();
                    recAdapter = new RecAdapter(list,MainActivity.this);
                    rec_view.setAdapter(recAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable throwable) {
                Log.d("MAIN ACRT", "onFailure: " +throwable.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ArrayList<Product>> call = apiService.getAllProduct();
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()){
                    list = response.body();
                    recAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable throwable) {
                Log.d("MAIN ACRT", "onFailure: " +throwable.getMessage());
            }
        });
    }
}