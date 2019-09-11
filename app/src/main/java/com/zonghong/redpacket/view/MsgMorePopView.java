package com.zonghong.redpacket.view;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.LayoutMsgMoreBinding;

public class MsgMorePopView extends PopupWindow {

    public MsgMorePopView() {
        LayoutMsgMoreBinding layoutMsgMoreBinding = DataBindingUtil.inflate(LayoutInflater.from(MAPP.mapp), R.layout.layout_msg_more, null, false);
        setWidth(MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x160));
        setHeight(MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y168));
//        setFocusable(true);
        setOutsideTouchable(true);
//        setTouchable(true);
        setContentView(layoutMsgMoreBinding.getRoot());
    }

}
