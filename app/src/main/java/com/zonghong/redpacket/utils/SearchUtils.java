package com.zonghong.redpacket.utils;

import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.BlackListBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.waw.hr.mutils.bean.SearchDialogueListBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class SearchUtils {


    public static List<ContatsChildBean> searchContacts(List<ContatsChildBean> datas, String key) {
        List<ContatsChildBean> result = new ArrayList<>();

        for (ContatsChildBean contatsChildBean : datas) {
            ContatsChildBean resultChildBean = new ContatsChildBean();
            List<ContatsFriendBean> subList = new ArrayList<>();
            for (ContatsFriendBean contatsFriendBean : contatsChildBean.getList()) {
                if (contatsFriendBean.getNick_name().indexOf(key) > -1) {
                    subList.add(contatsFriendBean);
                }
            }
            if (subList.size() > 0) {
                resultChildBean.setList(subList);
                result.add(resultChildBean);
            }
        }
        return result;
    }

    public static List<MyGroupBean> searchMyGroup(List<MyGroupBean> datas, String key) {

        List<MyGroupBean> allDatas = new ArrayList<>();
        allDatas.addAll(datas);

        List<MyGroupBean> result = new ArrayList<>();

        for (MyGroupBean contatsChildBean : allDatas) {
            if (!StringUtils.isEmpty(contatsChildBean.getTitle()) && contatsChildBean.getTitle().indexOf(key) > -1) {
                result.add(contatsChildBean);
            }

        }
        return result;
    }

    public static List<NewFriendListBean> searchNewFriendList(List<NewFriendListBean> datas, String key) {

        List<NewFriendListBean> allDatas = new ArrayList<>();
        allDatas.addAll(datas);

        List<NewFriendListBean> result = new ArrayList<>();

        for (NewFriendListBean contatsChildBean : allDatas) {
            if (!StringUtils.isEmpty(contatsChildBean.getFriend_name()) && contatsChildBean.getFriend_name().indexOf(key) > -1) {
                result.add(contatsChildBean);
            }

        }
        return result;
    }


    public static List<BlackListBean> searchBlackList(List<BlackListBean> datas, String key) {

        List<BlackListBean> allDatas = new ArrayList<>();
        allDatas.addAll(datas);

        List<BlackListBean> result = new ArrayList<>();

        for (BlackListBean contatsChildBean : allDatas) {
            if (!StringUtils.isEmpty(contatsChildBean.getNick_name()) && contatsChildBean.getNick_name().indexOf(key) > -1) {
                result.add(contatsChildBean);
            }

        }
        return result;
    }


    public static void getUserConversationList(List<SearchDialogueListBean.UidBean> uidList, List<SearchDialogueListBean.GidBean> gidList, Consumer<List<Conversation>> consumer) {

        List<Conversation> result = new ArrayList<>();

        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {

                if (conversations == null || conversations.size() == 0) {
                    try {
                        consumer.accept(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    for (Conversation conversation : conversations) {
                        for (SearchDialogueListBean.UidBean uidBean : uidList) {
                            if (conversation.getTargetId().equals(uidBean.getId())) {
                                conversation.setConversationTitle(uidBean.getName());
                                conversation.setPortraitUrl(uidBean.getImg());
                                result.add(conversation);
                            }
                        }
                        for (SearchDialogueListBean.GidBean gidBean : gidList) {
                            if (conversation.getTargetId().equals(gidBean.getId())) {
                                conversation.setConversationTitle(gidBean.getName());
                                conversation.setPortraitUrl(gidBean.getPhoto());
                                result.add(conversation);
                            }

                        }
                    }
                }


                try {
                    consumer.accept(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP);


    }


}
