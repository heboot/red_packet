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
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.waw.hr.mutils.bean.MingPianBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.MessageRedpackageChatBinding;
import com.zonghong.redpacket.databinding.MessageRedpackageMingpianBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.view.RedPackageDialog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

@ProviderTag(messageContent = MingPianMessage.class, showReadState = true)
public class MingPianMessageView extends IContainerItemProvider.MessageProvider<MingPianMessage> {


    @Override
    public void bindView(View view, int i, MingPianMessage messageContent, UIMessage uiMessage) {
        MessageRedpackageMingpianBinding messageRedpackageChatBinding = DataBindingUtil.bind(view);
        String s = new String(messageContent.encode());
        MingPianBean createRedPackageChildBean = JSON.parseObject(s, MingPianBean.class);
        messageRedpackageChatBinding.tvName.setText(createRedPackageChildBean.getCardName());
        messageRedpackageChatBinding.tvNo.setText(createRedPackageChildBean.getAccountId());
        ImageUtils.showAvatar(createRedPackageChildBean.getCardImg(), messageRedpackageChatBinding.ivAvatar);
    }

    @Override
    public Spannable getContentSummary(MingPianMessage messageContent) {
        return new SpannableString("[名片消息]");
    }

    @Override
    public void onItemClick(View view, int i, MingPianMessage messageContent, UIMessage uiMessage) {
        String s = new String(messageContent.encode());
        MingPianBean createRedPackageChildBean = JSON.parseObject(s, MingPianBean.class);
        IntentUtils.intent2ContactsDetailActivity(createRedPackageChildBean.getCardId(), ContactsDetailType.NORMAL);
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_redpackage_mingpian, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x231), MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y90)));
        return view;
    }


}
