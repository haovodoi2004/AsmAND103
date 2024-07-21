package fpoly.pro1121.asmand103.Service;

import java.util.ArrayList;

import fpoly.pro1121.asmand103.Model.Product;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    String DOMAIN = "http://192.168.2.3:3000/";

    @GET("api/product/get-all")
    Call<ArrayList<Product>> getAllProduct();

    ///
    @Multipart
    @POST("api/product/create")
    Call<Product> createProduct(
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image,
            @Part("price") RequestBody price,
            @Part("id_category") RequestBody id_category
    );

}
