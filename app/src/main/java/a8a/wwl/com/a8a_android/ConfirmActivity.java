package a8a.wwl.com.a8a_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.Map;

import a8a.wwl.com.a8a_android.services.ServiceProvider;
import a8a.wwl.com.a8a_android.services.TokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    EditText txtCode;
    private SVProgressHUD mSVProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        mSVProgressHUD = new SVProgressHUD(this);

        txtCode = (EditText) findViewById(R.id.txt_code);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next){
            if (TextUtils.isEmpty(txtCode.getText())){
                Toast.makeText(this, "Confirmation Code can not be empty. Please input it.", Toast.LENGTH_LONG).show();
            } else {
                login();
            }
        } else if (v.getId() == R.id.btn_cancel){
            finish();
        }
    }

    private void login() {

        mSVProgressHUD.show();

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("phoneNumber", Util.getPhone(this));
        requestParams.put("confirmationCode", txtCode.getText().toString());

        Call<TokenResponse> apiCall = ServiceProvider.getInstance().login(requestParams);
        apiCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                mSVProgressHUD.dismissImmediately();

                if (TextUtils.isEmpty(response.body().token)) {
                    mSVProgressHUD.showErrorWithStatus("The confirmation code not match. please try again", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                    txtCode.setText("");
                    return;
                }

                Util.saveToken(ConfirmActivity.this, response.body().token);

                Intent intent = new Intent(ConfirmActivity.this, ProfileActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                mSVProgressHUD.dismissImmediately();
                mSVProgressHUD.showSuccessWithStatus(t.getMessage());
            }
        });
    }

}
