package com.zonghong.redpacket.adapter.message;

import android.content.Context;

import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.model.Message;

public class MMessageListAdapter extends MessageListAdapter {
    public MMessageListAdapter(Context context) {
        super(context);
    }

    @Override
    protected boolean allowShowCheckButton(Message message) {
        return super.allowShowCheckButton(message);
    }
}
