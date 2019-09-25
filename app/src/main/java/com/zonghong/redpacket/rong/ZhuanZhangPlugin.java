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
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class ZhuanZhangPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_send_package);
        return drawable;
    }

    @Override
    public String obtainTitle(Context context) {
        return "\n转账";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        // TODO: 2019-09-11  去转账页面
//        LogUtil.e("hongbao", JSON.toJSONString(rongExtension));
        IntentUtils.intent2TransferAccountActivity(rongExtension.getTargetId());
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
