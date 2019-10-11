package com.waw.hr.mutils.event;

import android.graphics.drawable.Drawable;

import com.waw.hr.mutils.bean.CustomBiaoqingListBean;

public class MessageEvent {

    public static class GET_UNREAD_MESSAGE_NUM_EVENT {

        private int unreadNum;

        public GET_UNREAD_MESSAGE_NUM_EVENT(int unreadNum) {
            this.unreadNum = unreadNum;
        }

        public int getUnreadNum() {
            return unreadNum;
        }
    }

    public static class SEND_CUSTOM_BIAOQING_EVENT {
        private CustomBiaoqingListBean.ExpressionListBean expressionListBean;
        private Drawable drawable;
        public SEND_CUSTOM_BIAOQING_EVENT(CustomBiaoqingListBean.ExpressionListBean expressionListBean,Drawable drawable) {
            this.expressionListBean = expressionListBean;
            this.drawable = drawable;
        }

        public CustomBiaoqingListBean.ExpressionListBean getExpressionListBean() {
            return expressionListBean;
        }

        public Drawable getDrawable() {
            return drawable;
        }
    }


}
