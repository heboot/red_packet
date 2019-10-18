package com.zonghong.redpacket;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.event.MessageEvent;
import com.zonghong.redpacket.listenter.MActivtiyLifecycleCallBack;
import com.zonghong.redpacket.listenter.MyConversationClickListener;
import com.zonghong.redpacket.listenter.MyConversationListBehaviorListener;
import com.zonghong.redpacket.listenter.MyGroupInfoProvider;
import com.zonghong.redpacket.listenter.MyUserInfoProvider;
import com.zonghong.redpacket.rong.CustomBiaoqingMessage;
import com.zonghong.redpacket.rong.CustomBiaoqingMessageView;
import com.zonghong.redpacket.rong.DeleteGroupMessageEventMessage;
import com.zonghong.redpacket.rong.MingPianMessage;
import com.zonghong.redpacket.rong.MingPianMessageView;
import com.zonghong.redpacket.rong.RedPackageChatMessage;
import com.zonghong.redpacket.rong.RedPackageChatMessageView;
import com.zonghong.redpacket.rong.RedPackageChatOpenMessage;
import com.zonghong.redpacket.rong.RedPackageChatTipMessageView;
import com.zonghong.redpacket.rong.RedPackageZhuanZhangChatMessage;
import com.zonghong.redpacket.rong.RedPackageZhuanzhangChatMessageView;
import com.zonghong.redpacket.rong.RongUtils;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

public class MAPP extends Application {

    public static MAPP mapp;

    private Activity currentActivity;

    public static boolean isShow;

    private String  currentConversationId = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mapp = this;
        registerActivityLifecycleCallbacks(new MActivtiyLifecycleCallBack());
        registerMessageTemplate();
        registerMessageListener();
        registerProvider();
        ZXingLibrary.initDisplayOpinion(this);
        CrashReport.initCrashReport(getApplicationContext(), "74a820fae1", false);

    }

    private void registerProvider() {
        RongIM.setUserInfoProvider(new MyUserInfoProvider(), true);
        RongIM.setGroupInfoProvider(new MyGroupInfoProvider(), true);
        RongIM.setLocationProvider(new RongIM.LocationProvider() {
            @Override
            public void onStartLocation(Context context, LocationCallback locationCallback) {

            }
        });
        RongIM.setConversationClickListener(new MyConversationClickListener());
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());
    }


    private void registerMessageListener() {
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                RongIM.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        LogUtil.e("未读消息", "=====" + integer);
                        EventBus.getDefault().post(new MessageEvent.GET_UNREAD_MESSAGE_NUM_EVENT(integer));
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        LogUtil.e("未读消息 onError", "=====" + errorCode.getMessage());
                    }
                });
                return false;
            }
        });
    }


    private void registerMessageTemplate() {
        RongIM.init(this);
        RongIM.registerMessageType(RedPackageChatMessage.class);
        RongIM.registerMessageType(RedPackageChatOpenMessage.class);
        RongIM.registerMessageType(RedPackageZhuanZhangChatMessage.class);
        RongIM.registerMessageType(DeleteGroupMessageEventMessage.class);
        RongIM.registerMessageType(MingPianMessage.class);
        RongIM.registerMessageType(CustomBiaoqingMessage.class);
        setMyExtensionModule();
        RongIM.getInstance().registerMessageTemplate(new RedPackageChatMessageView());
        RongIM.getInstance().registerMessageTemplate(new RedPackageChatTipMessageView());
        RongIM.getInstance().registerMessageTemplate(new RedPackageZhuanzhangChatMessageView());
        RongIM.getInstance().registerMessageTemplate(new MingPianMessageView());
        RongIM.getInstance().registerMessageTemplate(new CustomBiaoqingMessageView());
    }

    public void setMyExtensionModule() {
        RongUtils.setPrivateExtensionModule();
    }


    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public String getCurrentConversationId() {
        return currentConversationId;
    }

    public void setCurrentConversationId(String currentConversationId) {
        this.currentConversationId = currentConversationId;
    }
}
