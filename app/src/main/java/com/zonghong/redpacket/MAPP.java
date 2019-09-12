package com.zonghong.redpacket;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.bean.GroupMember;
import com.waw.hr.mutils.bean.GroupModel;
import com.zonghong.redpacket.rong.CustomDefaultExtensionModule;
import com.zonghong.redpacket.rong.RedPackageChatMessage;
import com.zonghong.redpacket.rong.RedPackageChatMessageView;
import com.zonghong.redpacket.rong.RedPackageChatOpenMessage;
import com.zonghong.redpacket.rong.RedPackageChatTipMessageView;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;

public class MAPP extends Application {

    public static MAPP mapp;

    private Activity currentActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        mapp = this;
        RongIM.init(this);
        RongIM.registerMessageType(RedPackageChatMessage.class);
        RongIM.registerMessageType(RedPackageChatOpenMessage.class);
        setMyExtensionModule();
        RongIM.getInstance().registerMessageTemplate(new RedPackageChatMessageView());
        RongIM.getInstance().registerMessageTemplate(new RedPackageChatTipMessageView());
        GroupMember[] members = {new GroupMember().setId("ghJiu7H1"), new GroupMember().setId("ghJiu7H2")};


        GroupModel group = new GroupModel()
                .setId("groupId")
                .setMembers(members)
                .setName("groupName");
//        Result groupCreateResult = (Result)Group.create(group);
        LogUtil.e("group create result:》》》  ", JSON.toJSONString(group));

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
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
