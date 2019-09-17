package com.zonghong.redpacket.activity.contacts;

import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityContactsDetailBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsDetailActivity extends BaseActivity<ActivityContactsDetailBinding> {

    private SearchContatsBean searchContatsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("");
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initData() {
        searchContatsBean = (SearchContatsBean) getIntent().getExtras().get(MKey.DATA);
        binding.tvName.setText(searchContatsBean.getNick_name());
        ImageUtils.showAvatar(searchContatsBean.getImage(), binding.ivAvatar);
        binding.tvNo.setText(searchContatsBean.getID());
    }

    @Override
    public void initListener() {
        binding.tvAdd.setOnClickListener((v) -> {
            add();
        });
    }

    private void add() {
        loadingDialog.show();
        params.put("friend_id", searchContatsBean.getID());
        params.put("notes", "");
        HttpClient.Builder.getServer().fSendMsg(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Boolean>() {
            @Override
            public void onSuccess(BaseBean<Boolean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Boolean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
