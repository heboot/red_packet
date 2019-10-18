package com.zonghong.redpacket.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.SearchDialogueListBean;
import com.waw.hr.mutils.event.GroupEvent;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.contacts.ChooseContactsActivity;
import com.zonghong.redpacket.activity.contacts.SearchContactsActivity;
import com.zonghong.redpacket.adapter.message.SearchConversationAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.common.ContactsDetailType;
import com.zonghong.redpacket.databinding.FragmentConversationBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;
import com.zonghong.redpacket.utils.SearchUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import pub.devrel.easypermissions.EasyPermissions;

public class MConversationListFragment extends BaseFragment<FragmentConversationBinding> {

    private ConversationListFragment conversationListFragment;

    private int REQUEST_CODE = 988;
    private final int RC_CONTACTS = 999;
    private Consumer<List<Conversation>> listConsumer;

    private QMUIDialog permissionDialog;

    private SearchConversationAdapter searchConversationAdapter;

    public static MConversationListFragment newInstance() {
        Bundle args = new Bundle();
        MConversationListFragment fragment = new MConversationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_conversation;
    }

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
        binding.rvList.setLayoutManager(new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false));
        if (conversationListFragment == null) {
            conversationListFragment = new ConversationListFragment();
        }

        Uri uri = Uri.parse("rong://" + _mActivity.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();


        conversationListFragment.setUri(uri);

        getFragmentManager().beginTransaction().add(R.id.llyt_container, conversationListFragment).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void initData() {
        listConsumer = new Consumer<List<Conversation>>() {
            @Override
            public void accept(List<Conversation> conversations) throws Exception {
                if (conversations != null && conversations.size() > 0) {
                    searchConversationAdapter = new SearchConversationAdapter(conversations);
                    binding.rvList.setAdapter(searchConversationAdapter);
                }
            }
        };
        permissionDialog = new QMUIDialog.MessageDialogBuilder(_mActivity)
                .setMessage("系统需要获相机权限")
                .setTitle("提醒")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        permissionDialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String[] perms = {Manifest.permission.CAMERA};
                            EasyPermissions.requestPermissions(_mActivity, "系统需要获相机权限",
                                    RC_CONTACTS, perms);
                        } else {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", MAPP.mapp.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            MAPP.mapp.startActivity(intent);
                        }
                    }
                })
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        permissionDialog.dismiss();
                    }
                })
                .create();

    }

    @Override
    public void initListener() {
        binding.tvRight.setOnClickListener((v) -> {
            if (binding.includeMsgMore.getRoot().getVisibility() == View.GONE) {
                binding.includeMsgMore.getRoot().setVisibility(getView().VISIBLE);
            } else {
                binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
            }
//            MsgMorePopView msgMorePopView = new MsgMorePopView();
//            msgMorePopView.showAsDropDown(v);
        });
        binding.includeMsgMore.llytContainer.setOnClickListener((v) -> {
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
        });
        binding.includeMsgMore.tvNewGroup.setOnClickListener((v) -> {
            IntentUtils.doIntent(ChooseContactsActivity.class);
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
        });
//        binding.includeSearch.getRoot().setOnClickListener((v) -> {
//            IntentUtils.doIntent(SearchContactsActivity.class);
//            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
//        });
        binding.includeMsgMore.tvAddFriend.setOnClickListener((v) -> {
            IntentUtils.doIntent(SearchContactsActivity.class);
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
        });
        binding.includeMsgMore.tvScan.setOnClickListener((v) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!EasyPermissions.hasPermissions(_mActivity, Manifest.permission.CAMERA)) {
                    permissionDialog.show();
                    return;
                }
            }
            binding.includeMsgMore.getRoot().setVisibility(getView().GONE);
            Intent intent = new Intent(_mActivity, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        binding.includeSearch.etKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence) || charSequence.length() == 0) {
                    binding.rvList.setVisibility(View.GONE);
                    return;
                }
                getConversation(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String uid, gid;


    private void getConversation(String key) {
        binding.rvList.setVisibility(View.VISIBLE);
        gid = "";
        uid = "";
        //先获取本地会话列表
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations == null || conversations.size() == 0) {
                    return;
                }
                for (Conversation conversation : conversations) {
                    if (conversation.getConversationType() == Conversation.ConversationType.GROUP) {
                        gid = gid + conversation.getTargetId() + ",";
                    } else if (conversation.getConversationType() == Conversation.ConversationType.PRIVATE) {
                        uid = uid + conversation.getTargetId() + ",";
                    }

                    doSearch(key);
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GroupEvent.DELETE_GROUP_MESSAGE_EVENT event) {
        RongIM.getInstance().deleteMessages(Conversation.ConversationType.GROUP, event.getGroupId(), new RongIMClient.ResultCallback<Boolean>() {
            //            @Override
            public void onSuccess(Boolean aBoolean) {
//                Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.GROUP};
//
//
//                conversationListFragment.getConversationList(conversationTypes, new IHistoryDataResultCallback<List<Conversation>>() {
//                    @Override
//                    public void onResult(List<Conversation> conversations) {
//                        for (Conversation conversation : conversations) {
//                            if (conversation.getTargetId().equals(event.getGroupId())) {
////                        conversation.setLatestMessage(null);
//                                conversation.setUnreadMessageCount(0);
//                                conversationListFragment.updateListItem(UIConversation.obtain(_mActivity, conversation, false));
//                            }
//                        }
//
////                conversationListFragment.onUnreadCountChanged();
////                conversationListFragment.onLoad();
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                }, false);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                ToastUtils.show(MAPP.mapp, JSON.toJSONString(errorCode));
            }
        });


    }

    private void doSearch(String key) {
        params = new HashMap<>();
        params.put("str", key);
        params.put("uid", uid);
        params.put("gid", gid);

        HttpClient.Builder.getServer().searchDialogueList(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<SearchDialogueListBean>() {
            @Override
            public void onSuccess(BaseBean<SearchDialogueListBean> baseBean) {
                SearchUtils.getUserConversationList(baseBean.getData().getUid(), baseBean.getData().getGid(), listConsumer);

            }

            @Override
            public void onError(BaseBean<SearchDialogueListBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.indexOf("u") > -1) {
                        IntentUtils.intent2ContactsDetailActivity(result.substring(1, result.length()), ContactsDetailType.NORMAL);
                    } else if (result.indexOf("g") > -1) {
                        addGroup(result.substring(1, result.length()));
//                        IntentUtils.intent2ContactsDetailActivity(result.substring(1, result.length()), ContactsDetailType.NORMAL);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MAPP.mapp, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void addGroup(String groupId) {
        params = new HashMap<>();
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().gAddUser(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
