package com.example.api.http;
//封装请求
public interface IHttpService {
 void setUrl(String url);
 void setRequestData(byte[] requestData);
 void execute();//执行请求
    //  需要设置两个接口直接的关系
    void setHttpCallBack(IHttpListener httpListener);

}
