package com.zonghong.redpacket;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.http.HttpClient;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupMember;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.waw.hr.mutils.event.MessageEvent;
import com.zonghong.redpacket.activity.contacts.SearchContactsActivity;
import com.zonghong.redpacket.adapter.SearchContactsAdapter;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.listenter.MActivtiyLifecycleCallBack;
import com.zonghong.redpacket.listenter.MyConversationClickListener;
import com.zonghong.redpacket.listenter.MyGroupInfoProvider;
import com.zonghong.redpacket.listenter.MyUserInfoProvider;
import com.zonghong.redpacket.rong.CustomDefaultExtensionModule;
import com.zonghong.redpacket.rong.MingPianMessage;
import com.zonghong.redpacket.rong.MingPianMessageView;
import com.zonghong.redpacket.rong.RedPackageChatMessage;
import com.zonghong.redpacket.rong.RedPackageChatMessageView;
import com.zonghong.redpacket.rong.RedPackageChatOpenMessage;
import com.zonghong.redpacket.rong.RedPackageChatTipMessageView;
import com.zonghong.redpacket.rong.RedPackageZhuanZhangChatMessage;
import com.zonghong.redpacket.rong.RedPackageZhuanzhangChatMessageView;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.eventbus.EventBus;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

public class MAPP extends Application {

    public static MAPP mapp;

    private Activity currentActivity;

    public static boolean isShow;


    @Override
    public void onCreate() {
        super.onCreate();
        mapp = this;
        registerActivityLifecycleCallbacks(new MActivtiyLifecycleCallBack());
        registerMessageTemplate();
        registerMessageListener();
        registerProvider();
        ZXingLibrary.initDisplayOpinion(this);


    }

    private void registerProvider() {
        RongIM.setUserInfoProvider(new MyUserInfoProvider(), true);
        RongIM.setGroupInfoProvider(new MyGroupInfoProvider(), true);
        RongIM.setConversationClickListener(new MyConversationClickListener());
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
        RongIM.registerMessageType(MingPianMessage.class);

        setMyExtensionModule();
        RongIM.getInstance().registerMessageTemplate(new RedPackageChatMessageView());
        RongIM.getInstance().registerMessageTemplate(new RedPackageChatTipMessageView());
        RongIM.getInstance().registerMessageTemplate(new RedPackageZhuanzhangChatMessageView());
        RongIM.getInstance().registerMessageTemplate(new MingPianMessageView());
    }

    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new CustomDefaultExtensionModule());
            }
        }
    }


    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

}
