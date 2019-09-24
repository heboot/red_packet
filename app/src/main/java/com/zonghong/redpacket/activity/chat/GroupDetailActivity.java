package com.zonghong.redpacket.activity.chat;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

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
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupDetailActivity extends BaseActivity<ActivityGroupDetailBinding> {

    private String groupId;

    private GroupUserAdapter groupUserAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("聊天详情");
        binding.rvList.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void initData() {
        groupId = getIntent().getExtras().getString(MKey.ID);
        gInfo();
    }

    @Override
    public void initListener() {
        binding.tvGruopName.setOnClickListener((v) -> {
            IntentUtils.intent2AlterTextActivity(AlterTextType.GROUP_NAME, "");
        });
        binding.tvQrcode.setOnClickListener((v) -> {
        });
    }

    private void gInfo() {

        params.put("group_id", groupId);

        HttpClient.Builder.getServer().gInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GroupDetaiInfoBean>() {
            @Override
            public void onSuccess(BaseBean<GroupDetaiInfoBean> baseBean) {
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

    }

    private void showGroupUsersInfo(GroupDetaiInfoBean groupDetaiInfoBean) {
        if (groupDetaiInfoBean.getMyGInfo().getAdmin() == 3) {
            List<GroupDetaiInfoBean.GroupUserInfoBean> users = new ArrayList();
            if (groupDetaiInfoBean.getGroupUserInfo().size() >= 4) {
                users = groupDetaiInfoBean.getGroupUserInfo().subList(0, 4);
            } else {
                users = groupDetaiInfoBean.getGroupUserInfo();
            }
            users.add(new GroupDetaiInfoBean.GroupUserInfoBean("jia"));
            users.add(new GroupDetaiInfoBean.GroupUserInfoBean("jian"));
            groupUserAdapter = new GroupUserAdapter(users);
        } else {
            List<GroupDetaiInfoBean.GroupUserInfoBean> users = new ArrayList();
            if (groupDetaiInfoBean.getGroupUserInfo().size() >= 6) {
                users = groupDetaiInfoBean.getGroupUserInfo().subList(0, 5);

            } else {
                users = groupDetaiInfoBean.getGroupUserInfo();
            }
            groupUserAdapter = new GroupUserAdapter(users);
        }
        binding.rvList.setAdapter(groupUserAdapter);
    }
}
