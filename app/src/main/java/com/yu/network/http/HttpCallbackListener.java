package com.yu.network.http;

public interface HttpCallbackListener {
    //  成功时回调
    void onFinish(String response);
    // 出错时回调
    void onError(Exception e);

}