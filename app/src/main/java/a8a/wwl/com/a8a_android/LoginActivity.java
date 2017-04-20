package a8a.wwl.com.a8a_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.Map;

import a8a.wwl.com.a8a_android.services.CodeResponse;
import a8a.wwl.com.a8a_android.services.ServiceProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity implements View.OnClickListener{
    EditText txtPhone;
    private SVProgressHUD mSVProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSVProgressHUD = new SVProgressHUD(this);

        txtPhone = (EditText) findViewById(R.id.txt_phone);
        findViewById(R.id.btn_get_code).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get_code){
            if (TextUtils.isEmpty(txtPhone.getText())){
                Toast.makeText(this, "Phone number can not be empty. Please input it.", Toast.LENGTH_LONG).show();
            } else if (!txtPhone.getText().toString().contains("+")){
                Toast.makeText(this, "InValid Format.", Toast.LENGTH_LONG).show();
            } else {
                sendConfirmationSMS();
            }
        }
    }

    private void sendConfirmationSMS() {

        mSVProgressHUD.show();

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("phoneNumber",txtPhone.getText().toString());

        Call<CodeResponse> apiCall = ServiceProvider.getInstance().getConfirmationSMS(requestParams);
        apiCall.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                mSVProgressHUD.dismissImmediately();

                if (TextUtils.isEmpty(response.body().success) || !response.body().success.contains("ok")) {
                    mSVProgressHUD.showErrorWithStatus(response.body().message, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                    return;
                }

                Util.savePhone(LoginActivity.this, txtPhone.getText().toString());

                Intent intent = new Intent(LoginActivity.this, ConfirmActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mSVProgressHUD.dismissImmediately();
                mSVProgressHUD.showSuccessWithStatus(t.getMessage());
            }
        });
    }

}
