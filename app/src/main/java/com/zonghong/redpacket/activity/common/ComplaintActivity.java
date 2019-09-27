package com.zonghong.redpacket.activity.common;

import android.content.DialogInterface;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.ComplaintType;
import com.zonghong.redpacket.databinding.ActivityComplaintBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ComplaintActivity extends BaseActivity<ActivityComplaintBinding> {

    private ComplaintType complaintType;

    private String toId;

    private int checkId;

    private View.OnClickListener onClickListener;

    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complaint;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("投诉");
        binding.includeToolbar.tvRight.setText("完成");
        binding.includeToolbar.tvRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        complaintType = (ComplaintType) getIntent().getExtras().get(MKey.TYPE);
        toId = getIntent().getExtras().getString(MKey.ID);
        content = binding.tvTousu1.getText().toString();
    }

    @Override
    public void initListener() {
        onClickListener = view -> {
            checkId = view.getId();
            showCheck();
        };
        binding.tvTousu1.setOnClickListener(onClickListener);
        binding.tvTousu2.setOnClickListener(onClickListener);
        binding.tvTousu3.setOnClickListener(onClickListener);

        binding.includeToolbar.tvRight.setOnClickListener(view -> {
            complaint();
        });

    }

    private void showCheck() {
        if (binding.tvTousu1.getId() == checkId) {
            binding.iv1.setVisibility(View.VISIBLE);
            binding.iv2.setVisibility(View.GONE);
            binding.iv3.setVisibility(View.GONE);
            content = binding.tvTousu1.getText().toString();
        } else if (binding.tvTousu2.getId() == checkId) {
            binding.iv1.setVisibility(View.GONE);
            binding.iv2.setVisibility(View.VISIBLE);
            binding.iv3.setVisibility(View.GONE);
            content = binding.tvTousu2.getText().toString();
        } else if (binding.tvTousu3.getId() == checkId) {
            binding.iv1.setVisibility(View.GONE);
            binding.iv2.setVisibility(View.GONE);
            binding.iv3.setVisibility(View.VISIBLE);
            content = binding.tvTousu3.getText().toString();
        }
    }

    private void complaint() {


        params.put("type", complaintType == ComplaintType.GROUP ? 1 : 2);//投诉类型 1为群组，2为用户
        params.put("u_id", toId);
        params.put("info", content);
//        params.put(MKey.TITLE, binding.tvContent.getText().toString());
        HttpClient.Builder.getServer().complaint(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(ComplaintActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(ComplaintActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
