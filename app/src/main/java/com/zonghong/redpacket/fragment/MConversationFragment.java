package com.zonghong.redpacket.fragment;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.adapter.MMessageListAdapter;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.model.Conversation;

public class MConversationFragment extends ConversationFragment {

    private double total, user;

    private boolean isFirst = true;

    private String msg;

//    @Override
//    public MessageListAdapter getMessageAdapter() {
//        return new MMessageListAdapter(getContext());
//    }


    @Override
    public MessageListAdapter onResolveAdapter(Context context) {
        return new MMessageListAdapter(getContext());
    }

    @Override
    public boolean showMoreClickItem() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getConversationType() == Conversation.ConversationType.GROUP){
            complaint("null", null, null, null, false);
        }

    }

    @Override
    public void onSendToggleClick(View v, String text) {
//        super.onSendToggleClick(v, text);
        if(getConversationType() == Conversation.ConversationType.GROUP){
            complaint("onSendToggleClick", v, text, null, false);
        }else{
            super.onSendToggleClick(v, text);
        }

    }


    @Override
    public void onVoiceInputToggleTouch(View v, MotionEvent event) {
        if(getConversationType() == Conversation.ConversationType.GROUP){
            if (total == 1 || user == 1) {
                ToastUtils.show(MAPP.mapp, msg);
                return;
            }
        }
        super.onVoiceInputToggleTouch(v, event);
    }

//    @Override
//    public void onPluginClicked(IPluginModule pluginModule, int position) {
//        if(getConversationType() == Conversation.ConversationType.GROUP){
//            if (total == 1 || user == 1) {
//                ToastUtils.show(MAPP.mapp, msg);
//                return;
//            }
//        }
//        super.onPluginClicked(pluginModule, position);
//    }

    private void complaint(String action, View view, String text, LinkedHashMap<String, Integer> selectedMedias, boolean origin) {
        Map params = new HashMap();
        params.put("group_id", getTargetId());
        HttpClient.Builder.getServer().verifyGroupStatus(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {
                total = (double) baseBean.getData().get("bannet_total");
                user = (double) baseBean.getData().get("user_bannet");
                msg = (String) baseBean.getData().get("content");
                if ((double) baseBean.getData().get("bannet_total") == 1 || (double) baseBean.getData().get("user_bannet") == 1) {
                    if (!isFirst) {
                        ToastUtils.show(MAPP.mapp, msg);

                    }  isFirst = false;
                } else {
                    switch (action) {
                        case "onSendToggleClick":
                            MConversationFragment.super.onSendToggleClick(view, text);
                            break;
                        case "onImageResult":
                            MConversationFragment.super.onImageResult(selectedMedias, origin);
                            break;
                    }
                }
            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }
}
