package com.zonghong.redpacket.activity.chat;

import android.content.DialogInterface;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.CompoundButton;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupDetaiInfoBean;
import com.waw.hr.mutils.event.GroupEvent;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.group.GroupUserAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.common.ComplaintType;
import com.zonghong.redpacket.common.QRCodeType;
import com.zonghong.redpacket.common.SearchMessageType;
import com.zonghong.redpacket.databinding.ActivityGroupDetailBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class GroupDetailActivity extends BaseActivity<ActivityGroupDetailBinding> {

    private String groupId;

    private GroupUserAdapter groupUserAdapter;

    private GroupDetaiInfoBean groupDetaiInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_detail;
    }

    @Override
    public void initUI() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("聊天详情");
        binding.rvList.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    public void initData() {
        loadingDialog.show();
        groupId = getIntent().getExtras().getString(MKey.ID);
        gInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (groupUserAdapter != null) {
            gInfo();
        }
    }

    @Override
    public void initListener() {
        binding.tvGruopName.setOnClickListener((v) -> {
            if (groupDetaiInfoBean.getMyGInfo().getAdmin() > 1) {
                IntentUtils.intent2EditTextAreaActivity(groupDetaiInfoBean.getGroupInfo().getTitle(), String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()));
            }
        });
        binding.tvQrcode.setOnClickListener((v) -> {
            IntentUtils.intent2QRCodeActivity(QRCodeType.GROUP, groupDetaiInfoBean.getGroupInfo().getTitle(), groupDetaiInfoBean.getGroupInfo().getImg(), null, groupDetaiInfoBean.getGroupInfo().getID() + "");
        });
        binding.sbSetGroupTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                upChaTop();
                RongUtils.setConversationToTop(Conversation.ConversationType.GROUP, groupId);
            }
        });
        binding.tvGroupManager.setOnClickListener((v) -> {
            if (groupDetaiInfoBean.getMyGInfo().getAdmin() > 1) {
                IntentUtils.intent2GroupManagerActivity(groupDetaiInfoBean);
            }
        });
        binding.sbCloseNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                upRejct();
            }
        });
        binding.sbClearGroupContentTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (groupDetaiInfoBean.getMyGInfo().getAdmin() == 3) {
                   clear();
                } else {
                    binding.sbClearGroupContentTime.setCheckedNoEvent(!isChecked);
                    tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, "只有群主可以操作", true);
                    tipDialog.show();
                }
            }
        });
        binding.tvMyName.setOnClickListener((v) -> {
            IntentUtils.intent2AlterTextActivityByGroup(AlterTextType.GROUP_MY_NAME, groupDetaiInfoBean.getMyGInfo().getNick_name(), String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()), null);
        });
        binding.tvAll.setOnClickListener((v) -> {
            IntentUtils.intent2GroupUserListActivity(String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()), groupDetaiInfoBean.getMyGInfo().getAdmin());
        });
        binding.tvGroupNoti.setOnClickListener(view -> {
            if (groupDetaiInfoBean.getMyGInfo().getAdmin() > 1) {
                IntentUtils.intent2AlterGroupNotiActivity(groupDetaiInfoBean.getGroupInfo().getNotice(), String.valueOf(groupDetaiInfoBean.getGroupInfo().getID()));
            }
        });
        binding.tvComplain.setOnClickListener(view -> {
            IntentUtils.intent2ComplaintActivity(ComplaintType.GROUP, groupId);
        });
        binding.tvDelExit.setOnClickListener(view -> {
            delUser();
        });
        binding.tvClearGroupContent.setOnClickListener(view -> {
            if (groupDetaiInfoBean.getMyGInfo().getAdmin() == 3) {
                RongIM.getInstance().deleteMessages(Conversation.ConversationType.GROUP, groupId, new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        RongUtils.sendClearMessage(groupId);
                        tipDialog = DialogUtils.getSuclDialog(GroupDetailActivity.this, "清除成功", true);
                        tipDialog.show();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, "清除失败", true);
                        tipDialog.show();
                    }
                });
            } else {
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, "只有群主可以操作", true);
                tipDialog.show();
            }
        });
        binding.tvFindChatContent.setOnClickListener((v) -> {
            IntentUtils.intent2SearchMessageActivity(SearchMessageType.GROUP, groupId, groupDetaiInfoBean.getGroupInfo().getTitle());
        });
    }

    private void delUser() {
        params = new HashMap<>();
        params.put("group_id", groupId);
        params.put("user_id", UserService.getInstance().getUserId());
        if(groupDetaiInfoBean.getMyGInfo().getAdmin() == 3){
            params.put("operation", 2);
        }else{
            params.put("operation", 1);
        }
        HttpClient.Builder.getServer().gDelUser(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP,groupId,null);
                EventBus.getDefault().post(new GroupEvent.EXIT_GROUP_EVENT());
                tipDialog = DialogUtils.getSuclDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void clear() {
        params.put("group_id", groupId);
        params.put("clear", binding.sbClearGroupContentTime.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().clearHitory(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
//                tipDialog = DialogUtils.getSuclDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void upRejct() {
        params.put("group_id", groupId);
        params.put("reject_msg", binding.sbCloseNoti.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().upRejct(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
//                tipDialog = DialogUtils.getSuclDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void upChaTop() {
        params.put("group_id", groupId);
        params.put("chat_top", binding.sbSetGroupTop.isChecked() ? 1 : 0);
        HttpClient.Builder.getServer().upChaTop(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
//                tipDialog = DialogUtils.getSuclDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void gInfo() {

        params.put("group_id", groupId);

        HttpClient.Builder.getServer().gInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GroupDetaiInfoBean>() {
            @Override
            public void onSuccess(BaseBean<GroupDetaiInfoBean> baseBean) {
                dismissLoadingDialog();
                groupDetaiInfoBean = baseBean.getData();
                showGroupInfo(baseBean.getData());
            }

            @Override
            public void onError(BaseBean<GroupDetaiInfoBean> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(GroupDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void showGroupInfo(GroupDetaiInfoBean groupDetaiInfoBean) {
        showGroupUsersInfo(groupDetaiInfoBean);
        binding.tvGruopName.setText(groupDetaiInfoBean.getGroupInfo().getTitle());
        binding.tvGroupNoti.setText(groupDetaiInfoBean.getGroupInfo().getNotice());
        binding.tvMyName.setText(groupDetaiInfoBean.getMyGInfo().getNick_name());

        if (groupDetaiInfoBean.getMyGInfo().getReject_msg() == 1) {
            binding.sbCloseNoti.setCheckedNoEvent(true);
        }

        if (groupDetaiInfoBean.getMyGInfo().getChat_top() == 1) {
            binding.sbSetGroupTop.setCheckedNoEvent(true);
        }

        if (groupDetaiInfoBean.getGroupInfo().getClear() == 1) {
            binding.sbClearGroupContentTime.setCheckedNoEvent(true);
        }
    }

    private void showGroupUsersInfo(GroupDetaiInfoBean groupDetaiInfoBean) {
        if (groupDetaiInfoBean.getMyGInfo().getAdmin() > 1) {
            List<GroupDetaiInfoBean.GroupUserInfoBean> users = new ArrayList();
            if (groupDetaiInfoBean.getGroupUserInfo().size() >= 6) {
                users = groupDetaiInfoBean.getGroupUserInfo().subList(0, 6);
                binding.tvAll.setVisibility(View.VISIBLE);
            } else {
                users = groupDetaiInfoBean.getGroupUserInfo();
            }
            users.add(new GroupDetaiInfoBean.GroupUserInfoBean("jia"));
            users.add(new GroupDetaiInfoBean.GroupUserInfoBean("jian"));
            groupUserAdapter = new GroupUserAdapter(users, groupId, groupDetaiInfoBean.getMyGInfo().getAdmin());
        }
        else {
            List<GroupDetaiInfoBean.GroupUserInfoBean> users = new ArrayList();
            if (groupDetaiInfoBean.getGroupUserInfo().size() >= 7) {
                users = groupDetaiInfoBean.getGroupUserInfo().subList(0, 7);
                binding.tvAll.setVisibility(View.VISIBLE);
            } else {
                users = groupDetaiInfoBean.getGroupUserInfo();
            }
            users.add(new GroupDetaiInfoBean.GroupUserInfoBean("jia"));
            groupUserAdapter = new GroupUserAdapter(users, groupId, groupDetaiInfoBean.getMyGInfo().getAdmin());







//            List<GroupDetaiInfoBean.GroupUserInfoBean> users = new ArrayList();
//            if (groupDetaiInfoBean.getGroupUserInfo().size() >= 8) {
//                users = groupDetaiInfoBean.getGroupUserInfo().subList(0, 7);
//                binding.tvAll.setVisibility(View.VISIBLE);
//            } else {
//                users = groupDetaiInfoBean.getGroupUserInfo();
//            }
//            groupUserAdapter = new GroupUserAdapter(users, groupId, groupDetaiInfoBean.getMyGInfo().getAdmin());
        }
        binding.rvList.setAdapter(groupUserAdapter);
    }
}
