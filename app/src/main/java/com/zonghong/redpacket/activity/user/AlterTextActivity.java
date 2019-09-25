package com.zonghong.redpacket.activity.user;

import android.content.DialogInterface;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.LoginBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.databinding.ActivityAlterTextBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AlterTextActivity extends BaseActivity<ActivityAlterTextBinding> {

    private AlterTextType alterTextType;

    private String value, groupId, userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_text;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        alterTextType = (AlterTextType) getIntent().getExtras().get(MKey.TYPE);
        value = getIntent().getExtras().getString(MKey.DATA);
        if (alterTextType == AlterTextType.NICK_NAME || alterTextType == AlterTextType.GROUP_MY_NAME) {
            binding.includeToolbar.tvTitle.setText("修改昵称");
        }
        binding.etText.setText(value);
        binding.includeToolbar.tvRight.setText("完成");
        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initData() {
        if (alterTextType == AlterTextType.GROUP_MY_NAME) {
            groupId = getIntent().getStringExtra(MKey.ID);
        }
    }

    @Override
    public void initListener() {
        binding.ivClear.setOnClickListener((v) -> {
            binding.etText.setText("");
        });
        binding.includeToolbar.tvRight.setOnClickListener((v) -> {
            if (alterTextType == AlterTextType.NICK_NAME) {
                alterNickname();
            } else if (alterTextType == AlterTextType.GROUP_MY_NAME) {
                alterGroupNickname();
            }
        });
    }

    private void alterGroupNickname() {

        if (StringUtils.isEmpty(binding.etText.getText())) {
            String tip = "";
            if (alterTextType == AlterTextType.NICK_NAME) {
                tip = "请输入昵称";
            }
            tipDialog = DialogUtils.getFailDialog(this, tip, true);
            tipDialog.show();
            return;
        }


        loadingDialog.show();
        params.put("nick_name", binding.etText.getText().toString());
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().upNName(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(AlterTextActivity.this, baseBean.getMsg(), true);
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
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(AlterTextActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void alterNickname() {

        if (StringUtils.isEmpty(binding.etText.getText())) {
            String tip = "";
            if (alterTextType == AlterTextType.NICK_NAME) {
                tip = "请输入昵称";
            }
            tipDialog = DialogUtils.getFailDialog(this, tip, true);
            tipDialog.show();
            return;
        }


        loadingDialog.show();
        params.put("nick_name", binding.etText.getText().toString());
        HttpClient.Builder.getServer().uUpname(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(AlterTextActivity.this, baseBean.getMsg(), true);
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
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(AlterTextActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


}
