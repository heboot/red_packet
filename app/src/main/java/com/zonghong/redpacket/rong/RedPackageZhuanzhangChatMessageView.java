package com.zonghong.redpacket.rong;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.MessageRedpackageChatBinding;
import com.zonghong.redpacket.databinding.MessageRedpackageZhuanzhangBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.view.RedPackageDialog;

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
        messageRedpackageChatBinding.tvType.setText("转账");
        String s = new String(messageContent.encode());
        CreateRedPackageChildBean createRedPackageChildBean = JSON.parseObject(s, CreateRedPackageChildBean.class);
        if (uiMessage.getTargetId().equals(UserService.getInstance().getUserId())) {
            messageRedpackageChatBinding.tvDes.setText(createRedPackageChildBean.getNoUserContent());
        } else {
            messageRedpackageChatBinding.tvDes.setText(createRedPackageChildBean.getUserContent());
        }
    }

    @Override
    public Spannable getContentSummary(RedPackageZhuanZhangChatMessage messageContent) {
        return new SpannableString("[转账消息]");
    }

    @Override
    public void onItemClick(View view, int i, RedPackageZhuanZhangChatMessage messageContent, UIMessage uiMessage) {



        String s = new String(messageContent.encode());
        CreateRedPackageChildBean createRedPackageChildBean = JSON.parseObject(s, CreateRedPackageChildBean.class);
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
