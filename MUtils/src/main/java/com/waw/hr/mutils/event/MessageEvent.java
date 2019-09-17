package com.waw.hr.mutils.event;

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

}
