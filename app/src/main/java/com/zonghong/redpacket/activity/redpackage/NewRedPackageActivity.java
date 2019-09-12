package com.zonghong.redpacket.activity.redpackage;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.CreateRedPackageBean;
import com.waw.hr.mutils.bean.CreateRedPackageChildBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsActivity;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.RedPackageType;
import com.zonghong.redpacket.databinding.ActivityNewRedPackgeBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewRedPackageActivity extends BaseActivity<ActivityNewRedPackgeBinding> {

    private RedPackageType type;

    private String userId, groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_red_packge;
    }

    @Override
    public void initUI() {
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

    @Override
    public void initListener() {
        binding.etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvMoney.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.tvSubmit.setOnClickListener((v) -> {
            if (StringUtils.isEmpty(binding.etMoney.getText())) {
                tipDialog = DialogUtils.getFailDialog(this, "请输入金额", true);
                tipDialog.show();
                return;
            }
            createRedpackage();
        });
    }


    private void createRedpackage() {
        params.put("sum", binding.tvMoney.getText());
        params.put("number", type == RedPackageType.CHAT ? 1 : binding.etNum.getText());
        if (type == RedPackageType.CHAT) {
            params.put("get_user", userId);
        } else {
            params.put("group_id", groupId);
        }
        HttpClient.Builder.getServer().tCreate(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<CreateRedPackageChildBean>() {
            @Override
            public void onSuccess(BaseBean<CreateRedPackageChildBean> baseBean) {
                if (type == RedPackageType.CHAT) {
                    baseBean.getData().setUser_id(String.valueOf(userId));
                    baseBean.getData().setFrom_id(String.valueOf(userId));
                } else {
                    baseBean.getData().setGroup_id(String.valueOf(groupId));
                    baseBean.getData().setFrom_id(String.valueOf(groupId));
                }

                RongUtils.sendRedPackageMessage(baseBean.getData());
                finish();
            }

            @Override
            public void onError(BaseBean<CreateRedPackageChildBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(NewRedPackageActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
