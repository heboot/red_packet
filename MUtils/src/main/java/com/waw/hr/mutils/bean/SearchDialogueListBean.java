package com.waw.hr.mutils.bean;

import java.util.List;

public class SearchDialogueListBean {
    private List<UidBean> uid;
    private List<GidBean> gid;

    public List<UidBean> getUid() {
        return uid;
    }

    public void setUid(List<UidBean> uid) {
        this.uid = uid;
    }

    public List<GidBean> getGid() {
        return gid;
    }

    public void setGid(List<GidBean> gid) {
        this.gid = gid;
    }

    public static class UidBean {
        /**
         * id : 88
         * name : 花开富贵
         */

        private String id;
        private String name;
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class GidBean {
        /**
         * id : 403
         * name : 花开富贵的群聊
         */

        private String id;
        private String name;
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
