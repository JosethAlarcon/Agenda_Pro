package models;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("http://192.168.100.32/agenda_mysql/login.php")
    Call<LoginResponse> login(
            @Field("correo") String email,
            @Field("password") String password
    );
}
