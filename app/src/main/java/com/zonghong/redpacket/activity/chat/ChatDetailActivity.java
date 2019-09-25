package com.zonghong.redpacket.activity.chat;

import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ActivityChatDetailBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatDetailActivity extends BaseActivity<ActivityChatDetailBinding> {

    private String userId;

    private UserInfoBean userInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("聊天详情");
    }

    @Override
    public void initData() {
        userId = (String) getIntent().getExtras().get(MKey.USER_ID);
        userInfo();
    }

    @Override
    public void initListener() {
        binding.ivAvatar.setOnClickListener((v) -> {
            IntentUtils.intent2ContactsDetailActivity(userId, ContactsDetailType.NORMAL);
        });
    }


    private void userInfo() {
        Map params = new HashMap<>();
        params.put(MKey.ID, userId);
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
//                userInfo = new UserInfo(s, baseBean.getData().get(0).getNick_name(), Uri.parse(baseBean.getData().get(0).getImage()));
//                userInfoBean = baseBean.getData();
                ImageUtils.showAvatar(baseBean.getData().getImg(), binding.ivAvatar);
                binding.tvName.setText(baseBean.getData().getNick_name());
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {

            }
        });
    }
}
