package com.zonghong.redpacket.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.waw.hr.mutils.bean.MingPianBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.SendMingPianType;
import com.zonghong.redpacket.utils.IntentUtils;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class MingpianPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_mingpian);
        return drawable;
    }

    @Override
    public String obtainTitle(Context context) {
        return "\n名片";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        IntentUtils.intent2ChooseSendMingpianActivity(rongExtension.getTargetId(), rongExtension.getConversationType() == Conversation.ConversationType.GROUP ? SendMingPianType.GROUP : SendMingPianType.USER);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
