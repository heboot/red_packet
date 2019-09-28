package com.zonghong.redpacket.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.zonghong.redpacket.R;

public class MMMV extends LinearLayout implements QMUIPullRefreshLayout.IRefreshView {


    public MMMV(Context context) {
        super(context);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_refresh, this);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        ProgressBar pb = view.findViewById(R.id.pb);
        ColorStateList colorStateList = ColorStateList.valueOf(0xffF8D347);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pb.setIndeterminateTintList(colorStateList);
            pb.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
        }


    }

    public MMMV(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.view_refresh, this);
    }

    public MMMV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.view_refresh, this);
    }
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            setMeasuredDimension(mCircleDiameter, mCircleDiameter);
//        }


    @Override
    public void stop() {
    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void onPull(int offset, int total, int overPull) {

    }

}

