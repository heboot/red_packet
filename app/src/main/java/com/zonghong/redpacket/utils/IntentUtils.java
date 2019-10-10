package com.zonghong.redpacket.utils;

import android.content.Intent;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.activity.chat.AlterGroupNotiActivity;
import com.zonghong.redpacket.activity.chat.ChatDetailActivity;
import com.zonghong.redpacket.activity.chat.DelGroupUserActivity;
import com.zonghong.redpacket.activity.chat.ForbidRedpackageUserActivity;
import com.zonghong.redpacket.activity.chat.GroupDetailActivity;
import com.zonghong.redpacket.activity.chat.GroupManagerActivity;
import com.zonghong.redpacket.activity.chat.AddGroupUserActivity;
import com.zonghong.redpacket.activity.chat.GroupUserListActivity;
import com.zonghong.redpacket.activity.chat.SearchMessageActivity;
import com.zonghong.redpacket.activity.chat.SetGroupManagerActivity;
import com.zonghong.redpacket.activity.common.ComplaintActivity;
import com.zonghong.redpacket.activity.common.EditTextAreaActivity;
import com.zonghong.redpacket.activity.common.TextActivity;
import com.zonghong.redpacket.activity.contacts.ChooseContactsSendMingPianActivity;
import com.zonghong.redpacket.activity.contacts.ContactsDetailActivity;
import com.zonghong.redpacket.activity.redpackage.NewRedPackageActivity;
import com.zonghong.redpacket.activity.redpackage.RedPageResultActivity;
import com.zonghong.redpacket.activity.redpackage.ZhuanZhangResultActivity;
import com.zonghong.redpacket.activity.user.AlterPwdActivity;
import com.zonghong.redpacket.activity.user.AlterTextActivity;
import com.zonghong.redpacket.activity.user.ChooseBankActivity;
import com.zonghong.redpacket.activity.user.QRCodeActivity;
import com.zonghong.redpacket.activity.user.RechargeCashActivity;
import com.zonghong.redpacket.activity.user.TransferAccountActivity;
import com.zonghong.redpacket.activity.user.VerifyCodeActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.common.ComplaintType;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.common.QRCodeType;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.common.SearchMessageType;
import com.zonghong.redpacket.common.SendMingPianType;
import com.zonghong.redpacket.service.UserService;

import java.io.Serializable;
import java.util.Map;


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

    public static void intent2TransferAccountActivity(String toId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), TransferAccountActivity.class);
        intent.putExtra(MKey.USER_ID, toId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2RedPackageOpenActivity(GetRedpackageBean getRedpackageBean) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), RedPageResultActivity.class);
        intent.putExtra(MKey.DATA, getRedpackageBean);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2ContactsDetailActivity(String userId, ContactsDetailType contactsDetailType) {
        if (UserService.getInstance().getUserId().equals(userId)) {
            return;
        }
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ContactsDetailActivity.class);
        intent.putExtra(MKey.USER_ID, userId);
        intent.putExtra(MKey.TYPE, contactsDetailType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2ContactsDetailActivity(String userId, String groupId,int admin, ContactsDetailType contactsDetailType) {
        if (UserService.getInstance().getUserId().equals(userId)) {
            return;
        }
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ContactsDetailActivity.class);

        intent.putExtra(MKey.USER_ID, userId);
        intent.putExtra(MKey.ADRID, admin);
        intent.putExtra(MKey.ID, groupId);
        intent.putExtra(MKey.TYPE, contactsDetailType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2ChatDetailActivity(String userId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ChatDetailActivity.class);
        intent.putExtra(MKey.USER_ID, userId);
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

    public static void intent2AlterTextActivity(AlterTextType alterTextType, String oldValue) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AlterTextActivity.class);
        intent.putExtra(MKey.TYPE, alterTextType);
        intent.putExtra(MKey.DATA, oldValue);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2AlterTextActivityByContcactsDetail(AlterTextType alterTextType, String oldValue, String userId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AlterTextActivity.class);
        intent.putExtra(MKey.TYPE, alterTextType);
        intent.putExtra(MKey.DATA, oldValue);
        intent.putExtra(MKey.ID, userId);

        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2AlterTextActivityByGroup(AlterTextType alterTextType, String oldValue, String groupId, String userId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AlterTextActivity.class);
        intent.putExtra(MKey.TYPE, alterTextType);
        intent.putExtra(MKey.DATA, oldValue);
        intent.putExtra(MKey.ID, groupId);
        intent.putExtra(MKey.USER_ID, userId);

        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2VerifyCodeActivity(CheckCodeType checkCodeType) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), VerifyCodeActivity.class);
        intent.putExtra(MKey.TYPE, checkCodeType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2AlterPwdActivity(CheckCodeType checkCodeType, String vcode) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AlterPwdActivity.class);
        intent.putExtra(MKey.TYPE, checkCodeType);
        intent.putExtra(MKey.CODE, vcode);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2GroupDetailActivity(String groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), GroupDetailActivity.class);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


    public static void intent2GroupManagerActivity(GroupDetaiInfoBean groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), GroupManagerActivity.class);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2TextActivity(Map map) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), TextActivity.class);
        intent.putExtra(MKey.DATA, (Serializable) map);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2EditTextAreaActivity(String title, String groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), EditTextAreaActivity.class);
        intent.putExtra(MKey.TITLE, title);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2AlterGroupNotiActivity(String title, String groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AlterGroupNotiActivity.class);
        intent.putExtra(MKey.TITLE, title);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2SetGroupManagerActivtiy(GroupDetaiInfoBean groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), SetGroupManagerActivity.class);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2SetGroupForbidRedpackageActivtiy(GroupDetaiInfoBean groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ForbidRedpackageUserActivity.class);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


    public static void intent2AddGroupUserActivtiy(String groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AddGroupUserActivity.class);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2DelGroupUserActivtiy(String groupId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), DelGroupUserActivity.class);
        intent.putExtra(MKey.ID, groupId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2GroupUserListActivity(String groupId, int admin) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), GroupUserListActivity.class);
        intent.putExtra(MKey.ID, groupId);
        intent.putExtra(MKey.TYPE, admin);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2ZhuanzhuangResultActivity(String sum, String desc) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ZhuanZhangResultActivity.class);
        intent.putExtra(MKey.DATA, sum);
        intent.putExtra(MKey.TITLE, desc);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2QRCodeActivity(QRCodeType qrCodeType, String name, String avatar, String sex, String accountId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), QRCodeActivity.class);
        intent.putExtra(MKey.TYPE, qrCodeType);
        intent.putExtra(MKey.NAME, name);
        intent.putExtra(MKey.AVATAR, avatar);
        intent.putExtra(MKey.SEX, sex);
        intent.putExtra(MKey.ID, accountId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


    public static void intent2ComplaintActivity(ComplaintType type, String toId) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ComplaintActivity.class);
        intent.putExtra(MKey.TYPE, type);
        intent.putExtra(MKey.ID, toId);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }


    public static void intent2ChooseSendMingpianActivity(String targetId, SendMingPianType type) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), ChooseContactsSendMingPianActivity.class);
        intent.putExtra(MKey.ID, targetId);
        intent.putExtra(MKey.TYPE, type);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void intent2SearchMessageActivity(SearchMessageType type, String targetId, String targetName) {
        intent = new Intent(MAPP.mapp.getCurrentActivity(), SearchMessageActivity.class);
        intent.putExtra(MKey.ID, targetId);
        intent.putExtra(MKey.TYPE, type);
        intent.putExtra(MKey.NAME, targetName);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

}
