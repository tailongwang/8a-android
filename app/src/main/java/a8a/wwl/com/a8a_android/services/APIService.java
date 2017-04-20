package a8a.wwl.com.a8a_android.services;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface APIService {
    String API_CHECK_TOKEN = "login/check";
    String API_GET_CONFIRMATION = "login/request-code";
    String API_LOGIN = "login";

    @GET(API_CHECK_TOKEN)
    Call<BaseResponse> checkToken();

    @FormUrlEncoded
    @POST(API_GET_CONFIRMATION)
    Call<CodeResponse> getConfirmationSMS(@FieldMap Map<String, String> parameters);

    @FormUrlEncoded
    @POST(API_LOGIN)
    Call<TokenResponse> login(@FieldMap Map<String, String> parameters);
}