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
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.GroupUserListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.DelGroupUserAdapter;
import com.zonghong.redpacket.adapter.group.SetGroupManagerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DelGroupUserActivity extends BaseActivity<FragmentContactsBinding> {

    private DelGroupUserAdapter contactsAdapter;

    private String checkIds = "";

    private String groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.vBack.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("删除群成员");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setText("完成");
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
            for (GroupUserListBean.GUserBean friendBean : contactsAdapter.getData()) {
                if (friendBean.isCheck()) {
                    checkIds = checkIds + friendBean.getUser_id() + ",";
                }
            }

            if (StringUtils.isEmpty(checkIds)) {
                tipDialog = DialogUtils.getFailDialog(this, "请先选择成员", true);
                tipDialog.show();
                return;
            }

            delUser();
        });
    }

    private void delUser() {
        params = new HashMap<>();
        params.put("group_id", groupId);
        params.put("user_id", checkIds.substring(0, checkIds.length()));
        HttpClient.Builder.getServer().gDelUser(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(DelGroupUserActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(DelGroupUserActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void list() {
        params = new HashMap<>();
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().gUlist(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GroupUserListBean>() {
            @Override
            public void onSuccess(BaseBean<GroupUserListBean> baseBean) {
                if (contactsAdapter == null) {
                    if (baseBean.getData().getGUser() != null) {
                        contactsAdapter = new DelGroupUserAdapter(baseBean.getData().getGUser());
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
                tipDialog = DialogUtils.getFailDialog(DelGroupUserActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
