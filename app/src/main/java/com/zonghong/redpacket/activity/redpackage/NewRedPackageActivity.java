package com.zonghong.redpacket.activity.redpackage;

import android.view.View;

import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.databinding.ActivityNewRedPackgeBinding;
import com.zonghong.redpacket.utils.MoneyTextWatcher;
import com.zonghong.redpacket.view.PayDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewRedPackageActivity extends BaseActivity<ActivityNewRedPackgeBinding> {

    private RedPackageType type;

    private String userId, groupId;

    private PayDialog payDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_red_packge;
    }

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("发红包");
    }

    @Override
    public void initData() {
        type = (RedPackageType) getIntent().getExtras().get(MKey.TYPE);
        if (type == RedPackageType.CHAT) {
            userId = (String) getIntent().getExtras().get(MKey.GET_USER);
            binding.etNum.setVisibility(View.GONE);
            binding.tvNum.setVisibility(View.GONE);
            binding.tvGroupNums.setVisibility(View.GONE);
        } else {
            groupId = (String) getIntent().getExtras().get(MKey.ID);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent.SEND_REDPACKAGE_EVENT event) {
        finish();
    }

    @Override
    public void initListener() {
//        binding.etMoney.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.tvMoney.setText(charSequence);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        binding.etMoney.addTextChangedListener(new MoneyTextWatcher(binding.etMoney, binding.tvMoney).setDigits(2));
        binding.tvSubmit.setOnClickListener((v) -> {
            if (StringUtils.isEmpty(binding.etMoney.getText())) {
                tipDialog = DialogUtils.getFailDialog(this, "请输入金额", true);
                tipDialog.show();
                return;
            }
            if (type != RedPackageType.CHAT) {
                if (StringUtils.isEmpty(binding.etNum.getText())) {
                    tipDialog = DialogUtils.getFailDialog(this, "请输入个数", true);
                    tipDialog.show();
                    return;
                }
            }

            payDialog = PayDialog.newRedPackageInstance(PayDialogType.REDPACKAGE, Float.parseFloat((String) binding.etMoney.getText().toString()), type == RedPackageType.GROUP ? binding.etNum.getText().toString() : "1", type == RedPackageType.GROUP ? groupId : userId, type, binding.etRemark.getText().toString());
            payDialog.show(getSupportFragmentManager(), "");

        });
    }


}
