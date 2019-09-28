package com.zonghong.redpacket.activity.common;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.MStatusBarUtils;
import com.waw.hr.mutils.ObserableUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.LoginActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoadingActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    public void initUI() {
        MStatusBarUtils.setActivityLightMode(this);
        QMUIStatusBarHelper.translucent(this);
        ObserableUtils.countdown(3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer == 0) {
                    if (!StringUtils.isEmpty(UserService.getInstance().getToken()) && !StringUtils.isEmpty(UserService.getInstance().getRy_token())) {
                        RongUtils.connect(UserService.getInstance().getRy_token());
                    } else {
                        IntentUtils.doIntent(LoginActivity.class);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent.LOGIN_SUC_EVENT event) {
        LogUtil.e(TAG, "我要死了");
        finish();
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initListener() {

    }
}
