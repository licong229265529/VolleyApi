package com.example.api.http;
//回调用层的接口
public interface IDataListener<M> {
    void onSuccess(M m);
    void onFailure();
}

