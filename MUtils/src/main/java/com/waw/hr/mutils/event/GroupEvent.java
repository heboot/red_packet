package com.waw.hr.mutils.event;

public class GroupEvent {

    public static class CREATE_GROUP_SUC_EVENT {
    }

    public static class EXIT_GROUP_EVENT {

    }

    public static class ALTER_GROUP_NAME_EVENT{
        private String name;

        public String getName() {
            return name;
        }

        public ALTER_GROUP_NAME_EVENT(String name) {
            this.name = name;
        }
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
