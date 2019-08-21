package com.example.api.formdata;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by liangxq on 2018/9/21.
 * <p>
 * 请求体的封装
 */

public interface MyServer {
//    public String URL1="http://39.107.224.233/firstga/app/";
    public String URL="http://api.shujuzhihui.cn/api/news/";
//    list?appKey=25ffda53ed8d4ee69bd31837200ed4f5&category=%E5%A8%B1%E4%B9%90&page=1
    /**
     * 获取验证码接口
     * @param requestBody
     * @return
     */
    @POST("users/sendVerificationCode")
    @Headers("Content-Type:application/json")
    Call<ResponseBody>getVerificationCode(@Url String URL,@Body RequestBody requestBody);

    /**
     * 手机登录
     * @param requestBody
     * @return
     */
    @POST("users/phoneLogin")
    @Headers("Content-Type:application/json")
    Call<ResponseBody>phoneLogin(@Body RequestBody requestBody);


    /**
     * 上传文件
     * @param file
     * @param partMap
     * @return
     */
    @POST("users/uploadHeadImage")
    @Multipart
    Call<ResponseBody>uploadHeadImage(@Part MultipartBody.Part  file, @PartMap() Map<String, RequestBody> partMap);
    /**
     * 上传图片
     * @param requestBody
     * @return
     */
    @POST("users/uploadHeadImage")
    Call<ResponseBody>uploadHeadImageTwo(@Body RequestBody requestBody);

    @GET
    Call<String> get(@Url String url,@QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url,@FieldMap Map<String, Object> map);


    @POST
    @Headers("Content-Type: application/json")
    Call<String> postJson(@Url String url,@Body RequestBody requestBody);

    @Multipart
    @POST
    Call<String> upload(@Url String url,@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> map);

}
