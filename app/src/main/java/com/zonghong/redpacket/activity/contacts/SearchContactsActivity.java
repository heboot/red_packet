package com.zonghong.redpacket.activity.contacts;

import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.NewContactsAdapter;
import com.zonghong.redpacket.adapter.SearchContactsAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivitySearchContactsBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchContactsActivity extends BaseActivity<ActivitySearchContactsBinding> {

    private SearchContactsAdapter searchContactsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_contacts;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("添加朋友");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        binding.tvMyNo.setText("我的ID：" + UserService.getInstance().getUserId());
    }

    @Override
    public void initListener() {
        binding.etKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void search() {
        if (StringUtils.isEmpty(binding.etKey.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入ID或昵称", true);
            tipDialog.show();
            return;
        }


        loadingDialog.show();

        params.put(MKey.SEARCH_CONTENT, binding.etKey.getText());

        HttpClient.Builder.getServer().fAdd(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<SearchContatsBean>>() {
            @Override
            public void onSuccess(BaseBean<List<SearchContatsBean>> baseBean) {
                loadingDialog.dismiss();
                if (baseBean.getData() == null || baseBean.getData().size() == 0) {
                    tipDialog = DialogUtils.getFailDialog(SearchContactsActivity.this, baseBean.getMsg(), true);
                    tipDialog.show();
                    return;
                }
                if (searchContactsAdapter == null) {
                    if (baseBean.getData() != null) {
                        searchContactsAdapter = new SearchContactsAdapter(baseBean.getData());
                        binding.rvList.setAdapter(searchContactsAdapter);
                    }
                } else {
                    searchContactsAdapter.getData().clear();
                    searchContactsAdapter.setNewData(baseBean.getData());
                    searchContactsAdapter.notifyDataSetChanged();
                }
//                IntentUtils.intent2ContactsDetailActivity(baseBean.getData().get(0));
            }

            @Override
            public void onError(BaseBean<List<SearchContatsBean>> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(SearchContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
