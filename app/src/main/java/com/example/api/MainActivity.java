package com.example.api;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.api.bean.ResponceData;
import com.example.api.http.IDataListener;
import com.example.api.http.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Hello World!
     */
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    private static final String TAG = "MainActivity";
String url="http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                Volley.sendJSONRequest(null, url, ResponceData.class, new IDataListener<ResponceData>() {
                    @Override
                    public void onSuccess(ResponceData responceData) {
                        Log.d(TAG, "onSuccess: --------------"+responceData.getT1348647909107().get(0).getTitle().toString());
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onSuccess: ------error--------");
                    }
                });


                break;
        }
    }
}
