package com.zonghong.redpacket.activity.contacts;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.waw.hr.mutils.event.GroupEvent;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.common.RechargeCashType;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.utils.SearchUtils;
import com.zonghong.redpacket.view.PayDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.eventbus.EventBus;

public class ChooseContactsActivity extends BaseActivity<FragmentContactsBinding> {

    private ContactsContainerAdapter contactsAdapter;

    private String checkIds = "";

    private int toll = 0;//如果是1,表示该用户创建群里需要付钱

    private float charge = 0;//需要支付的额度

    private PayDialog payDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setText("完成");
        binding.includeToolbar.tvTitle.setText("选择联系人");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        list();

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
                contactsAdapter.setNewData(SearchUtils.searchContacts(contactsAdapter.getData(), charSequence.toString()));
                contactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return layoutSearchBinding.getRoot();
    }


    private void list() {

        HttpClient.Builder.getServer().fIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<ContatsListBean>() {
            @Override
            public void onSuccess(BaseBean<ContatsListBean> baseBean) {
                toll = baseBean.getData().getToll();
                charge = baseBean.getData().getCharge();
                if (contactsAdapter == null) {
                    if (baseBean.getData().getList() != null) {
                        contactsAdapter = new ContactsContainerAdapter(baseBean.getData().getList(), true);
                        contactsAdapter.addHeaderView(getSearchView());
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
                tipDialog = DialogUtils.getFailDialog(ChooseContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
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
            checkIds = checkIds + UserService.getInstance().getUserId();
            if (toll == 1 && charge > 0) {
                payDialog = PayDialog.newInstance(PayDialogType.CREATE_GROUP, charge, "");
                payDialog.show(getSupportFragmentManager(), "");
                return;
            }
            newgroup();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GroupEvent.CREATE_GROUP_SUC_EVENT event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UserEvent.PAY_SUC_EVENT event) {
        crePayMon();
    }


    private void crePayMon() {
        params.put("user_list", checkIds);
        HttpClient.Builder.getServer().crePayMon(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                newgroup();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ChooseContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void newgroup() {
        params.put("user_list", checkIds);
        HttpClient.Builder.getServer().gCreate(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {

                RongUtils.toGroupChat((String) baseBean.getData().get("g_id"), (String) baseBean.getData().get("g_name"));
                EventBus.getDefault().post(new GroupEvent.CREATE_GROUP_SUC_EVENT());
                finish();

            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ChooseContactsActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
