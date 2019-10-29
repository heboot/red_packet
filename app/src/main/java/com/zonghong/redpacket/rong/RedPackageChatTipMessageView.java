package com.zonghong.redpacket.rong;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.waw.hr.mutils.bean.GetRedpackageBean;
import com.waw.hr.mutils.bean.GetRedpackageModel;
import com.waw.hr.mutils.bean.RedpackageTipBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.databinding.MessageRedpackageChatBinding;
import com.zonghong.redpacket.databinding.MessageRedpackageOpenBinding;
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
import io.rong.imkit.utils.NotificationUtil;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation;

@ProviderTag(messageContent = RedPackageChatOpenMessage.class, showSummaryWithName = false, showPortrait = false, centerInHorizontal = true, showReadState = true)
public class RedPackageChatTipMessageView extends IContainerItemProvider.MessageProvider<RedPackageChatOpenMessage> {


    @Override
    public void bindView(View view, int i, RedPackageChatOpenMessage messageContent, UIMessage uiMessage) {
        MessageRedpackageOpenBinding messageRedpackageChatBinding = DataBindingUtil.bind(view);
        String s = new String(messageContent.encode());
        RedpackageTipBean redpackageTipBean = JSON.parseObject(s, RedpackageTipBean.class);
        messageRedpackageChatBinding.tvTip.setText(redpackageTipBean.getMessage());
        if (redpackageTipBean.getTargetId().equals(UserService.getInstance().getUserId())) {
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y25)));
        } else {
            view.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
        }
    }

    @Override
    public Spannable getContentSummary(RedPackageChatOpenMessage messageContent) {
        return new SpannableString("");
    }

    @Override
    public void onItemClick(View view, int i, RedPackageChatOpenMessage messageContent, UIMessage uiMessage) {
        MessageRedpackageOpenBinding messageRedpackageChatBinding = DataBindingUtil.bind(view);
        String s = new String(messageContent.encode());
        RedpackageTipBean redpackageTipBean = JSON.parseObject(s, RedpackageTipBean.class);
        messageRedpackageChatBinding.getRoot().setOnClickListener(v -> {

                checkredpackage(redpackageTipBean.getRedId(), uiMessage);

        });
    }

    private void checkredpackage(String redId, UIMessage uiMessage) {
        Map params = new HashMap<>();
        params.put("red_id", redId);
        HttpClient.Builder.getServer().tVerify(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Integer>() {
            @Override
            public void onSuccess(BaseBean<Integer> baseBean) {
                openPackage(redId,baseBean.getData());
            }

            @Override
            public void onError(BaseBean<Integer> baseBean) {
                Toast.makeText(MAPP.mapp, baseBean.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void openPackage(String redId, int status) {
        Map params = new HashMap<>();
        params.put("red_id", redId);
        params.put("show", 1);
        HttpClient.Builder.getServer().tGetMoney(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GetRedpackageBean>() {
            @Override
            public void onSuccess(BaseBean<GetRedpackageBean> baseBean) {
                if (baseBean.getData() instanceof GetRedpackageBean) {
                    IntentUtils.intent2RedPackageOpenActivity(baseBean.getData(), status);
                }
            }

            @Override
            public void onError(BaseBean<GetRedpackageBean> baseBean) {
                Toast.makeText(MAPP.mapp, baseBean.getMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemLongClick(View view, int position, RedPackageChatOpenMessage content, UIMessage message) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_redpackage_open, null);
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.y25)));
        return view;
    }


}
