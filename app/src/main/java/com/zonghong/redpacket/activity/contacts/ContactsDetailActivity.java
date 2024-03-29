package com.zonghong.redpacket.activity.contacts;

import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.CompoundButton;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.AlterTextType;
import com.zonghong.redpacket.common.ComplaintType;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.ActivityContactsDetailBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class ContactsDetailActivity extends BaseActivity<ActivityContactsDetailBinding> {

//    private SearchContatsBean searchContatsBean;

    private UserInfoBean userInfoBean;

    private ContactsDetailType contactsDetailType = ContactsDetailType.NORMAL;

    private String userId;

    private String groupId;

    private int admin;

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
//        searchContatsBean = (SearchContatsBean) getIntent().getExtras().get(MKey.DATA);
        userId = getIntent().getStringExtra(MKey.USER_ID);
        contactsDetailType = (ContactsDetailType) getIntent().getExtras().get(MKey.TYPE);

        if (contactsDetailType == ContactsDetailType.GROUP) {
            admin = (int) getIntent().getExtras().get(MKey.ADRID);
            if (admin > 1) {
                binding.sbBanned.setVisibility(View.VISIBLE);
                binding.tvClose.setVisibility(View.VISIBLE);
            }
            binding.tvAddgroupType.setVisibility(View.VISIBLE);
            groupId = getIntent().getStringExtra(MKey.ID);


        } else {
            binding.tvAddgroupType.setVisibility(View.GONE);
        }


        userInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userInfoBean != null) {
            userInfo();
        }
    }

    private void userInfo() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("id", userId);
        if (!StringUtils.isEmpty(groupId)) {
            params.put("group_id", groupId);
        }
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
                dismissLoadingDialog();
                userInfoBean = baseBean.getData();
                binding.tvName.setText(userInfoBean.getNick_name());
                ImageUtils.showAvatar(userInfoBean.getImg(), binding.ivAvatar);
                binding.tvNo.setText("简易号" + userInfoBean.getAccount_id());
//                查看用户的信息对当前用户的关系; 0处于非好友;  2是处于好友;
                if (userInfoBean.getStatus() == 0) {
                    binding.tvSetRemark.setVisibility(View.GONE);
                    binding.tvAdd.setText("添加好友");
                    binding.tvAdd.setEnabled(true);
                } else if (userInfoBean.getStatus() == 1) {
                    binding.tvSetRemark.setVisibility(View.GONE);
                    binding.tvAdd.setText("添加好友");
                    binding.tvAdd.setEnabled(true);
                } else if (userInfoBean.getStatus() == 2) {
                    binding.tvAdd.setText("发消息");
                    binding.tvSetRemark.setVisibility(View.VISIBLE);
                    binding.tvDel.setVisibility(View.VISIBLE);
                }

                if (baseBean.getData().getBlack() == 1) {
                    binding.sbAddBlack.setCheckedNoEvent(true);
                }

                if (contactsDetailType == ContactsDetailType.GROUP) {
                    if (baseBean.getData().getBannet_chat() == 1) {
                        binding.sbBanned.setCheckedNoEvent(true);
                    }
                    binding.sbAddBlack.setVisibility(View.VISIBLE);
                }

                binding.tvAddgroupType.setText("进群方式       " + userInfoBean.getSource());


                UserInfo uuu = new UserInfo(userId, baseBean.getData().getNick_name(), Uri.parse(baseBean.getData().getImg()));
                RongIM.getInstance().refreshUserInfoCache(uuu);
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    @Override
    public void initListener() {
        binding.tvAdd.setOnClickListener((v) -> {
            if (userInfoBean.getStatus() == 2) {
                RongUtils.toChat(userId, userInfoBean.getNick_name());
            } else {
                add();
            }

        });
        binding.sbAddBlack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    addBlack();
                } else {
                    removeBlack();
                }
            }
        });
        binding.sbBanned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                upAdmInavite();
            }
        });
        binding.tvDel.setOnClickListener((v) -> {
            delFriend();
        });
        binding.tvSetRemark.setOnClickListener(view -> {
            IntentUtils.intent2AlterTextActivityByContcactsDetail(AlterTextType.FRIEND_NAME, binding.tvName.getText().toString(), userId);
        });
        binding.tvTousu.setOnClickListener((v) -> {
            IntentUtils.intent2ComplaintActivity(ComplaintType.USER, userId);
        });
    }


    public void apply(String id) {
        params = new HashMap<>();
        params.put("my_id", id);
        HttpClient.Builder.getServer().fConsent(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void addBlack() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("friend_id", userId);
        HttpClient.Builder.getServer().fAddBlack(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
//                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void removeBlack() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("friend_id", userId);
        HttpClient.Builder.getServer().fReBla(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
//                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void delFriend() {
        params.put("friend_id", userId);
        HttpClient.Builder.getServer().fDel(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE,userId,null);
                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
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
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void upAdmInavite() {
        params = new HashMap<>();
        params.put("group_id", groupId);
        params.put("bannet_chat", binding.sbBanned.isChecked() ? 1 : 0);
        params.put("id", userId);
        HttpClient.Builder.getServer().upAdmInavite(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
//                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
//                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void add() {
        loadingDialog.show();
        params = new HashMap<>();
        params.put("friend_id", userId);
        params.put("notes", "");
        HttpClient.Builder.getServer().fSendMsg(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Boolean>() {
            @Override
            public void onSuccess(BaseBean<Boolean> baseBean) {
                loadingDialog.dismiss();
                tipDialog = DialogUtils.getSuclDialog(ContactsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
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
