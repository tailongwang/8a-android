package a8a.wwl.com.a8a_android.services;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import a8a.wwl.com.a8a_android.Util;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {

    private static final String SERVER_ADDR = "http://dev-api.wwl.tv/";   //
    private static final String BASE_URL = SERVER_ADDR + "api/v1/";
//    private static final String IMAGE_URL = SERVER_ADDR + "Upload/";
//    public static final String VERIFY_CODE_URL = SERVER_ADDR + "index.php/Service/Index/chkcode?device_uuid=";

    private static APIService API_SERVICE;

    public static APIService getInstance() {
        if (API_SERVICE == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS);

            OkHttpClient okHttpClient = builder.build();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            API_SERVICE = retrofit.create(APIService.class);
        }
        return API_SERVICE;
    }

    public static APIService getInstance(final String token) {
        if (API_SERVICE == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS);

            if (!TextUtils.isEmpty(token)){
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();
                        requestBuilder.header("Authorization", "Bearer "+token);

                        requestBuilder.method(original.method(), original.body());
                        Request request = requestBuilder.build();

                        return chain.proceed(request);
                    }
                });
            }

            OkHttpClient okHttpClient = builder.build();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            API_SERVICE = retrofit.create(APIService.class);
        }
        return API_SERVICE;
    }

}
