package com.zonghong.redpacket.activity.chat;

import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.MyGroupAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityNewContactsListBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyGroupListActivity extends BaseActivity<ActivityNewContactsListBinding> {

    private MyGroupAdapter myGroupAdapter;

    private List<MyGroupBean> myGroupBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_contacts_list;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("我的群聊");
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        list();
    }

    @Override
    public void initListener() {

    }


    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_search, null, false);
//        layoutSearchBinding.etKey.setOnClickListener((v) -> {
//            IntentUtils.doIntent(SearchContactsActivity.class);
//        });
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
                if (myGroupAdapter != null) {
                    myGroupAdapter.setNewData(SearchUtils.searchMyGroup(myGroupBeans, charSequence.toString()));
                    myGroupAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        layoutSearchBinding.getRoot().setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y55)));
        return layoutSearchBinding.getRoot();
    }

    private void list() {
        HttpClient.Builder.getServer().gIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<MyGroupBean>>() {
            @Override
            public void onSuccess(BaseBean<List<MyGroupBean>> baseBean) {
                myGroupBeans = baseBean.getData();
                if (myGroupAdapter == null) {
                    if (baseBean.getData() != null) {
                        myGroupAdapter = new MyGroupAdapter(baseBean.getData());
                        myGroupAdapter.addHeaderView(getSearchView());
                        binding.rvList.setAdapter(myGroupAdapter);
                    }
                } else {
                    myGroupAdapter.setNewData(baseBean.getData());
                    myGroupAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(BaseBean<List<MyGroupBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(MyGroupListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
