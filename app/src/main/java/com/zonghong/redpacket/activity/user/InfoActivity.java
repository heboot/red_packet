package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.LoginBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.LoginActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.databinding.ActivityInfoBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InfoActivity extends BaseActivity<ActivityInfoBinding> {

    private QMUIBottomSheet sexSheet;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initListener() {
        binding.tvSexTitle.setOnClickListener((v) -> {
            if (sexSheet == null) {
                sexSheet = DialogUtils.getSexbottomSheet(this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        sexSheet.dismiss();
                        if (position == 0) {
                            chooseSex(2);
                        } else {
                            chooseSex(1);
                        }
                    }
                });
            }
            sexSheet.show();
        });
        binding.tvName.setOnClickListener((v) -> {
            IntentUtils.intent2AlterTextActivity(AlterTextType.NICK_NAME, "");
        });
        binding.tvCodeTitle.setOnClickListener((v) -> {
            IntentUtils.doIntent(QRCodeActivity.class);
        });
    }

    private void chooseSex(int sex) {
        loadingDialog.show();
        params.put("sex", sex);
        HttpClient.Builder.getServer().uSex(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(InfoActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
