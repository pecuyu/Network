package com.yu.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText mEtLink;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtLink = (EditText) findViewById(R.id.id_et_link);
        mTv = (TextView) findViewById(R.id.id_tv_show_result);
    }

    public void openWebView(View view) {
        String link = mEtLink.getText().toString().trim();
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }

    /**
     * 使用HttpURLConnection访问网络
     * @param view
     */
    public void useHttpURLConnection(View view) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.e("TAG", "in thread~");
                HttpURLConnection conn = null;
                BufferedReader br = null;
                try {
                    URL url = new URL("http://blog.csdn.net/zm_crazy/article/details/46652919");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        br = new BufferedReader(new InputStreamReader(inputStream));
                        String line = null;
                        StringBuilder sb = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        showResponseText(sb.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null)
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (conn != null) conn.disconnect();
                }
            }
        }.start();
    }

    /**
     * 使用OkHttp访问网络
     * @param view
     */
    public void useOkHttp(View view) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://www.baidu.com/").build();
                try {
                    Response response = client.newCall(request).execute();
                    // 注意最后调用的是string()方法，而不是toString()
                    String result = response.body().string();
                    Log.e("TAG", "result="+result);
                    showResponseText(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 显示响应结果
     * @param response
     */
    private void showResponseText(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "response="+response);
                mTv.setText(response);
            }
        });
    }
}
