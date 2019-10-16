package com.zonghong.redpacket.activity.contacts;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ContatsChildBean;
import com.waw.hr.mutils.bean.ContatsFriendBean;
import com.waw.hr.mutils.bean.ContatsListBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.MingPianBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.waw.hr.mutils.event.GroupEvent;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.chat.GroupDetailActivity;
import com.zonghong.redpacket.adapter.ContactsContainerAdapter;
import com.zonghong.redpacket.adapter.contacts.ContactsSendMingPianContainerAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.common.PayDialogType;
import com.zonghong.redpacket.common.SendMingPianType;
import com.zonghong.redpacket.databinding.FragmentContactsBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.view.PayDialog;
import com.zonghong.redpacket.view.SendMingPianDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.eventbus.EventBus;

public class ChooseContactsSendMingPianActivity extends BaseActivity<FragmentContactsBinding> {

    private ContactsSendMingPianContainerAdapter contactsAdapter;

    private String checkIds = "";

    private int toll = 0;//如果是1,表示该用户创建群里需要付钱

    private float charge = 0;//需要支付的额度

    private PayDialog payDialog;

    private UserInfoBean userInfoBean;

    private String targetId;

    private SendMingPianType sendMingPianType;

    private SendMingPianDialog sendMingPianDialog;

    private String checkName;

    private String uid;

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
        targetId = (String) getIntent().getExtras().get(MKey.ID);
        uid = (String) getIntent().getExtras().get(MKey.ID);

        sendMingPianType = (SendMingPianType) getIntent().getExtras().get(MKey.TYPE);
        userInfo();
        if (sendMingPianType == SendMingPianType.USER) {

        } else {
            gInfo();
        }
        list();

    }

    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_search, null, false);
        layoutSearchBinding.etKey.setOnClickListener((v) -> {
            IntentUtils.doIntent(SearchContactsActivity.class);
        });
        return layoutSearchBinding.getRoot();
    }


    private void list() {
        params =new HashMap<>();
        HttpClient.Builder.getServer().fIndex(UserService.getInstance().getToken(),params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<ContatsListBean>() {
            @Override
            public void onSuccess(BaseBean<ContatsListBean> baseBean) {
                toll = baseBean.getData().getToll();
                charge = baseBean.getData().getCharge();
                if (contactsAdapter == null) {
                    if (baseBean.getData().getList() != null) {
                        contactsAdapter = new ContactsSendMingPianContainerAdapter(baseBean.getData().getList(), new WeakReference<>(ChooseContactsSendMingPianActivity.this));
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
                tipDialog = DialogUtils.getFailDialog(ChooseContactsSendMingPianActivity.this, baseBean.getMsg(), true);
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
                        checkIds = "" + friendBean.getFriend_id();
                        checkName = friendBean.getNick_name();
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
            showDialog();
        });
    }

    private void showDialog() {
        sendMingPianDialog = SendMingPianDialog.newInstance(sendMingPianType, userInfoBean, groupDetaiInfoBean, checkName, targetId);
        sendMingPianDialog.show(getSupportFragmentManager(), "");
    }


    private void newgroup() {
        MingPianBean mingPianBean = new MingPianBean();
        mingPianBean.setAccountId(userInfoBean.getAccount_id());
        mingPianBean.setCardId(userInfoBean.getID() + "");
        mingPianBean.setCardImg(userInfoBean.getImg());
        mingPianBean.setCardName(userInfoBean.getNick_name());
        RongUtils.sendMingPianMessage(sendMingPianType, targetId, mingPianBean);
        finish();
    }

    private GroupDetaiInfoBean groupDetaiInfoBean;

    private void gInfo() {

        params.put("group_id", targetId);

        HttpClient.Builder.getServer().gInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GroupDetaiInfoBean>() {
            @Override
            public void onSuccess(BaseBean<GroupDetaiInfoBean> baseBean) {
                groupDetaiInfoBean = baseBean.getData();
            }

            @Override
            public void onError(BaseBean<GroupDetaiInfoBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ChooseContactsSendMingPianActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public String getCheckIds() {
        return checkIds;
    }

    public void setCheckIds(String checkIds) {
        this.checkIds = checkIds;
        contactsAdapter.notifyDataSetChanged();
        uid = checkIds;
        userInfo();
    }

    private void userInfo() {
        params = new HashMap<>();
        params.put("id", uid);
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
                dismissLoadingDialog();
                userInfoBean = baseBean.getData();


            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(ChooseContactsSendMingPianActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
