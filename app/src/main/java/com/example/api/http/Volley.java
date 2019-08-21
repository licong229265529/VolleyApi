package com.example.api.http;

public class Volley {
    public static <T,M> void sendJSONRequest(T requestInfo,String url,Class<M> response,IDataListener<M>dataListener){
        IHttpService httpService=new JsonHttpService();
        IHttpListener httpListener=new JsonHttpListener(response,dataListener);//结果
        HttpTask<T> httpTask=new HttpTask<T>(requestInfo,url,httpService,httpListener);
        ThreadPoolManage.getOurInstance().execute(httpTask);
    }
}
