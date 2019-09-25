package com.zonghong.redpacket.activity.chat;

import android.content.DialogInterface;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityEditTextBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AlterGroupNotiActivity extends BaseActivity<ActivityEditTextBinding> {

    private String groupId, title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_text;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("群公告");
        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
        binding.includeToolbar.tvRight.setText("完成");
    }

    @Override
    public void initData() {
        title = getIntent().getStringExtra(MKey.TITLE);
        groupId = getIntent().getStringExtra(MKey.ID);
        binding.tvContent.setText(title);
    }

    @Override
    public void initListener() {
        binding.includeToolbar.tvRight.setOnClickListener(view -> {
            alterGroupName();
        });
    }

    private void alterGroupName() {
        if (StringUtils.isEmpty(binding.tvContent.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入群公告", true);
            tipDialog.show();
            return;
        }

        params.put("group_id", groupId);
        params.put("notice", binding.tvContent.getText().toString());
        HttpClient.Builder.getServer().gTitle(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(AlterGroupNotiActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(AlterGroupNotiActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
