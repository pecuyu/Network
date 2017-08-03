package com.yu.network;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mEtLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtLink = (EditText) findViewById(R.id.id_et_link);
    }

    public void openWebView(View view) {
        String link = mEtLink.getText().toString().trim();
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }
}
