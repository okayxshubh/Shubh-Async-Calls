package com.dit.shubh;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dit.shubh.Presentation.CustomDialog;
import com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities.CommonUtils;
import com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities.JsonParse;
import com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities.ShubhNetworkUtil;
import com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities.ShubhPreferences;
import com.dit.shubh.ShubhNetworkCallKit.econstants.HttpTaskType;
import com.dit.shubh.ShubhNetworkCallKit.interfaces.ShubhApiCallback;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnFetch, btnFetch2 ;
    TextView tvResult, tvResult2;
    CustomDialog CD = new CustomDialog();

//    // This Works in the Background of Each API Call
//    ExecutorService executor = Executors.newSingleThreadExecutor(); // Runs tasks off the UI/main thread
//    Handler handler = new Handler(Looper.getMainLooper()); // Posts result Back to UI/main thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // SHUBH Preferences can be used like
        // Save data
        ShubhPreferences.getInstance(this).putString("username", "Shubh");

        // Get data
        String name = ShubhPreferences.getInstance(this).getString("username");

        // Clear all
        ShubhPreferences.getInstance(this).clearAll();





        // UPDATE: NO NEED FOR THIS (OLD WAY)
//        // THIS Code bypasses network calls on main thread (Not Recommended)
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);


        btnFetch = findViewById(R.id.btnFetch);
        tvResult = findViewById(R.id.tvResult);
        btnFetch2 = findViewById(R.id.btnFetch2);

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
                            tvResult.setText("Raw: " + response.getResponse());

                            // GSON
                            try {
                                Type listType = new TypeToken<List<GenderPojo>>() {
                                }.getType();
                                List<GenderPojo> genderList = new Gson().fromJson(response.getResponse(), listType);

                                for (GenderPojo item : genderList) {
                                    Log.e("GENDER GSON: ", item.toString());
                                }

                            } catch (Exception e) {
                                Log.e("PARSE_ERROR", e.getMessage());
                            }

//                                                                OR
                            // JSON PARSE
                            try {
                                List<GenderPojo> genderPojoList = JsonParse.parseGendersList(response.getResponse());
                                for (GenderPojo item : genderPojoList) {
                                    Log.e("GENDER JSON Parse: ", item.toString());
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }

                        @Override
                        public void onFailure(String errorMessage, ShubhOfflineObject fullResponse) {
                            Log.e("═══════════════ API ERROR ═══════════════", "");
                            Log.e("API_ERROR", errorMessage);
                            Log.e("API_RAW_RESPONSE", fullResponse != null ? fullResponse.getResponse() : "null");

                        }
                    }
            );
        });

        btnFetch2.setOnClickListener(view -> {
            ShubhNetworkUtil.makeApiCall(
                    this,
                    HttpTaskType.POST,
                    "https://himparivarservices.hp.gov.in/ldap",
                    "/login?",
                    null,
                    null,

                    new ShubhApiCallback() {
                        @Override
                        public void onSuccess(ShubhSuccessResponse response) {

                        }

                        @Override
                        public void onFailure(String errorMessage, ShubhOfflineObject fullResponse) {

                        }
                    }
            );
        });

    }


}