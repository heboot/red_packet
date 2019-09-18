package com.zonghong.redpacket.utils;

import android.content.Intent;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.activity.ConversationActivity;
import com.zonghong.redpacket.activity.chat.ChatDetailActivity;
import com.zonghong.redpacket.activity.contacts.ContactsDetailActivity;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.activity.redpackage.RedPageResultActivity;
import com.zonghong.redpacket.activity.user.ChooseBankActivity;
import com.zonghong.redpacket.activity.user.RechargeCashActivity;
import com.zonghong.redpacket.common.RechargeCashType;
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

    public static void intent2RedPackageOpenActivity(GetRedpackageBean getRedpackageBean) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), RedPageResultActivity.class);
        intent.putExtra(MKey.DATA, getRedpackageBean);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


    public static void intent2ContactsDetailActivity(SearchContatsBean searchContatsBean) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ContactsDetailActivity.class);
        intent.putExtra(MKey.DATA, searchContatsBean);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2ChatDetailActivity() {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ChatDetailActivity.class);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2ChooseBankActivity(RechargeCashType rechargeCashType) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ChooseBankActivity.class);
        intent.putExtra(MKey.TYPE, rechargeCashType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2RechargeCaseActivity(RechargeCashType rechargeCashType) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), RechargeCashActivity.class);
        intent.putExtra(MKey.TYPE, rechargeCashType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


}
