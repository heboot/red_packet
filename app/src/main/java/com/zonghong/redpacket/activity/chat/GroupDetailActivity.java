package com.zonghong.redpacket.activity.chat;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.CompoundButton;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.GroupUserAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.databinding.ActivityGroupDetailBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.model.Conversation;

public class GroupDetailActivity extends BaseActivity<ActivityGroupDetailBinding> {

    private String groupId;

    private GroupUserAdapter groupUserAdapter;

    private GroupDetaiInfoBean groupDetaiInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("聊天详情");
        binding.rvList.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    public void initData() {
        groupId = getIntent().getExtras().getString(MKey.ID);
        gInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (groupUserAdapter != null) {
            gInfo();
        }
    }

    @Override
    public void initListener() {
        binding.tvGruopName.setOnClickListener((v) -> {
            if (groupDetaiInfoBean.getMyGInfo().getAdmin() > 1) {
                IntentUtils.intent2EditTextAreaActivity(groupDetaiInfoBean.getGroupInfo().getTitle(), String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()));
            }
        });
        binding.tvQrcode.setOnClickListener((v) -> {
        });
        binding.sbSetGroupTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                upChaTop();
                RongUtils.setConversationToTop(Conversation.ConversationType.GROUP, groupId);
            }
        });
        binding.tvGroupManager.setOnClickListener((v) -> {
            if (groupDetaiInfoBean.getMyGInfo().getAdmin() > 1) {
                IntentUtils.intent2GroupManagerActivity(groupDetaiInfoBean);
            }
        });
        binding.sbCloseNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                upRejct();
            }
        });
        binding.tvMyName.setOnClickListener((v) -> {
            IntentUtils.intent2AlterTextActivityByGroup(AlterTextType.GROUP_MY_NAME, groupDetaiInfoBean.getMyGInfo().getNick_name(), String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()), null);
        });
        binding.tvAll.setOnClickListener((v) -> {
            IntentUtils.intent2GroupUserListActivity(String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()));
        });
        binding.tvGroupNoti.setOnClickListener(view -> {
            if (groupDetaiInfoBean.getMyGInfo().getAdmin() > 1) {
                IntentUtils.intent2AlterGroupNotiActivity(groupDetaiInfoBean.getGroupInfo().getNotice(), String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()));
            }
        });

    }

    private void upRejct() {
        params.put("group_id", groupId);
        params.put("reject_msg", binding.sbSetGroupTop.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().upRejct(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void upChaTop() {
        params.put("group_id", groupId);
        params.put("chat_top", binding.sbSetGroupTop.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().upChaTop(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void gInfo() {

        params.put("group_id", groupId);

        HttpClient.Builder.getServer().gInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GroupDetaiInfoBean>() {
            @Override
            public void onSuccess(BaseBean<GroupDetaiInfoBean> baseBean) {
                groupDetaiInfoBean = baseBean.getData();
                showGroupInfo(baseBean.getData());
            }

            @Override
            public void onError(BaseBean<GroupDetaiInfoBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void showGroupInfo(GroupDetaiInfoBean groupDetaiInfoBean) {
        showGroupUsersInfo(groupDetaiInfoBean);
        binding.tvGruopName.setText(groupDetaiInfoBean.getGroupInfo().getTitle());
        binding.tvGroupNoti.setText(groupDetaiInfoBean.getGroupInfo().getNotice());
        binding.tvMyName.setText(groupDetaiInfoBean.getMyGInfo().getNick_name());

        if (groupDetaiInfoBean.getMyGInfo().getReject_msg() == 1) {
            binding.sbCloseNoti.setCheckedNoEvent(true);
        }

        if (groupDetaiInfoBean.getMyGInfo().getChat_top() == 1) {
            binding.sbSetGroupTop.setCheckedNoEvent(true);
        }


    }

    private void showGroupUsersInfo(GroupDetaiInfoBean groupDetaiInfoBean) {
        if (groupDetaiInfoBean.getMyGInfo().getAdmin() == 3) {
            List<GroupDetaiInfoBean.GroupUserInfoBean> users = new ArrayList();
            if (groupDetaiInfoBean.getGroupUserInfo().size() >= 4) {
                users = groupDetaiInfoBean.getGroupUserInfo().subList(0, 4);
                binding.tvAll.setVisibility(View.VISIBLE);
            } else {
                users = groupDetaiInfoBean.getGroupUserInfo();
            }
            users.add(new GroupDetaiInfoBean.GroupUserInfoBean("jia"));
            users.add(new GroupDetaiInfoBean.GroupUserInfoBean("jian"));
            groupUserAdapter = new GroupUserAdapter(users, groupId);
        } else {
            List<GroupDetaiInfoBean.GroupUserInfoBean> users = new ArrayList();
            if (groupDetaiInfoBean.getGroupUserInfo().size() >= 6) {
                users = groupDetaiInfoBean.getGroupUserInfo().subList(0, 5);
                binding.tvAll.setVisibility(View.VISIBLE);
            } else {
                users = groupDetaiInfoBean.getGroupUserInfo();
            }
            groupUserAdapter = new GroupUserAdapter(users, groupId);
        }
        binding.rvList.setAdapter(groupUserAdapter);
    }
}
