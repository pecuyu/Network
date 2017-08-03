package com.yu.network.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(address);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    if (conn.getResponseCode() == 200) {  // 成功
                        InputStream is = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        StringBuilder response = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (listener != null) {
                            // 回调onFinish()方法
                            listener.onFinish(response.toString());
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调onError()方法
                        listener.onError(e);
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }.start();
    }

    public static void sendOkHttpRequest(final String address, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
