package com.example.http;


import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CheckResBean;
import com.waw.hr.mutils.bean.CheckWordBean;
import com.waw.hr.mutils.bean.WordDetailBean;
import com.waw.hr.mutils.bean.WordListBaseBean;
import com.waw.hr.mutils.bean.WordListBean;
import com.waw.hr.mutils.bean.WordTypeBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {

        public static HttpClient getServer() {
            return HttpUtils.getInstance().getGuodongServer(HttpClient.class);
        }

        public static HttpClient getOtherServer() {
            return HttpUtils.getInstance().getGuodongServer(HttpClient.class);
        }
    }


    @FormUrlEncoded
    @POST("api/login")
    Observable<BaseBean<String>> login(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/pwdfind")
    Observable<BaseBean<List>> pwdfind(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/code")
    Observable<BaseBean<String>> code(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/register")
    Observable<BaseBean<String>> register(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/login")
    Observable<BaseBean<String>> mobilelogin(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/resetpwd")
    Observable<BaseBean<String>> resetpwd(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/addressedit")
    Observable<BaseBean<Object>> addressedit(@Header("token") String token, @FieldMap Map<String, Object> params);

    @Multipart
    @POST("api/editIcon")
    Observable<BaseBean<String>> editIcon(@Header("token") String token, @Part MultipartBody.Part img);


    @FormUrlEncoded
    @POST("api/addressadd")
    Observable<BaseBean<Object>> addressadd(@Header("token") String token, @FieldMap Map<String, Object> params);

    @GET("type/read")
    Observable<BaseBean<List<WordTypeBean>>> read(@QueryMap Map<String, Object> params);

    @GET("word/read")
    Observable<BaseBean<WordListBaseBean>> word_read(@QueryMap Map<String, Object> params);


    @GET("vocab/read")
    Observable<BaseBean<WordDetailBean>> vocab_read(@QueryMap Map<String, Object> params);


    @GET("test/get")
    Observable<BaseBean<List<CheckWordBean>>> test_get(@QueryMap Map<String, Object> params);

    @GET("res/grade")
    Observable<BaseBean<CheckResBean>> grade(@QueryMap Map<String, Object> params);


}