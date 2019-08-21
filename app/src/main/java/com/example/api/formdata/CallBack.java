package com.example.api.formdata;

public interface CallBack<T> {
    void getResponse(T  t);
    void getError(T  t);

}
