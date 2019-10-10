package com.zonghong.redpacket.activity.common;

import android.content.DialogInterface;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityComplaint2Binding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ComplaintActivity2 extends BaseActivity<ActivityComplaint2Binding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_complaint2;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("投诉与建议");

        binding.qlytInput.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x10)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytSubmit.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytLianxi.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.qlytSubmit.setOnClickListener(view -> {
            complaint();
        });

    }


    private void complaint() {

        if (StringUtils.isEmpty(binding.etContent.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入投诉内容", true);
            tipDialog.show();
            return;
        }

        params.put("phone", binding.etLianxi.getText());
        params.put("info", binding.etContent.getText().toString());
        HttpClient.Builder.getServer().complaintToApp(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(ComplaintActivity2.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(ComplaintActivity2.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
