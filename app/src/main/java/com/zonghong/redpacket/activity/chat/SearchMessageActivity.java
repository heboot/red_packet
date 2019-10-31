package com.zonghong.redpacket.activity.chat;

import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.adapter.message.SearchMessageAdapter;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.SearchMessageType;
import com.zonghong.redpacket.databinding.ActivitySearchMessageBinding;
import com.zonghong.redpacket.databinding.LayoutSearchBinding;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class SearchMessageActivity extends BaseActivity<ActivitySearchMessageBinding> {


    private SearchMessageType searchMessageType;

    private String targetId, targetName;

    private SearchMessageAdapter searchMessageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_message;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("查找聊天记录");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void initData() {
        searchMessageType = (SearchMessageType) getIntent().getExtras().get(MKey.TYPE);
        targetId = getIntent().getStringExtra(MKey.ID);
        targetName = getIntent().getStringExtra(MKey.NAME);
        searchMessageAdapter = new SearchMessageAdapter(new ArrayList<>(), searchMessageType, targetId, targetName);
        searchMessageAdapter.addHeaderView(getSearchView());
        binding.rvList.setAdapter(searchMessageAdapter);

    }

    @Override
    public void initListener() {


    }

    private List<Message> resultMessageList = new ArrayList<>();

    private View getSearchView() {
        LayoutSearchBinding layoutSearchBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_search, null, false);
        layoutSearchBinding.etKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (StringUtils.isEmpty(charSequence) || charSequence.length() == 0) {
                    resultMessageList.clear();
                    searchMessageAdapter.setNewData(resultMessageList);
                    searchMessageAdapter.notifyDataSetChanged();
                    return;
                }
                resultMessageList.clear();
                RongIM.getInstance().getHistoryMessages(searchMessageType == SearchMessageType.GROUP ? Conversation.ConversationType.GROUP : Conversation.ConversationType.PRIVATE, targetId, "RC:TxtMsg", -1, 100, new RongIMClient.ResultCallback<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> messages) {

                        if(messages != null){
                            for (Message message : messages) {
                                TextMessage textMessage = new TextMessage(message.getContent().encode());
                                String content = textMessage.getContent();
                                if (content.indexOf(charSequence.toString()) > -1) {
                                    resultMessageList.add(message);
                                }
                            }

                            searchMessageAdapter.setNewData(resultMessageList);
                            searchMessageAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        layoutSearchBinding.getRoot().setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.y65)));


        return layoutSearchBinding.getRoot();
    }
}
