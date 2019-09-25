package com.zonghong.redpacket.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.chat.MyGroupListActivity;
import com.zonghong.redpacket.activity.contacts.BlackUserListActivity;
import com.zonghong.redpacket.activity.contacts.ChooseContactsActivity;
import com.zonghong.redpacket.activity.contacts.ContactsNewListActivity;
import com.zonghong.redpacket.activity.contacts.SearchContactsActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutContactsHeadBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsFragment extends BaseFragment<FragmentContactsBinding> {

    private ContactsContainerAdapter contactsAdapter;

    public static ContactsFragment newInstance() {
        Bundle args = new Bundle();
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.vBack.setVisibility(View.GONE);
        binding.includeToolbar.tvTitle.setText("通讯录");
        binding.rvList.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        list();
    }

    @Override
    public void initData() {
        list();
    }

    private void list() {

        HttpClient.Builder.getServer().fIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<ContatsListBean>() {
            @Override
            public void onSuccess(BaseBean<ContatsListBean> baseBean) {
                if (contactsAdapter == null) {
                    if (baseBean.getData().getList() != null) {
                        contactsAdapter = new ContactsContainerAdapter(baseBean.getData().getList(), false);
                        contactsAdapter.addHeaderView(getSearchView());
                        contactsAdapter.addHeaderView(getContactsHeadView(baseBean.getData().getStayConsentNum() > 0));
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
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(_mActivity), R.layout.layout_search, null, false);
        layoutSearchBinding.etKey.setOnClickListener((v) -> {
            IntentUtils.doIntent(SearchContactsActivity.class);
        });
        layoutSearchBinding.getRoot().setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y55)));
        return layoutSearchBinding.getRoot();
    }

    private View getContactsHeadView(boolean hasNew) {
        LayoutContactsHeadBinding layoutContactsHeadBinding = DataBindingUtil.inflate(LayoutInflater.from(_mActivity), R.layout.layout_contacts_head, null, false);
        if (hasNew) {
            layoutContactsHeadBinding.vNewnum.setVisibility(View.VISIBLE);
        } else {
            layoutContactsHeadBinding.vNewnum.setVisibility(View.GONE);
        }
        layoutContactsHeadBinding.tvAddFriend.setOnClickListener((v) -> {
            IntentUtils.doIntent(ContactsNewListActivity.class);
        });
        layoutContactsHeadBinding.tvGroupChat.setOnClickListener((v) -> {
            IntentUtils.doIntent(MyGroupListActivity.class);
        });
        layoutContactsHeadBinding.tvBlack.setOnClickListener(view -> {
            IntentUtils.doIntent(BlackUserListActivity.class);
        });
        return layoutContactsHeadBinding.getRoot();
    }


    @Override
    public void initListener() {

    }
}
