package com.zonghong.redpacket.adapter;

import android.content.Context;

import com.zonghong.redpacket.rong.RedPackageChatOpenMessage;

import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.model.Message;

public class MMessageListAdapter extends MessageListAdapter {
    public MMessageListAdapter(Context context) {
        super(context);
    }

    @Override
    protected boolean allowShowCheckButton(Message message) {
        if (message.getContent() instanceof RedPackageChatOpenMessage  ) {
            return false;
        }
        return super.allowShowCheckButton(message);
    }
}
