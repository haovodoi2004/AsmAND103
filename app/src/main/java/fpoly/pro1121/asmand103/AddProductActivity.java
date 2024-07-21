package fpoly.pro1121.asmand103;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fpoly.pro1121.asmand103.Model.Product;
import fpoly.pro1121.asmand103.Service.ApiService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProductActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ImageView img_addProd;
    private TextInputLayout ed_addProd_name, ed_addProd_des, ed_addProd_price;
    private Button btn_addProd, btn_cancel;
    private Uri imageUri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        img_addProd = findViewById(R.id.img_addProd);
        ed_addProd_name = findViewById(R.id.ed_addProd_name);
        ed_addProd_des = findViewById(R.id.ed_addProd_des);
        ed_addProd_price = findViewById(R.id.ed_addProd_price);
        btn_addProd = findViewById(R.id.btn_addProd);
        btn_cancel = findViewById(R.id.btn_cancel);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        img_addProd.setOnClickListener(v -> {
            openFileChooser();
        });
        btn_cancel.setOnClickListener(v -> {
            startActivity(new Intent(AddProductActivity.this, MainActivity.class));
        });
        btn_addProd.setOnClickListener(v->{
            String name = ed_addProd_name.getEditText().getText().toString();
            String des = ed_addProd_des.getEditText().getText().toString();
            String price = ed_addProd_price.getEditText().getText().toString();
            Product product = new Product(name, des, Integer.parseInt(price));
            upload(product);
            startActivity(new Intent(AddProductActivity.this , MainActivity.class));
        });
    }
    private final ActivityResultLauncher<Intent> pickImageLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == RESULT_OK && result.getData() !=null){
                    imageUri =result.getData().getData();
                    img_addProd.setImageURI(imageUri);
                }
            }
    );
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        pickImageLaucher.launch(intent);
    }
    private void upload(Product product) {
        if (imageUri != null) {
            try {
                File imageFile = createFileFromUri(imageUri);
                createProduct(product, imageFile);
            } catch (IOException e) {
                Log.e("Upload Error", "Failed to upload file: " + e.getMessage());
                Toast.makeText(this, "Failed to upload file", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private File createFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        String fileName = getFileName(uri);
        File tempFile = new File(getCacheDir(), fileName);
        FileOutputStream outputStream = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();

        return tempFile;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void createProduct(Product product, File file) {
        RequestBody nameBD = RequestBody.create(product.getName(), MediaType.parse("text/plain"));
        RequestBody desDB = RequestBody.create(product.getDescription(), MediaType.parse("text/plain"));
        RequestBody priceDB = RequestBody.create(String.valueOf(product.getPrice()), MediaType.parse("text/plain"));
        RequestBody idCategoryDB = RequestBody.create("default_ID_category", MediaType.parse("text/plain"));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Product> call = apiService.createProduct(nameBD, desDB, imagePart, priceDB, idCategoryDB);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable throwable) {
                Log.d("API ADD ERR", "onFailure: " + throwable.getMessage());
            }
        });
    }
}