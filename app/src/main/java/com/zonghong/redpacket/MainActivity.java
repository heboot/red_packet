package com.zonghong.redpacket;

import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityMainBinding;
import com.zonghong.redpacket.fragment.ContactsFragment;
import com.zonghong.redpacket.fragment.MConversationListFragment;
import com.zonghong.redpacket.fragment.MyFragment;
import com.zonghong.redpacket.fragment.WalletFragment;

import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private MConversationListFragment mConversationListFragment = MConversationListFragment.newInstance();

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

    }

    @Override
    public void initData() {
        mDelegate.loadMultipleRootFragment(binding.flytContainer.getId(), 0, mConversationListFragment, contactsFragment, walletFragment, myFragment);
        currentFragment = mConversationListFragment;
        binding.ivMsg.setSelected(true);
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
    }
}
