package com.example.http;


import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.waw.hr.mutils.bean.CreateRedPackageBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.LoginBean;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.waw.hr.mutils.bean.SearchContatsBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("in/uCreate")
    Observable<BaseBean<Object>> uCreate(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/sendVerify")
    Observable<BaseBean<String>> sendVerify(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/uLogin")
    Observable<BaseBean<LoginBean>> uLogin(@FieldMap Map<String, Object> params);


    //    @FormUrlEncoded
    @POST("in/fIndex")
    Observable<BaseBean<ContatsListBean>> fIndex(@Header("token") String token);

    @POST("in/fSCon")
    Observable<BaseBean<List<NewFriendListBean>>> fSCon(@Header("token") String token);

    @POST("in/fSCon")
    Observable<BaseBean<Object>> fConsent(@Header("token") String token);


    @FormUrlEncoded
    @POST("in/tCreate")
    Observable<BaseBean<CreateRedPackageChildBean>> tCreate(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/tGetMoney")
    Observable<BaseBean<Object>> tGetMoney(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/gCreate")
    Observable<BaseBean<Object>> gCreate(@Header("token") String token, @FieldMap Map<String, Object> params);

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

    @FormUrlEncoded
    @POST("in/fAdd")
    Observable<BaseBean<List<SearchContatsBean>>> fAdd(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/fSendMsg")
    Observable<BaseBean<Boolean>> fSendMsg(@Header("token") String token, @FieldMap Map<String, Object> params);


//    @GET("type/read")
//    Observable<BaseBean<List<WordTypeBean>>> read(@QueryMap Map<String, Object> params);
//
//    @GET("word/read")
//    Observable<BaseBean<WordListBaseBean>> word_read(@QueryMap Map<String, Object> params);
//
//
//    @GET("vocab/read")
//    Observable<BaseBean<WordDetailBean>> vocab_read(@QueryMap Map<String, Object> params);
//
//
//    @GET("test/get")
//    Observable<BaseBean<List<CheckWordBean>>> test_get(@QueryMap Map<String, Object> params);
//
//    @GET("res/grade")
//    Observable<BaseBean<CheckResBean>> grade(@QueryMap Map<String, Object> params);


}