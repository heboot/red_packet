package com.zonghong.redpacket.utils;

import android.content.Intent;

import com.waw.hr.mutils.MKey;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.activity.ConversationActivity;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.activity.redpackage.RedPageResultActivity;
import com.zonghong.redpacket.common.RedPackageType;

import io.rong.imlib.model.Conversation;


public class IntentUtils {

    private static Intent intent;

    public static void doIntent(Class cls) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), cls);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


    public static void intent2NewRedPackageActivity(RedPackageType type, String toId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), NewRedPackageActivity.class);
        intent.putExtra(MKey.TYPE, type);
        if (type == RedPackageType.CHAT) {
            intent.putExtra(MKey.GET_USER, toId);
        } else {
            intent.putExtra(MKey.ID, toId);
        }
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2RedPackageOpenActivity(String money) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), RedPageResultActivity.class);
        intent.putExtra(MKey.MONEY, money);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


}
