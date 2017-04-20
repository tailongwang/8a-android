package a8a.wwl.com.a8a_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViewById(R.id.btn_report_news).setOnClickListener(this);
    }

    private boolean isLogined(){
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_report_news){
            if (!isLogined()){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
