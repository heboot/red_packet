package com.zonghong.redpacket.activity.common;

import android.view.View;
import android.widget.CompoundButton;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.PhoneContactsActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityNotiSetBinding;
import com.zonghong.redpacket.service.UserService;

public class NotiSetActivity extends BaseActivity<ActivityNotiSetBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_noti_set;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("消息通知");
    }

    @Override
    public void initData() {
        if (UserService.getInstance().isTixing()) {
            binding.sbJoinConfirm.setCheckedNoEvent(true);
        } else {
            binding.sbJoinConfirm.setCheckedNoEvent(false);
        }

        if (UserService.getInstance().isShengyin()) {
            binding.sbVoice.setCheckedNoEvent(true);
        } else {
            binding.sbVoice.setCheckedNoEvent(false);
        }

        if (UserService.getInstance().isZhendong()) {
            binding.sbZhendong.setCheckedNoEvent(true);
        } else {
            binding.sbZhendong.setCheckedNoEvent(false);
        }
    }

    @Override
    public void initListener() {
        binding.sbJoinConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserService.getInstance().setTixing(isChecked);
            }
        });
        binding.sbVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserService.getInstance().setShengyin(isChecked);
            }
        });
        binding.sbZhendong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserService.getInstance().setZhendong(isChecked);
            }
        });
    }
}
