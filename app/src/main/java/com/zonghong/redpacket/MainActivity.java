package com.zonghong.redpacket;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.waw.hr.mutils.event.GroupEvent;
import com.waw.hr.mutils.event.MessageEvent;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.activity.chat.MyGroupListActivity;
import com.zonghong.redpacket.adapter.group.MyGroupAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityMainBinding;
import com.zonghong.redpacket.fragment.ContactsFragment;
import com.zonghong.redpacket.fragment.MConversationListFragment;
import com.zonghong.redpacket.fragment.MyFragment;
import com.zonghong.redpacket.fragment.WalletFragment;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.rong.CustomBiaoqingMessage;
import com.zonghong.redpacket.rong.DeleteGroupMessageEventMessage;
import com.zonghong.redpacket.rong.GroupTipEventMessage;
import com.zonghong.redpacket.rong.RedPackageChatMessage;
import com.zonghong.redpacket.rong.RedPackageChatOpenMessage;
import com.zonghong.redpacket.rong.RedPackageZhuanZhangChatMessage;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.rong.UpdateGroupInfoEventMessage;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.NotificationUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private MConversationListFragment mConversationListFragment;

    private ContactsFragment contactsFragment = ContactsFragment.newInstance();

    private WalletFragment walletFragment = WalletFragment.newInstance();

    private MyFragment myFragment = MyFragment.newInstance();

    private ISupportFragment currentFragment;

    private Handler mHandler;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        mConversationListFragment = MConversationListFragment.newInstance();
        mDelegate.loadMultipleRootFragment(binding.flytContainer.getId(), 0, mConversationListFragment, contactsFragment, walletFragment, myFragment);
        currentFragment = mConversationListFragment;
        binding.ivMsg.setSelected(true);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull android.os.Message msg) {
                contactsNumber();
                sendEmptyMessageDelayed(1001, 5000);
            }
        };
        mHandler.sendEmptyMessageDelayed(1001, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RongUtils.checkUnread();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    public void initListener() {
        binding.llytMsg.setOnClickListener((v) -> {
            mDelegate.showHideFragment(mConversationListFragment, currentFragment);
            currentFragment = mConversationListFragment;
            binding.ivMsg.setSelected(true);
            binding.ivContacts.setSelected(false);
            binding.ivWallet.setSelected(false);
            binding.ivMy.setSelected(false);
        });
        binding.llytContacts.setOnClickListener((v) -> {
            mDelegate.showHideFragment(contactsFragment, currentFragment);
            currentFragment = contactsFragment;
            binding.ivMsg.setSelected(false);
            binding.ivContacts.setSelected(true);
            binding.ivWallet.setSelected(false);
            binding.ivMy.setSelected(false);
        });
        binding.llytWallet.setOnClickListener((v) -> {
            mDelegate.showHideFragment(walletFragment, currentFragment);
            currentFragment = walletFragment;
            binding.ivMsg.setSelected(false);
            binding.ivContacts.setSelected(false);
            binding.ivWallet.setSelected(true);
            binding.ivMy.setSelected(false);

        });
        binding.llytMy.setOnClickListener((v) -> {
            mDelegate.showHideFragment(myFragment, currentFragment);
            currentFragment = myFragment;
            binding.ivMsg.setSelected(false);
            binding.ivContacts.setSelected(false);
            binding.ivWallet.setSelected(false);
            binding.ivMy.setSelected(true);
        });

        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
    }


    private class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param left    剩余未拉取消息数目。
         * @return 收到消息是否处理完成，true 表示自己处理铃声和后台通知，false 走融云默认处理方式。
         */
        @Override
        public boolean onReceived(Message message, int left) {
            RongUtils.checkUnread();
            if (message.getContent() instanceof DeleteGroupMessageEventMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RongUtils.deleteGroupMessage(new DeleteGroupMessageEventMessage(message.getContent().encode()).getGroupId());
                    }
                });

            } else if (message.getContent() instanceof UpdateGroupInfoEventMessage) {
                list(new UpdateGroupInfoEventMessage(message.getContent().encode()).getContent());
            } else {

                //开发者根据自己需求自行处理
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!MAPP.isShow) {
                            String content;
                            if (message.getContent() instanceof ImageMessage) {
                                content = "[图片消息]";
                            } else if (message.getContent() instanceof VoiceMessage) {
                                content = "[语音消息]";
                            } else if (message.getContent() instanceof TextMessage) {
                                content = new TextMessage(message.getContent().encode()).getContent();
                            } else if (message.getContent() instanceof RedPackageChatOpenMessage) {
                                content = "[新消息]";
//                                return;
                            } else if (message.getContent() instanceof RedPackageChatMessage) {
                                content = "[红包消息]";
                            } else if (message.getContent() instanceof RedPackageZhuanZhangChatMessage) {
                                content = "[转账消息]";
                            } else if (message.getContent() instanceof CustomBiaoqingMessage) {
                                content = "[表情]";
                            } else {
                                content = "新消息";
                            }

                            String name = "消息通知";

                            if (message.getContent() != null && message.getContent().getUserInfo() != null) {
                                name = message.getContent().getUserInfo().getName();
                            }
                            NotificationUtils.showNoti(Integer.parseInt(message.getTargetId()), name, content);
                        }

                    }
                });
            }

            return true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent.GET_UNREAD_MESSAGE_NUM_EVENT event) {
        if (event.getUnreadNum() > 0) {
            binding.tvUnread.setText(event.getUnreadNum() + "");
            binding.tvUnread.setVisibility(View.VISIBLE);
        } else {
            binding.tvUnread.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(1001);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent.LOGOUT_EVENT event) {
        finish();
    }

    private void list(String msg) {
        params = new HashMap<>();
        params.put("group_id", msg);
        HttpClient.Builder.getServer().gIndex(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<MyGroupBean>>() {
            @Override
            public void onSuccess(BaseBean<List<MyGroupBean>> baseBean) {
                Group userInfo = new Group(msg, baseBean.getData().get(0).getTitle(), Uri.parse(baseBean.getData().get(0).getImg()));
                RongIM.getInstance().refreshGroupInfoCache(userInfo);
                EventBus.getDefault().post(new GroupEvent.ALTER_GROUP_NAME_EVENT(baseBean.getData().get(0).getTitle()));
            }

            @Override
            public void onError(BaseBean<List<MyGroupBean>> baseBean) {
            }
        });
    }

    private void contactsNumber() {
        params = new HashMap<>();
        HttpClient.Builder.getServer().fnoCTotal(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {
                if (baseBean.getData() != null) {
                    if (baseBean.getData().get("number") != null) {
                        if (((double) baseBean.getData().get("number")) > 0) {
                            binding.tvContactsUnread.setVisibility(View.VISIBLE);
                            binding.tvContactsUnread.setText((int) ((double) baseBean.getData().get("number")) + "");
                        }else{
                            binding.tvContactsUnread.setVisibility(View.GONE);
                        }
                    }else{
                        binding.tvContactsUnread.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
            }
        });
    }

}
