package com.zonghong.redpacket.rong;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.model.ZhuanZhangModel;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.MessageRedpackageZhuanzhangBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.NumberUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

@ProviderTag(messageContent = RedPackageZhuanZhangChatMessage.class, showReadState = true)
public class RedPackageZhuanzhangChatMessageView extends IContainerItemProvider.MessageProvider<RedPackageZhuanZhangChatMessage> {


    @Override
    public void bindView(View view, int i, RedPackageZhuanZhangChatMessage messageContent, UIMessage uiMessage) {
        MessageRedpackageZhuanzhangBinding messageRedpackageChatBinding = DataBindingUtil.bind(view);

//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
//            messageRedpackageChatBinding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_right);
//        } else {
//            messageRedpackageChatBinding.getRoot().setBackgroundResource(R.drawable.rc_ic_bubble_no_left);
//        }

        messageRedpackageChatBinding.tvType.setText("转账");
        String s = new String(messageContent.encode());
        ZhuanZhangModel createRedPackageChildBean = JSON.parseObject(s, ZhuanZhangModel.class);
        if (uiMessage.getTargetId().equals(UserService.getInstance().getUserId())) {
            messageRedpackageChatBinding.tvDesc.setText(createRedPackageChildBean.getNoUserContent());
        } else {
            messageRedpackageChatBinding.tvDesc.setText(createRedPackageChildBean.getUserContent());
        }

//        messageRedpackageChatBinding.tvDes.setText(NumberUtils.formatDouble(Double.parseDouble(createRedPackageChildBean.getMoneys())));
    }

    @Override
    public Spannable getContentSummary(RedPackageZhuanZhangChatMessage messageContent) {
        return new SpannableString("[转账消息]");
    }

    @Override
    public void onItemClick(View view, int i, RedPackageZhuanZhangChatMessage messageContent, UIMessage uiMessage) {


        String s = new String(messageContent.encode());
        ZhuanZhangModel createRedPackageChildBean = JSON.parseObject(s, ZhuanZhangModel.class);
        if (uiMessage.getTargetId().equals(UserService.getInstance().getUserId())) {
            return;
        }
        getMoney(createRedPackageChildBean.getID() + "", createRedPackageChildBean.getUserContent());
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_redpackage_zhuanzhang, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x231), MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y90)));
        return view;
    }

    private void getMoney(String redId, String desc) {
        Map params = new HashMap<>();
        params.put("red_id", redId);
        HttpClient.Builder.getServer().bGetTransfer(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {
                Toast.makeText(MAPP.mapp, baseBean.getMsg(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                Toast.makeText(MAPP.mapp, baseBean.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
