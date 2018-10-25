package com.example.hoon.thesiswork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

// 맨처음 로그인 화면 ########
public class MainActivity extends Activity {

    // !!!!!!!!!!!!!!!!!!!!! 모든 IP를 이 변수에서 static으로 관리함. 이것만 수정하면 됨
    public static String IPstring = "192.168.43.58";    // hoon's hotspot to laptop IP (192.168.43.58)!!!!!!!!!!!!!!!

    Button signUpButton;    // 가입
    Button signInButton;    // 로그인
    Button justLook;        // 둘러보기

    EditText putID , putPW;
    TextView textResult;

    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

    String UserFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        UserFound = "User Found"; // String Type


        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        putID = (EditText) findViewById(R.id.putID);
        putPW = (EditText) findViewById(R.id.putPW);

        justLook = (Button) findViewById(R.id.justLook);

        textResult = (TextView) findViewById(R.id.textResult);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , SignUpPage.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tt" , "hello1?");
                new Thread(new Runnable() {
                    public void run() {
                        Log.i("tt" , "hello?2");
                        Looper.prepare();
                        login();
                    }
                }).start();
                Looper.loop();
            }
        });

        justLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MenuPage.class);
                startActivity(intent);
            }
        });

    }

    void login() {
        try {
            Log.i("tt" , "hello?3");
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://"+IPstring+"/login.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("ID", putID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("PW", putPW.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            response = httpclient.execute(httppost);
            // !!!!!!!!!! equalsIgnorecase == response 안먹히는 문제 해결가즈아
            final String responseTxt = EntityUtils.toString(response.getEntity());
            //String responseTxt2 = response.toString();

            //JSONObject json = new JSONObject(responseTxt);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String response1 = httpclient.execute(httppost, responseHandler);

            System.out.println("Response : " + response1);
            //textResult.setText("Response from PHP : " + response);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textResult.setText("Response from PHP : " + response1);
                    Log.e("tt" , response1); // response should be "User Found"
                    System.out.println(response1);
                    System.out.println(responseTxt);
                    // ### User Found라고 response는 뜨는데 if문에 제대로 안걸리는 문제있음!!
                }
            });

//            if (json.has("User Found"))
            // (responseTxt == "User Found")
            // if (responseTxt.equalsIgnoreCase("User Found"))
            //XXX  if (response.equalsIgnoreCase(responseTxt))
            //if (responseTxt.equalsIgnoreCase(UserFound))
            // if (responseTxt2 == UserFound)
            //if (response.equalsIgnoreCase("User Foud"))
            //if (json.has("User Found"))

            Log.i("tagtt",response1);

            // 로그인 성공시##########. ... trim()안하면 이상하게 안넘어감ㅋㅋㅋㅋㅋㅋㅋ개고생함이거때문에.. 값은 분명 같이 로그캣에 뜨는데..
            if (response1.trim().equalsIgnoreCase("Success"))
            {
                Log.i("tt" , "User found!!!! and Login Success!!!!");
                // 쓰레드에서 안하면 토스트메시지 안뜸###
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "로그인 성공~", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity((new Intent(MainActivity.this, MenuPage.class)));
                finish();

            }

            else
            { //  로그인 실패
                Log.i("tt" , "Login Failed!!!!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "로그인 실패. ID나 PW를 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception : " + e.getMessage());
        }
    }


}
