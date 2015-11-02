package com.thousandsunny.kemis;

/**
 * Created by hallidafykzir on 10/25/2015.
 */

import com.thousandsunny.kemis.model.Keluarga;
import com.thousandsunny.kemis.model.Post;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by hallidafykzir on 7/1/2015.
 */
public class WebServiceHelper {

    private static final String API_ENDPOINT_LOCALHOST = "http://36.86.177.169/kemis/public/";
    private static WebServiceHelper instance;

    private Services services;

    public static WebServiceHelper getInstance() {
        if (instance == null) instance = new WebServiceHelper();
        return instance;
    }

    private WebServiceHelper(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT_LOCALHOST).build();
        services = restAdapter.create(Services.class);
    }

    public Services getServices() {
        return services;
    }

    public interface Services{

        //@GET("/getuser/{id}")
        //void getUserByIdLocalhost(@Path("id") int id, Callback<User> callback);

        @GET("/getAll")
        void getAllKeluarga(Callback<List<Keluarga>> callback);

        //@FormUrlEncoded
        //@POST("/register")
        //void createUser(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("password_confirm") String password_confirm, Callback<Register> callback);

        @Multipart
        @POST("/upload")
        void postKeluarga(@Part("longitude") double longitude, @Part("latitude") double latitude, @Part("no_kk") String no_kk,
                           @Part("nama_kepala") String nama_kepala, @Part("alamat") String alamat,
                           @Part("luas_lantai") double luas_lantai, @Part("jenis_lantai") int jenis_lantai, @Part("jenis_dinding") int jenis_dinding,
                           @Part("toilet") int toilet, @Part("listrik") int listrik, @Part("air") int air, @Part("bahan_bakar_masak") int bahan_bakar_masak,
                           @Part("daging_susu") int daging_susu, @Part("baju") int baju, @Part("makan") int makan, @Part("bayar_obat") int bayar_obat,
                           @Part("pendapatan") double pendapatan, @Part("pendidikan") int pendidikan, @Part("tabungan") double tabungan,
                           @Part("foto_keluarga") TypedFile foto_keluarga, @Part("foto_kk") TypedFile foto_kk, @Part("no_rt") int no_rt, Callback<Post> callback);

        
    }

}