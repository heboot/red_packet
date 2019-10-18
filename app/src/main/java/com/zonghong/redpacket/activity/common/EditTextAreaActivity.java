package com.zonghong.redpacket.activity.common;

import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.waw.hr.mutils.event.GroupEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.chat.GroupDetailActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityEditTextBinding;
import com.zonghong.redpacket.databinding.ActivityTextBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class EditTextAreaActivity extends BaseActivity<ActivityEditTextBinding> {

    private String groupId, title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_text;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("群昵称");
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
            tipDialog = DialogUtils.getFailDialog(this, "请输入群昵称", true);
            tipDialog.show();
            return;
        }


        params.put("group_id", groupId);
        params.put(MKey.TITLE, binding.tvContent.getText().toString());
        HttpClient.Builder.getServer().gTitle(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                groupInfo(groupId);
                EventBus.getDefault().post(new GroupEvent.ALTER_GROUP_NAME_EVENT(binding.tvContent.getText().toString()));
                tipDialog = DialogUtils.getSuclDialog(EditTextAreaActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(EditTextAreaActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private Group userInfo;

    private void groupInfo(String s) {
        Map params = new HashMap<>();
        params.put("group_id", s);


        HttpClient.Builder.getServer().gIndex(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<MyGroupBean>>() {
            @Override
            public void onSuccess(BaseBean<List<MyGroupBean>> baseBean) {
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    userInfo = new Group(s, baseBean.getData().get(0).getTitle(), Uri.parse(baseBean.getData().get(0).getImg()));
                    RongIM.getInstance().refreshGroupInfoCache(userInfo);
                }
            }

            @Override
            public void onError(BaseBean<List<MyGroupBean>> baseBean) {

            }
        });
    }

}
