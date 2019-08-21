package com.example.api.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonHttpListener<M> implements IHttpListener{
    Class<M> responceClass;
    IDataListener<M> dataListener;

    public JsonHttpListener(Class<M> responceClass, IDataListener dataListener) {
        this.responceClass = responceClass;
        this.dataListener = dataListener;
    }

    //用于切换线程
    Handler handler=new Handler(Looper.getMainLooper());


    //由JsonHttpListener对象 把inputStream结果拿回来 先传字符串 再变对象 在往外传

    @Override
    public void onSucces(InputStream inputStream) {
        //获取响应结果 ，把byte数据转换成string 数据
        String content=getContent(inputStream);
        //结果（json字符串）转换成对象
      final   M responce = JSON.parseObject(content, responceClass);
        //把结果传送到调用层
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener!=null){
                    dataListener.onSuccess(responce);
                }
            }
        });

    }

    private String getContent(InputStream inputStream) {
        String content=null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line=null;
            try {
                while (((line=reader.readLine())!=null)) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error="+e.toString());
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error="+e.toString());
                }
            }
            return sb.toString();
        }catch (Exception e) {
           e.printStackTrace();
        }
        return content;

    }

    @Override
    public void onFailure() {
        //将结果传送到调用层
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener!=null){
                    dataListener.onFailure();
                }
            }
        });

    }
}
