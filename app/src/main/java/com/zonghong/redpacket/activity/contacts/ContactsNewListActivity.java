package com.zonghong.redpacket.activity.contacts;

import android.databinding.DataBindingUtil;
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
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.NewFriendListBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.NewContactsAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityNewContactsListBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.utils.SearchUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsNewListActivity extends BaseActivity<ActivityNewContactsListBinding> {

    private NewContactsAdapter newContactsAdapter;

    private List<NewFriendListBean> listBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_contacts_list;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("新的朋友");
    }

    @Override
    public void initData() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initListener() {
        list();
    }

    private void list() {
        HttpClient.Builder.getServer().fSCon(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<NewFriendListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<NewFriendListBean>> baseBean) {
                listBeans.clear();
                listBeans.addAll(baseBean.getData());
                if (newContactsAdapter == null) {
                    if (baseBean.getData() != null) {
                        newContactsAdapter = new NewContactsAdapter(baseBean.getData(), new WeakReference<>(ContactsNewListActivity.this));
                        newContactsAdapter.addHeaderView(getSearchView());
                        binding.rvList.setAdapter(newContactsAdapter);
                    }
                } else {
                    newContactsAdapter.getData().clear();
                    newContactsAdapter.setNewData(baseBean.getData());
                    newContactsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<List<NewFriendListBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ContactsNewListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void apply(String id) {
        params.put("my_id", id);
        HttpClient.Builder.getServer().fConsent(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
                list();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ContactsNewListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_search, null, false);
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
                newContactsAdapter.setNewData(SearchUtils.searchNewFriendList(listBeans, charSequence.toString()));
                newContactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        layoutSearchBinding.getRoot().setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y55)));
        return layoutSearchBinding.getRoot();
    }
}
