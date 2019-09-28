package com.zonghong.redpacket.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.user.AlterPayPwdActivity;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

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

        // TODO: 2019-09-11  去发红包页面
//        LogUtil.e("hongbao", JSON.toJSONString(rongExtension));
        IntentUtils.intent2NewRedPackageActivity(rongExtension.getConversationType() == Conversation.ConversationType.GROUP ? RedPackageType.GROUP : RedPackageType.CHAT, rongExtension.getTargetId());
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
