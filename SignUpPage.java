package com.example.hoon.thesiswork;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignUpPage extends Activity {


    private EditText editID;
    private EditText editPW;
    private EditText editNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);


        editID = (EditText) findViewById(R.id.editID);
        editPW = (EditText) findViewById(R.id.editPW);
        editNAME = (EditText) findViewById(R.id.editNAME);

    }

    // insertToDB함수를 부른다.
    public void insert(View view) {
        String ID = editID.getText().toString();
        String PW = editPW.getText().toString();
        String NAME = editNAME.getText().toString();

        insertToDB(ID , PW , NAME);
    }

    private void insertToDB(String ID , String PW , String NAME) {
        class InsertData extends AsyncTask<String , Void , String> {

            protected void onPreExecute() {
                super.onPreExecute();
            }

            // s의 의미는 ??????????????
            protected  void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String ID = (String) params[0];
                    String PW = (String) params[1];
                    String NAME = (String) params[2];

                    String link = "http://"+MainActivity.IPstring+"/register.php";
                    String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
                    data += "&" + URLEncoder.encode("PW", "UTF-8") + "=" + URLEncoder.encode(PW, "UTF-8");
                    data += "&" + URLEncoder.encode("NAME", "UTF-8") + "=" + URLEncoder.encode(NAME, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(ID,PW,NAME);
    }


}
