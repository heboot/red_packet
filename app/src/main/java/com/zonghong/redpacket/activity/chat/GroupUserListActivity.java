package com.zonghong.redpacket.activity.chat;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.waw.hr.mutils.bean.GroupUserListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.AddGroupUserContainerAdapter;
import com.zonghong.redpacket.adapter.group.GroupUserListAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupUserListActivity extends BaseActivity<FragmentContactsBinding> {

    private GroupUserListAdapter contactsAdapter;

    private String groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.vBack.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("群成员");
        binding.rvList.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    public void initData() {
        groupId = (String) getIntent().getExtras().get(MKey.ID);
        list();
    }

    @Override
    public void initListener() {

    }

    private void list() {
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().gUlist(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GroupUserListBean>() {
            @Override
            public void onSuccess(BaseBean<GroupUserListBean> baseBean) {
                if (contactsAdapter == null) {
                    if (baseBean.getData().getGUser() != null) {
                        contactsAdapter = new GroupUserListAdapter(baseBean.getData().getGUser(), groupId);
                        binding.rvList.setAdapter(contactsAdapter);
                    }
                } else {
                    contactsAdapter.getData().clear();
                    contactsAdapter.setNewData(baseBean.getData().getGUser());
                    contactsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<GroupUserListBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupUserListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
