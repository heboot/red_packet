package com.example.http;


import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BalanceLogListBean;
import com.waw.hr.mutils.bean.BankListBean;
import com.waw.hr.mutils.bean.BlackListBean;
import com.waw.hr.mutils.bean.CashListBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.waw.hr.mutils.bean.CreateRedPackageBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.GroupInfoBean;
import com.waw.hr.mutils.bean.GroupUserListBean;
import com.waw.hr.mutils.bean.HelpBean;
import com.waw.hr.mutils.bean.LoginBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.waw.hr.mutils.bean.RedpackageLogBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.waw.hr.mutils.bean.SearchDialogueListBean;
import com.waw.hr.mutils.bean.UserInfoBean;

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


    @FormUrlEncoded
    @POST("in/uSex")
    Observable<BaseBean<Object>> uSex(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/bIndex")
    Observable<BaseBean<BalanceLogListBean>> bIndex(@Header("token") String token, @FieldMap Map<String, Object> params);


    @Multipart
    @POST("in/editHeadImg")
    Observable<BaseBean<Object>> editHeadImg(@Header("token") String token, @Part MultipartBody.Part img);


    @FormUrlEncoded
    @POST("in/upNName")
    Observable<BaseBean<Object>> upNName(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/fRealName")
    Observable<BaseBean<Object>> fRealName(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/uUpname")
    Observable<BaseBean<Object>> uUpname(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/kAdd")
    Observable<BaseBean<Object>> add_bank(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/bEmHistory")
    Observable<BaseBean<CashListBean>> bEmHistory(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/kList")
    Observable<BaseBean<List<BankListBean>>> bankList(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/uVerPay")
    Observable<BaseBean<Object>> uVerPay(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/kRelease")
    Observable<BaseBean<Object>> kRelease(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/bEmbody")
    Observable<BaseBean<Object>> bEmbody(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/upPay")
    Observable<BaseBean<Object>> upPay(@Header("token") String token, @FieldMap Map<String, Object> params);

    //    @FormUrlEncoded
    @POST("in/fIndex")
    Observable<BaseBean<ContatsListBean>> fIndex(@Header("token") String token);


    @FormUrlEncoded
    @POST("in/gAddUser")
    Observable<BaseBean<Object>> gAddUser(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/searchDialogueList")
    Observable<BaseBean<SearchDialogueListBean>> searchDialogueList(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/rList")
    Observable<BaseBean<RedpackageLogBean>> rList(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/gUlist")
    Observable<BaseBean<GroupUserListBean>> gUlist(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/gAdmin")
    Observable<BaseBean<Object>> gAdmin(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/gDelUser")
    Observable<BaseBean<Object>> gDelUser(@Header("token") String token, @FieldMap Map<String, Object> params);


    @POST("in/fSCon")
    Observable<BaseBean<List<NewFriendListBean>>> fSCon(@Header("token") String token);

    @FormUrlEncoded
    @POST("in/gIndex")
    Observable<BaseBean<List<MyGroupBean>>> gIndex(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/fConsent")
    Observable<BaseBean<Object>> fConsent(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/groupInfo")
    Observable<BaseBean<GroupInfoBean>> groupInfo(@Header("token") String token, @FieldMap Map<String, Object> params);

    @POST("in/iRegard")
    Observable<BaseBean<Map>> iRegard(@Header("token") String token);

    @POST("in/iHelp")
    Observable<BaseBean<HelpBean>> iHelp(@Header("token") String token);


    @FormUrlEncoded
    @POST("in/complaint")
    Observable<BaseBean<Object>> complaint(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/bRechar")
    Observable<BaseBean<Object>> bRechar(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/fAddBlack")
    Observable<BaseBean<Object>> fAddBlack(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/fReBla")
    Observable<BaseBean<Object>> fReBla(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/tCreate")
    Observable<BaseBean<CreateRedPackageChildBean>> tCreate(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/tTransfer")
    Observable<BaseBean<Object>> tTransfer(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/uRePss")
    Observable<BaseBean<Object>> uRePss(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/fDel")
    Observable<BaseBean<Object>> fDel(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/gInfo")
    Observable<BaseBean<GroupDetaiInfoBean>> gInfo(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/upBannet")
    Observable<BaseBean<Object>> upBannet(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/upAdmInavite")
    Observable<BaseBean<Object>> upAdmInavite(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/upChaTop")
    Observable<BaseBean<Object>> upChaTop(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/upRejct")
    Observable<BaseBean<Object>> upRejct(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/upInvite")
    Observable<BaseBean<Object>> upInvite(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/gTitle")
    Observable<BaseBean<Object>> gTitle(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/gNoice")
    Observable<BaseBean<Object>> gNoice(@Header("token") String token, @FieldMap Map<String, Object> params);


    @POST("in/bRead")
    Observable<BaseBean<Map>> bRead(@Header("token") String token);

    @FormUrlEncoded
    @POST("in/tVerify")
    Observable<BaseBean<Integer>> tVerify(@Header("token") String token, @FieldMap Map<String, Object> params);

    @POST("in/fBlackL")
    Observable<BaseBean<List<BlackListBean>>> fBlackL(@Header("token") String token);


    @FormUrlEncoded
    @POST("in/tGetMoney")
    Observable<BaseBean<GetRedpackageBean>> tGetMoney(@Header("token") String token, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("in/bGetTransfer")
    Observable<BaseBean<Map>> bGetTransfer(@Header("token") String token, @FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("in/gCreate")
    Observable<BaseBean<Map>> gCreate(@Header("token") String token, @FieldMap Map<String, Object> params);

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
    @POST("in/userInfo")
    Observable<BaseBean<UserInfoBean>> userInfo(@Header("token") String token, @FieldMap Map<String, Object> params);


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