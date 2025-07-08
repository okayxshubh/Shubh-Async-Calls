package com.dit.shubh;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.dit.shubh.Presentation.CustomDialog;
import com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities.AppStatus;
import com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities.CommonUtils;
import com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities.ShubhNetworkUtil;
import com.dit.shubh.ShubhNetworkCallKit.econstants.HttpTaskType;
import com.dit.shubh.ShubhNetworkCallKit.interfaces.ShubhApiCallback;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhUploadObject;
import com.dit.shubh.ShubhNetworkCallKit.network.ShubhHttpManager;
import com.dit.shubh.ShubhNetworkCallKit.network.ShubhResponseWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Button btnFetch;
    TextView tvResult;
    CustomDialog CD = new CustomDialog();

//    // This Works in the Background of Each API Call
//    ExecutorService executor = Executors.newSingleThreadExecutor(); // Runs tasks off the UI/main thread
//    Handler handler = new Handler(Looper.getMainLooper()); // Posts result Back to UI/main thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UPDATE: NO NEED FOR THIS (OLD WAY)
//        // THIS Code bypasses network calls on main thread (Not Recommended)
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);


        btnFetch = findViewById(R.id.btnFetch);
        tvResult = findViewById(R.id.tvResult);

        String a = String.valueOf(CommonUtils.getDeviceInfo());
        tvResult.setText(a);

        btnFetch.setOnClickListener(view -> {

            ShubhNetworkUtil.makeApiCall(
                    this,
                    HttpTaskType.GET,
                    "https://himparivarservices.hp.gov.in/bocw-survey-api",
                    "/master-data?masterName=gender&status=true",
                    null,
                    null,

                    new ShubhApiCallback() {
                        @Override
                        public void onSuccess(ShubhSuccessResponse response) {
                            tvResult.setText("Response: " + response.toString());
                        }

                        @Override
                        public void onFailure(String errorMessage, ShubhOfflineObject fullResponse) {
                            CD.showDialog(MainActivity.this, errorMessage);
                            Log.e("API_RAW", fullResponse != null ? fullResponse.getResponse() : "null");
                            Log.e("API_PARSED", fullResponse != null ? fullResponse.toString() : "null");
                        }
                    }
            );

        });




    }


}