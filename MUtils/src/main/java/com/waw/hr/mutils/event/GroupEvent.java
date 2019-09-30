package com.waw.hr.mutils.event;

public class GroupEvent {

    public static class CREATE_GROUP_SUC_EVENT {
    }

    public static class EXIT_GROUP_EVENT {

    }

    public static class DELETE_GROUP_MESSAGE_EVENT {
        private String groupId;

        public DELETE_GROUP_MESSAGE_EVENT(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupId() {
            return groupId;
        }
    }

}
