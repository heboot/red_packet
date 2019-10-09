package com.zonghong.redpacket.activity.chat;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.waw.hr.mutils.bean.GroupUserListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.AddGroupUserContainerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddGroupUserActivity extends BaseActivity<FragmentContactsBinding> {

    private AddGroupUserContainerAdapter contactsAdapter;

    private String groupId;

    private String checkIds = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.vBack.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("添加成员");
        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setText("完成");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        groupId = (String) getIntent().getExtras().get(MKey.ID);
        list();
    }

    @Override
    public void initListener() {
        binding.includeToolbar.tvRight.setOnClickListener((v) -> {


            checkIds = "";
            for (ContatsChildBean contatsChildBean : contactsAdapter.getData()) {
                for (ContatsFriendBean friendBean : contatsChildBean.getList()) {
                    if (friendBean.isCheck()) {
                        checkIds = checkIds + friendBean.getFriend_id() + ",";
                    }
                }
            }

            if (StringUtils.isEmpty(checkIds)) {
                tipDialog = DialogUtils.getFailDialog(this, "请先选择好友", true);
                tipDialog.show();
                return;
            }
            addUser();
        });
    }

    private void addUser() {
        params = new HashMap<>();
        params.put("group_id", groupId);
        params.put("user_id", checkIds.substring(0, checkIds.length()-1));
        HttpClient.Builder.getServer().gAddUser(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(AddGroupUserActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(AddGroupUserActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void list() {
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().fIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<ContatsListBean>() {
            @Override
            public void onSuccess(BaseBean<ContatsListBean> baseBean) {
                if (contactsAdapter == null) {
                    if (baseBean.getData().getList() != null) {
                        contactsAdapter = new AddGroupUserContainerAdapter(baseBean.getData().getList());
                        binding.rvList.setAdapter(contactsAdapter);
                    }
                } else {
                    contactsAdapter.getData().clear();
                    contactsAdapter.setNewData(baseBean.getData().getList());
                    contactsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<ContatsListBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(AddGroupUserActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
