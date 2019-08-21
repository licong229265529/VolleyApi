package com.example.api.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class HttpTask<T> implements Runnable{
   private IHttpService httpService;
   private IHttpListener httpListener;
   public <T>HttpTask(T requestInfo,String url, IHttpService httpService,IHttpListener httpListener ){    //构造
       this.httpService=httpService;
       this.httpListener=httpListener;
       httpService.setUrl(url);
       httpService.setHttpCallBack(httpListener);//关联接口
       if (requestInfo!=null){  //如果requestInfo存在
           String requestContent= JSON.toJSONString(requestInfo);//把对象转成字符串
           try {
               httpService.setRequestData(requestContent.getBytes("utf-8"));
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }

       }
   }
    @Override
    public void run() {
        httpService.execute();  //发送请求
    }
}
