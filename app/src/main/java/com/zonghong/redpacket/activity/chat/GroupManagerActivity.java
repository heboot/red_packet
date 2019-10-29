package com.zonghong.redpacket.activity.chat;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.IntiveUserBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.InviteGroupUserAdapter;
import com.zonghong.redpacket.adapter.group.MyGroupAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityGroupDetailBinding;
import com.zonghong.redpacket.databinding.ActivityGroupManagerBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupManagerActivity extends BaseActivity<ActivityGroupManagerBinding> {

    private GroupDetaiInfoBean groupDetaiInfoBean;

    private InviteGroupUserAdapter inviteGroupUserAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_manager;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("群管理");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    public void initData() {
        groupDetaiInfoBean = (GroupDetaiInfoBean) getIntent().getExtras().get(MKey.ID);

        binding.sbBanned.setCheckedNoEvent(groupDetaiInfoBean.getGroupInfo().getBannet_total() == 1 ? true : false);
        binding.sbJoinConfirm.setCheckedNoEvent(groupDetaiInfoBean.getGroupInfo().getInvite_confirm() == 1?true:false);
        if (groupDetaiInfoBean.getMyGInfo().getAdmin() == 3) {
            binding.tvVerify.setVisibility(View.VISIBLE);
            binding.rvList.setVisibility(View.VISIBLE);
            waitConsent();
        } else {
            binding.tvVerify.setVisibility(View.GONE);
            binding.rvList.setVisibility(View.GONE);
        }


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
        binding.tvForbidSet.setOnClickListener(view -> {
            IntentUtils.intent2SetGroupForbidRedpackageActivtiy(groupDetaiInfoBean);
        });

    }


    private void waitConsent() {
        params = new HashMap<>();
        params.put("group_id", groupDetaiInfoBean.getGroupInfo().getID() + "");
        HttpClient.Builder.getServer().waitConsent(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<IntiveUserBean.ListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<IntiveUserBean.ListBean>> baseBean) {
                if (baseBean.getData() == null || baseBean.getData().size() == 0) {
                    return;
                }
                if (inviteGroupUserAdapter == null) {
                    if (baseBean.getData() != null) {
                        inviteGroupUserAdapter = new InviteGroupUserAdapter(baseBean.getData(), new WeakReference<>(GroupManagerActivity.this));
                        binding.rvList.setAdapter(inviteGroupUserAdapter);
                    }
                } else {
                    inviteGroupUserAdapter.setNewData(baseBean.getData());
                }
//                tipDialog = DialogUtils.getSuclDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<List<IntiveUserBean.ListBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void upInvite() {
        params = new HashMap<>();
        params.put("group_id", groupDetaiInfoBean.getGroupInfo().getID() + "");
        params.put("invite_confirm", binding.sbJoinConfirm.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().upInvite(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
//                tipDialog = DialogUtils.getSuclDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
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
//                tipDialog = DialogUtils.getSuclDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void executeOperation(String userId, int op) {
        params = new HashMap<>();
        params.put("group_id", groupDetaiInfoBean.getGroupInfo().getID() + "");
        params.put("wait_user_id", userId);
        params.put("res", op);
        HttpClient.Builder.getServer().executeOperation(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
//                tipDialog = DialogUtils.getSuclDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupManagerActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


}
