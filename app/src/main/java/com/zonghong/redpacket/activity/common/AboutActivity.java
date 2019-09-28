package com.zonghong.redpacket.activity.common;

import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityAboutBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AboutActivity extends BaseActivity<ActivityAboutBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("关于");
    }

    @Override
    public void initData() {
        about();
    }

    @Override
    public void initListener() {

    }


    public void about() {
        HttpClient.Builder.getServer().iRegard(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {
                ImageUtils.showImage((String) baseBean.getData().get("image"), binding.ivLogo);
                binding.tvTitle.setText((String) baseBean.getData().get("name"));
                binding.tvContent.setText((String) baseBean.getData().get("recommend"));
            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                tipDialog = DialogUtils.getFailDialog(AboutActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
