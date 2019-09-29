package com.zonghong.redpacket.activity.chat;

import android.view.View;
import android.widget.CompoundButton;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityGroupDetailBinding;
import com.zonghong.redpacket.databinding.ActivityGroupManagerBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupManagerActivity extends BaseActivity<ActivityGroupManagerBinding> {

    private GroupDetaiInfoBean groupDetaiInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_manager;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("群管理");
    }

    @Override
    public void initData() {
        groupDetaiInfoBean = (GroupDetaiInfoBean) getIntent().getExtras().get(MKey.ID);


        binding.sbBanned.setCheckedNoEvent(groupDetaiInfoBean.getGroupInfo().getBannet_chat() == 1 ? true : false);

    }

    @Override
    public void initListener() {
        binding.sbBanned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                upAdmInavite();
            }
        });
        binding.sbJoinConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                upInvite();
            }
        });
        binding.tvManagerSet.setOnClickListener((v) -> {
            IntentUtils.intent2SetGroupManagerActivtiy(groupDetaiInfoBean);
        });
    }


    private void upInvite() {
        params = new HashMap<>();
        params.put("group_id", groupDetaiInfoBean.getGroupInfo().getID() + "");
        params.put("invite_confirm", binding.sbJoinConfirm.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().upInvite(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void upAdmInavite() {
        params = new HashMap<>();
        params.put("group_id", groupDetaiInfoBean.getGroupInfo().getID());
        params.put("bannet_total", binding.sbBanned.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().upBannet(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
