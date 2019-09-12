package com.zonghong.redpacket.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.utils.IntentUtils;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class RedpackagePlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.icon_plugin_redpackage);
        return drawable;
    }

    @Override
    public String obtainTitle(Context context) {
        return "\n发红包";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        // TODO: 2019-09-11  去发红包页面
//        LogUtil.e("hongbao", JSON.toJSONString(rongExtension));
        IntentUtils.intent2NewRedPackageActivity(rongExtension.getConversationType() == Conversation.ConversationType.GROUP ? RedPackageType.GROUP : RedPackageType.CHAT, rongExtension.getTargetId());
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
