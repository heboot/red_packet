package com.zonghong.redpacket.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.user.AlterPayPwdActivity;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.common.SendMingPianType;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class RedpackagePlugin implements IPluginModule {

    private QMUIDialog qmuiDialog;

    @Override
    public Drawable obtainDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_send_package);
        return drawable;
    }

    @Override
    public String obtainTitle(Context context) {
        return "\n发红包";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        if (UserService.getInstance().getUserInfoBean().getSign() == 0) {
            if (qmuiDialog == null) {
                qmuiDialog = new QMUIDialog.MessageDialogBuilder(MAPP.mapp.getCurrentActivity())
                        .setMessage("设置支付密码后才发红包")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                qmuiDialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                qmuiDialog.dismiss();
                                IntentUtils.doIntent(AlterPayPwdActivity.class);
                            }
                        })
                        .create();
            }
            qmuiDialog.show();
            return;
        }
        if (rongExtension.getConversationType() == Conversation.ConversationType.GROUP) {
            complaint(rongExtension.getTargetId(), rongExtension);
        } else {
            IntentUtils.intent2NewRedPackageActivity(rongExtension.getConversationType() == Conversation.ConversationType.GROUP ? RedPackageType.GROUP : RedPackageType.CHAT, rongExtension.getTargetId());
        }

    }


    private void complaint(String groupId, RongExtension rongExtension) {
        Map params = new HashMap();
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().verifyGroupStatus(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {

                if ((double) baseBean.getData().get("bannet_total") == 1) {
                    ToastUtils.show(MAPP.mapp, (String) baseBean.getData().get("content"));
                } else if ((double) baseBean.getData().get("user_bannet") == 1) {
                    ToastUtils.show(MAPP.mapp, (String) baseBean.getData().get("content"));
                } else {
                    IntentUtils.intent2NewRedPackageActivity(rongExtension.getConversationType() == Conversation.ConversationType.GROUP ? RedPackageType.GROUP : RedPackageType.CHAT, rongExtension.getTargetId());
                }
            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
