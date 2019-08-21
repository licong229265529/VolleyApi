package com.example.api.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonHttpService implements IHttpService{
    String url;
    private  byte[] requestData;

    IHttpListener httpListener;  //JsonHttpService持有httpListener的引用

    @Override
    public void setUrl(String url) {
this.url=url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData=requestData;
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.httpListener=httpListener;
    }
    //真实的网络操作在这样实现
    @Override
    public void execute() {
        httpUrlconnPost();
    }
    HttpURLConnection urlConnection=null;
    public void httpUrlconnPost() {
        URL url=null;
        try {
            url=new URL(this.url);
            urlConnection= (HttpURLConnection) url.openConnection();//打开http连接
            urlConnection.setConnectTimeout(6000);//连接的超时时间
            urlConnection.setUseCaches(false);//不使用缓存
            urlConnection.setInstanceFollowRedirects(true);//是成员函数,仅作用于当前函数
            urlConnection.setReadTimeout(3000);//响应的超时时间
            urlConnection.setDoInput(true);//设置这个连接是否可以写入数据
            urlConnection.setDoOutput(true);//设置这个连接是否可以输出数据
            urlConnection.setRequestMethod("POST");//设置请求方式
            urlConnection.setRequestProperty("Content-Type","application/json;charset=utf-8");
            urlConnection.connect();//连接
            //使用字节流发送数据
            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);//缓冲字节流包装字节
            if (requestData!=null){
                bos.write(requestData);
            }
            //把这个字节数组的数据写入缓冲区中
            bos.flush();//刷新缓存区,发送数据
            out.close();
            bos.close();
            //字符流写入数据
            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){//得到服务端
                InputStream in = urlConnection.getInputStream();
                httpListener.onSucces(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpListener.onFailure();
        } finally {
            urlConnection.disconnect();//使用完关闭TCP连接，释放资源
        }
    }
}
