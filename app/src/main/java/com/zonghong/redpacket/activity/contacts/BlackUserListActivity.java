package com.zonghong.redpacket.activity.contacts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.BlackListBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.chat.MyGroupListActivity;
import com.zonghong.redpacket.adapter.BlackUserAdapter;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutContactsHeadBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BlackUserListActivity extends BaseActivity<FragmentContactsBinding> {

    private BlackUserAdapter contactsAdapter;

    private List<BlackListBean> blackListBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("黑名单");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }


    @Override
    public void initData() {
        list();
    }

    private void list() {

        HttpClient.Builder.getServer().fBlackL(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<BlackListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<BlackListBean>> baseBean) {
                blackListBeans.clear();
                blackListBeans.addAll(baseBean.getData());
                if (contactsAdapter == null) {
                    if (baseBean.getData() != null) {
                        contactsAdapter = new BlackUserAdapter(baseBean.getData());
                        contactsAdapter.addHeaderView(getSearchView());
                        binding.rvList.setAdapter(contactsAdapter);
                    }
                } else {
                    contactsAdapter.getData().clear();
                    contactsAdapter.setNewData(baseBean.getData());
                    contactsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<List<BlackListBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(BlackUserListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(BlackUserListActivity.this), R.layout.layout_search, null, false);
        layoutSearchBinding.etKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || charSequence.length() == 0) {
                    list();
                    return;
                }
                contactsAdapter.setNewData(SearchUtils.searchBlackList(blackListBeans, charSequence.toString()));
                contactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        layoutSearchBinding.getRoot().setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y55)));
        return layoutSearchBinding.getRoot();
    }


    @Override
    public void initListener() {

    }
}
