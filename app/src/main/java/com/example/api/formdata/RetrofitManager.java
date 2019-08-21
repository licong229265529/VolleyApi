/*
package com.example.api.formdata;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import com.liangxq.myfourclass.retrofit.httputils.MyServer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

*/
/**
 * Created by liangxq on 2018/9/23.
 *//*


public class RetrofitManager {

    private static RetrofitManager mRetrofitManager = null;

    private boolean isCheckChece = false;

    public static RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }

        return mRetrofitManager;
    }


    public MyServer retrofitClient(boolean isCheckChece) {
        Retrofit retrofit = null;
        if (isCheckChece) {
            retrofit =
                    new Retrofit.Builder()
                            .baseUrl(MyServer.URL)
                            .client(getOkhttpClient())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
        } else {
            retrofit =
                    new Retrofit.Builder()
                            .baseUrl(MyServer.URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
        }

        return retrofit.create(MyServer.class);

    }


    public OkHttpClient getOkhttpClient() {
        MyCacheinterceptor myCacheinterceptor = new MyCacheinterceptor();
        Cache cache = new Cache(new File(MyApp.myApp.getFilesDir(), "Cache"), 1024 * 1024 * 10);
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(myCacheinterceptor)
                .addNetworkInterceptor(myCacheinterceptor)
                .build();
    }

    private class MyCacheinterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
            if (!isNetworkAvailable(MyApp.myApp)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();

            }
            okhttp3.Response originalResponse = chain.proceed(request);
            if (isNetworkAvailable(MyApp.myApp)) {
                int maxAge = 0;
                return originalResponse.newBuilder()
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 15;
                return originalResponse.newBuilder()
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    public <T> void get(boolean isCheckChece,String url, Map<String, Object> map, final Class<T> bean, final CallBack callBack) {
        MyServer myServer = retrofitClient(isCheckChece);
        Call<String> stringCall = myServer.get(url, map);
        huidiao(bean, callBack, stringCall);
    }

    public <T> void post(String url, Map<String, Object> map, final Class<T> bean, final CallBack callBack) {
        MyServer myServer = retrofitClient(isCheckChece);
        Call<String> stringCall = myServer.get(url, map);
        huidiao(bean, callBack, stringCall);
    }

    public <T> void postJson(String url, String jsonString, final Class<T> bean, final CallBack callBack) {
        MyServer myServer = retrofitClient(isCheckChece);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json,charset-UTF-8"), jsonString);
        Call<String> stringCall = myServer.postJson(url, requestBody);
        huidiao(bean, callBack, stringCall);
    }

    public <T> void upload(String url, String fileString, Map<String, String> map, final Class<T> bean, final CallBack callBack) {
        MyServer myServer = retrofitClient(isCheckChece);
		//要上传的文件
        File file = new File(fileString);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
		//要上传的其他的字段  
        Map<String, RequestBody> map1 = new HashMap<>();
		
        for (String key : map.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get(key));
            map1.put(key, requestBody);
        }
        Call<String> stringCall = myServer.upload(url, part, map1);
        huidiao(bean, callBack, stringCall);
    }

    private <T> void huidiao(final Class<T> bean, final CallBack callBack, Call<String> stringCall) {
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                Gson gson = new Gson();
                if (bean != null) {
                    T t = gson.fromJson(body, bean);
                    callBack.getResponse(t);
                } else {
                    callBack.getResponse(body);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBack.getError(t.getMessage());
            }
        });
    }

    */
/**
     * 实体类转对象
     *
     * @param
     * @param <T>
     * @return
     *//*

    public <T> String beanToJson(T t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }
	
	//================================================================================
	  @POST()
      Call<ResponseBody> upLoad(
      @Url() String url,
      @Body RequestBody Body);

	
	 //创建请求体并指定上传类型
	 RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
	 //上传文件字段
	 .addFormDataPart("name", name) 
	 .addFormDataPart("psd", psd) 
	 //上传文件
	 .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
	 .build();
 
      Call<ResponseBody> call = service.upload(url, requestBody );
	 //================================================================================
       //创建文件
	   File file=new File("/sdcard/Pictures/Screenshots/b.png");
                if(file.exists()){
					//指定上传文件类型
                    RequestBody requestBody3=RequestBody.create(MediaType.parse("image/png"),file);
					//创建form-data
                    MultipartBody.Part part= MultipartBody.Part.createFormData("file",file.getName(),requestBody3);
					//携带的数据
                    RequestBody requestBody4=RequestBody.create(MediaType.parse("multipart/form-data"),"sdsa");
}
*/
