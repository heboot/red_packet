package com.zonghong.redpacket.rong;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.MessageRedpackageChatBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.view.RedPackageDialog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.utils.BitmapUtil;

@ProviderTag(messageContent = RedPackageChatMessage.class, showReadState = true)
public class RedPackageChatMessageView extends IContainerItemProvider.MessageProvider<RedPackageChatMessage> {


    @Override
    public void bindView(View view, int i, RedPackageChatMessage messageContent, UIMessage uiMessage) {
        MessageRedpackageChatBinding messageRedpackageChatBinding = DataBindingUtil.bind(view);
//
//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
//            messageRedpackageChatBinding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_right);
//        } else {
//            messageRedpackageChatBinding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_left);
//        }


        String s = new String(messageContent.encode());
        CreateRedPackageChildBean createRedPackageChildBean = JSON.parseObject(s, CreateRedPackageChildBean.class);
//
//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
//            messageRedpackageChatBinding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_right);
//        } else {
//            messageRedpackageChatBinding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_left);
//        }


        messageRedpackageChatBinding.tvDes.setText(createRedPackageChildBean.getDesc());
    }

    @Override
    public Spannable getContentSummary(RedPackageChatMessage messageContent) {
        return new SpannableString("[红包消息]");
    }


    @Override
    public void onItemClick(View view, int i, RedPackageChatMessage messageContent, UIMessage uiMessage) {
        // TODO: 2019-09-11 抢红包
        String s = new String(messageContent.encode());
        LogUtil.e("解析红包消息", s);
        CreateRedPackageChildBean createRedPackageChildBean = JSON.parseObject(s, CreateRedPackageChildBean.class);

        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
            if (uiMessage.getSenderUserId().equals(UserService.getInstance().getUserId())) {
                ToastUtils.show(MAPP.mapp, "不能领取自己的红包");
                return;
            }
        }

        checkredpackage(String.valueOf(createRedPackageChildBean.getID()), uiMessage, createRedPackageChildBean);
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_redpackage_chat, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x231), MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y90)));
        return view;
    }

    private void checkredpackage(String redId, UIMessage uiMessage, CreateRedPackageChildBean createRedPackageChildBean) {
        Map params = new HashMap<>();
        params.put("red_id", redId);
        HttpClient.Builder.getServer().tVerify(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Integer>() {
            @Override
            public void onSuccess(BaseBean<Integer> baseBean) {
                if (uiMessage.getConversationType() == Conversation.ConversationType.GROUP) {

                    if (baseBean.getData() == 100 || baseBean.getData() == 101) {
                        RedPackageDialog redPackageDialog = RedPackageDialog.newInstance(String.valueOf(createRedPackageChildBean.getID()), uiMessage.getConversationType(), MAPP.mapp.getCurrentConversationId(), createRedPackageChildBean, baseBean.getData());
                        redPackageDialog.show(((FragmentActivity) MAPP.mapp.getCurrentActivity()).getSupportFragmentManager(), "");
                    } else {
                        openPackage(redId, baseBean.getData(),false);
                    }


                } else {
                    if (baseBean.getData() != null && baseBean.getData() == 10) {
                        openPackage(redId, baseBean.getData(),false);
                    } else {
                        RedPackageDialog redPackageDialog = RedPackageDialog.newInstance(String.valueOf(createRedPackageChildBean.getID()), uiMessage.getConversationType(), MAPP.mapp.getCurrentConversationId(), createRedPackageChildBean, baseBean.getData());
                        redPackageDialog.show(((FragmentActivity) MAPP.mapp.getCurrentActivity()).getSupportFragmentManager(), "");
                    }
                }
            }

            @Override
            public void onError(BaseBean<Integer> baseBean) {
                Toast.makeText(MAPP.mapp, baseBean.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openPackage(String redId, int status,boolean show) {
        Map params = new HashMap<>();
        params.put("red_id", redId);
        HttpClient.Builder.getServer().tGetMoney(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GetRedpackageBean>() {
            @Override
            public void onSuccess(BaseBean<GetRedpackageBean> baseBean) {
                if (baseBean.getData() instanceof GetRedpackageBean) {
                    GetRedpackageModel getRedpackageModel = new GetRedpackageModel();
//                    RongUtils.sendRedPackageOpenMessage(baseBean.getData().getGetUsersentence(),to,conversationType);
                    IntentUtils.intent2RedPackageOpenActivity(baseBean.getData(),status);

                }
//                GetRedpackageBean

            }

            @Override
            public void onError(BaseBean<GetRedpackageBean> baseBean) {
                Toast.makeText(MAPP.mapp, baseBean.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
