package com.zonghong.redpacket;

import android.content.Intent;
import android.view.View;

import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.event.MessageEvent;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityMainBinding;
import com.zonghong.redpacket.fragment.ContactsFragment;
import com.zonghong.redpacket.fragment.MConversationListFragment;
import com.zonghong.redpacket.fragment.MyFragment;
import com.zonghong.redpacket.fragment.WalletFragment;
import com.zonghong.redpacket.rong.RongUtils;
import com.zonghong.redpacket.utils.NotificationUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import io.rong.push.RongPushClient;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private MConversationListFragment mConversationListFragment;

    private ContactsFragment contactsFragment = ContactsFragment.newInstance();

    private WalletFragment walletFragment = WalletFragment.newInstance();

    private MyFragment myFragment = MyFragment.newInstance();

    private ISupportFragment currentFragment;


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
        getPushMessage(intent);
    }

    /**
     * Android push 消息
     */
    private void getPushMessage(Intent intent) {

        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //该条推送消息的内容。
            String content = intent.getData().getQueryParameter("pushContent");
            //标识该推送消息的唯一 Id。
            String id = intent.getData().getQueryParameter("pushId");
            //用户自定义参数 json 格式，解析后用户可根据自己定义的 Key 、Value 值进行业务处理。
            String extra = intent.getData().getQueryParameter("extra");
            //统计通知栏点击事件.
            RongPushClient.recordNotificationEvent(id);
//            Log.d("TestPushActivity", "--content:" + content + "--id:" + id + "---extra:" + extra);
        }
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
            //开发者根据自己需求自行处理
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!MAPP.isShow) {
                        String content;
                        if (message.getContent() instanceof ImageMessage) {
                            content = "图片消息";
                        } else if (message.getContent() instanceof VoiceMessage) {
                            content = "语音消息";
                        } else if (message.getContent() instanceof TextMessage) {
                            content = new TextMessage(message.getContent().encode()).getContent();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent.LOGOUT_EVENT event) {
        finish();
    }


}
